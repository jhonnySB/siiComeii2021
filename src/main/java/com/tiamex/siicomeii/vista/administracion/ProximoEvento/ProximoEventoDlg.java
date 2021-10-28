package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.Result;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vaadin.hene.popupbutton.PopupButton;

/** @author fred **/
public class ProximoEventoDlg extends TemplateDlg<ProximoEvento>{
    ListDataProvider<ProximoEvento> dataProvider = DataProvider.ofCollection(ControladorProximoEvento.getInstance().getAll());
    List<ProximoEvento> filterList=null;
    TextField filtroTitulo;
    DateField fechaInicioF;
    DateField fechaFinF;
    Button btnClear,btnToday;
    ZoneId zoneId = ZoneId.of("America/Mexico_City");
    public ProximoEventoDlg() throws Exception{
        init();
    }

    private void init(){
        SerializableComparator<ProximoEvento> comparator = (a1,a2)->{
            int y1 = a1.getFecha().getYear(), y2 = a2.getFecha().getYear(),
                    m1=a1.getFecha().getMonthValue(),m2=a2.getFecha().getMonthValue(),
                    d1=a1.getFecha().getDayOfMonth(),d2=a2.getFecha().getDayOfMonth();
            if(y1<y2)return -1;
            if(y1>y2)return 1;
            if(m1<m2)return -1;
            if(m1>m2)return 1;
            if(d1<d2)return -1;
            if(d1>d2)return 1;
            return 0;
        };
        grid.setHeaderRowHeight(40);
        grid.setResponsive(true);
        rowBar.removeAllComponents(); rowBar.addColumn().withComponent(btnAdd);
        grid.addColumn(ProximoEvento::getTitulo).setCaption("<b>Título</b>").setHidable(false).setId("colTitulo");
        grid.addColumn(ProximoEvento::getFechaFrm).setCaption("Fecha").setHidable(true).setHidingToggleCaption("Mostrar Fecha").
                setId("colFecha").setMinimumWidth(480).setComparator(comparator).setWidthUndefined();
        grid.addComponentColumn(proxEvento -> {
            String desc = proxEvento.getDescripcion();
            Label lblDes = new Label();
            lblDes.setContentMode(ContentMode.HTML);
            lblDes.setValue("<span title=\""+proxEvento.getDescripcion()+"\" style=\"display:inline-block;white-space:normal;\">"+desc+"</span>");
            return lblDes;
        }).setMaximumWidth(300).setCaption("Descripción").setHidable(true).setHidingToggleCaption("Mostrar Descripción");
        grid.addComponentColumn((ProximoEvento web) -> {
            if (web.getImagen() != null) {
                return createPopupImageBtn(web.getImagen(), web.getTitulo());
            }
            return new Label("Sin imagen");
        }).setCaption("Imagen").setHidable(true).setHidden(false).setHidingToggleCaption("Mostrar imagen").clearExpandRatio();
        grid.addColumn(ProximoEvento::getObjUsuario).setCaption("Usuario").setHidable(true).setHidingToggleCaption("Mostrar Usuario").setHidden(true);
        setCaption("<b>Próximos eventos</b>"); 
         
        HeaderRow headerTitulo = grid.appendHeaderRow(); 
        headerTitulo.getCell("colTitulo").setComponent(buildFilterTitulo());
        headerTitulo.getCell("colFecha").setComponent(buildFilterDate());
        eventMostrar();
        enableFilters();
    }
    
    private void enableFilters() {
        if (dataProvider.getItems().isEmpty()) {
            fechaInicioF.setEnabled(false);
        }
    }
    
    private PopupButton createPopupImageBtn(byte[] bytes, String title) {
        PopupButton popBtn = new PopupButton();
        VerticalLayout vL = new VerticalLayout();
        vL.setResponsive(true);
        vL.addStyleName(ValoTheme.ACCORDION_BORDERLESS);
        vL.setSpacing(false);
        vL.setMargin(false);
        popBtn.setCaption("Ver imagen");
        popBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        popBtn.setDirection(Alignment.BOTTOM_CENTER);
        Image img = new Image();
        img.setSource(new StreamResource(() -> new ByteArrayInputStream(bytes), "image_proxEvento_" + title));
        img.setWidth(250, Unit.PIXELS);
        img.setHeight(250, Unit.PIXELS);
        img.setResponsive(true);
        img.setAlternateText("No se pudo cargar la imagen");
        Button closeBtn = new Button();
        closeBtn.setIcon(VaadinIcons.CLOSE_SMALL);
        closeBtn.addStyleName(ValoTheme.BUTTON_TINY);
        closeBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        closeBtn.addClickListener((Button.ClickListener) listener -> {
            popBtn.setPopupVisible(false);
        });
        vL.addComponent(closeBtn);
        vL.addComponent(img);
        popBtn.setContent(vL);
        return popBtn;
    }
    
