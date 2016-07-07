package gx.client;

import java.security.MessageDigest;
import java.text.ParseException;
import java.util.List;

//import org.jboss.resteasy.util.Base64;










import java.util.Set;

import javax.validation.ConstraintViolation;

import gx.client.domain.FactRss;
import gx.client.domain.FactRss.rcRss;
import gx.client.domain.RolePrx;
import gx.client.domain.UrroPrx;
import gx.client.domain.UserPrx;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.info.Info;

public class PanRole extends ContentPanel{
    private static final int PAN_TAB_WIDTH = 1000;
    private static final int PAN_TAB_HEIGHT = 800;
//=================== Role ======================    
    private static final int PAN_ROLE_WIDTH = 220;
    private static final int PAN_ROLE_HEIGHT = 210;
    interface RoleProperties extends PropertyAccess<RolePrx> {
	    ModelKeyProvider<RolePrx> id();
	    @Path("id")
	    ValueProvider<RolePrx, Integer> idVal();
	    ValueProvider<RolePrx, String> code();
	  }
    private final RoleProperties propRole = GWT.create(RoleProperties.class);
 //=================== User ======================    
    private static final int PAN_USER_WIDTH = 220;
    private static final int PAN_USER_HEIGHT = 210;
    interface UserProperties extends PropertyAccess<UserPrx> {
	    ModelKeyProvider<UserPrx> id();
	    @Path("id")
	    ValueProvider<UserPrx, Integer> idVal();
	    ValueProvider<UserPrx, String> name();
	    ValueProvider<UserPrx, String> mail();
	    ValueProvider<UserPrx, String> pass();
	  }
    private final UserProperties propUser = GWT.create(UserProperties.class);
 //=================== Urro ======================
    private static final int PAN_URRO_WIDTH = 220;
    private static final int PAN_URRO_HEIGHT = 210;
    interface UrroProperties extends PropertyAccess<UrroPrx> {
	    ModelKeyProvider<UrroPrx> id();
	    @Path("id")
	    ValueProvider<UrroPrx, Integer> idVal();
	    ValueProvider<UrroPrx, RolePrx> role();
	    ValueProvider<UrroPrx, UserPrx> user();
	  }
    private final UrroProperties propUrro = GWT.create(UrroProperties.class);
 //========================================= 
    PanList<RolePrx> tabRole;
    PanList<UserPrx> tabUser;
    PanList<UrroPrx> tabUrro;
    StringComboBox cbRoles = new StringComboBox();
    interface AllUserProperties extends PropertyAccess<UserPrx> {
	    ModelKeyProvider<UserPrx> id();
//	    @Path("id")
//	    ValueProvider<UserPrx, Integer> idVal();
	    ValueProvider<UserPrx, String> name();
	  }
    private final AllUserProperties propAllUser = GWT.create(AllUserProperties.class);
    final ListStore<UserPrx> stAllUsers = new ListStore<UserPrx>(propAllUser.id());
    ComboBox<UserPrx> cbAllUsers;
    
    interface AllRoleProperties extends PropertyAccess<RolePrx> {
	    ModelKeyProvider<RolePrx> id();
	    ValueProvider<RolePrx, String> code();
	  }
    private final AllRoleProperties propAllRole = GWT.create(AllRoleProperties.class);
    final ListStore<RolePrx> stAllRoles = new ListStore<RolePrx>(propAllRole.id());
    ComboBox<RolePrx> cbAllRoles;

  //========================================= 
    public PanRole(final FactRss Fct, boolean isAdmin) {
		getHeader().addStyleName("txt_center");
		addStyleName("margin-10");
		setHeadingText("Ваши роли");
		setPixelSize(PAN_TAB_WIDTH, PAN_TAB_HEIGHT);
		if (!isAdmin){
		  makeUser(Fct);
		}else{
		  makeUserAdm(Fct);
	 }
	}
	
