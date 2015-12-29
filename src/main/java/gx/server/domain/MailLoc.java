package gx.server.domain;

import jpaRss.Mail;
import com.google.web.bindery.requestfactory.shared.Locator;

public class MailLoc extends Locator<Mail, Integer>{

	@Override
	public Mail create(Class<? extends Mail> clazz) {
		// TODO Auto-generated method stub
		return new Mail();
	}

	@Override
	public Mail find(Class<? extends Mail> clazz, Integer id) {
		return (Mail) Dao.findObject(clazz, id);
	}

	@Override
	public Class<Mail> getDomainType() {
		// TODO Auto-generated method stub
		return Mail.class;
	}

	@Override
	public Integer getId(Mail domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(Mail domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}
