package gx.client.domain;

import rolo.Urro;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Urro.class, locator = gx.server.domain.UrroLoc.class)
public interface UrroPrx extends EntityProxy{
	public Integer getId();
	public void setId(Integer id);
	public RolePrx getRole();
	public void setRole(RolePrx role);
	public UserPrx getUser();
	public void setUser(UserPrx user);
	
	public Integer getVersion();
}
