package gx.server.domain;

import javax.naming.InitialContext;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class DaoServ implements ServiceLocator{
//    private static Dao serviceInstance;
  
    @Override
    public Object getInstance(Class<?> clazz) {
        try {
//          return clazz.newInstance();
  		return (Dao)InitialContext.doLookup("java:module/Dao");
        } catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  	 }
       return null;
    }
}