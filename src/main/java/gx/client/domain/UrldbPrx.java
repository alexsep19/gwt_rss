package gx.client.domain;


import other.Urldb;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Urldb.class, locator = gx.server.domain.UrldbLoc.class)
public interface UrldbPrx extends EntityProxy{
	public Integer getId();
	public void setId(Integer id);
	public String getName();
	public void setName(String name);
	public String getUrl();
	public void setUrl(String url);
	
	public static final int LEN_name = 20;
	public static final int LEN_url = 100;
	public Integer getVersion();
}
