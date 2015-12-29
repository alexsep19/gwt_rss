package gx.client.domain;

import gx.server.domain.Dao;
import gx.server.domain.DaoServ;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;

public interface FactRss extends RequestFactory{

	@Service(value = Dao.class, locator = DaoServ.class)
    public interface rcRss extends RequestContext{
		Request<List<MailPrx>> getAllMail();
	}
}
