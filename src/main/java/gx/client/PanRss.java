package gx.client;

import java.util.Date;
import java.util.List;

import gx.client.PanList;
import gx.client.domain.FactRss;
import gx.client.domain.FactRss.rcRss;
import gx.client.domain.ItemPrx;
import gx.client.domain.LabVal;
import gx.client.domain.MailPrx;
import gx.client.domain.RolePrx;
import gx.client.domain.UrlPrx;
import gx.client.domain.UrroPrx;
import gx.client.domain.UserPrx;

import java.text.ParseException;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class PanRss extends ContentPanel{
    private static final int WIN_WIDTH = 1150;
    private static final int WIN_HEIGHT = 850;
    ComboBox<String> cbYN;
  //=================== Url ======================    
    private static final int PAN_URL_WIDTH = 420;
    private static final int PAN_URL_HEIGHT = 410;
    interface UrlProperties extends PropertyAccess<UrlPrx> {
	    ModelKeyProvider<UrlPrx> id();
	    @Path("id")
	    ValueProvider<UrlPrx, Integer> idVal();
	    ValueProvider<UrlPrx, String> url();
	    ValueProvider<UrlPrx, Date> laststart();
	    ValueProvider<UrlPrx, String> isActive();
	  }
    private final UrlProperties propUrl = GWT.create(UrlProperties.class);
  //=================== Mail ======================    
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
    //=================== Item ======================    
    private static final int PAN_ITEM_WIDTH = 420;
    private static final int PAN_ITEM_HEIGHT = 800;
    interface ItemProperties extends PropertyAccess<ItemPrx> {
	    ModelKeyProvider<ItemPrx> id();
	    @Path("id")
	    ValueProvider<ItemPrx, Integer> idVal();
	    ValueProvider<ItemPrx, String> title();
	    ValueProvider<ItemPrx, Date> lastpub();
	    ValueProvider<ItemPrx, String> isActive();
	  }
    private final ItemProperties propItem = GWT.create(ItemProperties.class);
//===================================================      
    interface ActProperties extends PropertyAccess<LabVal> {
	    @Path("value")
	    ModelKeyProvider<LabVal> id();
	    ValueProvider<LabVal, String> label();
    }
    private final ActProperties propAct = GWT.create(ActProperties.class);
    final ListStore<LabVal> stUrlAct = new ListStore<LabVal>(propAct.id());
    final ListStore<LabVal> stItemAct = new ListStore<LabVal>(propAct.id());
    ComboBox<LabVal> cbUrlAct;
    ComboBox<LabVal> cbItemAct;

    PanList<UrlPrx> tabUrl;
    PanList<ItemPrx> tabItem;
    PanList<MailPrx> tabMail;
    DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
    private String role = "";
    MailPrx curMail;
    UrlPrx curUrl;
    FactRss Fct;
  //===================================================     
    protected ToolButton tbRssIsOn = new ToolButton(ToolButton.PIN, new SelectHandler() {
        @Override  
        public void onSelect(SelectEvent event) {  
        	//выключить
        	tbRssIsOn.setVisible(false);
        	Fct.creRcRss().setTimerState(false).fire(new Receiver<Void>() {
				@Override
				public void onSuccess(Void response) {
				  tbRssIsOn.setVisible(true);
				  setButtRssOnOff(false);
				}
				public void onFailure(ServerFailure error) {
					tbRssIsOn.setVisible(true);
				    AlertMessageBox d = new AlertMessageBox("Ошибка", error.toString());
				    d.show();
				    super.onFailure(error);
		          }
      	      });
           }    });

    protected ToolButton tbRssIsOff= new ToolButton(ToolButton.UNPIN, new SelectHandler() {
        @Override  
        public void onSelect(SelectEvent event) {   
        	//включить
        	tbRssIsOff.setVisible(false);
        	Fct.creRcRss().setTimerState(true).fire(new Receiver<Void>() {
				@Override
				public void onSuccess(Void response) {
				  tbRssIsOff.setVisible(true);
				  setButtRssOnOff(true);
				}
				public void onFailure(ServerFailure error) {
					tbRssIsOff.setVisible(true);
				    AlertMessageBox d = new AlertMessageBox("Ошибка", error.toString());
				    d.show();
				    super.onFailure(error);
		          }
      	      });
           }    });
//===================================================   
    public void setButtRssOnOff(boolean isOn){
    	if (isOn && getHeader().getTools().indexOf(tbRssIsOff) >= 0 ){
    	  getHeader().removeTool(tbRssIsOff);
    	  getHeader().addTool(tbRssIsOn);
    	}else if (!isOn && getHeader().getTools().indexOf(tbRssIsOn) >= 0 ){
      	  getHeader().removeTool(tbRssIsOn);
      	  getHeader().addTool(tbRssIsOff);
    	}
    }
    
    public PanRss(FactRss fct, final UserPrx User){
    Fct = fct;
    tbRssIsOn.setTitle("включено");
    tbRssIsOff.setTitle("выключено");
//    setCollapsible(false);    	
	getHeader().addStyleName("txt_center");
	addStyleName("margin-10");
	setHeadingText("Rss настройка");
	setPixelSize(WIN_WIDTH, WIN_HEIGHT);
    HtmlLayoutContainer contMain = new HtmlLayoutContainer(getMainMarkup());
//====================== tabMail  
    tabMail = new PanList<MailPrx>(PAN_MAIL_WIDTH, PAN_MAIL_HEIGHT, "Mail"){
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
     		  req.getListMail(sortInfo, User).to(receiver).fire();
			}});
	       setStT(new ListStore<MailPrx>(propMail.id()));    
	    
	       initValues(true, !startpoint.userRoles[RolePrx.ROLE_ADMIN], true);
	       getG().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<MailPrx>(){
	   		@Override
	   		public void onSelectionChanged(SelectionChangedEvent<MailPrx> event) {
	   			if (event.getSelection().size() <= 0) {
	   				tabUrl.getG().setVisible(false);
	   				return; 
	   			}else if (!tabUrl.getG().isVisible()) tabUrl.getG().setVisible(true);
	   			curMail = event.getSource().getSelectedItem();
	   			tabUrl.getG().getLoader().load();
	   		}});
	       
	       if (isEditOn()){
	         getEditing().addEditor(ccName, txName);
	         getEditing().addEditor(ccUrl, txUrl);
	       }
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
	     	o.setUser(User);
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
  //====================== tabUrl 
    tabUrl =  new PanList<UrlPrx>(PAN_URL_WIDTH, PAN_URL_HEIGHT, "Url"){
	    ColumnConfig<UrlPrx, Integer> ccIdVal;
	    ColumnConfig<UrlPrx, String> ccUrl;
	    ColumnConfig<UrlPrx, Date> ccLaststart;
	    ColumnConfig<UrlPrx, String> ccIsActive;
	    
	    TextField txUrl = new TextField();
	    TextField txSchedule = new TextField();
	    rcRss reqIns;
	    @Override
        public void fill(){
	       ccIdVal = new ColumnConfig<UrlPrx, Integer>( propUrl.idVal(), 20, "id");
	       ccIdVal.setCell(new AbstractCell<Integer>() {
			      @Override
			      public void render(Context context, Integer value, SafeHtmlBuilder sb) {
			    	  sb.appendHtmlConstant(value == null? "?": value.toString());
			      } });
	       ccUrl = new ColumnConfig<UrlPrx, String>(propUrl.url(), 40, "Url rss");
	       ccIsActive = new ColumnConfig<UrlPrx, String>(propUrl.isActive(), 40, "Включено");
	       ccIsActive.setCell(new AbstractCell<String>() {
		      @Override
		      public void render(Context context, String value, SafeHtmlBuilder sb) {
		    	  for(LabVal it: stUrlAct.getAll()){
				        if (it.getValue().equals(value)) {
				        	sb.appendHtmlConstant(it.getLabel());
				        	return;
				        }
				      }
		    	  sb.appendHtmlConstant("XX");
		      } });

	       ccLaststart = new ColumnConfig<UrlPrx, Date>(propUrl.laststart(), 40, "Запускалось");
	       ccLaststart.setCell(new AbstractCell<Date>() {
		      @Override
		      public void render(Context context, Date value, SafeHtmlBuilder sb) {
		    	  sb.appendHtmlConstant(value == null? "?": fmt.format(value));
		      } });
		   getCcL().add(ccIdVal);
	       getCcL().add(ccUrl);
	       getCcL().add(ccIsActive);
	       getCcL().add(ccLaststart);

	       setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<UrlPrx>>() {
			@Override
			public void load(ListLoadConfig loadConfig,	Receiver<? super ListLoadResult<UrlPrx>> receiver) {
              if (curMail != null){ 
	              setHeadingText("mail "+curMail.getName());
                  rcRss req = Fct.creRcRss();
   	 		      List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
     		      req.getListUrl(sortInfo, curMail).to(receiver).fire();
              } else G.setVisible(false);
			}});
	       setStT(new ListStore<UrlPrx>(propUrl.id()));       
	       stUrlAct.add(new LabVal("Y", "вкл"));
	       stUrlAct.add(new LabVal("N", "выкл"));
	       cbUrlAct = new ComboBox<LabVal>(stUrlAct, new LabelProvider<LabVal>(){
	            @Override
	            public String getLabel(LabVal item) {
	              return item==null ? "": item.getLabel();
	            }
	        });
	       cbUrlAct.setPropertyEditor(new PropertyEditor<LabVal>() {
		          @Override
		          public LabVal parse(CharSequence text) throws ParseException {
		            for(LabVal it: stUrlAct.getAll()){
		        	if (it.getLabel().equals(text)) return it;
		            }
		            return null;
		          }
		          @Override
		          public String render(LabVal object) {
		            return object == null ? "XXX" : object.getLabel();
		          }});
	       cbUrlAct.setTriggerAction(TriggerAction.ALL);
	       cbUrlAct.setForceSelection(true);
	       
	       initValues(true, !startpoint.userRoles[RolePrx.ROLE_ADMIN], true);
	       getG().getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UrlPrx>(){
	   		@Override
	   		public void onSelectionChanged(SelectionChangedEvent<UrlPrx> event) {
	   			if (event.getSelection().size() <= 0) {
	   				tabItem.getG().setVisible(false);
	   				return; 
	   			}else if (!tabItem.getG().isVisible()) tabItem.getG().setVisible(true);
	   			curUrl = event.getSource().getSelectedItem();
	   			tabItem.getG().getLoader().load();
	   		}});
	       if (isEditOn()){
	          getEditing().addEditor(ccUrl, txUrl);
	          getEditing().addEditor(ccIsActive, new Converter<String, LabVal>(){
				@Override
				public String convertFieldValue(LabVal object) {
					return object == null ? "" : object.getValue();
				}
				@Override
				public LabVal convertModelValue(String object) {
					return stUrlAct.findModelWithKey(object);
				}
	        }, cbUrlAct);
	       }
	    }
	    @Override
	    public void mergItem(UrlPrx item){
	       rcRss req = null;
	       if (isIns) {req = reqIns; isIns = false;}
	       else req = Fct.creRcRss();
	       UrlPrx editItem = req.edit(item);
	       editItem.setUrl(txUrl.getText());
	       editItem.setIsActive(cbUrlAct.getCurrentValue().getValue());
	       req.merg(editItem).fire(mergReceiver);
	    }
	    @Override
	    public void insItem(){
	     	reqIns = Fct.creRcRss();
	     	UrlPrx o = reqIns.create(UrlPrx.class);
	     	o.setMail(curMail);
	     	o.setUrl("");
	     	o.setIsActive(stUrlAct.get(0).getValue());
	        stT.add(0, o);
	    }
	    @Override
	    public String getItemName(UrlPrx item){
		return String.valueOf(item.getId());
	    }
	    @Override
	    public void delItem(UrlPrx item, Receiver<Void> R){
	    	Fct.creRcRss().remov(item).fire(R);
	    }
	    @Override
	    protected void beforEdit(){
	    	txUrl.getCell().getInputElement(txUrl.getElement()).setMaxLength(UrlPrx.LEN_url);
	    }
    };
