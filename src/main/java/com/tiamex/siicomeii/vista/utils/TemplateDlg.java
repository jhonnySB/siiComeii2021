package com.tiamex.siicomeii.vista.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/** @author cerimice **/
/** @company tiamex **/

/** @param <T> la clase que se va administrar **/
public abstract class TemplateDlg<T> extends Panel{
    
    protected SiiComeiiUI ui;
    
    protected TextField searchField;
    protected Button btnSearch;
    protected Button btnAdd;
    protected Button buttonDataImport;
    protected Button buttonDataExport;
    
    protected VerticalLayout contentLayout;
    
    protected Grid<T> grid;
    
    private VerticalLayout main;
    public VerticalLayout getMain(){return main;}
    
    public TemplateDlg(){
        initDlg();
    }    
    
    private void initDlg(){
        ui = Element.getUI();
        ResponsiveLayout content = new ResponsiveLayout();
            content.setSizeFull();
        
        searchField = new TextField();
            Element.cfgComponent(searchField);
            searchField.setPlaceholder("Buscar");
            searchField.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {buttonSearchEvent();});
        btnSearch = new Button("Buscar");
            Element.cfgComponent(btnSearch);
            btnSearch.addStyleName(ValoTheme.BUTTON_PRIMARY);
            btnSearch.addClickListener((Button.ClickEvent event) -> {buttonSearchEvent();});
        btnAdd = new Button("Agregar");
            Element.cfgComponent(btnAdd);
            btnAdd.addStyleName(ValoTheme.BUTTON_FRIENDLY);
            btnAdd.addClickListener((Button.ClickEvent event) -> {buttonAddEvent();});
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1,true,false);
            row1.addColumn().withDisplayRules(12,6,6,8).withComponent(searchField);
            row1.addColumn().withDisplayRules(12,3,3,2).withComponent(btnSearch);
            row1.addColumn().withDisplayRules(12,3,3,2).withComponent(btnAdd);
        
        grid = new Grid<>("");
            Element.cfgComponent(grid);
            grid.setHeight(Element.windowHeightPx(100)+"px");
            grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
            grid.setSelectionMode(SelectionMode.SINGLE);
            grid.addSelectionListener((SelectionEvent<T> event) -> {gridEvent();});
            
            grid.addComponentColumn(this::buildEditButton).setMaximumWidth(50);
            
        ResponsiveRow row3 = content.addRow().withAlignment(Alignment.MIDDLE_CENTER);
            Element.cfgLayoutComponent(row3,true,false);
            row3.addColumn().withDisplayRules(12,12,12,12).withComponent(grid);
        
        contentLayout = new VerticalLayout();
            Element.cfgLayoutComponent(contentLayout,true,false);
        
        main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,false);
            main.addComponent(content);
            //main.addComponent(contentLayout);
                
        this.setSizeFull();
        this.setContent(main);
        this.setCaptionAsHtml(true);
        this.setHeight(Element.windowHeightPx());
    }
    
    private Button buildEditButton(T obj){
        Button button = new Button(VaadinIcons.EDIT);
            button.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
            button.setDescription("Editar");
            button.addClickListener(e -> eventEditButtonGrid(obj));
            return button;
    }
    
    public void updateDlg(){
        buttonSearchEvent();
    }
    
    protected abstract void buttonSearchEvent();
    protected abstract void buttonAddEvent();
    protected abstract void gridEvent();    
    
    protected abstract void eventEditButtonGrid(T obj);
}
