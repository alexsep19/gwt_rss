package gx.server.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jpaRss.Mail;

public class Dao {
	private static final EntityManagerFactory emfRss = Persistence.createEntityManagerFactory("jpaRss");
	private static final String  sqlSelFrom = "select t from ";
	public static EntityManager emRss() {
		   EntityManager em = emfRss.createEntityManager();
//		   em.setFlushMode(FlushModeType.COMMIT);
		   return em;
	}
	
	public List<Mail> getAllMail(){
//		EntityManager em = emSimple();
//		em.createNamedQuery("Category.findAll", Category.class).getResultList();
//		List<Mail> l = emRss().createQuery(new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t").toString(), Mail.class).getResultList();
//		System.out.println("++++++++++++++++++++++++++++"+l.get(0).getName());
//		return l;
		return emRss().createQuery(new StringBuilder(sqlSelFrom).append(Mail.class.getSimpleName()).append(" t").toString(), Mail.class).getResultList();
	}
//	public Category findCategory(Integer id){
//		   
//
//	}
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
