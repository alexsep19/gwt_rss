package gx.client;


import gx.client.domain.FactRss;
import gx.client.domain.RolePrx;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class NavPan extends ContentPanel{
	FactRss fct;
	PanRss panRss = null;
    PanLog panLog = null;
    PanRole panRole = null;
    TextButton bRss = null, bLog = null, bRole;
    FlowLayoutContainer contPan = null;
    boolean[] userRoles = new boolean[RolePrx.codeRole.length];
    
    public void setActive(String Role){
    	
    for(int i = 0; i < RolePrx.codeRole.length; i++){
    	userRoles[i] = Role.indexOf(RolePrx.codeRole[i]) >= 0;
    }
	bRss.setEnabled(true);
	bRole.setEnabled(userRoles[RolePrx.ROLE_ADMIN]);
	bLog.setEnabled(userRoles[RolePrx.ROLE_ADMIN]);
	if (panRss == null) panRss = new PanRss(fct, userRoles);
	ShowPan(panRss);
    }
    
    public NavPan(final FlowLayoutContainer blc, final FactRss Fct){
	    fct = Fct;
	    contPan = blc;
	    setHeadingText("Навигация");
	    getHeader().addStyleName("txt_center");
        VBoxLayoutContainer bc = new VBoxLayoutContainer();
        bc.setPadding(new Padding(1));
        bc.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
        bRss = new TextButton("Rss", new SelectHandler(){
	       @Override
	       public void onSelect(SelectEvent event) {
		     if (panRss == null) panRss = new PanRss(fct, userRoles);
		     ShowPan(panRss);
	        }});
        bRss.setEnabled(false);
        bc.add( bRss, new BoxLayoutData(new Margins(0, 0, 1, 0)));
        
        bRole = new TextButton("Role", new SelectHandler(){
	       @Override
	       public void onSelect(SelectEvent event) {
	    	bRole.setEnabled(false);
		    if (panRole == null) panRole = new PanRole(fct);
		    ShowPan(panRole);
	        bRole.setEnabled(true);
	        }});
        bc.add( bRole, new BoxLayoutData(new Margins(0, 0, 1, 0)));
 
        bLog = new TextButton("Журнал", new SelectHandler(){
	       @Override
	       public void onSelect(SelectEvent event) {
		   if (panLog == null) panLog = new PanLog(fct);
		   ShowPan(panLog);
	       }}); 
        bLog.setEnabled(false);
        bc.add( bLog, new BoxLayoutData(new Margins(0)));
        
        add(bc);
    }
    
    private void ShowPan(ContentPanel pan){
    	contPan.clear();
    	contPan.add(pan);
    }
}
