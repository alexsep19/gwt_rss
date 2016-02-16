package gx.server.domain;

import java.util.List;

import jpaRss.Item;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class ItemLoadResultBean extends ListLoadResultBean<Item>{
	private static final long serialVersionUID = -6609219248942992632L;
	public ItemLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public ItemLoadResultBean(List<Item> list) {
	      super(list);
	    }
    public Integer getVersion() { return 1;}

}
