package gx.client.domain;

import java.util.Date;

public interface LogPrx {
	public Integer getId();
	public String getGrp();
	public String getItem();
	public String getMess();
	public Date getUpdate();
	
	public Integer getVersion();
}
