package gx.client.domain;

import gx.server.domain.Dao;
import gx.server.domain.DaoServ;
import gx.server.domain.ItemLoadResultBean;
import gx.server.domain.MailLoadResultBean;
import gx.server.domain.RoleLoadResultBean;
import gx.server.domain.UrlLoadResultBean;
import gx.server.domain.UrroLoadResultBean;
import gx.server.domain.UserLoadResultBean;

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
//		Request<List<String>> getUserInfo();
		Request<List<UrroPrx>> getUserInfo();
		Request<Void> setTimerState(boolean turnOn);
		Request<Boolean> getTimerState();
		
		Request<Void> merg(MailPrx rec);
		Request<Void> remov(MailPrx rec);
		Request<Void> merg(UrlPrx rec);
		Request<Void> remov(UrlPrx rec);
		Request<Void> merg(ItemPrx rec);
		Request<Void> remov(ItemPrx rec);
		Request<Void> merg(RolePrx rec);
		Request<Void> remov(RolePrx rec);
		Request<Void> merg(UserPrx rec);
		Request<Void> remov(UserPrx rec);
		Request<Void> merg(UrroPrx rec);
		Request<Void> remov(UrroPrx rec);

		Request<List<RolePrx>> getAllRole();
		Request<List<UserPrx>> getAllUser();

		@ProxyFor(MailLoadResultBean.class)
		public interface MailLoadResultProxy extends ValueProxy, ListLoadResult<MailPrx> {
		    @Override
		    public List<MailPrx> getData();
		  }
		Request<MailLoadResultProxy> getListMail(List<? extends SortInfo> sortInfo, UserPrx u);

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

		@ProxyFor(RoleLoadResultBean.class)
		public interface RoleLoadResultProxy extends ValueProxy, ListLoadResult<RolePrx> {
		    @Override
		    public List<RolePrx> getData();
		  }
		Request<RoleLoadResultProxy> getListRole(List<? extends SortInfo> sortInfo);

		@ProxyFor(UserLoadResultBean.class)
		public interface UserLoadResultProxy extends ValueProxy, ListLoadResult<UserPrx> {
		    @Override
		    public List<UserPrx> getData();
		  }
		Request<UserLoadResultProxy> getListUser(List<? extends SortInfo> sortInfo);
		
		@ProxyFor(UrroLoadResultBean.class)
		public interface UrroLoadResultProxy extends ValueProxy, ListLoadResult<UrroPrx> {
		    @Override
		    public List<UrroPrx> getData();
		  }
		Request<UrroLoadResultProxy> getListUrro(List<? extends SortInfo> sortInfo);

	}
}
