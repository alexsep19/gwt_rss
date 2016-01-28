package gx.client.rss;

import java.util.List;

import gx.client.PanList;
import gx.client.domain.FactRss;
import gx.client.domain.FactRss.rcRss;
import gx.client.domain.MailPrx;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class PanRss extends ContentPanel{
    private static final int WIN_WIDTH = 1150;
    private static final int WIN_HEIGHT = 850;
  //=================== Category ======================    
    private static final int PAN_MAIL_WIDTH = 420;
    private static final int PAN_MAIL_HEIGHT = 410;
    interface MailProperties extends PropertyAccess<MailPrx> {
	    ModelKeyProvider<MailPrx> id();
	    @Path("id")
	    ValueProvider<MailPrx, Integer> idVal();
	    ValueProvider<MailPrx, String> name();
	    ValueProvider<MailPrx, String> url();
	  }
    private final MailProperties propMail = GWT.create(MailProperties.class);
//===================================================      

    private String role = "";
    
    public PanRss(final FactRss Fct, String Role){
//    setCollapsible(false);    	
	getHeader().addStyleName("txt_center");
	addStyleName("margin-10");
	setHeadingText("Rss настройка");
	setPixelSize(WIN_WIDTH, WIN_HEIGHT);
    HtmlLayoutContainer contMain = new HtmlLayoutContainer(getMainMarkup());
    
    PanList<MailPrx> tabMail =  new PanList<MailPrx>(PAN_MAIL_WIDTH, PAN_MAIL_HEIGHT, "Mail"){
	    ColumnConfig<MailPrx, Integer> ccIdVal;
	    ColumnConfig<MailPrx, String> ccName;
	    ColumnConfig<MailPrx, String> ccUrl;
	    TextField txName = new TextField();
	    TextField txUrl = new TextField();
	    rcRss reqIns;
	    @Override
        public void fill(){
	       ccIdVal = new ColumnConfig<MailPrx, Integer>( propMail.idVal(), 20, "id");
	       ccIdVal.setCell(new AbstractCell<Integer>() {
			      @Override
			      public void render(Context context, Integer value, SafeHtmlBuilder sb) {
			    	  sb.appendHtmlConstant(value == null? "?": value.toString());
			      } });
	       ccName = new ColumnConfig<MailPrx, String>(propMail.name(), 40, "Name");
	       ccUrl = new ColumnConfig<MailPrx, String>(propMail.url(), 40, "Url");
		   getCcL().add(ccIdVal);
	       getCcL().add(ccName);
	       getCcL().add(ccUrl);

	       setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<MailPrx>>() {
			@Override
			public void load(ListLoadConfig loadConfig,	Receiver<? super ListLoadResult<MailPrx>> receiver) {
	 		  rcRss req = Fct.creRcRss();
   	 		  List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
     		  req.getListMail(sortInfo).to(receiver).fire();
			}});
	       setStT(new ListStore<MailPrx>(propMail.id()));       
	       initValues(true, true, true);
	       getEditing().addEditor(ccName, txName);
	       getEditing().addEditor(ccUrl, txUrl);
	    }
	    @Override
	    public void mergItem(MailPrx item){
	       rcRss req = null;
	       if (isIns) {req = reqIns; isIns = false;}
	       else req = Fct.creRcRss();
	       MailPrx editItem = req.edit(item);
	       editItem.setName(txName.getText());
	       editItem.setUrl(txUrl.getText());
	       req.merg(editItem).fire(mergReceiver);
	    }
	    @Override
	    public void insItem(){
	     	reqIns = Fct.creRcRss();
	     	MailPrx o = reqIns.create(MailPrx.class);
	     	o.setName("");
	     	o.setUrl("");
	        stT.add(0, o);
	    }
	    @Override
	    public String getItemName(MailPrx item){
		return String.valueOf(item.getId());
	    }
	    @Override
	    public void delItem(MailPrx item, Receiver<Void> R){
	    	Fct.creRcRss().remov(item).fire(R);
	    }
	    @Override
	    protected void beforEdit(){
	    	txName.getCell().getInputElement(txName.getElement()).setMaxLength(MailPrx.LEN_name);
	    	txUrl.getCell().getInputElement(txUrl.getElement()).setMaxLength(MailPrx.LEN_url);
	    }
    };
    tabMail.fill();
    contMain.add( tabMail, new HtmlData(".mail"));
    setWidget(contMain);
    }
    
    private native String getMainMarkup() /*-{
    return [ '<table cellpadding=0 cellspacing=4 cols="2">',
        '<tr><td class=mail valign="top"></td><td class=item rowspan=2 valign="top"></td></tr>',
        '<tr><td class=url valign="top"></td></tr>',
        '</table>'
    ].join("");
  }-*/;
}