    public DateField buildDateField(String placeHolder, String description,String caption) {
        DateField dateField = new DateField() {
            @Override
            protected Result<LocalDate> handleUnparsableDateString(String dateString) {
                try {
                    LocalDate parsedAtServer = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    return Result.ok(parsedAtServer);
                } catch (DateTimeParseException e) {
                    return Result.error("Fecha no válida");
                }
            }
        };
        dateField.setResponsive(true);
        dateField.setTextFieldEnabled(false);
        dateField.setDefaultValue(LocalDate.now(ZoneId.systemDefault()));
        dateField.addStyleName(ValoTheme.DATEFIELD_TINY);
        dateField.setPlaceholder(placeHolder);
        dateField.setDescription(description);
        dateField.setShowISOWeekNumbers(true);
        dateField.setZoneId(ZoneId.of("America/Mexico_City"));
        dateField.setResolution(DateResolution.DAY);
        dateField.setLocale(new Locale("es", "MX"));
        return dateField;
    }
    
    public ResponsiveLayout buildFilterDate() {
        ResponsiveLayout lay = new ResponsiveLayout();
        lay.setResponsive(true);
        lay.setSizeFull();
        fechaInicioF = buildDateField("Fecha inicio", "Selecciona la fecha de inicio","");
        fechaFinF = buildDateField("Fecha fin", "Selecciona la fecha final","");
        fechaFinF.setEnabled(false);
        Label label = new Label("-");
        label.setResponsive(true);
        btnClear = new Button("Limpiar");
        btnClear.setResponsive(true);
        btnClear.setDescription("Reiniciar las celdas de selección de fecha");
        btnClear.setEnabled(false);
        btnClear.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(null);
            fechaFinF.setValue(null);
            fechaFinF.setEnabled(false);
            btnClear.setEnabled(false);
            dataProvider.clearFilters();
        });
        btnClear.addStyleNames(ValoTheme.BUTTON_TINY,ValoTheme.BUTTON_LINK); 
        //withComponents(fechaInicioF, label, fechaFinF, btnClear)
        
        btnToday= new Button("Hoy");
        btnToday.setResponsive(true);
        btnToday.addStyleNames(ValoTheme.BUTTON_TINY,ValoTheme.BUTTON_LINK);
        btnToday.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(LocalDate.now(zoneId));
        });
        
        ResponsiveRow row = lay.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row.withComponents(fechaInicioF,fechaFinF,btnToday,btnClear);
        row.setHorizontalSpacing(ResponsiveRow.SpacingSize.SMALL, true); row.setSizeFull();

        fechaInicioF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if(event.getValue()!=null){
                filterDate(fechaInicioF);
                fechaFinF.setRangeStart(fechaInicioF.getValue().plusDays(1));
                fechaFinF.setEnabled(true);
                btnClear.setEnabled(true);
            }
        });
        fechaFinF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if(event.getValue()!=null){
                dataProvider.setFilter(filter());
                fechaInicioF.setRangeEnd(((LocalDate)event.getValue()).minusDays(1));
            }
        });
        return lay;
    }
    
     private SerializablePredicate<ProximoEvento> filter(){
        SerializablePredicate<ProximoEvento> columnPredicate;
        columnPredicate = proxEvento -> {
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaFin = fechaFinF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime newFecha = proxEvento.getFecha().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaFin = fechaFin.withHour(0).withMinute(0).withSecond(0);
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
    
    public ResponsiveLayout buildFilterTitulo(){
        ResponsiveLayout lay = new ResponsiveLayout();
        lay.setResponsive(true); lay.setSizeFull();
        //ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        filtroTitulo = new TextField();
        filtroTitulo.setWidth("100%");
        filtroTitulo.addStyleName(ValoTheme.TEXTFIELD_TINY);
        filtroTitulo.setPlaceholder("Buscar por título");
        filtroTitulo.setResponsive(true);
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
        ResponsiveRow row = lay.addRow().withComponents(filtroTitulo).
                withAlignment(Alignment.MIDDLE_CENTER).withDefaultRules(12, 12, 12, 12);
        row.setSizeFull();
        //layout.addComponent(filtroTitulo);
        
        return lay;
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
