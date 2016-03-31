package gx.server.domain;

import java.util.List;

import rolo.Urro;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class UrroLoadResultBean extends ListLoadResultBean<Urro>{
	private static final long serialVersionUID = 8248686906372511823L;
	public UrroLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public UrroLoadResultBean(List<Urro> list) {
	      super(list);
	    }
    public Integer getVersion() { return 1;}

}
