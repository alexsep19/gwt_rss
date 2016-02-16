package gx.server.domain;

import java.util.List;

import jpaRss.Url;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class UrlLoadResultBean extends ListLoadResultBean<Url>{

	public UrlLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public UrlLoadResultBean(List<Url> list) {
	      super(list);
	    }
    public Integer getVersion() { return 1;}

}
