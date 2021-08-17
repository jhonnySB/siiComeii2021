package com.tiamex.siicomeii.vista.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.vista.administracion.WebinarRealizado.WebinarRealizadoModalWin;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
/**
 * @company tiamex *
 */
/**
 * @param <T> la clase que se va administrar *
 */
public abstract class TemplateDlg<T> extends Panel {

    protected SiiComeiiUI ui;
    protected Label resBusqueda;
    protected TextField searchField;
    protected Button btnSearch;
    protected Button btnAdd;
    protected Button btnReturn;
    protected Button buttonDataImport;
    protected Button buttonDataExport;
    protected Button buttonWebinar;
    protected Button buttonListado;
    protected Button buttonConstancias;
    protected int banBoton;
    protected Button tutorialsesion;
    protected Button upWebinar;
    protected Button delete;
    protected VerticalLayout contentLayout;
    protected ResponsiveLayout content;
    protected ResponsiveRow row1;

    protected Grid<T> grid;

    public VerticalLayout main;

    public VerticalLayout getMain() {
        return main;
    }

    public TemplateDlg() throws Exception {
        initDlg();
        
    }
    
    private void initDlg() throws Exception {
        ui = Element.getUI();
        content = new ResponsiveLayout();
        content.setSizeFull();

        resBusqueda = new Label();
        Element.cfgComponent(resBusqueda);
        
        searchField = new TextField();
        Element.cfgComponent(searchField); searchField.setId("searchFld");
        searchField.setPlaceholder("Buscar"); 
        searchField.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {
            buttonSearchEvent();
        });
        btnSearch = new Button("Buscar");
        Element.cfgComponent(btnSearch); btnSearch.setId("btnSearch");
        btnSearch.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSearch.addClickListener((Button.ClickEvent event) -> {
            buttonSearchEvent();
        });
        btnAdd = new Button("Nuevo");
        Element.cfgComponent(btnAdd);
        btnAdd.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnAdd.addClickListener((Button.ClickEvent event) -> {
            buttonAddEvent();
        });
            row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
            Element.cfgLayoutComponent(row1, true, false);
                row1.addColumn().withDisplayRules(12, 6, 6, 8).withComponent(searchField);
                row1.addColumn().withDisplayRules(12, 3, 3, 2).withComponent(btnSearch);
                row1.addColumn().withDisplayRules(12, 3, 3, 2).withComponent(btnAdd); 
            
              
        
        ResponsiveRow row2 = content.addRow().withAlignment(Alignment.BOTTOM_LEFT);
            Element.cfgLayoutComponent(row2, false, false);
            row2.addColumn().withDisplayRules(12, 6, 6, 8).withComponent(resBusqueda);
        resBusqueda.setHeight("10px");
        
        grid = new Grid<>();
        Element.cfgComponent(grid);
        //grid.setHeight(Element.windowHeightPx(100) + "px");
        grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addSelectionListener((SelectionEvent<T> event) -> {
            gridEvent();
        });
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setColumnReorderingAllowed(true);
        grid.addComponentColumn(this::buildEditButton).setId("botones").setCaption("Acción");
        
        ResponsiveRow row3 = content.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        Element.cfgLayoutComponent(row3, true, false);
        row3.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(grid);

        contentLayout = new VerticalLayout();
        Element.cfgLayoutComponent(contentLayout, true, false);

