package gx.client;

import java.util.List;
import java.util.logging.Level;

import gx.client.domain.FactRss;
import gx.client.domain.RolePrx;
import gx.client.domain.UrroPrx;
import gx.client.domain.UserPrx;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.Status;
import java.util.logging.Logger;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class startpoint implements EntryPoint {
	private FactRss fct;
	NavPan navPanel;
	FlowLayoutContainer mp = new FlowLayoutContainer();
    public static boolean[] userRoles = new boolean[RolePrx.codeRole.length];
    final Logger rootLogger = Logger.getLogger("");

  public void onModuleLoad() {
	  fct = GWT.create(FactRss.class);
	  fct.initialize(new SimpleEventBus());
	  ToolBar botBar = new ToolBar();
	  final Status userState = new Status();
	  userState.setWidth(450);
	  
	  navPanel = new NavPan(mp, fct);
	  fct.creRcRss().getUserInfo().with("user","role").fire(new Receiver<List<UrroPrx>>() {
	      public void onSuccess(List<UrroPrx> data) {
	    	if (data != null && data.size() >= 0){
//	    	if (data != null){
	    		String roles = "";
//	    		rootLogger.log(Level.INFO, "data.getUrros() = "+data.size());
	    		for(UrroPrx it: data){
//	    		   rootLogger.log(Level.INFO, "it.getRole() = "+it.getRole());
	    	      for(int i = 0; i < RolePrx.codeRole.length; i++){
 	    	        if (it.getRole().getCode().equals(RolePrx.codeRole[i])){
 	    	          userRoles[i] = true;
 	    	          break;
 	    	        }
	    	      }
	    	      roles += it.getRole().getCode() + ";";
	    	    }

//		      userState.setText(data.get(0).concat(" роль:").concat(data.get(1))); 
		      userState.setText(data.get(0).getUser().getName().concat(" роль:").concat(roles)); 
	    	}else userState.setText("гюзер не айден"); 
	    	navPanel.setActive(data.get(0).getUser());
	      }});
	  
		Viewport viewport = new Viewport();
		final BorderLayoutContainer blc = new BorderLayoutContainer();
	 	BorderLayoutData westData = new BorderLayoutData(100);
		westData.setMargins(new Margins(0, 5, 5, 5));
		westData.setCollapsible(true);
		westData.setSplit(true);
		westData.setCollapseMini(true);
		blc.setWestWidget(navPanel, westData);
		
	    mp.getScrollSupport().setScrollMode(ScrollMode.AUTO);
		blc.setCenterWidget(mp);
		
		BorderLayoutData southData = new BorderLayoutData(25);
//		southData.setMargins(new Margins(0, 5, 5, 5));
		southData.setMargins(new Margins(0));
		southData.setCollapsible(false);
		southData.setSplit(false);
		southData.setCollapseMini(false);
		
		botBar.add(userState);
		blc.setSouthWidget(botBar,southData);
		viewport.setWidget(blc);
		RootPanel.get().add(viewport);
 }
}
