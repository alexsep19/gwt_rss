package gx.client.domain;

import gx.server.domain.Dao;
import gx.server.domain.DaoServ;
import gx.server.domain.ItemLoadResultBean;
import gx.server.domain.MailLoadResultBean;
import gx.server.domain.UrlLoadResultBean;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public interface FactRss extends RequestFactory{
	rcRss creRcRss();
	
	@Service(value = Dao.class, locator = DaoServ.class)
    public interface rcRss extends RequestContext{
		Request<List<String>> getUserInfo();

		Request<Void> merg(MailPrx rec);
		Request<Void> remov(MailPrx rec);
		Request<Void> merg(UrlPrx rec);
		Request<Void> remov(UrlPrx rec);
		Request<Void> merg(ItemPrx rec);
		Request<Void> remov(ItemPrx rec);

		@ProxyFor(MailLoadResultBean.class)
		public interface MailLoadResultProxy extends ValueProxy, ListLoadResult<MailPrx> {
		    @Override
		    public List<MailPrx> getData();
		  }
		Request<MailLoadResultProxy> getListMail(List<? extends SortInfo> sortInfo);

		@ProxyFor(UrlLoadResultBean.class)
		public interface UrlLoadResultProxy extends ValueProxy, ListLoadResult<UrlPrx> {
		    @Override
		    public List<UrlPrx> getData();
		  }
		Request<UrlLoadResultProxy> getListUrl(List<? extends SortInfo> sortInfo, MailPrx mail);
		
		@ProxyFor(ItemLoadResultBean.class)
		public interface ItemLoadResultProxy extends ValueProxy, ListLoadResult<ItemPrx> {
		    @Override
		    public List<ItemPrx> getData();
		  }
		Request<ItemLoadResultProxy> getListItem(List<? extends SortInfo> sortInfo, MailPrx mail, UrlPrx url);

	}
}
