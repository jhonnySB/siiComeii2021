package com.tiamex.siicomeii.vista.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/** @author cerimice */
public class Element{
    
    private static String systemName = "SiiComeii | Tiamex";
    public static String getSystemName(){return systemName;}
    public static void setSystemName(String nombre){systemName = nombre;}
    
    public static SiiComeiiUI getUI(){
        return (SiiComeiiUI)UI.getCurrent();
    }
    
    private static int windowWidth(){
        int width = 100;
        UI ui = UI.getCurrent();
        int screenWidth = ui.getPage().getBrowserWindowWidth();
        
        if(screenWidth <= 640){width = 100;}
        if(screenWidth >= 641){width = 75;}
        if(screenWidth >= 1025){width = 50;}
        return width;
    }
    
    private static int windowHeight(){
        UI ui = UI.getCurrent();
        int screenHeight = ui.getPage().getBrowserWindowHeight();
        return screenHeight;
    }
    
    public static String windowWidthPx(){
        return Element.windowWidth()+"px";
    }
    
    public static String windowWidthPercent(){
        return Element.windowWidth()+"%";
    }
    
    public static String windowHeightPx(){
        return (Element.windowHeight()-220)+"px";
    }
    
    public static int windowHeightPx(double percent){
        UI ui = UI.getCurrent();
        int screenHeight = (int)((ui.getPage().getBrowserWindowHeight()-220)*(percent/100.0));
        return screenHeight;
    }
    
    public static int windowHeightPx(int height){
        UI ui = UI.getCurrent();
        int screenHeight = (int)((ui.getPage().getBrowserWindowHeight()-220-height));
        return screenHeight;
    }
    
    public static Notification makeNotification(String description, Notification.Type type){
        Notification ejemplo = new Notification(systemName, description, type);
            ejemplo.setPosition(Position.TOP_CENTER);
            ejemplo.setHtmlContentAllowed(true);
            ejemplo.setDelayMsec(2000);
        return ejemplo;
    }
    
    public static Notification makeNotification(String description, Notification.Type type, Position position){
        Notification example = new Notification(systemName, description, type);
            example.setPosition(position);
            example.setHtmlContentAllowed(true);
            example.setDelayMsec(2000);
        return example;
    }
    
    public static Notification makeNotification(String description, Notification.Type type, Position position,String themeResource){
        Notification example = new Notification(systemName, description, type);
            example.setPosition(position);
            example.setHtmlContentAllowed(true);
            example.setDelayMsec(2000);
            example.setIcon(new ThemeResource(themeResource));
        return example;
    }
    
    public static void cfgLayoutComponent(HorizontalLayout component, boolean spacing, boolean margin){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setSpacing(spacing);
        component.setMargin(margin);
        component.setCaptionAsHtml(true);
        component.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
    }
    
    public static void cfgLayoutComponent(VerticalLayout component, boolean spacing, boolean margin){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setSpacing(spacing);
        component.setMargin(margin);
        component.setCaptionAsHtml(true);
        component.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
    }
    
    public static void cfgLayoutComponent(ResponsiveLayout component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        component.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
    }
    
    public static void cfgLayoutComponent(ResponsiveRow component, boolean spacing, boolean margin){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        component.setHorizontalSpacing(ResponsiveRow.SpacingSize.SMALL,spacing);
        component.setVerticalSpacing(ResponsiveRow.SpacingSize.SMALL,spacing);
        
        if(margin){
            component.setMargin(ResponsiveRow.MarginSize.SMALL,ResponsiveLayout.DisplaySize.XS);
            component.setMargin(ResponsiveRow.MarginSize.SMALL,ResponsiveLayout.DisplaySize.SM);
            component.setMargin(ResponsiveRow.MarginSize.SMALL,ResponsiveLayout.DisplaySize.MD);
            component.setMargin(ResponsiveRow.MarginSize.SMALL,ResponsiveLayout.DisplaySize.LG);}
        component.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
    }
    
    public static void cfgComponent(Label component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        component.setContentMode(ContentMode.HTML);
    }
    
    public static void cfgComponent(Label component,String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(DateField component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        component.setTextFieldEnabled(false);
    }
    
    public static void cfgComponent(DateField component, String caption){
        Element.cfgComponent(component);
        component.setDateFormat("dd/MM/yyyy");
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(DateTimeField component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(DateTimeField component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
        component.setDateFormat("dd/MM/yyyy HH:mm");
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(TextField component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        //component.setStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
    }
    
    public static void cfgComponent(TextField component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(TextArea component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(TextArea component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgElement(RichTextArea component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(RichTextArea component, String caption){
        cfgElement(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(ComboBox component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        component.setEmptySelectionAllowed(false);
    }
    
    public static void cfgComponent(ComboBox component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(ListSelect component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(ListSelect component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(Grid component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(Grid component, String caption){
        Element.cfgComponent(component);
        component.setCaption(caption);
    }
    
    public static void cfgComponent(Image component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);    
    }
    
    public static void cfgComponent(Button component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(CheckBox component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(CheckBox component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(ProgressBar component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(ProgressBar component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(TabSheet component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
    }
    
    public static void cfgComponent(TabSheet component, String caption){
        Element.cfgComponent(component);
        component.setCaption("<b><center>"+caption+"</center></b>");
    }
    
    public static void cfgComponent(Upload component){
        component.setWidth("100%");
        component.setResponsive(true);
        component.setCaptionAsHtml(true);
        component.setImmediateMode(true);
    }
    
    public static void cfgComponent(Upload component, String caption){
        Element.cfgComponent(component);
        component.setButtonCaption(""+caption+"");
    }
}
