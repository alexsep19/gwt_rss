package gx.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle{
	public Images INSTANCE = GWT.create(Images.class);
	 
	@Source("connect.png")
	ImageResource connect();

	@Source("json.gif")
	ImageResource copydb();

}
