package gx.server.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;

import jpaRss.Mail;

public class Dao {
	private static final EntityManagerFactory emfRss = Persistence.createEntityManagerFactory("jpaRss");
	private static final String  sqlSelFrom = "select t from ";
	public static EntityManager emRss() {
		   EntityManager em = emfRss.createEntityManager();
//		   em.setFlushMode(FlushModeType.COMMIT);
		   return em;
	}
	
	
	public List<String> getUserInfo(){
		
	 return Arrays.asList(new String[]{"hom","A"});
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
	   public MailLoadResultBean getListMail(List<SortInfoBean> sortInfo){
	       EntityManager em = emRss();
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

//=============================================================
//	   public void rem(Object rec, /*Class<?> cl,*/ long id){
//	       EntityManager em = emRss();
//	       try {
//		      em.getTransaction().begin();
//		      em.remove(em.find(rec.getClass(), id));
//		      em.flush();
//		      em.getTransaction().commit();
//		    }catch(RuntimeException e){
//		      if (em.getTransaction().isActive()) em.getTransaction().rollback();
//	   	      e.printStackTrace();
//		      throw e;
//		    } 
//	   }

//	   public void remov(Object rec) throws RuntimeException{
//		   if (rec instanceof Mail)  rem(rec, ((Mail)rec).getId());
//	   }

	   public void remov(Object rec) throws RuntimeException{
	       EntityManager em = emRss();
	       try {
		      em.getTransaction().begin();
		      em.remove(em.find(rec.getClass(), Integer.parseInt(rec.toString() )));
		      em.flush();
		      em.getTransaction().commit();
		    }catch(RuntimeException e){
		      if (em.getTransaction().isActive()) em.getTransaction().rollback();
	   	      e.printStackTrace();
		      throw e;
		    } 
	   }

	   public void merg(Object rec){
		    System.out.println("rec.toString() = "+rec.toString());
			EntityManager em = emRss();
		       try {
//		    	   System.out.println("merg = "+ rec.toString());
			      em.getTransaction().begin();
			      if (rec.toString().equals("0")) em.persist(rec);
			      else em.merge(rec);
			      em.flush();
			      em.getTransaction().commit();
			    }catch(RuntimeException e){
			      if (em.getTransaction().isActive()) em.getTransaction().rollback();
		  	      e.printStackTrace();
			      throw e;
			    }
		   }
//=============================================================
	public static Object findObject(Class<?> cl, Integer id) {
	       if (id == null) return null; 
	       EntityManager em = emRss();
	       try {
		     return em.find(cl, id);
	       }catch(RuntimeException e){
		     e.printStackTrace();
//		     throw e;
	       } 
	     return null;
  }
}
