package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAgremiadosPDF;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoWebinarsPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.reportes.agremiadosChart;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.HasValue;
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
import com.vaadin.ui.Label;
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
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public class AgremiadoDlg extends TemplateDlg<Agremiado> {

    private Button listadoAgremiados;
    ListDataProvider<Agremiado> dataProvider = DataProvider.ofCollection(ControladorAgremiado.getInstance().getAll());
    List<Agremiado> filterList = new ArrayList<>();
    DateField fechaInicioF;
    DateField fechaFinF;
    Button btnClear, currentDate, btnToday;
    ZoneId zoneId = ZoneId.of("America/Mexico_City");

    public AgremiadoDlg() throws Exception {  //Constructor de la clase AgremiadoDlg
        init();
    }

    private void init() throws Exception {    //Columnas que son asignadas a la tabla de agremiado en la interfaz web
        btnAdd.setIcon(VaadinIcons.PLUS);
        listadoAgremiados = new Button();
        listadoAgremiados.setResponsive(true);
        listadoAgremiados.setDescription("Descargar lista de agremiados");
        listadoAgremiados.setIcon(VaadinIcons.FILE_TEXT_O);
        listadoAgremiados.addClickListener(event -> {
            eventoBotonListadoAgremiados();
        });

        Button btnChart = new Button();
        btnChart.setResponsive(true);
        btnChart.setDescription("Mostrar estadísticas");
        btnChart.setIcon(VaadinIcons.BAR_CHART_H);
        btnChart.addClickListener((Button.ClickEvent event) -> {
            try {
                Window wdw = new Window();
                agremiadosChart panelChart;
                wdw.setCaption(VaadinIcons.BAR_CHART.getHtml());
                wdw.setCaptionAsHtml(true);
                wdw.setModal(true);
                wdw.center();
                wdw.setClosable(true);
                wdw.setDraggable(true);
                wdw.setResponsive(true);
                wdw.setResizable(true);
                if (filterList.isEmpty()) {
                    panelChart = new agremiadosChart();
                } else {
                    panelChart = new agremiadosChart(filterList);
                }
                wdw.setHeightUndefined();
                wdw.setWidth("65%");
                wdw.setContent(panelChart);
                ui.addWindow(wdw);
            } catch (Exception ex) {
                Logger.getLogger(AgremiadoDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SerializableComparator<Agremiado> comparator = (a1, a2) -> {
            int y1 = a1.getFechaReg().getYear(), y2 = a2.getFechaReg().getYear(),
                    m1 = a1.getFechaReg().getMonthValue(), m2 = a2.getFechaReg().getMonthValue(),
                    d1 = a1.getFechaReg().getDayOfMonth(), d2 = a2.getFechaReg().getDayOfMonth();
            if (y1 < y2) {
                return -1;
            }
            if (y1 > y2) {
                return 1;
            }
            if (m1 < m2) {
                return -1;
            }
            if (m1 > m2) {
                return 1;
            }
            if (d1 < d2) {
                return -1;
            }
            if (d1 > d2) {
                return 1;
            }
            return 0;
        };
        ResponsiveLayout layoutReportes = new ResponsiveLayout();
        ResponsiveRow r = layoutReportes.addRow().withAlignment(Alignment.MIDDLE_RIGHT);
        r.setSpacing(true);
        r.addColumn().withComponent(listadoAgremiados);
        r.addColumn().withComponent(btnChart);
        r.addColumn().withComponent(btnAdd);
        rowBar.removeAllComponents();
        rowBar.withComponents(searchField, btnAdd, btnChart, listadoAgremiados);
        banBoton = 2;
        grid.setHeaderRowHeight(40);
        int minWidth = 200;
        grid.addColumn(Agremiado::getNombre).setCaption("Nombre").setHidable(false).setMinimumWidth(minWidth);
        grid.addColumn(Agremiado::getCorreo).setCaption("Correo").setHidable(true).setHidingToggleCaption("Mostrar Correo").setHidden(true)
                .setMinimumWidth(minWidth);
        grid.addColumn(Agremiado::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar instituto")
                .setMinimumWidth(150);
        grid.addColumn(Agremiado::getObjGradoEstudio).setCaption("Grado estudio").setHidable(true).setHidingToggleCaption("Mostrar Grado Estudio")
                .setMinimumWidth(150);
        grid.addColumn(Agremiado::getObjPais).setCaption("País").setHidable(true).setHidingToggleCaption("Mostrar País")
                .setMinimumWidth(150);
        grid.addColumn(Agremiado::getFechaReg).setCaption("Fecha registro").setHidable(true).setHidingToggleCaption("Mostrar Fecha registro")
                .setId("colFechaReg").setMinimumWidth(480).setComparator(comparator);
        grid.addColumn(agremiadoBean -> (agremiadoBean.getSexo() == 'H' ? "Hombre" : "Mujer")).setCaption("Género")
                .setHidable(true).setHidingToggleCaption("Mostrar Género").setHidden(true);

        HeaderRow headerDate = grid.appendHeaderRow();
        headerDate.getCell("colFechaReg").setComponent(buildFilterDate());

        ///////////////////////
        //contentLayout.addComponent(contenido);
        setCaption("<b>Agremiados</b>");
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
        fechaInicioF = buildDateField("Fecha inicio", "Selecciona la fecha de inicio", "");
        fechaFinF = buildDateField("Fecha fin", "Selecciona la fecha final", "");
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
        btnClear.addStyleNames(ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_LINK);
        //withComponents(fechaInicioF, label, fechaFinF, btnClear)

        btnToday = new Button("Hoy");
        btnToday.setResponsive(true);
        btnToday.addStyleNames(ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_LINK);
        btnToday.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(LocalDate.now(zoneId));
        });

        ResponsiveRow row = lay.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row.withComponents(fechaInicioF, fechaFinF, btnToday, btnClear);
        row.setHorizontalSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row.setSizeFull();

        fechaInicioF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if (event.getValue() != null) {
                filterList = new ArrayList<>();
                if(fechaFinF.isEmpty()){
                    filterDate(fechaInicioF);
                    fechaFinF.setEnabled(true);
                    btnClear.setEnabled(true);
                }else{
                    dataProvider.setFilter(filter());
                }
                fechaFinF.setRangeStart(fechaInicioF.getValue().plusDays(1));
            }
        });
        fechaFinF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if (event.getValue() != null) {
                filterList = new ArrayList<>();
                dataProvider.setFilter(filter());
                fechaInicioF.setRangeEnd(((LocalDate) event.getValue()).minusDays(1));
            }
        });
        return lay;
    }

    public DateField buildDateField(String placeHolder, String description, String caption) {
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

    private SerializablePredicate<Agremiado> filter() {
        SerializablePredicate<Agremiado> columnPredicate;
        
        columnPredicate = a -> {
            boolean predicate;
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaFin = fechaFinF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime newFecha = a.getFechaReg().atTime(0, 0, 0);
            LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaFin = fechaFin.withHour(0).withMinute(0).withSecond(0);
            predicate = newFechaIn.compareTo(newFecha) * newFecha.compareTo(newFechaFin) >= 0;
            if(predicate){
                if(!filterList.contains(a))
                    filterList.add(a);
            }
            return predicate;
        };

        return columnPredicate;
    }

    public void updateDateFilters(boolean fechaInicio, boolean fechaFin) {
        this.fechaInicioF.setEnabled(fechaInicio);
        this.fechaFinF.setEnabled(fechaFin);
    }

    private void filterDate(DateField fechaInicioF) {
        LocalDate fechaInicio = fechaInicioF.getValue();
        dataProvider.setFilter(agremiado -> {
            LocalDate date = agremiado.getFechaReg();
            if(fechaInicio.compareTo(date)==0){
                if(!filterList.contains(agremiado))
                    filterList.add(agremiado);
            }
                
            return fechaInicio.compareTo(date) == 0;
        });

    }

    @Override
    protected void eventMostrar() {
        dataProvider = DataProvider.ofCollection(ControladorAgremiado.getInstance().getAll());
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

    private SerializablePredicate<Agremiado> filterAllByString(String searchTxt) {
        SerializablePredicate<Agremiado> predicate;
        predicate = (a) -> {
            String nombre = a.getNombre(), inst = a.getInstitucion(), gradoE = a.getObjGradoEstudio().getNombre(),
                    pais = a.getObjPais().getNombre();
            Pattern pattern = Pattern.compile(Pattern.quote(searchTxt), Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(nombre).find() || pattern.matcher(inst).find() || pattern.matcher(gradoE).find()
                    || pattern.matcher(pais).find()) {
                resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                        + "letter-spacing: 1px;\">Se encontraron coincidencias para la búsqueda '" + searchTxt + "'" + " </span></b>");
                return true;
            }
            return false;
        };
        return predicate;
    }

    @Override
    protected void eventDeleteButtonGrid(Agremiado obj) {
        try {
            ui.addWindow(new AgremiadoModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(AgremiadoDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eventoBotonListadoAgremiados() {
        try {
            ui.addWindow(new ShowPDFDlg(new StreamResource(new ListadoAgremiadosPDF(), (Utils.getFormatIdLong() + ".pdf").replace(" ", ""))));
        } catch (Exception ex) {
        }
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new AgremiadoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(Agremiado obj) {
        ui.addWindow(new AgremiadoModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(Agremiado obj, String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(Agremiado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(Agremiado obj) {
        try {
            ui.addWindow(new ShowPDFDlg(new StreamResource(new ListadoWebinarsPDF(obj), (Utils.getFormatIdLong() + ".pdf").replace(" ", ""))));
        } catch (Exception ex) {
            Logger.getLogger(AgremiadoDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void eventTutorialSesiones(Agremiado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
