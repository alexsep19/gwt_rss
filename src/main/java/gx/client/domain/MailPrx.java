package gx.client.domain;


import java.util.List;

import jpaRss.Item;
import jpaRss.Mail;
import jpaRss.Url;

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
	
	public Integer getVersion();
}
