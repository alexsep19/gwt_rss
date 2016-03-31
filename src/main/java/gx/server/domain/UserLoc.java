package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import rolo.User;

import com.google.web.bindery.requestfactory.shared.Locator;

public class UserLoc extends Locator<User, Integer>{

	public UserLoc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User create(Class<? extends User> clazz) {
		// TODO Auto-generated method stub
		return new User();
	}

	@Override
	public User find(Class<? extends User> clazz, Integer id) {
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (User) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<User> getDomainType() {
		// TODO Auto-generated method stub
		return User.class;
	}

	@Override
	public Integer getId(User domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Object getVersion(User domainObject) {
		// TODO Auto-generated method stub
		return domainObject.getVersion();
	}

}
