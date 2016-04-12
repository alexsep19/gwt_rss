package gx.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.OverridesAttribute;

import jpaRss.Item;
import jpaRss.Mail;
import jpaRss.Url;
import jvRss2obj.rss.Feed;
import jvRss2obj.rss.FeedMessage;
import jvRss2obj.rss.LostFilmParser;

@Singleton(name="RssTimer")
@LocalBean
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class IntervalTimer {
    final static long RSS_2H = 1000*60*60*2;
    final static long RSS_5M = 1000*60*5;
    final static long RSS_2M = 1000*60*2;
//	public IntervalTimer() {
//		// TODO Auto-generated constructor stub
//	}
    @Resource
    private TimerService timerService;
    
	@Resource
	UserTransaction tr;

	@PersistenceContext(unitName = "jpaRss")
	private EntityManager em;
	
	@Resource(mappedName = "java:jboss/mail/Gmail") 
	private Session mailSession;

//    @PostConstruct
    public void startTimer() {
//       System.out.println("startTimer() : " + new Date());
       
	   timerService.createTimer( 1, RSS_2H, "Rss timer");
	}
    
    public boolean isTimerOn(){
     return	!timerService.getTimers().isEmpty();
    }
    
    public void stopTimer(){
    	for(Object obj : timerService.getTimers()) ((Timer)obj).cancel();
    }

    @Timeout
    public void execute(Timer timer) throws Exception {
//	        System.out.println("Timer Service : " + timer.getInfo());
//	        System.out.println("Current Time : " + new Date());
//	        System.out.println("Next Timeout : " + timer.getNextTimeout());
//	        System.out.println("Time Remaining : " + timer.getTimeRemaining());
//	        System.out.println("____________________________________________");
	  final ArrayList<Item> items = new ArrayList<Item>();
	  Date lastPub = null;
	  List<Mail> m = em.createQuery(new StringBuilder("select t from ").append(Mail.class.getSimpleName()).append(" t ").toString()).getResultList();
	  for(Mail mit: m){
		 String mailTo = mit.getUrl();
		 for(Url uit: mit.getUrls()){
			uit.setLaststart(new Date());
			tr.begin();
			em.merge(uit);
			tr.commit();
			if (!uit.getIsActive().equals("Y")) continue;
			for(Item iit: uit.getItems()) {
		      if (!iit.getIsActive().equals("Y")) continue;
		      items.add(iit);
			}
			if (items.isEmpty()) continue;
			LostFilmParser parser = new LostFilmParser(uit.getUrl()){
				@Override
				public boolean titleFoundInRss(String titleRss, Integer[] itemId){
				  for(int i = 0; i < items.size();i++){
					if (titleRss.indexOf(items.get(i).getTitle()) >= 0){ 
//					  message.setItem(items.get(i).getTitle());
//					  message.setPubSaved(items.get(i).getLastpub());
					  itemId[0] = items.get(i).getId();
					  items.remove(i);
					  return true;
					}
				  }
				 return false; 
				}
			};
			Feed feed = parser.readFeed();
//		    System.out.println(feed);
			for (FeedMessage message : feed.getMessages()) {
			   Item item = em.find(Item.class, message.getItemId());
			   if (item.getLastpub() == null || item.getLastpub().before(message.getPubDate())){
				   
				   System.out.println("title = " + item.getTitle() + "; description = "+message.getDescription()+"; pubDate = "+message.getPubDate());
		           sendMail(message.getTitle(), mailTo, item.getTitle() + " "  + message.getDescription());

				   item.setLastpub(message.getPubDate());
				   tr.begin();
				   em.merge(item);
				   tr.commit();
			   }
		    }
			items.clear();
		}
	 }
    }
    
	private boolean sendMail(String mess, String to, String subj){
	try {
	        MimeMessage message = new MimeMessage(mailSession);
	        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject(subj);
	        message.setContent(mess, "text/plain; charset=UTF-8");
	        Transport.send(message);
			return true;
	    } catch (MessagingException e) {
	    	e.printStackTrace();
	       return false;
	    }
	}

}
