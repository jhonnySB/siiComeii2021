package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAsistentesPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.reportes.webinarRealizadoChart;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.Result;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.Setter;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public class WebinarRealizadoDlg extends TemplateDlg<WebinarRealizado> {

    private Button btnCharts;
    ListDataProvider<WebinarRealizado> dataProvider;
    DateField fechaInicioF, fechaFinF;
    Button btnClear,btnToday;
    List<WebinarRealizado> filterList = new ArrayList<>();
    ZoneId zoneId = ZoneId.of("America/Mexico_City");
    Label errorLbl;

    public WebinarRealizadoDlg() throws Exception {
        init();
    }

    private void init() {
        btnCharts = new Button();
        btnCharts.setIcon(VaadinIcons.CHART);
        btnCharts.setResponsive(true);
        btnCharts.setDescription("Mostrar estadísticas de webinars realizados");
        SerializableComparator<WebinarRealizado> comparator = (web1,web2)->{
            int y1 = web1.getFecha().getYear(), y2 = web2.getFecha().getYear(),
                    m1=web1.getFecha().getMonthValue(),m2=web2.getFecha().getMonthValue(),
                    d1=web1.getFecha().getDayOfMonth(),d2=web2.getFecha().getDayOfMonth();
            if(y1<y2)return -1;
            if(y1>y2)return 1;
            if(m1<m2)return -1;
            if(m1>m2)return 1;
            if(d1<d2)return -1;
            if(d1>d2)return 1;
            return 0;
        };
        btnCharts.addClickListener(event -> {
            try {
                Window wdw = new Window();
                webinarRealizadoChart panelChart;
                wdw.setCaption(VaadinIcons.CHART.getHtml());
                wdw.setCaptionAsHtml(true);
                wdw.setModal(true);
                wdw.center();
                wdw.setClosable(true);
                wdw.setDraggable(true);
                wdw.setResponsive(true);
                wdw.setResizable(true);
                panelChart = new webinarRealizadoChart();
                wdw.setHeightUndefined();
                wdw.setWidth("75%");
                wdw.setHeightUndefined();
                wdw.setContent(panelChart);
                ui.addWindow(wdw);
            } catch (Exception ex) {
                Logger.getLogger(WebinarRealizadoDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        banBoton = 1;
        rowBar.removeAllComponents();
        rowBar.withComponents(searchField, btnCharts);
        grid.setHeaderRowHeight(40);
        grid.addColumn(WebinarRealizado::getNombre).setCaption("Nombre").setHidable(false);
        grid.addColumn(WebinarRealizado::getPonente).setCaption("Ponente").setHidable(true).setHidingToggleCaption("Mostrar Ponente");
        grid.addColumn(WebinarRealizado::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        grid.addColumn(WebinarRealizado::getFechaFrm).setCaption("Fecha").setHidable(true).setHidingToggleCaption("Mostrar Fecha").setId("colDate")
                .setMinimumWidth(480).setComparator(comparator);
        grid.addColumn(WebinarRealizado::getPresentacion).setCaption("Presentación").setHidable(true).
                setHidingToggleCaption("Mostrar Presentación").setHidden(true);
        grid.addComponentColumn(this::buildUrlCmpt).setCaption("Enlace video").setHidable(true).setHidingToggleCaption("Mostrar enlace");
        grid.addColumn(webinar -> String.valueOf(webinar.getAsistentes())).setCaption("Asistentes").setHidable(true).setHidden(true).
                setHidingToggleCaption("Mostrar Asistentes");

        HeaderRow headerDate = grid.appendHeaderRow();
        headerDate.getCell("colDate").setComponent(buildFilterDate());
        setCaption("<b>Webinar Realizado</b>");
        eventMostrar();
        enableFilters();
    }

    private void enableFilters() {
        if (dataProvider.getItems().isEmpty()) {
            fechaInicioF.setEnabled(false);
        }
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

    private void filterDate(DateField fechaInicioF) {
        LocalDate fechaInicio = fechaInicioF.getValue();
        dataProvider.setFilter((WebinarRealizado::getFecha), fecha -> {
            if (fecha == null) {
                return false;
            }
            return fechaInicio.compareTo(fecha.toLocalDate()) == 0;
        });

    }

    private SerializablePredicate<WebinarRealizado> filter() {
        SerializablePredicate<WebinarRealizado> columnPredicate;
        columnPredicate = web -> {
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaFin = fechaFinF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime newFecha = web.getFecha().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaFin = fechaFin.withHour(0).withMinute(0).withSecond(0);
            return newFechaIn.compareTo(newFecha) * newFecha.compareTo(newFechaFin) >= 0;
        };

        return columnPredicate;
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

    private ResponsiveLayout buildUrlCmpt(WebinarRealizado web) {
        ResponsiveLayout rl = new ResponsiveLayout();
        Link link = new Link();
        link.setResponsive(true);
        link.addStyleNames(ValoTheme.LINK_SMALL);
        link.setResource(new ExternalResource(web.getUrlYoutube()));
        link.setIcon(VaadinIcons.ARROW_FORWARD);
        link.setTargetName("_blank");
        link.setCaption("Video YouTube");
        rl.addRow().withComponents(link);
        return rl;
    }

    @Override
    protected void eventMostrar() {
        dataProvider = DataProvider.ofCollection(ControladorWebinarRealizado.getInstance().getAll());
        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void buttonSearchEvent() {
        try {
            if (!searchField.isEmpty()) {
                String searchTxt = searchField.getValue();
                dataProvider.setFilter(filterAllByString(searchTxt));
                resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:14px;font-family:Open Sans\">"
                        + "No se encontro ninguna coincidencia para la búsqueda '" + searchTxt + "'" + " </span></b>");
            } else {
                resBusqueda.setValue(null);
                dataProvider.clearFilters();
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    private SerializablePredicate<WebinarRealizado> filterAllByString(String searchTxt) {
        SerializablePredicate<WebinarRealizado> predicate;
        predicate = (web) -> {
            String nombre = web.getNombre(), inst = web.getInstitucion(), ponente = web.getPonente();
            Pattern pattern = Pattern.compile(Pattern.quote(searchTxt), Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(nombre).find() || pattern.matcher(inst).find() || pattern.matcher(ponente).find()) {
                resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                        + "letter-spacing: 1px;\">Se encontraron coincidencias para la búsqueda '" + searchTxt + "'" + " </span></b>");
                return true;
            }
            return false;
        };
        return predicate;
    }

    @Override
    protected void eventDeleteButtonGrid(WebinarRealizado obj) {
        try {
            ui.addWindow(new WebinarRealizadoModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(WebinarRealizadoDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new WebinarRealizadoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(WebinarRealizado obj) {
        ui.addWindow(new WebinarRealizadoModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(WebinarRealizado obj, String idBtn) {
        ui.addWindow(new AsistenciaWebinarModalWin(obj.getId(), idBtn));
    }

    @Override
    protected void eventListaAsistentes(WebinarRealizado obj) {
        try {
            ui.addWindow(new ShowPDFDlg(new StreamResource(new ListadoAsistentesPDF(obj), (Utils.getFormatIdLong() + ".pdf").replace(" ", ""))));
        } catch (Exception ex) {
        }
    }

    @Override
    protected void eventWebinarsAgremiado(WebinarRealizado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(WebinarRealizado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