//======================    tabItem
    tabItem =  new PanList<ItemPrx>(PAN_ITEM_WIDTH, PAN_ITEM_HEIGHT, "Item"){
	    ColumnConfig<ItemPrx, Integer> ccIdVal;
	    ColumnConfig<ItemPrx, String> ccTitle;
	    ColumnConfig<ItemPrx, Date> ccLastpub;
	    ColumnConfig<ItemPrx, String> ccIsActive;
	    
	    TextField txTitle = new TextField();
	    rcRss reqIns;
	    @Override
        public void fill(){
	       ccIdVal = new ColumnConfig<ItemPrx, Integer>( propItem.idVal(), 20, "id");
	       ccIdVal.setCell(new AbstractCell<Integer>() {
			      @Override
			      public void render(Context context, Integer value, SafeHtmlBuilder sb) {
			    	  sb.appendHtmlConstant(value == null? "?": value.toString());
			      } });
	       ccTitle = new ColumnConfig<ItemPrx, String>(propItem.title(), 40, "title");
	       ccLastpub = new ColumnConfig<ItemPrx, Date>(propItem.lastpub(), 40, "Опубликовано");
	       ccLastpub.setCell(new AbstractCell<Date>() {
		      @Override
		      public void render(Context context, Date value, SafeHtmlBuilder sb) {
		    	  sb.appendHtmlConstant(value == null? "?": fmt.format(value));
		      } });
	       ccIsActive = new ColumnConfig<ItemPrx, String>(propItem.isActive(), 40, "Включено");
	       ccIsActive.setCell(new AbstractCell<String>() {
		      @Override
		      public void render(Context context, String value, SafeHtmlBuilder sb) {
			    	  for(LabVal it: stItemAct.getAll()){
					        if (it.getValue().equals(value)) {
					        	sb.appendHtmlConstant(it.getLabel());
					        	return;
					        }
					      }
			    	  sb.appendHtmlConstant("XX");
		      } });
		   getCcL().add(ccIdVal);
	       getCcL().add(ccTitle);
	       getCcL().add(ccLastpub);
	       getCcL().add(ccIsActive);

	       setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<ItemPrx>>() {
			@Override
			public void load(ListLoadConfig loadConfig,	Receiver<? super ListLoadResult<ItemPrx>> receiver) {
              if (curUrl != null){ 
             	 setHeadingText("Url "+curUrl.getUrl());
 	 		     rcRss req = Fct.creRcRss();
   	 		     List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
     		     req.getListItem(sortInfo, curMail, curUrl).to(receiver).fire();
              } else G.setVisible(false); 
			}});
	       setStT(new ListStore<ItemPrx>(propItem.id()));       
	       stItemAct.add(new LabVal("Y", "вкл"));
	       stItemAct.add(new LabVal("N", "выкл"));
	       cbItemAct = new ComboBox<LabVal>(stItemAct, new LabelProvider<LabVal>(){
	            @Override
	            public String getLabel(LabVal item) {
	              return item==null ? "": item.getLabel();
	            }
	        });
	       cbItemAct.setPropertyEditor(new PropertyEditor<LabVal>() {
		          @Override
		          public LabVal parse(CharSequence text) throws ParseException {
		            for(LabVal it: stItemAct.getAll()){
		        	if (it.getLabel().equals(text)) return it;
		            }
		            return null;
		          }
		          @Override
		          public String render(LabVal object) {
		            return object == null ? "XXX" : object.getLabel();
		          }});
	       cbItemAct.setTriggerAction(TriggerAction.ALL);
	       cbItemAct.setForceSelection(true);
	       
	       initValues(true, !startpoint.userRoles[RolePrx.ROLE_ADMIN], true);
	       if (isEditOn()){
	         getEditing().addEditor(ccTitle, txTitle);
	         getEditing().addEditor(ccIsActive, new Converter<String, LabVal>(){
				@Override
				public String convertFieldValue(LabVal object) {
					return object == null ? "" : object.getValue();
				}
				@Override
				public LabVal convertModelValue(String object) {
					return stItemAct.findModelWithKey(object);
				}
	        }, cbItemAct);
	       }
	    }
	    @Override
	    public void mergItem(ItemPrx item){
	       rcRss req = null;
	       if (isIns) {req = reqIns; isIns = false;}
	       else req = Fct.creRcRss();
	       ItemPrx editItem = req.edit(item);
	       editItem.setTitle(txTitle.getText());
	       editItem.setIsActive(cbItemAct.getCurrentValue().getValue());
	       req.merg(editItem).fire(mergReceiver);
	    }
	    @Override
	    public void insItem(){
	     	reqIns = Fct.creRcRss();
	     	ItemPrx o = reqIns.create(ItemPrx.class);
	     	o.setMail(curMail);
	     	o.setUrl(curUrl);
	     	o.setTitle("");
	     	o.setIsActive(stItemAct.get(0).getValue());
	        stT.add(0, o);
	    }
	    @Override
	    public String getItemName(ItemPrx item){
		return String.valueOf(item.getId());
	    }
	    @Override
	    public void delItem(ItemPrx item, Receiver<Void> R){
	    	Fct.creRcRss().remov(item).fire(R);
	    }
	    @Override
	    protected void beforEdit(){
	    	txTitle.getCell().getInputElement(txTitle.getElement()).setMaxLength(ItemPrx.LEN_Title);
	    }
    };
    
    tabMail.fill();
    tabUrl.fill();
    tabItem.fill();
    contMain.add( tabMail, new HtmlData(".mail"));
    contMain.add( tabUrl, new HtmlData(".url"));
    contMain.add( tabItem, new HtmlData(".item"));
    setWidget(contMain);
    getHeader().addTool(tbRssIsOff);
    }    
    private native String getMainMarkup() /*-{
    return [ '<table cellpadding=0 cellspacing=4 cols="2">',
        '<tr><td class=mail valign="top"></td><td class=item rowspan=2 valign="top"></td></tr>',
        '<tr><td class=url valign="top"></td></tr>',
        '</table>'
    ].join("");
  }-*/;
}
