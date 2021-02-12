package com.tiamex.siicomeii.vista.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
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

    protected TextField searchField;
    protected Button btnSearch;
    protected Button btnAdd;
    protected Button buttonDataImport;
    protected Button buttonDataExport;
    protected Button buttonWebinar;
    protected Button buttonListado;
    protected Button buttonConstancias;
    protected int banBoton;
    protected Button tutorialsesion;
    protected VerticalLayout contentLayout;

    protected Grid<T> grid;

    public VerticalLayout main;

    public VerticalLayout getMain() {
        return main;
    }

    public TemplateDlg() {
        initDlg();
        
    }
    
    private void initDlg() {
        updateDlg();
        ui = Element.getUI();
        ResponsiveLayout content = new ResponsiveLayout();
        content.setSizeFull();

        searchField = new TextField();
        Element.cfgComponent(searchField);
        searchField.setPlaceholder("Buscar");
        searchField.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {
            buttonSearchEvent();
        });
        btnSearch = new Button("Buscar");
        Element.cfgComponent(btnSearch);
        btnSearch.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSearch.addClickListener((Button.ClickEvent event) -> {
            buttonSearchEvent();
        });
        btnAdd = new Button("Agregar");
        Element.cfgComponent(btnAdd);
        btnAdd.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnAdd.addClickListener((Button.ClickEvent event) -> {
            buttonAddEvent();
        });
        ResponsiveRow row1 = content.addRow().withAlignment(Alignment.BOTTOM_CENTER);
        Element.cfgLayoutComponent(row1, true, false);
        row1.addColumn().withDisplayRules(12, 6, 6, 8).withComponent(searchField);
        row1.addColumn().withDisplayRules(12, 3, 3, 2).withComponent(btnSearch);
        row1.addColumn().withDisplayRules(12, 3, 3, 2).withComponent(btnAdd);

        grid = new Grid<>("");
        Element.cfgComponent(grid);
        grid.setHeight(Element.windowHeightPx(100) + "px");
        grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addSelectionListener((SelectionEvent<T> event) -> {
            gridEvent();
        });

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

        switch (banBoton) {
            case 1: //botones webinar realizado
                grid.getColumn("botones").setMinimumWidth(220).setMaximumWidth(220).
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
                row.addColumn().withComponent(buttonListado);
                row.addColumn().withComponent(buttonWebinar);
                break;
            case 2: //botones agremiado para listado de webinar asistidos
                grid.getColumn("botones").setMinimumWidth(180).setMaximumWidth(180).
                        setEditable(false).clearExpandRatio();
                buttonConstancias = new Button(VaadinIcons.LIST);
                buttonConstancias.addStyleName(ValoTheme.BUTTON_LINK);
                row.addColumn().withComponent(buttonConstancias);
                buttonConstancias.addClickListener(e -> eventWebinarsAgremiado(obj));
                break;
            case 3:
                grid.getColumn("botones").setMinimumWidth(220).setMaximumWidth(220).
                        setEditable(false).clearExpandRatio().setCaption("Acción");
                tutorialsesion = new Button(VaadinIcons.ACADEMY_CAP);
                tutorialsesion.addStyleName(ValoTheme.BUTTON_LINK);
                tutorialsesion.addClickListener(e -> eventTutorialSesiones(obj));
                tutorialsesion.setEnabled(true);
                tutorialsesion.setDescription("Ingresar sesiones");
                row.addColumn().withComponent(tutorialsesion);
                break;
            default:
            grid.getColumn("botones").setMinimumWidth(80).setMaximumWidth(80).
                        setEditable(false);
        }
        row.addColumn().withComponent(button);
        //updateButtonsPdf();
        return layout;
    }

    public void updateDlg() {
        buttonSearchEvent();
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

    protected abstract void buttonSearchEvent();

    protected abstract void buttonAddEvent();

    protected abstract void gridEvent();

    protected abstract void eventEditButtonGrid(T obj);

    protected abstract void eventListaAsistentes(T obj);

    protected abstract void eventAsistenciaButton(T obj, String idBtn);
    
    protected abstract void eventWebinarsAgremiado(T obj);

    protected abstract void eventTutorialSesiones(T obj);

}
