package gx.client;

import gx.client.domain.FactRss;

import com.sencha.gxt.widget.core.client.ContentPanel;

public class PanLog extends ContentPanel{
    private static final int WIN_WIDTH = 1150;
    private static final int WIN_HEIGHT = 850;

    public PanLog(final FactRss fct){
	  getHeader().addStyleName("txt_center");
	  addStyleName("margin-10");
	  setHeadingText("Журнал");
	  setPixelSize(WIN_WIDTH, WIN_HEIGHT);
    }
}
