package gx.server.domain;

import java.util.List;

import other.Urldb;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class UrldbLoadResultBean extends ListLoadResultBean<Urldb>{
	private static final long serialVersionUID = 3574749577702464970L;
	public UrldbLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public UrldbLoadResultBean(List<Urldb> list) {
	      super(list);
	    }
   public Integer getVersion() { return 1;}

}
