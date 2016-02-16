package gx.client.domain;

import jpaRss.Item;
import jpaRss.Url;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = Item.class, locator = gx.server.domain.ItemLoc.class)
public interface ItemPrx extends EntityProxy{
	public Integer getId();
	public String getTitle();
	public void setTitle(String title);
	public MailPrx getMail();
	public void setMail(MailPrx mail);
	public UrlPrx getUrl();
	public void setUrl(UrlPrx url);
	public String getIsActive();
	public void setIsActive(String isActive);

	public static final int LEN_Title = 50;
	public Integer getVersion();
}