    private void makeUser(final FactRss Fct){
    	FramedPanel panelPass = new FramedPanel();
        panelPass.setHeadingText("Изменить пароль");
        panelPass.setWidth(400);
        panelPass.setBodyStyle("background: none; padding: 15px");
        VerticalLayoutContainer vert = new VerticalLayoutContainer();
        vert.setLayoutData(new MarginData(2));
        panelPass.add(vert);
        
        final TextField tfPass = new TextField();
        tfPass.setAllowBlank(false);
        tfPass.getCell().getInputElement(tfPass.getElement()).setMaxLength(UserPrx.LEN_pass);
        tfPass.setWidth(50);
        vert.add(new FieldLabel(tfPass, "Новый пароль"), new VerticalLayoutData(1, -1));
        panelPass.addButton(new TextButton("Сохранить", new SelectHandler(){
		       @Override
		       public void onSelect(SelectEvent event) {
//		    	   final rcRss req = Fct.creRcRss();
//			       UserPrx editUser = (UserPrx) 
		    	   String pass = tfPass.getText().trim();
			       if (!pass.isEmpty()) {
			    	 Fct.creRcRss().setPassByLogin(pass).fire(new Receiver<Void>() {
			    		  public void onSuccess(Void data) {
  		    			    tfPass.setValue("");
							Info.display("Пароль изменен", "Пароль изменен");
						  }
						  public void onFailure(ServerFailure error) {
						    Info.display("Пароль не изменен", "Пароль не изменен");
						    super.onFailure(error);
						  }
					  });
				  }
		    	   
//		    	   req.getUserByLogin().fire(new Receiver<UserPrx>() {
//			    		  public void onSuccess(UserPrx data) {
//						       String pass = tfPass.getText().trim();
//						       if (!pass.isEmpty()) {
//						    	   UserPrx user = Fct.creRcRss().create(UserPrx.class);
//						    	   user.setPass(pass);
//						    	   user.setId(data.getId());
//						    	   user.setMail(data.getMail());
//						    	   user.setName(data.getName());
//						    	   user.setUrros(data.getUrros());
//						    	   Fct.creRcRss().merg(user).fire(new Receiver<Void>() {
//							    		  public void onSuccess(Void data) {
//							    			  tfPass.setValue("");
//							    			  Info.display("Пароль изменен", "Пароль изменен");
//							    		  }
//							    		  public void onFailure(ServerFailure error) {
//							    			  Info.display("Пароль не изменен", "Пароль не изменен");
//							    			  super.onFailure(error);
//							    		  }
//							        });
//						       }
//			    			  }
//	    		          public void onFailure(ServerFailure error) {
//			    			 super.onFailure(error);
//			    	      }		}
//			   	   );
//		    	   Info.display("Music Changed", "email = " + pass.getValue());
		        }}));
        
        VerticalPanel vp = new VerticalPanel();
	    vp.setSpacing(10);
        vp.add(panelPass);
        setWidget(vp);
    }
    
