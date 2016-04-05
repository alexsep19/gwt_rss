package gx.client.domain;

import rolo.Role;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Role.class, locator = gx.server.domain.RoleLoc.class)
public interface RolePrx extends EntityProxy{
	public Integer getId();
	public void setId(Integer id);
	public String getCode();
	public void setCode(String name);
	
	public static final int LEN_name = 30;
	public Integer getVersion();
	public static final String[] codeRole = new String[]{"admin","any"};
	public static final int ROLE_ADMIN = 0;
	public static final int ROLE_ANY = 1;
//	public List<String> getCodeRole();
}
