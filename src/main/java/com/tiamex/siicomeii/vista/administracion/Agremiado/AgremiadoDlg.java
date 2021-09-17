package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAgremiadosPDF;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoWebinarsPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.reportes.agremiadosChart;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.HasValue;
import com.vaadin.data.Result;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DateFormatter;

/**
 * @author fred *
 */
public class AgremiadoDlg extends TemplateDlg<Agremiado> {

    private Button listadoAgremiados;
    ListDataProvider<Agremiado> dataProvider = DataProvider.ofCollection(ControladorAgremiado.getInstance().getAll());
    List<Agremiado> filterList = new ArrayList<>();
    DateField fechaInicioF;
    DateField fechaFinF;
    Button btnClear;
    Button currentDate;

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
                wdw.setCaptionAsHtml(true); wdw.setModal(true);
                wdw.center();wdw.setClosable(true);wdw.setDraggable(true);wdw.setResponsive(true);wdw.setResizable(true);
                if (filterList.isEmpty()) {
                     panelChart = new agremiadosChart();
                } else {
                     panelChart = new agremiadosChart(filterList);
                }
                
                wdw.setHeight(panelChart.getHeight() + 41, panelChart.getHeightUnits());
                wdw.setWidth("60%");
                wdw.setContent(panelChart);
                ui.addWindow(wdw);
            } catch (Exception ex) {
                Logger.getLogger(AgremiadoDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ResponsiveLayout layoutReportes = new ResponsiveLayout();
        ResponsiveRow r = layoutReportes.addRow().withAlignment(Alignment.MIDDLE_RIGHT);
        r.setSpacing(true);
        r.addColumn().withComponent(listadoAgremiados);
        r.addColumn().withComponent(btnChart);
        r.addColumn().withComponent(btnAdd);
        //colSearchField.withComponent(layoutReportes);
        //colSearchField.withDisplayRules(12, 6,6,6);

        rowBar.removeComponent(colSearchField);
        rowBar.removeComponent(colBtnSearch);
        colBtnAdd.withDisplayRules(12, 12, 12, 12);
        colBtnAdd.setContent(layoutReportes);
        //rowBar.removeComponent(btnSearch);
        //colBtnSearch.getContent().setVisible(false); colBtnSearch.withDisplayRules(0, 0, 0, 0);
        //colBtnAdd.withDisplayRules(12, 6,6,6); btnAdd.setWidthUndefined();
        banBoton = 2;

        grid.addColumn(Agremiado::getNombre).setCaption("Nombre").setHidable(false);
        grid.addColumn(Agremiado::getCorreo).setCaption("Correo").setHidable(true).setHidingToggleCaption("Mostrar Correo").setHidden(true);
        grid.addColumn(Agremiado::getInstitucion).setCaption("Institución").setHidable(false);
        grid.addColumn(Agremiado::getObjGradoEstudio).setCaption("Grado estudio").setHidable(true).setHidingToggleCaption("Mostrar Grado Estudio");
        grid.addColumn(Agremiado::getObjPais).setCaption("País").setHidable(true).setHidingToggleCaption("Mostrar País");
        grid.addColumn(Agremiado::getFechaReg).setCaption("Fecha registro").setHidable(true).setHidingToggleCaption("Mostrar Fecha registro")
                .setId("colFechaReg").setMinimumWidth(370).setMaximumWidth(370);
        grid.addColumn(agremiadoBean -> (agremiadoBean.getSexo() == 'H' ? "Hombre" : "Mujer")).setCaption("Género")
                .setHidable(true).setHidingToggleCaption("Mostrar Género").setHidden(true);

        HeaderRow headerTitulo = grid.appendHeaderRow();
        headerTitulo.getCell("colFechaReg").setComponent(buildFilterDate());

        ///////////////////////
        //contentLayout.addComponent(contenido);
        setCaption("<b>Agremiados</b>");
        eventMostrar();
    }

    private DateField newDateField(String placeHolder, String description) {
        DateField dateField = new DateField() {
            @Override
            protected Result<LocalDate> handleUnparsableDateString(
                    String dateString) {
                try {
                    LocalDate parsedAtServer = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    return Result.ok(parsedAtServer);
                } catch (DateTimeParseException e) {
                    return Result.error("Fecha no válida");
                }
            }
        };
        dateField.setZoneId(ZoneId.systemDefault());
        dateField.addStyleName(ValoTheme.DATEFIELD_TINY);
        dateField.setWidth("125px");
        dateField.setPlaceholder(placeHolder);
        dateField.setDescription(description);
        return dateField;
    }

    public HorizontalLayout buildFilterDate() {
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setResponsive(true);

        fechaInicioF = newDateField("Fecha inicio", "Seleccionar fecha de inicio");
        fechaFinF = newDateField("Fecha fin", "Seleccionar fecha fin");

        Label label = new Label("-");

        hLayout.addComponent(fechaInicioF);
        hLayout.addComponent(label);
        hLayout.addComponent(fechaFinF);

        btnClear = new Button();
        btnClear.setIcon(VaadinIcons.TRASH);
        btnClear.setDescription("Limpiar");
        btnClear.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(null);
            fechaFinF.setValue(null);
            btnClear.setEnabled(false);
            dataProvider.clearFilters();
        });
        btnClear.addStyleName(ValoTheme.BUTTON_TINY); //btnClear.addStyleName(ValoTheme.BUTTON_ICON_ONLY); 
        hLayout.addComponent(btnClear);

        if (dataProvider.getItems().isEmpty()) {
            fechaInicioF.setEnabled(false);
            fechaFinF.setEnabled(false);
            btnClear.setEnabled(false);
        }

        //fechaInicioF.addValueChangeListener(filterDate(fechaInicioF, fechaFinF, "colFecha"));
        fechaInicioF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if (fechaInicioF.getValue() != null && fechaFinF.getValue() == null) {
                //filterDate(fechaInicioF);
                filterList = new ArrayList<>();
                dataProvider.setFilter(filter(false));
                btnClear.setEnabled(true);
            }
            /*else {
                if(fechaInicioF.getValue()!= null && fechaFinF.getValue() != null){
                    //filterList = new ArrayList<>();
                    //dataProvider.setFilter(filter());
                    btnClear.setEnabled(true);
                }else{
                    dataProvider.clearFilters();
                }
            } */

        });

        //fechaFinF.addValueChangeListener(filterDate(fechaInicioF, fechaFinF, "colFecha"));
        fechaFinF.addValueChangeListener((HasValue.ValueChangeListener) event -> {

            if (fechaInicioF.getValue() != null && fechaFinF.getValue() != null) {
                btnClear.setEnabled(true);
                filterList = new ArrayList<>();
                dataProvider.setFilter(filter(true));
            } else {
                dataProvider.clearFilters();
            }
        });
        return hLayout;
    }

    private SerializablePredicate<Agremiado> filter(boolean rangeFilter) {
        SerializablePredicate<Agremiado> columnPredicate = null;
        columnPredicate = agremiado -> {
            boolean predicate = false;
            LocalDate fechaInicio = fechaInicioF.getValue();
            LocalDate fechaReg = agremiado.getFechaReg();
            if (rangeFilter) {
                LocalDate fechaFin = fechaFinF.getValue();
                /*
                if (fechaInicio.compareTo(fechaReg) * fechaReg.compareTo(fechaFin) >= 0) {
                    if (!filterList.contains(agremiado)) {
                        filterList.add(agremiado);
                    }
                } */
                predicate = fechaInicio.compareTo(fechaReg) * fechaReg.compareTo(fechaFin) >= 0;
                //return fechaInicio.compareTo(newFecha) * newFecha.compareTo(fechaFin) >= 0;
            } else {
                predicate = fechaInicio.compareTo(fechaReg) == 0;
            }

            if (predicate) {
                if (!filterList.contains(agremiado)) {
                    filterList.add(agremiado);
                }
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
        dataProvider.setFilter((Agremiado::getFechaReg), fecha -> {
            if (fecha == null) {
                return false;
            }
            return fechaInicio.compareTo(fecha) == 0;
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
                resBusqueda.setHeight("35px");
                resBusqueda.setContentMode(ContentMode.HTML);
                String strBusqueda = searchField.getValue();
                Collection<Agremiado> agremiados = ControladorAgremiado.getInstance().getByName(strBusqueda);
                int listSize = agremiados.size();
                if (listSize > 1) {
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron " + Integer.toString(listSize) + " coincidencias para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                } else if (listSize == 1) {
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró " + Integer.toString(listSize) + " coincidencia para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                } else {
                    resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                }
                grid.setItems(agremiados);
            } else {
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorAgremiado.getInstance().getAll());
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
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