    private void makeUserAdm(final FactRss Fct){
	    HtmlLayoutContainer contMain = new HtmlLayoutContainer(getMainMarkup());
    //====================== tabUrro
	    tabUrro = new PanList<UrroPrx>(PAN_URRO_WIDTH, PAN_URRO_HEIGHT, "Твоя роль"){
		    ColumnConfig<UrroPrx, Integer> ccIdVal;
		    ColumnConfig<UrroPrx, RolePrx> ccRole;
		    ColumnConfig<UrroPrx, UserPrx> ccUser;
		    rcRss reqIns;
		    @Override
	        public void fill(){
		       ccIdVal = new ColumnConfig<UrroPrx, Integer>( propUrro.idVal(), 20, "id");
		       ccIdVal.setCell(new AbstractCell<Integer>() {
				      @Override
				      public void render(Context context, Integer value, SafeHtmlBuilder sb) {
				    	  sb.appendHtmlConstant(value == null? "?": value.toString());
				      } });
		       
		       ccRole = new ColumnConfig<UrroPrx, RolePrx>(propUrro.role(), 40, "Роль");
		       ccRole.setCell(new AbstractCell<RolePrx>() {
				      @Override
				      public void render(Context context, RolePrx value, SafeHtmlBuilder sb) {
				    	  sb.appendHtmlConstant(value == null? "?": value.getCode());
				      } });
		       
		       ccUser = new ColumnConfig<UrroPrx, UserPrx>(propUrro.user(), 40, "User");
		       ccUser.setCell(new AbstractCell<UserPrx>() {
				      @Override
				      public void render(Context context, UserPrx value, SafeHtmlBuilder sb) {
				    	  sb.appendHtmlConstant(value == null? "?": value.getName());
				      } });
			   getCcL().add(ccIdVal);
		       getCcL().add(ccUser);
		       getCcL().add(ccRole);

		       setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<UrroPrx>>() {
				@Override
				public void load(ListLoadConfig loadConfig,	Receiver<? super ListLoadResult<UrroPrx>> receiver) {
				  rcRss req = Fct.creRcRss();
	   	 		  List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
	     		  req.getListUrro(sortInfo).with("data.role","data.user").to(receiver).fire();
	     		  Fct.creRcRss().getAllRole().fire(new Receiver<List<RolePrx>>(){
	    		  	  @Override
	    		  	  public void onSuccess(List<RolePrx> response){
	    		  		if (response != null)  cbAllRoles.getStore().replaceAll((List<RolePrx>)response);
	    		  		else cbAllRoles.clear();
	    		  	   }});
	     		  Fct.creRcRss().getAllUser().fire(new Receiver<List<UserPrx>>(){
	    		  	  @Override
	    		  	  public void onSuccess(List<UserPrx> response){
	    		  		if (response != null)  cbAllUsers.getStore().replaceAll((List<UserPrx>)response);
	    		  		else cbAllUsers.clear();
	    		  	   }});

				}});
		       setStT(new ListStore<UrroPrx>(propUrro.id()));  

		       cbAllRoles = new ComboBox<RolePrx>(stAllRoles, new LabelProvider<RolePrx>(){
				@Override
				public String getLabel(RolePrx item) {
					return item==null ? "": item.getCode();
				}});
		       cbAllRoles.setPropertyEditor(new PropertyEditor<RolePrx>() {
			          @Override
			          public RolePrx parse(CharSequence text) throws ParseException {
			            for(RolePrx it: stAllRoles.getAll()){
			        	if (text.equals(it.getCode())) return it;
			            }
			            return null;
			          }
			          @Override
			          public String render(RolePrx object) {
			            return object == null ? "XXX" : object.getCode();
			          }});
		       cbAllRoles.setTriggerAction(TriggerAction.ALL);
		       cbAllRoles.setForceSelection(true);
		       
		       cbAllUsers = new ComboBox<UserPrx>(stAllUsers, new LabelProvider<UserPrx>(){
				@Override
				public String getLabel(UserPrx item) {
					return item==null ? "": item.getName();
				}});
		       cbAllUsers.setPropertyEditor(new PropertyEditor<UserPrx>() {
			          @Override
			          public UserPrx parse(CharSequence text) throws ParseException {
			            for(UserPrx it: stAllUsers.getAll()){
			        	if (text.equals(it.getName())) return it;
			            }
			            return null;
			          }
			          @Override
			          public String render(UserPrx object) {
			            return object == null ? "XXX" : object.getName();
			          }});
		       cbAllUsers.setTriggerAction(TriggerAction.ALL);
		       cbAllUsers.setForceSelection(true);

		       initValues(true, true, true);
		       
		       getEditing().addEditor(ccUser, cbAllUsers);
		       getEditing().addEditor(ccRole, cbAllRoles);	
	        }
		    @Override
		    public void mergItem(UrroPrx item){
		       rcRss req = null;
		       if (isIns) {req = reqIns; isIns = false;}
		       else req = Fct.creRcRss();
		       UrroPrx editItem = req.edit(item);
		       editItem.setRole(cbAllRoles.getCurrentValue());
		       editItem.setUser(cbAllUsers.getCurrentValue());
		       req.merg(editItem).fire(mergReceiver);
		    }
		    @Override
		    public void insItem(){
		     	reqIns = Fct.creRcRss();
		     	UrroPrx o = reqIns.create(UrroPrx.class);
		     	o.setRole(cbAllRoles.getStore().get(0));
		     	o.setUser(cbAllUsers.getStore().get(0));
		        stT.add(0, o);
		    }
		    @Override
		    public String getItemName(UrroPrx item){
			return String.valueOf(item.getId());
		    }
		    @Override
		    public void delItem(UrroPrx item, Receiver<Void> R){
		    	Fct.creRcRss().remov(item).fire(R);
		    }

	    };
	//====================== tabRole  
	    tabRole = new PanList<RolePrx>(PAN_ROLE_WIDTH, PAN_ROLE_HEIGHT, "Role"){
		    ColumnConfig<RolePrx, Integer> ccIdVal;
		    ColumnConfig<RolePrx, String> ccCode;
//		    TextField txName = new TextField();
		    rcRss reqIns;
		    @Override
	        public void fill(){
		       ccIdVal = new ColumnConfig<RolePrx, Integer>( propRole.idVal(), 20, "id");
		       ccIdVal.setCell(new AbstractCell<Integer>() {
				      @Override
				      public void render(Context context, Integer value, SafeHtmlBuilder sb) {
				    	  sb.appendHtmlConstant(value == null? "?": value.toString());
				      } });
		       ccCode = new ColumnConfig<RolePrx, String>(propRole.code(), 40, "Name");
		       ccCode.setCell(new AbstractCell<String>() {
				      @Override
				      public void render(Context context, String value, SafeHtmlBuilder sb) {
				    	  sb.appendHtmlConstant(value);
				      } });

			   getCcL().add(ccIdVal);
		       getCcL().add(ccCode);

		       setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<RolePrx>>() {
				@Override
				public void load(ListLoadConfig loadConfig,	Receiver<? super ListLoadResult<RolePrx>> receiver) {
				  rcRss req = Fct.creRcRss();
	   	 		  List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
	     		  req.getListRole(sortInfo).to(receiver).fire();
				}});
		       setStT(new ListStore<RolePrx>(propRole.id()));  
		       for(String it: RolePrx.codeRole){
		    	   cbRoles.add(it);
		       }
//		       cbRoles.setPropertyEditor(new PropertyEditor<String>() {
//				@Override
//				public String render(String object) {
//					return object == null ? "XXX" : object;
//				}
//				@Override
//				public String parse(CharSequence text) throws ParseException {
//					return text.toString();
//				}
//                });

		       cbRoles.setEditable(false);
		       cbRoles.setTriggerAction(TriggerAction.ALL);
		       initValues(true, true, true);
		       
		       getEditing().addEditor(ccCode, cbRoles);	
//		       getEditing().addEditor(ccCode, new Converter<String, String>(){
//				@Override
//				public String convertFieldValue(String object) {
//					return object == null ? "" : object;
//				}
//				@Override
//				public String convertModelValue(String object) {
//					return object == null ? "" : object;
//				}}, cbRoles);
//
	        }
		    @Override
		    public void mergItem(RolePrx item){
		       rcRss req = null;
		       if (isIns) {req = reqIns; isIns = false;}
		       else req = Fct.creRcRss();
		       RolePrx editItem = req.edit(item);
		       editItem.setCode(cbRoles.getCurrentValue());
		       req.merg(editItem).fire(mergReceiver);
		    }
		    @Override
		    public void insItem(){
		     	reqIns = Fct.creRcRss();
		     	RolePrx o = reqIns.create(RolePrx.class);
		     	o.setCode(cbRoles.getStore().get(0));
		        stT.add(0, o);
		    }
		    @Override
		    public String getItemName(RolePrx item){
			return String.valueOf(item.getId());
		    }
		    @Override
		    public void delItem(RolePrx item, Receiver<Void> R){
		    	Fct.creRcRss().remov(item).fire(R);
		    }
//		    @Override
//		    protected void beforEdit(){
//		    	txName.getCell().getInputElement(txName.getElement()).setMaxLength(RolePrx.LEN_name);
//		    }
	};
	//====================== tabUser
    tabUser = new PanList<UserPrx>(PAN_USER_WIDTH, PAN_USER_HEIGHT, "User"){
	    ColumnConfig<UserPrx, Integer> ccIdVal;
	    ColumnConfig<UserPrx, String> ccName;
	    ColumnConfig<UserPrx, String> ccPass;
	    TextField txName = new TextField();
	    TextField txPass = new TextField();
	    rcRss reqIns;
	    @Override
        public void fill(){
	       ccIdVal = new ColumnConfig<UserPrx, Integer>( propUser.idVal(), 20, "id");
	       ccIdVal.setCell(new AbstractCell<Integer>() {
			      @Override
			      public void render(Context context, Integer value, SafeHtmlBuilder sb) {
			    	  sb.appendHtmlConstant(value == null? "?": value.toString());
			      } });
	       ccName = new ColumnConfig<UserPrx, String>(propUser.name(), 40, "Name");
	       ccPass = new ColumnConfig<UserPrx, String>(propUser.pass(), 40, "Pass");
	       ccPass.setCell(new AbstractCell<String>() {
			      @Override
			      public void render(Context context, String value, SafeHtmlBuilder sb) {
			    	  sb.appendHtmlConstant("*");
			      } });
		   getCcL().add(ccIdVal);
	       getCcL().add(ccName);
	       getCcL().add(ccPass);
	       
	       setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<UserPrx>>() {
			@Override
			public void load(ListLoadConfig loadConfig,	Receiver<? super ListLoadResult<UserPrx>> receiver) {
			  rcRss req = Fct.creRcRss();
   	 		  List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
     		  req.getListUser(sortInfo).to(receiver).fire();
			}});
	       setStT(new ListStore<UserPrx>(propUser.id()));    
	       initValues(true, true, true);
	       
	       getEditing().addEditor(ccName, txName);		 
	       getEditing().addEditor(ccPass, txPass);	
        }
	    @Override
	    public void mergItem(UserPrx item){
	       rcRss req = null;
	       if (isIns) {req = reqIns; isIns = false;}
	       else req = Fct.creRcRss();
	       UserPrx editItem = req.edit(item);
	       editItem.setName(txName.getText());
//	       editItem.setPass(txPass.getText());
	       String pass = txPass.getText().trim();
	       if (!pass.isEmpty()) {
	    	 editItem.setPass(pass);
	       }
	       req.merg(editItem).fire(mergReceiver);
	    }
	    @Override
	    public void insItem(){
	     	reqIns = Fct.creRcRss();
	     	UserPrx o = reqIns.create(UserPrx.class);
	     	o.setName("");
	     	o.setPass("");
	        stT.add(0, o);
	    }
	    @Override
	    public String getItemName(UserPrx item){
		return String.valueOf(item.getId());
	    }
	    @Override
	    public void delItem(UserPrx item, Receiver<Void> R){
	    	Fct.creRcRss().remov(item).fire(R);
	    }
	    @Override
	    protected void beforEdit(){
	    	txName.getCell().getInputElement(txName.getElement()).setMaxLength(UserPrx.LEN_name);
	    	txPass.getCell().getInputElement(txName.getElement()).setMaxLength(UserPrx.LEN_pass);
	    	txPass.setValue("");
	    }
};
	
	//======================
    tabRole.fill();
    tabUser.fill();
    tabUrro.fill();
    contMain.add( tabRole, new HtmlData(".role"));
    contMain.add( tabUser, new HtmlData(".user"));
    contMain.add( tabUrro, new HtmlData(".urro"));
    setWidget(contMain);
    }
    private native String getMainMarkup() /*-{
    return [ '<table cellpadding=0 cellspacing=4 cols="2">',
        '<tr><td class=role valign="top"></td><td class=user rowspan=2 valign="top"></td></tr>',
        '<tr><td class=urro valign="top"></td></tr>',
        '</table>'
    ].join("");
  }-*/;

}
