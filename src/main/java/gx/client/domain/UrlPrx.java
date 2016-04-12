package gx.client.domain;

import java.util.Date;
import java.util.List;

import jpaRss.Url;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Url.class, locator = gx.server.domain.UrlLoc.class)
public interface UrlPrx extends EntityProxy{
	public Integer getId();
	public Date getLaststart();
	public void setLaststart(Date laststart);
	public String getUrl();
	public void setUrl(String url);
	public List<ItemPrx> getItems();
	public MailPrx getMail();
	public void setMail(MailPrx mail);
	public String getIsActive();
	public void setIsActive(String isActive);
	
	public static final int LEN_url = 100;
    public Integer getVersion();
}
