package gx.server.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import rolo.Log;
import rolo.Role;

import com.google.web.bindery.requestfactory.shared.Locator;

public class LogLoc extends Locator<Log, Integer>{

	@Override
	public Log create(Class<? extends Log> clazz) {
		// TODO Auto-generated method stub
		return new Log();
	}

	@Override
	public Log find(Class<? extends Log> clazz, Integer id) {
		try {
			Dao d = (Dao)InitialContext.doLookup("java:module/Dao");
			return (Log) d.findObject(clazz, id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Class<Log> getDomainType() {
		// TODO Auto-generated method stub
		return Log.class;
	}

	@Override
	public Integer getId(Log domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		return Integer.class;
	}

	@Override
	public Object getVersion(Log domainObject) {
		return domainObject.getVersion();
	}

}