        main = new VerticalLayout();
        Element.cfgLayoutComponent(main, true, false);
        main.addComponent(content); 
        main.addComponent(contentLayout); 
        this.setSizeFull();
        this.setContent(main);
        this.setCaptionAsHtml(true);
        this.setHeight(Element.windowHeightPx());  
    }

    private ResponsiveLayout buildEditButton(T obj) {
        ResponsiveLayout layout = new ResponsiveLayout();
        ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        Element.cfgLayoutComponent(row, true, false);

        Button button = new Button(VaadinIcons.EDIT);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.setDescription("Editar");
        button.addClickListener(e -> {
            eventEditButtonGrid(obj);
        });
        
        delete = new Button(VaadinIcons.TRASH);
        delete.addStyleName(ValoTheme.BUTTON_LINK);
        delete.setDescription("Eliminar");
        delete.addClickListener(e -> {
            eventDeleteButtonGrid(obj);
        });
        
        row.addColumn().withComponent(delete).setId("btnDel");
        row.addColumn().withComponent(button).setId("btnEdit");
        
        switch (banBoton) {
            case 1: //botones webinar realizado
                grid.getColumn("botones").setMinimumWidth(180).setMaximumWidth(180).
                        setEditable(false).clearExpandRatio();
                buttonListado = new Button(VaadinIcons.FILE_TEXT_O);
                buttonListado.addStyleName(ValoTheme.BUTTON_LINK);
                buttonListado.addClickListener(e -> eventListaAsistentes(obj));
                buttonWebinar = new Button(VaadinIcons.ENVELOPE);
                buttonWebinar.addStyleName(ValoTheme.BUTTON_LINK);
                buttonWebinar.addClickListener(e -> eventAsistenciaButton(obj, e.getButton().getId()));
                if (updateButtonCorreo()) {
                    buttonWebinar.setDescription("Enviar constancias a Agremiados por correo");
                } else {
                    buttonWebinar.setDescription("No se encontraron agremiados registrados");
                }
                if (obj.getClass().getSimpleName().compareTo("WebinarRealizado") == 0) {
                    WebinarRealizado wr = (WebinarRealizado) obj;
                    String idLista = Long.toString(wr.getId()) + "_lista";
                    buttonListado.setId(idLista);
                    buttonWebinar.setId(Long.toString(wr.getId()) + "_correo");
                    if (ControladorAsistenciaWebinar.getInstance().getByWebinar(wr.getId()).isEmpty()) {
                        buttonListado.setEnabled(false);
                        buttonListado.setDescription("No se encontraron asistentes al webinar");
                    } else {
                        buttonListado.setEnabled(true);
                        buttonListado.setDescription("Descargar listado de asistentes");
                    }
                }
                row.removeAllComponents();
                row.addColumn().withComponent(buttonListado);
                row.addColumn().withComponent(buttonWebinar);
                break;
            case 2: //botones agremiado para listado de webinar asistidos
                grid.getColumn("botones").setMinimumWidth(240).setMaximumWidth(240).
                        setEditable(false).clearExpandRatio();
                buttonConstancias = new Button(VaadinIcons.LIST);
                buttonConstancias.addStyleName(ValoTheme.BUTTON_LINK);
                row.addColumn().withComponent(buttonConstancias);
                buttonConstancias.addClickListener(e -> eventWebinarsAgremiado(obj));
                break;
            case 3:
                grid.getColumn("botones").setMinimumWidth(310).setMaximumWidth(310).
                        setEditable(false).clearExpandRatio().setCaption("Acción");
                tutorialsesion = new Button(VaadinIcons.ACADEMY_CAP);
                tutorialsesion.addStyleName(ValoTheme.BUTTON_LINK);
                tutorialsesion.addClickListener(e -> eventTutorialSesiones(obj));
                tutorialsesion.setEnabled(true);
                tutorialsesion.setDescription("Ingresar sesiones");
                row.addColumn().withComponent(tutorialsesion);
                break;
            case 4: //proximos webinars
                
                grid.getColumn("botones").setMinimumWidth(240).setMaximumWidth(240).
                        setEditable(false).clearExpandRatio().setCaption("Acción");
                upWebinar = new Button(VaadinIcons.ARCHIVE);
                upWebinar.addStyleName(ValoTheme.BUTTON_LINK);
                upWebinar.addClickListener(e -> this.eventWebinarRealizado(obj));
                upWebinar.setEnabled(true);
                upWebinar.setDescription("Agregar a webinars realizados");
                row.addColumn().withComponent(upWebinar);
                
                break;
            default:
                grid.getColumn("botones").setMinimumWidth(180).setMaximumWidth(180);
                break;
        }
        grid.getColumn("botones").setEditable(false);
        //updateButtonsPdf();
        return layout;
    }
    
    public void updateDlg(){
        eventMostrar();
    }

    public boolean updateButtonCorreo() {
        if (ControladorAgremiado.getInstance().getAll().isEmpty()) {
            buttonWebinar.setEnabled(false);
        } else {
            buttonWebinar.setEnabled(true);
        }
        return buttonWebinar.isEnabled();
    }

    public void updateButtonPdf(long idWebinar, String idBtnLista) {
        try {
            Component btn = findComponentById(grid, idBtnLista);
            if (!ControladorAsistenciaWebinar.getInstance().getByWebinar(idWebinar).isEmpty()) {
                ((Button) btn).setEnabled(true);
                ((Button) btn).setDescription("Descargar listado de asistentes ");
            } else {
                ((Button) btn).setEnabled(false);
                ((Button) btn).setDescription("No se encontraron asistentes al webinar");
            }
        } catch (Exception ex) {
            Logger.getLogger(TemplateDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Component findComponentById(HasComponents root, String id) {
        for (Component child : root) {
            if (id.equals(child.getId())) {
                return child; 
            } else if (child instanceof HasComponents) { 
                Component result = findComponentById((HasComponents) child, id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null; 
    }
   
    protected void eventWebinarRealizado(T obj){
        ProximoWebinar proxWebinar = (ProximoWebinar)obj;
        WebinarRealizado webinarR = (WebinarRealizado) ControladorWebinarRealizado.getInstance().getByNames(proxWebinar.getTitulo());
        if (webinarR != null) {
            ui.addWindow(new WebinarRealizadoModalWin(webinarR));
        } else {
            ui.addWindow(new WebinarRealizadoModalWin(proxWebinar));
        }
    }

    protected abstract void buttonSearchEvent();
    
    protected abstract void eventMostrar();

    protected abstract void buttonAddEvent();

    protected abstract void gridEvent();

    protected abstract void eventEditButtonGrid(T obj);
    
    protected abstract void eventDeleteButtonGrid(T obj);

    protected abstract void eventListaAsistentes(T obj);

    protected abstract void eventAsistenciaButton(T obj, String idBtn);
    
    protected abstract void eventWebinarsAgremiado(T obj);

    protected abstract void eventTutorialSesiones(T obj);

}
