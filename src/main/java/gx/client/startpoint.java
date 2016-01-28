package gx.client;

import java.util.List;

import gx.client.domain.FactRss;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class startpoint implements EntryPoint {
	private FactRss fct;
	NavPan navPanel;
	FlowLayoutContainer mp = new FlowLayoutContainer();
	
  public void onModuleLoad() {
	  fct = GWT.create(FactRss.class);
	  fct.initialize(new SimpleEventBus());
	  ToolBar botBar = new ToolBar();
	  final Status userState = new Status();
	  userState.setWidth(450);
	  
	  navPanel = new NavPan(mp, fct);
	  fct.creRcRss().getUserInfo().fire(new Receiver<List<String>>() {
	      public void onSuccess(List<String> data) {
	    	if (data != null && data.size() >= 2){
		      userState.setText(data.get(0).concat(" ").concat(data.get(1))); 
	    	}else userState.setText("гюзер не найден"); 
	    	navPanel.setActive(data.get(1));
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
