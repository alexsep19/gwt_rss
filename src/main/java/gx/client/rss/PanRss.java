package gx.client.rss;

import gx.client.domain.FactRss;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;

public class PanRss extends ContentPanel{
    private static final int WIN_WIDTH = 1150;
    private static final int WIN_HEIGHT = 850;
    private String role = "";
    
    public PanRss(final FactRss fct, String role){
	getHeader().addStyleName("txt_center");
	addStyleName("margin-10");
	setHeadingText("Справочники");
	setPixelSize(WIN_WIDTH, WIN_HEIGHT);
    HtmlLayoutContainer contMain = new HtmlLayoutContainer(getMainMarkup());
//        contMain.add(new PanML(fct), new HtmlData(".line"));
//        contMain.add(new PanMS(fct), new HtmlData(".station"));
//        contMain.add(new PanCat(fct), new HtmlData(".cat"));
//        contMain.add(new PanTown(fct), new HtmlData(".town"));
     setWidget(contMain);
    }
    
    private native String getMainMarkup() /*-{
    return [ '<table cellpadding=0 cellspacing=4 cols="3">',
        '<tr><td class=line valign="top"></td><td class=station rowspan=2 valign="top"></td><td class=town rowspan=2 valign="top"></td></tr>',
        '<tr><td class=cat valign="top"></td></tr>',
        '</table>'
    ].join("");
  }-*/;
}
