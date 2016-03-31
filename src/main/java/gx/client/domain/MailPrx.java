package gx.client.domain;


import java.util.List;

import jpaRss.Mail;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Mail.class, locator = gx.server.domain.MailLoc.class)
public interface MailPrx extends EntityProxy{
	public Integer getId();
	public String getName();
	public void setName(String name);
	public String getUrl();
	public void setUrl(String url);
	public List<ItemPrx> getItems();
	public List<UrlPrx> getUrls();
	
	public static final int LEN_name = 50;
	public static final int LEN_url = 50;
	public Integer getVersion();
}
