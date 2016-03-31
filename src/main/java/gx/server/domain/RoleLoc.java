package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import rolo.Role;

import com.google.web.bindery.requestfactory.shared.Locator;

public class RoleLoc extends Locator<Role, Integer>{

	public RoleLoc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Role create(Class<? extends Role> clazz) {
		// TODO Auto-generated method stub
		return new Role();
	}

	@Override
	public Role find(Class<? extends Role> clazz, Integer id) {
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (Role) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<Role> getDomainType() {
		// TODO Auto-generated method stub
		return Role.class;
	}

	@Override
	public Integer getId(Role domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(Role domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}
