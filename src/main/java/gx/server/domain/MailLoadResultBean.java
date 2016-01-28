package gx.server.domain;

import java.util.List;

import jpaRss.Mail;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class MailLoadResultBean extends ListLoadResultBean<Mail>{
	private static final long serialVersionUID = -7807368835431502266L;
	public MailLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public MailLoadResultBean(List<Mail> list) {
	      super(list);
	    }
  public Integer getVersion() { return 1;}

}
