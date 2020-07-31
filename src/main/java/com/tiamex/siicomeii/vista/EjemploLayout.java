package com.tiamex.siicomeii.vista;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

/** @author cerimice **/
public class EjemploLayout extends Panel{
    
    private TextField campo1;
    private TextField campo2;
    private TextField campo3;
    private TextField campo4;
    
    public EjemploLayout(){
        init();
    }
    
    private void init(){
        
        campo1 = new TextField();
            Element.cfgComponent(campo1,"Campo 1");
        campo2 = new TextField();
            Element.cfgComponent(campo2,"Campo 2");
        campo3 = new TextField();
            Element.cfgComponent(campo3,"Campo 3");
        campo4 = new TextField();
            Element.cfgComponent(campo4,"Campo 4");
        
        /*HorizontalLayout horizontalLayout = new HorizontalLayout();
            Element.cfgLayoutComponent(horizontalLayout,true,true);
            horizontalLayout.addComponent(campo1);
            horizontalLayout.addComponent(campo2);
            horizontalLayout.addComponent(campo3);
            horizontalLayout.addComponent(campo4);
        setContent(horizontalLayout);*/
        
        /*VerticalLayout verticalLayout = new VerticalLayout();
            Element.cfgLayoutComponent(verticalLayout,true,true);
            verticalLayout.addComponent(campo1);
            verticalLayout.addComponent(campo2);
            verticalLayout.addComponent(campo3);
            verticalLayout.addComponent(campo4);
        setContent(verticalLayout);*/
        
        ResponsiveLayout responsiveLayout = new ResponsiveLayout();
            Element.cfgLayoutComponent(responsiveLayout);
        ResponsiveRow row = responsiveLayout.addRow().withAlignment(Alignment.TOP_CENTER);
            Element.cfgLayoutComponent(row,true,true);
            row.addColumn().withDisplayRules(12,6,3,1).withComponent(campo1);
            row.addColumn().withDisplayRules(12,6,3,1).withComponent(campo2);
            row.addColumn().withDisplayRules(12,6,3,1).withComponent(campo3);
            row.addColumn().withDisplayRules(12,6,3,1).withComponent(campo4);
        setContent(responsiveLayout);
        
        setCaption("Ejemplo Layout");
    }
}
