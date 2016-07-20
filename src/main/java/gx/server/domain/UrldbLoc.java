package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import other.Urldb;

import com.google.web.bindery.requestfactory.shared.Locator;

public class UrldbLoc extends Locator<Urldb, Integer>{

	public UrldbLoc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Urldb create(Class<? extends Urldb> clazz) {
		// TODO Auto-generated method stub
		return new Urldb();
	}

	@Override
	public Urldb find(Class<? extends Urldb> clazz, Integer id) {
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (Urldb) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<Urldb> getDomainType() {
		// TODO Auto-generated method stub
		return Urldb.class;
	}

	@Override
	public Integer getId(Urldb domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(Urldb domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}
