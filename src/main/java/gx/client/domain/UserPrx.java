package gx.client.domain;

import rolo.User;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = User.class, locator = gx.server.domain.UserLoc.class)
public interface UserPrx extends EntityProxy{
	public Integer getId();
	public void setId(Integer id);
	public String getMail();
	public void setMail(String mail);
	public String getName();
	public void setName(String name);
	public String getPass();
	public void setPass(String pass);

	public static final int LEN_name = 30;
	public static final int LEN_pass = 20;
	public static final int LEN_mail = 50;
	public Integer getVersion();

}
