package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jpaRss.Mail;
import jpaRss.Url;

import com.google.web.bindery.requestfactory.shared.Locator;

public class UrlLoc extends Locator<Url, Integer>{

	@Override
	public Url create(Class<? extends Url> clazz) {
		// TODO Auto-generated method stub
		return new Url();
	}

	@Override
	public Url find(Class<? extends Url> clazz, Integer id) {
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (Url) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<Url> getDomainType() {
		// TODO Auto-generated method stub
		return Url.class;
	}

	@Override
	public Integer getId(Url domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(Url domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}
