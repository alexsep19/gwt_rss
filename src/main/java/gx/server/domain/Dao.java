package gx.server.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.commons.lang3.ArrayUtils;

import rolo.Role;
import rolo.State;
import rolo.Urro;
import rolo.User;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;

import jpaRss.Item;
import jpaRss.Mail;
import jpaRss.Url;
import jvRss2obj.rss.Feed;
import jvRss2obj.rss.FeedMessage;
import jvRss2obj.rss.LostFilmParser;

@Stateless(name="Dao")
//@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Dao {
	private static final EntityManagerFactory emfRss = Persistence.createEntityManagerFactory("jpaRss");
	private static final String  sqlSelFrom = "select t from ";
	public static final String SESSION_KEYS = "Keys";
	public static final String SK_IS_TIMER_ON = "is_rss_on";
//	public static final String KEY_WEB_ROLES = "Wrole";
//	public static final String KEY_UI = "Uid";
//	public static final String KEY_USER = "User";
	public static final String KEY_URRO = "Urro";
	
	@Resource
	UserTransaction tr;
	@PersistenceContext(unitName = "jpaRss")
	private EntityManager em;
	@Resource(mappedName = "java:jboss/mail/Gmail") 
	private Session mailSession;

//===================== User Info =============
//	private void setSessionAttr(HttpServletRequest sreq, HashMap<String,String> m){
//		m.put(KEY_LOGIN, login);
//		m.put(KEY_WEB_ROLES, web_role);
//		m.put(KEY_UI, Long.toString(userId));
//		sreq.getSession().setAttribute(SESSION_KEYS, m);
//	}
//	final static String sql_user = "Select u.id, r.code from "+User.class.getSimpleName()+" u,"+Urro.class.getSimpleName()+" o,"+Role.class.getSimpleName()+" r "+
//	                               "where o.user=u and o.role=r and u.name=:1";
//	final static String sql_user = "Select u from "+User.class.getSimpleName()+" u LEFT JOIN FETCH u.urros ur LEFT JOIN FETCH ur.role r where u.name=?1";
//	final static String sql_user = "Select u from "+User.class.getSimpleName()+" u where u.name=?1";
	final static String sql_urro = "Select o from "+Urro.class.getSimpleName()+" o where o.user.name=?1";
	public List<Urro> getUserInfo() throws Exception{
		String login = "alex";
//		String roles = "";
		HashMap<String,Object> m = (HashMap<String,Object>)RequestFactoryServlet.getThreadLocalRequest().getSession().getAttribute(SESSION_KEYS);
		if (m == null){
		    m = new HashMap<String,Object>();
		    List<Urro> u = em.createQuery(sql_urro).setParameter(1, login).getResultList();
		    if (u.isEmpty()) throw new Exception("Юзер "+login+" не найден или нет ролей");
		    m.put(KEY_URRO, u);
		    RequestFactoryServlet.getThreadLocalRequest().getSession().setAttribute(SESSION_KEYS, m);
//		    System.out.println("getRssOn = "+getRssOn());
//		    System.out.println("u.get(0).getUrros() = "+ ((List<Urro>)m.get(KEY_URRO)).size());
//		    System.out.println("u.get(0).getUser().getName() = "+u.get(0).getRole().getCode());
//		    for(Urro it: u.get(0).getUrros()){
//		      roles += it.getRole().getCode().trim() + ";";
//		    }
//		    m.put(KEY_LOGIN, u.get(0).getName());
//		    m.put(KEY_WEB_ROLES, roles);
//		    m.put(KEY_UI, u.get(0).getId().toString());
		}
//	 return Arrays.asList(new String[]{m.get(KEY_LOGIN), m.get(KEY_WEB_ROLES)});
	 return (List<Urro>)m.get(KEY_URRO);
	}
	
	//===================== timer on(Y) off(N) =============	
	public void setTimerState(boolean turnOn){
		if (turnOn){
		  ArrayList<String> titles = new ArrayList<String>();
		  List<Mail> m = em.createQuery(new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t").toString()).getResultList();
		  for(Mail mit: m){
			 String mailTo = mit.getUrl();
			 for(Url uit: mit.getUrls()){
				for(Item iit: uit.getItems()) titles.add(iit.getTitle());
				LostFilmParser parser = new LostFilmParser(uit.getUrl(), titles);
		        Feed feed = parser.readFeed();
//		        System.out.println(feed);
		        for (FeedMessage message : feed.getMessages()) {
//		            System.out.println("title = "+message.getTitle()+ "; description = "+message.getDescription()+"; pubDate = "+message.getPubDate());
		            sendMail(message.getTitle(), mailTo, message.getItem() + " "  + message.getDescription());
  	            }
		        titles.clear();
			 }
		   }
        }
		
		fixTimerState(turnOn);
		System.out.println("turnOn = "+turnOn);
		
	}
	
	public boolean getTimerState(){
		Object turnOn = RequestFactoryServlet.getThreadLocalRequest().getServletContext().getAttribute(SK_IS_TIMER_ON);
		if (turnOn == null) turnOn = fixTimerState(false);
		return turnOn.toString().equals("Y");
	}
	
	//Y/N
	private String fixTimerState(boolean TurnOn){
		String turnOn = TurnOn ? "Y": "N";
		List<State> s = em.createQuery(new StringBuilder(sqlSelFrom).append(State.class.getSimpleName()).append(" t where name=?1").toString()).setParameter(1, SK_IS_TIMER_ON).getResultList();
		if (s.isEmpty()){
//		  turnOn = "N";
		  State o = new State();
		  o.setName(SK_IS_TIMER_ON);
		  o.setValue(turnOn.toString());
		  try {
			tr.begin();
			em.merge(o);
			tr.commit();
		  } catch (Exception e) {
		      try {tr.rollback();}catch(Exception ee){}
	  	      e.printStackTrace();
		      throw new RuntimeException(e.getMessage());
		  }
		}else{
		  turnOn = s.get(0).getValue();
		}
		RequestFactoryServlet.getThreadLocalRequest().getServletContext().setAttribute(SK_IS_TIMER_ON, turnOn);
      return turnOn;
	}
	
	public boolean sendMail(String mess, String to, String subj){
//		System.out.println("mailSession = "+mailSession);
	try {
	        MimeMessage message = new MimeMessage(mailSession);
//	        Address[] addr = new InternetAddress[]{new InternetAddress(to)};
//	        message.setFrom( new InternetAddress( "alexsep19@gmail.com" ) );
//	        message.setText(mess);
//	        String[] toArr = to.split(",");
//	        ArrayList<InternetAddress> ia = new ArrayList<InternetAddress>();
//	        for(String it: toArr){
//	         if (!it.trim().isEmpty()) ia.add(new InternetAddress(it));
//	        }
//	        message.setRecipients(RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});
	        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject(subj);
	        message.setContent(mess, "text/plain; charset=UTF-8");
	        Transport.send(message);
//	        System.out.println("return true");
			return true;
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	       return false;
	    }
	}

	//=============================================================
	   public List<Role> getAllRole(){
		   return em.createQuery(new StringBuilder(sqlSelFrom).append(Role.class.getSimpleName()).append(" t").toString()).getResultList();
	   }
	   public List<User> getAllUser(){
		   return em.createQuery(new StringBuilder(sqlSelFrom).append(User.class.getSimpleName()).append(" t").toString()).getResultList();
	   }
	//=============================================================


	   public RoleLoadResultBean getListRole(List<SortInfoBean> sortInfo){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(Role.class.getSimpleName()).append(" t");
	       StringBuilder order = new StringBuilder(sortInfo.isEmpty()?" ":" order by");
//	       String orderIt = "";
	       try {
	           for(SortInfo it:sortInfo){
	             order = order.append(" t.").append(it.getSortField()).append(" ").append(it.getSortDir()).append(",");
	            }
	            order.setCharAt( order.length()-1, ' ');
	         return new RoleLoadResultBean(em.createQuery(sql.append(order).toString()).getResultList());
	       }catch (RuntimeException re) {
	         re.printStackTrace();
	       throw re;
	       }
	   }

	   public UserLoadResultBean getListUser(List<SortInfoBean> sortInfo){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(User.class.getSimpleName()).append(" t");
	       StringBuilder order = new StringBuilder(sortInfo.isEmpty()?" ":" order by");
//	       String orderIt = "";
	       try {
	           for(SortInfo it:sortInfo){
	             order = order.append(" t.").append(it.getSortField()).append(" ").append(it.getSortDir()).append(",");
	            }
	            order.setCharAt( order.length()-1, ' ');
	         return new UserLoadResultBean(em.createQuery(sql.append(order).toString()).getResultList());
	       }catch (RuntimeException re) {
	         re.printStackTrace();
	       throw re;
	       }
	   }

	   public UrroLoadResultBean getListUrro(List<SortInfoBean> sortInfo){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(Urro.class.getSimpleName()).append(" t");
	       StringBuilder order = new StringBuilder(sortInfo.isEmpty()?" ":" order by");
//	       String orderIt = "";
	       try {
//	           for(SortInfo it:sortInfo){
//	             order = order.append(" t.").append(it.getSortField()).append(" ").append(it.getSortDir()).append(",");
//	            }
//	            order.setCharAt( order.length()-1, ' ');
	         return new UrroLoadResultBean(em.createQuery(sql.append(order).toString()).getResultList());
	       }catch (RuntimeException re) {
	         re.printStackTrace();
	       throw re;
	       }
	   }

	   public MailLoadResultBean getListMail(List<SortInfoBean> sortInfo, User user){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t JOIN t.user u where u = ?1");
	       StringBuilder order = new StringBuilder(sortInfo.isEmpty()?" ":" order by");
//	       String orderIt = "";
	       try {
	           for(SortInfo it:sortInfo){
	             order = order.append(" t.").append(it.getSortField()).append(" ").append(it.getSortDir()).append(",");
	            }
	            order.setCharAt( order.length()-1, ' ');
//	            List<Mail> r = em.createQuery(sql.append(order).toString()).getResultList();
//	            System.out.println(sql.append(order).toString());
//	            System.out.println("r.size() = " + r.size());
//	            return new MailLoadResultBean(r);
	         return new MailLoadResultBean(em.createQuery(sql.append(order).toString()).setParameter(1, user).getResultList());
	       }catch (RuntimeException re) {
	         re.printStackTrace();
	       throw re;
	       }
	   }
	   public UrlLoadResultBean getListUrl(List<SortInfoBean> sortInfo, Mail mail){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(Url.class.getSimpleName()).append(" t").append(" where t.mail=?1");
	       StringBuilder order = new StringBuilder(sortInfo.isEmpty()?" ":" order by");
//	       String orderIt = "";
	       try {
	           for(SortInfo it:sortInfo){
	             order = order.append(" t.").append(it.getSortField()).append(" ").append(it.getSortDir()).append(",");
	            }
	            order.setCharAt( order.length()-1, ' ');
//	            List<Mail> r = em.createQuery(sql.append(order).toString()).getResultList();
//	            System.out.println("mail = "+mail.getName());
//	            System.out.println("r.size() = " + r.size());
//	            return new MailLoadResultBean(r);
	         return new UrlLoadResultBean(em.createQuery(sql.append(order).toString()).setParameter(1, mail).getResultList());
	       }catch (RuntimeException re) {
	         re.printStackTrace();
	       throw re;
	       }
	   }

	   public ItemLoadResultBean getListItem(List<SortInfoBean> sortInfo, Mail mail, Url url){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(Item.class.getSimpleName()).append(" t").append(" where t.mail=?1 and t.url=?2");
	       StringBuilder order = new StringBuilder(sortInfo.isEmpty()?" ":" order by");
//	       String orderIt = "";
	       try {
	           for(SortInfo it:sortInfo){
	             order = order.append(" t.").append(it.getSortField()).append(" ").append(it.getSortDir()).append(",");
	            }
	            order.setCharAt( order.length()-1, ' ');
//	            List<Mail> r = em.createQuery(sql.append(order).toString()).getResultList();
//	            System.out.println(sql.append(order).toString());
//	            System.out.println("r.size() = " + r.size());
//	            return new MailLoadResultBean(r);
	         return new ItemLoadResultBean(em.createQuery(sql.append(order).toString()).setParameter(1, mail).setParameter(2, url).getResultList());
	       }catch (RuntimeException re) {
	         re.printStackTrace();
	       throw re;
	       }
	   }

//=============================================================

	   public void remov(Object rec) throws RuntimeException{
	       try {
		      tr.begin();
		      em.remove(em.find(rec.getClass(), Integer.parseInt(rec.toString() )));
//		      em.flush();
		      tr.commit();
		    }catch(Exception e){
		    	try {tr.rollback();}catch(Exception ee){}
	   	      e.printStackTrace();
		      throw new RuntimeException(e.getMessage());
		    } 
	   }

	   public void merg(Object rec){
	       try {
	    	  tr.begin();
		      em.merge(rec);
//	    	  em.merge(em.find(rec.getClass(), Integer.parseInt(rec.toString() )));
		      tr.commit();
		    }catch(Exception e){
		      try {tr.rollback();}catch(Exception ee){}
	  	      e.printStackTrace();
		      throw new RuntimeException(e.getMessage());
		    }
		   }
	   public Object findObject(Class<?> cl, Integer id) {
	       if (id == null) return null; 
	       try {
		     return em.find(cl, id);
	       }catch(RuntimeException e){
		     e.printStackTrace();
//		     throw e;
	       } 
	     return null;
  }

}
