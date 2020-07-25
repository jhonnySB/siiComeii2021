package com.tiamex.siicomeii.vista.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/** @author cerimice **/
/** @company tiamex **/

public abstract class TemplateModalWin extends Window{
    
    protected SiiComeiiUI ui;
    
    protected long id;
    
    protected VerticalLayout contentLayout;
    protected Button delete;
    protected Button accept;
    protected Button cancel;
    
    public TemplateModalWin(){
        initDlg();
    }
    
    private void initDlg(){
        ui = Element.getUI();
        ResponsiveLayout actions = new ResponsiveLayout();
        
        delete = new Button("Eliminar");
            Element.cfgComponent(delete);
            delete.addClickListener((Button.ClickEvent event) -> {buttonDeleteEvent();});
            delete.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        accept = new Button("Aceptar");
            Element.cfgComponent(accept);
            accept.addClickListener((Button.ClickEvent event) -> {buttonAcceptEvent();});
            accept.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel = new Button("Cancelar");
            Element.cfgComponent(cancel);
            cancel.addClickListener((Button.ClickEvent event) -> {buttonCancelEvent();});
            cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        ResponsiveRow row1 = actions.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,true);
            row1.addColumn().withDisplayRules(12,4,3,3).withComponent(delete);
            row1.addColumn().withDisplayRules(12,4,3,3).withComponent(accept);
            row1.addColumn().withDisplayRules(12,4,3,3).withComponent(cancel);
        
        contentLayout = new VerticalLayout();
            Element.cfgLayoutComponent(contentLayout,false,false);
        
        VerticalLayout main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(contentLayout);
            main.addComponent(actions);
            main.setComponentAlignment(contentLayout,Alignment.MIDDLE_CENTER);
        
        this.setContent(main);
        this.setModal(true);
        this.setClosable(true);
        this.setResizable(false);
        this.setCaptionAsHtml(true);
        this.setWidth(Element.windowWidthPercent());
    }
    
    protected abstract void loadData(long id);
    protected abstract void buttonDeleteEvent();
    protected abstract void buttonAcceptEvent();
    protected abstract void buttonCancelEvent();
}
