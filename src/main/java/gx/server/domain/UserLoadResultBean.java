package gx.server.domain;

import java.util.List;

import rolo.User;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class UserLoadResultBean extends ListLoadResultBean<User>{
	private static final long serialVersionUID = -6388057776265945239L;
	public UserLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public UserLoadResultBean(List<User> list) {
	      super(list);
	    }
   public Integer getVersion() { return 1;}

}
