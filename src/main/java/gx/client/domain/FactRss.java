package gx.client.domain;

import gx.server.domain.Dao;
import gx.server.domain.DaoServ;
import gx.server.domain.MailLoadResultBean;

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

		@ProxyFor(MailLoadResultBean.class)
		public interface MailLoadResultProxy extends ValueProxy, ListLoadResult<MailPrx> {
		    @Override
		    public List<MailPrx> getData();
		  }
		Request<MailLoadResultProxy> getListMail(List<? extends SortInfo> sortInfo);

	}
}
