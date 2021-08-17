package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoEventoDlg extends TemplateDlg<ProximoEvento>{
    ListDataProvider<ProximoEvento> dataProvider = DataProvider.ofCollection(ControladorProximoEvento.getInstance().getAll());
    List<ProximoEvento> filterList=null;
    TextField filtroTitulo;
    DateField fechaInicioF;
    DateField fechaFinF;
    Button btnClear;
    public ProximoEventoDlg() throws Exception{
        init();
    }

    private void init(){
        searchField.setVisible(false); btnSearch.setVisible(false);
        content.removeComponent(resBusqueda); 
        grid.setHeightByRows(6); 
        grid.setResponsive(true); grid.setRowHeight(52); grid.setHeaderRowHeight(40);
        //grid.setHeightMode(HeightMode.CSS);
        grid.addColumn(ProximoEvento::getTitulo).setCaption("<b>Título</b>").setHidable(false).setId("colTitulo");
        grid.addColumn(ProximoEvento::getFechaFrm).setCaption("Fecha").setHidable(true)
                .setHidingToggleCaption("Mostrar Fecha").setId("colFecha").setMinimumWidth(480).setMaximumWidth(480);
        grid.addComponentColumn(proxEvento -> {
            String desc = proxEvento.getDescripcion();
            Label lblDes = new Label();
            lblDes.setContentMode(ContentMode.HTML);
            lblDes.setValue("<span title=\""+proxEvento.getDescripcion()+"\" style=\"display:inline-block;white-space:normal;\">"+desc+"</span>");
            return lblDes;
        }).setMaximumWidth(300).setCaption("Descripción").setHidable(true).setHidingToggleCaption("Mostrar Descripción");
        //grid.addColumn(ProximoEvento::getDescripcion).setCaption("Descripción").setHidable(true).setHidingToggleCaption("Mostrar Descripción").setMaximumWidth(400); 
        grid.addColumn(ProximoEvento::getImagen).setCaption("Imagen").setHidable(true).setHidingToggleCaption("Mostrar Imagen");
        grid.addColumn(ProximoEvento::getObjUsuario).setCaption("Usuario").setHidable(true).setHidingToggleCaption("Mostrar Usuario").setHidden(true);
        setCaption("<b>Próximos eventos</b>"); 
         
        HeaderRow headerTitulo = grid.appendHeaderRow(); 
        headerTitulo.getCell("colTitulo").setComponent(buildFilterTitulo());
        headerTitulo.getCell("colFecha").setComponent(buildFilterDate());
        eventMostrar();
    }
    
    public HorizontalLayout buildFilterDate(){
        HorizontalLayout hLayout = new HorizontalLayout();
        fechaInicioF = new DateField(); fechaInicioF.setZoneId(ZoneId.systemDefault());
        fechaInicioF.addStyleName(ValoTheme.DATEFIELD_TINY);
        fechaInicioF.setWidth("150px");
        fechaInicioF.setPlaceholder("Fecha inicio"); fechaInicioF.setDescription("Selecciona la fecha de inicio");
        fechaFinF = new DateField(); fechaFinF.setZoneId(ZoneId.systemDefault());
        fechaFinF.addStyleName(ValoTheme.DATEFIELD_TINY); 
        fechaFinF.setWidth("150px");
        fechaFinF.setPlaceholder("Fecha fin"); fechaFinF.setDescription("Selecciona la fecha final");
        if(dataProvider.getItems().isEmpty()){
            fechaInicioF.setEnabled(false);
            fechaFinF.setEnabled(false);
        }
        Label label = new Label("-");
        hLayout.addComponent(fechaInicioF);
        hLayout.addComponent(label);
        hLayout.addComponent(fechaFinF);
        btnClear = new Button();
        btnClear.setIcon(VaadinIcons.TRASH); btnClear.setDescription("Limpiar");
        if(fechaInicioF.getValue()==null || fechaFinF.getValue()==null )
           btnClear.setEnabled(false);
        btnClear.addClickListener((Button.ClickListener)event->{
            fechaInicioF.setValue(null);
            fechaFinF.setValue(null); 
            btnClear.setEnabled(false);
        });
        btnClear.addStyleName(ValoTheme.BUTTON_TINY); //btnClear.addStyleName(ValoTheme.BUTTON_ICON_ONLY); 
        hLayout.addComponent(btnClear);
        
        //fechaInicioF.addValueChangeListener(filterDate(fechaInicioF, fechaFinF, "colFecha"));
        fechaInicioF.addValueChangeListener((ValueChangeListener) event ->{
            if(fechaInicioF.getValue()!=null && fechaFinF.getValue()==null){
                filterDate(fechaInicioF);
                filtroTitulo.setEnabled(false);
                btnClear.setEnabled(true);
            }else{
                if (fechaInicioF.getValue() != null && fechaFinF.getValue() != null) {
                    //filterDateRange(fechaInicioF, fechaFinF);
                    dataProvider.setFilter(filter());
                    filtroTitulo.setEnabled(false);
                    btnClear.setEnabled(true);
                } else {
                    dataProvider.clearFilters();
                    filtroTitulo.setEnabled(true);
                    
                }
            }
            
        });
        
        //fechaFinF.addValueChangeListener(filterDate(fechaInicioF, fechaFinF, "colFecha"));
        fechaFinF.addValueChangeListener((ValueChangeListener) event ->{
            
            if (fechaInicioF.getValue() != null && fechaFinF.getValue() != null) {
                
                //filterDateRange(fechaInicioF, fechaFinF);
                filtroTitulo.setEnabled(false); 
                btnClear.setEnabled(true);
                filterList = new ArrayList<>();
                dataProvider.setFilter(filter());
            } else {
                dataProvider.clearFilters();
                filtroTitulo.setEnabled(true); 
            }
        });
        return hLayout;
    }
    
     private SerializablePredicate<ProximoEvento> filter(){
        SerializablePredicate<ProximoEvento> columnPredicate=null;
        columnPredicate = proxEvento -> {
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaFin = fechaFinF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime newFecha = proxEvento.getFecha().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaFin = fechaFin.withHour(0).withMinute(0).withSecond(0);
            if(newFechaIn.compareTo(newFecha) * newFecha.compareTo(newFechaFin) >= 0){
                if(!filterList.contains(proxEvento)){
                    filterList.add(proxEvento);
                }
            }
            return newFechaIn.compareTo(newFecha) * newFecha.compareTo(newFechaFin) >= 0;
        };
        
        return columnPredicate;
    }
    
    private void filterDate(DateField fechaInicioF) {
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            dataProvider.setFilter((ProximoEvento::getFecha), fecha -> { 
                if (fecha == null) { 
                    return false;  
                }
                LocalDateTime newFecha = fecha.withHour(0).withMinute(0).withSecond(0);
                LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
                return newFechaIn.compareTo(newFecha) == 0;
            });
            
    }
    
    private void filterDateRange(DateField fechaInicioF, DateField fechaFinF) {
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaFin = fechaFinF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            dataProvider.setFilter(ProximoEvento::getFecha, fecha -> {
                if (fecha == null) {
                    return false;  
                }
                LocalDateTime newFecha = fecha.withHour(0).withMinute(0).withSecond(0);
                LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
                LocalDateTime newFechaFin = fechaFin.withHour(0).withMinute(0).withSecond(0);
                return newFechaIn.compareTo(newFecha) * newFecha.compareTo(newFechaFin) >= 0; 
            });
    }
    
    public HorizontalLayout buildFilterTitulo(){
        HorizontalLayout layout = new HorizontalLayout();
        layout.setResponsive(true);
        //ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        filtroTitulo = new TextField();
        filtroTitulo.setWidth("100%");
        filtroTitulo.addStyleName(ValoTheme.TEXTFIELD_TINY);
        filtroTitulo.setPlaceholder("Buscar por título");
        filtroTitulo.setResponsive(true);
        filtroTitulo.setDescription("Ingresa las palabras clave");
        //filtroTitulo.setIcon(VaadinIcons.SEARCH);
        filtroTitulo.addValueChangeListener(event -> {
            if(event.getValue().compareTo("")!=0){
                fechaInicioF.setEnabled(false);
                fechaFinF.setEnabled(false);
                btnClear.setEnabled(false);
                
                dataProvider.setFilter(ProximoEvento::getTitulo, titulo -> {
                    if (titulo == null) {
                        return false;
                    }
                    String tituloMin = titulo.toLowerCase();
                    String filtroMin = event.getValue().toLowerCase();
                    return tituloMin.contains(filtroMin);
                });
                
            }else{
                fechaInicioF.setEnabled(true);
                fechaFinF.setEnabled(true);
                dataProvider.clearFilters();
            }
            
        });
        layout.addComponent(filtroTitulo);
        
        return layout;
    }
    
    @Override
    protected void eventMostrar(){ 
        dataProvider = DataProvider.ofCollection(ControladorProximoEvento.getInstance().getAll());
        grid.setDataProvider(dataProvider);
        if(dataProvider.getItems().isEmpty()){
            filtroTitulo.setEnabled(false);
        }else{
            filtroTitulo.setEnabled(true);
        }
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                resBusqueda.setContentMode(ContentMode.HTML);
                String strBusqueda = searchField.getValue();
                Collection<ProximoEvento> eventos = ControladorProximoEvento.getInstance().getByName(strBusqueda);
                int eventoSize = eventos.size();
                if(eventoSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(eventoSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(eventoSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(eventoSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(eventos);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorProximoEvento.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
               @Override
    protected void eventDeleteButtonGrid(ProximoEvento obj) {
        try {
            ui.addWindow(new ProximoEventoModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(ProximoEventoDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new ProximoEventoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(ProximoEvento obj) {
        ui.addWindow(new ProximoEventoModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(ProximoEvento obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(ProximoEvento obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void eventWebinarsAgremiado(ProximoEvento obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(ProximoEvento obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
