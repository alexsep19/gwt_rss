package gx.server.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

import rolo.Role;
import rolo.Urro;
import rolo.User;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;

import jpaRss.Item;
import jpaRss.Mail;
import jpaRss.Url;

@Stateless(name="Dao")
//@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Dao {
	private static final EntityManagerFactory emfRss = Persistence.createEntityManagerFactory("jpaRss");
	private static final String  sqlSelFrom = "select t from ";
	public static final String SESSION_KEYS = "Keys";
	public static final String KEY_LOGIN = "Login";
	public static final String KEY_WEB_ROLES = "Wrole";
	public static final String KEY_UI = "Uid";
	
	@Resource
	UserTransaction tr;
	@PersistenceContext(unitName = "jpaRss")
	private EntityManager em;
	
//===================== User Info =============
//	private void setSessionAttr(HttpServletRequest sreq, HashMap<String,String> m){
//		m.put(KEY_LOGIN, login);
//		m.put(KEY_WEB_ROLES, web_role);
//		m.put(KEY_UI, Long.toString(userId));
//		sreq.getSession().setAttribute(SESSION_KEYS, m);
//	}
//	final static String sql_user = "Select u.id, r.code from "+User.class.getSimpleName()+" u,"+Urro.class.getSimpleName()+" o,"+Role.class.getSimpleName()+" r "+
//	                               "where o.user=u and o.role=r and u.name=:1";
	final static String sql_user = "Select u from "+User.class.getSimpleName()+" u LEFT JOIN FETCH u.urros ur LEFT JOIN FETCH ur.role r where u.name=?1";
	public List<String> getUserInfo() throws Exception{
		String login = "admin";
		String roles = "";
		HashMap<String,String> m = (HashMap<String,String>)RequestFactoryServlet.getThreadLocalRequest().getSession().getAttribute(SESSION_KEYS);
		if (m == null){
		    m = new HashMap<String,String>();
		    List<User> u = em.createQuery(sql_user).setParameter(1, login).getResultList();
		    if (u.isEmpty()) throw new Exception("Юзер "+login+" не найден");
		    for(Urro it: u.get(0).getUrros()){
		      roles += it.getRole().getCode().trim() + ";";
		    }
		    m.put(KEY_LOGIN, u.get(0).getName());
		    m.put(KEY_WEB_ROLES, roles);
		    m.put(KEY_UI, u.get(0).getId().toString());
		}
	 return Arrays.asList(new String[]{m.get(KEY_LOGIN), m.get(KEY_WEB_ROLES)});
	}
	
//	public List<Mail> getAllMail(){
//		EntityManager em = emSimple();
//		em.createNamedQuery("Category.findAll", Category.class).getResultList();
//		List<Mail> l = emRss().createQuery(new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t").toString(), Mail.class).getResultList();
//		System.out.println("++++++++++++++++++++++++++++"+l.get(0).getName());
//		return l;
//		return emRss().createQuery(new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t").toString(), Mail.class).getResultList();
//	}
//	public Category findCategory(Integer id){
//		   
//
//	}
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

	   public MailLoadResultBean getListMail(List<SortInfoBean> sortInfo){
	       StringBuilder sql = new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t");
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
	         return new MailLoadResultBean(em.createQuery(sql.append(order).toString()).getResultList());
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
		      if (em.getTransaction().isActive()) em.getTransaction().rollback();
	   	      e.printStackTrace();
		      throw new RuntimeException(e.getMessage());
		    } 
	   }

	   public void merg(Object rec){
	       try {
	    	  tr.begin();
		      em.merge(rec);
		      tr.commit();
		    }catch(Exception e){
		      if (em.getTransaction().isActive()) em.getTransaction().rollback();
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
