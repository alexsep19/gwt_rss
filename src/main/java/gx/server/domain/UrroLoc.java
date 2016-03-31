package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import rolo.Urro;

import com.google.web.bindery.requestfactory.shared.Locator;

public class UrroLoc extends Locator<Urro, Integer>{

	public UrroLoc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Urro create(Class<? extends Urro> clazz) {
		// TODO Auto-generated method stub
		return new Urro();
	}

	@Override
	public Urro find(Class<? extends Urro> clazz, Integer id) {
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (Urro) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<Urro> getDomainType() {
		// TODO Auto-generated method stub
		return Urro.class;
	}

	@Override
	public Integer getId(Urro domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(Urro domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}
