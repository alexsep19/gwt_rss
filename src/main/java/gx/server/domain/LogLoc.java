package gx.server.domain;

import jpaRss.Log;
import com.google.web.bindery.requestfactory.shared.Locator;

public class LogLoc extends Locator<Log, Integer>{

	@Override
	public Log create(Class<? extends Log> clazz) {
		// TODO Auto-generated method stub
		return new Log();
	}

	@Override
	public Log find(Class<? extends Log> clazz, Integer id) {
		return (Log) Dao.findObject(clazz, id);
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
