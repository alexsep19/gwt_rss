package gx.server.domain;

import java.util.List;

import rolo.Role;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class RoleLoadResultBean extends ListLoadResultBean<Role>{
	private static final long serialVersionUID = -1867016934862501658L;
	public RoleLoadResultBean() {
		// TODO Auto-generated constructor stub
	}
	public RoleLoadResultBean(List<Role> list) {
	      super(list);
	    }
  public Integer getVersion() { return 1;}

}
