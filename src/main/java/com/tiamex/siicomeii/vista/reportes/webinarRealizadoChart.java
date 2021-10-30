/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.reportes;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.ReporteChartAgremiado;
import com.tiamex.siicomeii.reportes.base.pdf.ReporteChartWebRealizado;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartDrillupListener;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.DrilldownEvent;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Crosshair;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataProviderSeries;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Exporting;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Lang;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.themes.VaadinTheme;
import com.vaadin.addon.charts.util.SVGGenerator;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import org.threeten.extra.YearWeek;
import com.google.common.collect.Lists;
import com.google.common.base.Functions;
import com.vaadin.ui.HorizontalLayout;

/**
 *
 * @author jhon
 * @param <T>
 */
public class webinarRealizadoChart<T> extends Panel {

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
    protected VerticalLayout contentModalLayout;
    protected ResponsiveLayout contentModal;
    protected ResponsiveLayout contentChart;
    protected ResponsiveRow row1;
    TextField fileName;
    protected Button btnFullReport;
    protected Button btnReportChart;
    Exporting exporting;
    protected Grid<T> grid;
    public VerticalLayout main;
    CheckBox checkData;
    List<Agremiado> listaAg_INST = ControladorAgremiado.getInstance().getAllSorted("institucion");
    List<Agremiado> listaAg_PAIS = ControladorAgremiado.getInstance().getAllSorted("pais");
    List<Agremiado> filteredList = new ArrayList<>();
    HashMap<String, Integer> mapInstitutions;
    final String LABEL_FORMATTER = "'<b>'+ this.point.name +'</b> ('+this.point.y+') : '+ this.percentage.toFixed(2) +'%'";
    NativeSelect<String> selectBy, selectGraph;
    MenuBar menuDownload;
    // webinar
    ComboBox<String> comboBox, comboYearSel;
    NativeSelect<Integer> selectYear;
    List<WebinarRealizado> listaWebR = ControladorWebinarRealizado.getInstance().getAllSorted("nombre"), listaComboBox, copyListWebR;
    List<ProximoWebinar> listaProxWeb = ControladorProximoWebinar.getInstance().getAll();
    List<Agremiado> listaAgremiados = ControladorAgremiado.getInstance().getAll();
    List<AsistenciaWebinar> listAsistencia = ControladorAsistenciaWebinar.getInstance().getAll();
    List<List<WebinarRealizado>> orderedByYearList;
    Map<String, Integer> mapInstitutoWeb = new HashMap<>();
    final LocalDate currentDate = LocalDate.now(ZoneId.of("America/Mexico_City"));
    int currentYear = currentDate.getYear();
    final Locale locale = Locale.forLanguageTag("es-MX");
    List<Integer> listYears;
    Chart generalChart;
    int highestRecord;
    Panel scrollPanelChart;
    Button btnDownload;

    public VerticalLayout getMain() {
        return main;
    }

    public webinarRealizadoChart(List<Agremiado> filteredList) throws Exception {
        this.filteredList = filteredList;
        initDlg();
    }

    public webinarRealizadoChart() throws Exception {
        initDlg();
    }

    private void initDlg() throws Exception {
        ui = Element.getUI();
        selectGraph = new NativeSelect<>("Tipo de gráfico:", Arrays.asList("Columna", "Pastel"));
        selectGraph.setEmptySelectionAllowed(false);
        selectGraph.setResponsive(true);
        selectGraph.setSelectedItem("Pastel");
        selectGraph.addValueChangeListener((ValueChangeListener) e -> {
            String selected = e.getValue().toString(), filterValue = selectBy.getValue();
            List<Agremiado> reorderedList;
            switch (selected) {
                case "Pastel":
                    if (checkData.getValue()) {
                        switch (filterValue) {
                            case "Institución":
                                reorderedList = sortFilteredList("institucion");
                                setLayoutContentChart(getAgremiadoChartPie(reorderedList, "instituciones", "Agremiados registrados por institución", true));
                                break;
                            case "País":
                                reorderedList = sortFilteredList("pais");
                                setLayoutContentChart(getAgremiadoChartPie(reorderedList, "paises", "Agremiados registrados por país", false));
                                break;
                            case "Género":
                                setLayoutContentChart(getAgremiadoChartPie(filteredList, "genero", "Agremiados registrados por género", false));
                                break;
                        }
                    } else {
                        switch (filterValue) {
                            case "Institución":
                                setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "instituciones", "Agremiados registrados por institución", true));
                                break;
                            case "País":
                                setLayoutContentChart(getAgremiadoChartPie(listaAg_PAIS, "paises", "Agremiados registrados por país", false));
                                break;
                            case "Género":
                                setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "genero", "Agremiados registrados por género", false));
                                break;
                        }
                    }
                    break;
                case "Columna":
                    if (checkData.getValue()) {
                        switch (filterValue) {
                            case "Institución":
                                reorderedList = sortFilteredList("institucion");
                                //setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por institución","Instituciones"));
                                break;
                            case "País":
                                reorderedList = sortFilteredList("pais");
                                //setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por país","Paises"));
                                break;
                            case "Género":
                                //setLayoutContentChart(getAgremiadoChartColumn(filteredList,"Agremiados registrados por género","Género"));
                                break;
                        }
                    } else {
                        switch (filterValue) {
                            case "Institución":
                                //setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST,"Agremiados registrados por institución","Instituciones"));
                                break;
                            case "País":
                                //setLayoutContentChart(getAgremiadoChartColumn(listaAg_PAIS,"Agremiados registrados por país","Países"));
                                break;
                            case "Género":
                                //setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST,"Agremiados registrados por género","Género"));
                                break;
                        }
                    }
                    break;
            }
            selectGraph.setSelectedItem(selected);
        });

        selectBy = new NativeSelect<>("Mostrar por:", Arrays.asList("Institución", "País", "Género"));
        selectBy.setEmptySelectionAllowed(false);
        selectBy.setResponsive(true);
        selectBy.setSelectedItem("Institución");
        selectBy.addValueChangeListener((ValueChangeListener) e -> {
            String selected = e.getValue().toString();
            switch (selected) {
                case "Institución":
                    if (checkData.getValue()) {
                        sortFilteredList("institucion");
                        setLayoutContentChart(getAgremiadoChartPie(filteredList, "instituciones", "Agremiados registrados por institución", true));
                    } else {
                        setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "instituciones", "Agremiados registrados por institución", true));
                    }
                    break;
                case "País":
                    if (checkData.getValue()) {
                        sortFilteredList("pais");
                        setLayoutContentChart(getAgremiadoChartPie(filteredList, "paises", "Agremiados registrados por país", false));
                    } else {
                        setLayoutContentChart(getAgremiadoChartPie(listaAg_PAIS, "paises", "Agremiados registrados por país", false));
                    }
                    break;
                case "Género":
                    if (checkData.getValue()) {
                        setLayoutContentChart(getAgremiadoChartPie(filteredList, "genero", "Agremiados registrados por género", false));
                    } else {
                        setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "genero", "Agremiados registrados por género", false));
                    }
                    break;
            }
            selectGraph.setSelectedItem("Pastel");
            selectBy.setSelectedItem(selected);
        });

        checkData = new CheckBox();
        checkData.setResponsive(true);
        checkData.setDescription("Seleccionar para mostrar solo los resultados filtrados en la tabla");
        checkData.setCaption("Datos filtrados");

        checkData.addValueChangeListener((ValueChangeListener) event -> {
            boolean chartTypePie = selectGraph.getValue().compareTo("Pastel") == 0;
            String filteredValue = selectBy.getValue();
            List<Agremiado> reorderedList;
            if ((boolean) event.getValue()) {
                if (chartTypePie) {
                    switch (filteredValue) {
                        case "Institución":
                            reorderedList = sortFilteredList("institucion");
                            setLayoutContentChart(getAgremiadoChartPie(reorderedList, "instituciones", "Agremiados registrados por institución", true));
                            break;
                        case "País":
                            reorderedList = sortFilteredList("pais");
                            setLayoutContentChart(getAgremiadoChartPie(reorderedList, "paises", "Agremiados registrados por país", false));
                            break;
                        case "Género":
                            setLayoutContentChart(getAgremiadoChartPie(filteredList, "genero", "Agremiados registrados por institución", false));
                            break;
                    }
                } else {
                    switch (filteredValue) {
                        case "Institución":
                            reorderedList = sortFilteredList("institucion");
                            //setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por institución","Instituciones"));
                            break;
                        case "País":
                            reorderedList = sortFilteredList("pais");
                            //setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por país","Países"));
                            break;
                        case "Género":
                            //setLayoutContentChart(getAgremiadoChartColumn(filteredList,"Agremiados registrados por institución","Género"));
                            break;
                    }
                }
            } else {
                if (chartTypePie) {
                    switch (filteredValue) {
                        case "Institución":
                            setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "instituciones", "Agremiados registrados por institución", true));
                            break;
                        case "País":
                            setLayoutContentChart(getAgremiadoChartPie(listaAg_PAIS, "paises", "Agremiados registrados por país", false));
                            break;
                        case "Género":
                            setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "genero", "Agremiados registrados por institución", false));
                            break;
                    }
                } else {
                    switch (filteredValue) {
                        case "Institución":
                            //setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST, "Agremiados registrados por institución", "Instituciones"));
                            break;
                        case "País":
                            //setLayoutContentChart(getAgremiadoChartColumn(listaAg_PAIS, "Agremiados registrados por país", "Países"));
                            break;
                        case "Género":
                            //setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST, "Agremiados registrados por institución", "Género"));
                            break;
                    }
                }
            }
        });
        //getSvgStrings();

        /*btnFullReport = createExportButton(VaadinIcons.FILE_TEXT_O.getHtml(), currentDate.toString() + ".pdf", createPdfStreamSource(true),
                "Descargar reporte de todos los datos registrados");*/
        btnFullReport = new Button();
        btnFullReport.setResponsive(true);
        btnFullReport.setDescription("Descargar reporte de todos los datos registrados");
        btnFullReport.setIcon(VaadinIcons.FILE_TEXT_O);
        btnFullReport.addClickListener((ClickListener) listener -> {
            if (listaWebR.isEmpty() && listAsistencia.isEmpty()) {
                Notification.show("", "Sin registros", Notification.Type.ERROR_MESSAGE);
            } else {
                ui.addWindow(new ShowPDFDlg(new StreamResource(createPdfStreamSource(true, 0),
                        currentDate.format(DateTimeFormatter.ISO_DATE))));
            }
        });

        /*btnReportChart = createExportButton(VaadinIcons.BAR_CHART_H.getHtml(), currentDate.toString() + ".pdf", createPdfStreamSource(false),
                "Descargar reporte de los datos filtrados en la tabla");
        btnReportChart.addClickListener((ClickListener) listener -> {
        }); */
        comboBox = new ComboBox<>("<b>Buscar institución:</b>", getInstitutos());
        comboBox.setResponsive(true);
        comboBox.setPlaceholder("Ingrese nombre");
        comboBox.setTextInputAllowed(true);
        comboBox.setPageLength(10);
        //comboBox.setWidth(90F, Unit.PERCENTAGE);
        comboBox.setWidthFull();
        comboBox.addStyleName("searchbox");
        comboBox.setCaptionAsHtml(true);
        comboBox.setEmptySelectionAllowed(true);
        comboBox.setEmptySelectionCaption("Todos los registros");
        comboBox.setStyleGenerator(str -> {
            if (mapInstitutoWeb.get(str) > 0) {
                return "websR";
            }
            return "noWebsR";
        });
        comboBox.setItemCaptionGenerator(str -> {
            int total = mapInstitutoWeb.get(str);
            if (total == 0) {
                return str + " - Ninguno.";
            }
            if (total == 1) {
                return str + " - " + total + " realizado.";
            }
            return str + " - " + total + " realizados.";
        });
        
        comboBox.addSelectionListener((SingleSelectionEvent<String> event) -> {
            if (event.getSelectedItem().isPresent()) {
                String selectedItem = event.getValue();
                listaComboBox = ControladorWebinarRealizado.getInstance().getByInstituto(selectedItem);
                scrollPanelChart.setContent(getAgremiadoChartColumn(listaComboBox, "Webinars realizados "+currentYear,
                        "Webinars realizados en todo el año", currentYear));
                if (mapInstitutoWeb.get(selectedItem) > 0) {
                    selectYear.setEnabled(true);
                    selectYear.setSelectedItem(currentYear);
                } else {
                    selectYear.clear();
                    selectYear.setEnabled(false);
                }
            } else {
                scrollPanelChart.setContent(generalChart);
                selectYear.setEnabled(true);
                selectYear.setSelectedItem(currentYear);
            }
        });
        selectYear = new NativeSelect<>("<b>Año:</b>", getYears());
        selectYear.setEmptySelectionAllowed(false);
        selectYear.setCaptionAsHtml(true);
        selectYear.setWidthFull();
        //selectYear.setWidth(90F, Unit.PERCENTAGE);
        selectYear.setEmptySelectionAllowed(true);
        selectYear.setEmptySelectionCaption("Seleccionar año");
        selectYear.setItemCaptionGenerator(year -> {
            return String.valueOf(year);
        });
        selectYear.setSelectedItem(currentYear);
        selectYear.addSelectionListener((SingleSelectionListener) listener -> {
            if (listener.isUserOriginated()) {
                String year;
                if (listener.getSelectedItem().isPresent()) {
                    year = String.valueOf(listener.getSelectedItem().get());
                    if(comboYearSel.getSelectedItem().isPresent()){
                        scrollPanelChart.setContent(getAgremiadoChartColumn(listaComboBox, "Webinars realizados " + year,
                                "Webinars realizados en todo el año", year.isEmpty() ? 0 : Integer.valueOf(year)));
                    }else
                        scrollPanelChart.setContent(getAgremiadoChartColumn(listaWebR, "Webinars realizados "+currentYear,""
                                + "Webinars realizados en todo el año "+currentYear, currentYear));
                }else
                    scrollPanelChart.setContent(getAgremiadoChartColumn(new ArrayList<>(), "","", currentYear));
            }

        });

        ResponsiveLayout downloadLayout = new ResponsiveLayout();
        String htmlIcon = VaadinIcons.DOWNLOAD.getHtml();
        downloadLayout.setCaption("<b><span style=\"font-size:12px\">Opciones de descarga " + htmlIcon + "</span></b>");
        downloadLayout.setDescription("Se muestran algunas opciones para descargar el gráfico y el reporte en PDF");
        downloadLayout.setCaptionAsHtml(true);
        downloadLayout.setSizeFull();

        btnFullReport.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        ResponsiveRow rr = downloadLayout.addRow();
        rr.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        rr.setSizeFull();
        rr.addColumn().withComponent(btnFullReport).withDisplayRules(4, 4, 3, 3);
        createMenuDownload();
        HorizontalLayout hl = new HorizontalLayout();
        hl.setResponsive(true); hl.setSpacing(false); hl.setMargin(false);
        hl.setSizeFull();
        hl.addComponent(comboYearSel); hl.addComponent(btnDownload);
        rr.addColumn().withComponent(hl).withDisplayRules(8, 8, 9, 9);
        //rr.addColumn().withComponent(comboYearSel).withDisplayRules(5, 5, 5, 5);
        //rr.addColumn().withComponent(btnDownload).withDisplayRules(3, 3, 3, 3);

        Panel infoPanel = new Panel(); //infoPanel.addStyleName("captionPanelNoPadding");
        infoPanel.addStyleNames("blue");
        //<span style=\"padding:0;\">Información general y descargas </span>
        infoPanel.setCaption("Información general y descargas");
        infoPanel.setCaptionAsHtml(true);
        infoPanel.setResponsive(true);
        //infoPanel.setWidth(90f, Unit.PERCENTAGE);
        infoPanel.setHeightUndefined();
        ResponsiveLayout layoutInfoP = new ResponsiveLayout();
        layoutInfoP.setResponsive(true);
        layoutInfoP.setHeightUndefined();
        ResponsiveRow rowInfoP = layoutInfoP.addRow().withAlignment(Alignment.TOP_LEFT);
        rowInfoP.setMargin(ResponsiveRow.MarginSize.SMALL, ResponsiveLayout.DisplaySize.LG);
        rowInfoP.addColumn().withComponent(downloadLayout);
        Label lblInfo;
        lblInfo = new Label();
        lblInfo.setWidthFull();
        lblInfo.setCaption("<br><b>Webinars realizados:</b>");
        lblInfo.setCaptionAsHtml(true);
        lblInfo.setContentMode(ContentMode.HTML);
        rowInfoP.addColumn().withComponent(lblInfo);
        ObjectHolderWebinars obj = getInfoWebinars();
        lblInfo.setHeight(100f, Unit.PIXELS);
        lblInfo.setValue("Esta semana: " + obj.getWeek() + "<br>"
                + "Este mes (" + currentDate.getMonth().getDisplayName(TextStyle.FULL, locale) + "): " + obj.getMonth() + "<br>"
                + "Este año (" + currentDate.getYear() + "): " + obj.getYear() + "<br>"
                + "Totales: " + obj.getTotal());

        lblInfo = new Label();
        lblInfo.setResponsive(true);
        lblInfo.setCaption("<br>");
        lblInfo.setCaptionAsHtml(true);
        
        lblInfo = new Label();
        lblInfo.setCaption("<b>Constancias:</b>");
        lblInfo.setCaptionAsHtml(true);
        lblInfo.setWidthFull();
        lblInfo.setContentMode(ContentMode.HTML);
        rowInfoP.addColumn().withComponent(lblInfo);
        String totalConst = listaWebR.isEmpty() ? "Ninguna" : String.valueOf(listaWebR.size());
        String ranking = getCountConstanciasInst(listAsistencia);
        lblInfo.setValue("Total enviadas: " + totalConst + "<br>"
                + "Institución(es) con mayor constancias recibidas (" + highestRecord + "):" + "<br>" + ranking);
        infoPanel.setContent(layoutInfoP);
        ResponsiveLayout chartOptionsLayout = new ResponsiveLayout();
        ResponsiveRow rowChart = chartOptionsLayout.addRow();
        rowChart.setSpacing(true); 
        
        contentModal = new ResponsiveLayout();
        contentModal.setSizeFull();
        contentModal.setSpacing();

        ResponsiveRow rowModal = contentModal.addRow();
        rowModal.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        rowModal.setMargin(ResponsiveRow.MarginSize.NORMAL, ResponsiveLayout.DisplaySize.XS);
        rowModal.setMargin(ResponsiveRow.MarginSize.NORMAL, ResponsiveLayout.DisplaySize.SM);
        rowModal.setMargin(ResponsiveRow.MarginSize.NORMAL, ResponsiveLayout.DisplaySize.MD);
        rowModal.setMargin(ResponsiveRow.MarginSize.NORMAL, ResponsiveLayout.DisplaySize.LG);
       
        ResponsiveLayout rLayLateral = new ResponsiveLayout();
        rLayLateral.setResponsive(true);
        rLayLateral.setSpacing();
        ResponsiveRow rowLat = rLayLateral.addRow();
        rowLat.setResponsive(true); rowLat.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        rowLat.addStyleName("");
        
        ResponsiveLayout layoutComboSelect = new ResponsiveLayout();
        layoutComboSelect.setResponsive(true);
        ResponsiveRow rComboSelect = layoutComboSelect.addRow();
        rComboSelect.setResponsive(true);
        rComboSelect.addColumn().withComponent(comboBox).withDisplayRules(12, 12, 12, 12);
        rComboSelect.addColumn().withComponent(selectYear).withDisplayRules(12, 12, 12, 12);
        rowChart.addColumn().withComponent(layoutComboSelect);
        rowChart.addColumn().withComponent(infoPanel);
        rowLat.addColumn().withComponent(chartOptionsLayout);

        rowModal.addColumn().withComponent(rLayLateral).withDisplayRules(12, 12, 3, 3);

        contentChart = new ResponsiveLayout();
        contentChart.setSizeFull();
        scrollPanelChart = new Panel();
        scrollPanelChart.setCaption("Vista del gráfico");
        scrollPanelChart.setResponsive(true);
        scrollPanelChart.addStyleName("captionColor");
        generalChart = getAgremiadoChartColumn(listaWebR, "Webinars realizados " + currentYear,
                "Webinars realizados en todo el año", currentYear);
        scrollPanelChart.setContent(generalChart);
        contentChart.addComponent(scrollPanelChart);

        rowModal.addColumn().withComponent(contentChart, ResponsiveColumn.ColumnComponentAlignment.CENTER).withDisplayRules(12, 12, 9, 9);
        
        Lang lang = new Lang();
        lang.setNoData("No hay datos que mostrar.");
        lang.setPrintChart("Imprimir gráfico");
        lang.setDownloadPNG("Descargar en PNG");
        lang.setDownloadJPEG("Descargar en JPG");
        lang.setDownloadPDF("Descargar en PDF");
        lang.setDownloadSVG("Descargar en SVG");
        lang.setContextButtonTitle("Opciones de descarga (Gráfico)");
        lang.setDrillUpText("Regresar al gráfico principal");
        lang.setLoading("Cargando...");
        ChartOptions.get().setLang(lang);
        ChartOptions.get().setTheme(new VaadinTheme());

        this.setCaption("Estadísitcas de Agremiados");
        this.setWidthFull();
        this.setHeightUndefined();
        this.setContent(contentModal);
        this.setCaptionAsHtml(true);
    }

    private void createMenuDownload() {
        Collection<String> items = new ArrayList<>();
        listaWebR.stream().map(web -> String.valueOf(web.getFecha().getYear())).filter(year -> (items.isEmpty() || !items.contains(year))).
                forEachOrdered(year -> {
                    items.add(year);
                });
        comboYearSel = new ComboBox();
        comboYearSel.setWidthFull();
        comboYearSel.setResponsive(true);
        comboYearSel.setDescription("Descargar reporte de los webinars del año seleccionado");
        comboYearSel.setItems(Lists.transform(getYears(), Functions.toStringFunction()));
        comboYearSel.setPlaceholder("Año...");
        //comboYearSel.addStyleName(ValoTheme.COMBOBOX_TINY);
        comboYearSel.setEmptySelectionAllowed(false);
        comboYearSel.setPageLength(7);
        comboYearSel.setTextInputAllowed(false);
        comboYearSel.addSelectionListener((SingleSelectionListener) listener -> {
            btnDownload.setEnabled(true);
        });
        btnDownload = new Button();
        btnDownload.setEnabled(false);
        btnDownload.setResponsive(true);
        btnDownload.setIcon(VaadinIcons.DOWNLOAD_ALT);
        btnDownload.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        btnDownload.addClickListener((ClickListener) listener->{
            String item = comboYearSel.getValue();
            int year = Integer.valueOf(item);
            ui.addWindow(new ShowPDFDlg(new StreamResource(createPdfStreamSource(false, year),
                    currentDate.format(DateTimeFormatter.ISO_DATE))));
        });

    }

    private List<Integer> getYears() {
        List<Integer> list = new ArrayList<>();
        int currentYear, oldestYear, index = 0;
        currentYear = oldestYear = currentDate.getYear();
        for (WebinarRealizado web : listaWebR) {
            if (web.getFecha().getYear() < oldestYear) {
                oldestYear = web.getFecha().getYear();
            }
        }
        list.add(oldestYear);
        if (oldestYear != currentYear) {
            for (; oldestYear < currentYear; oldestYear += 1) {
                list.add(list.get(index) + 1);
                index++;
            }
        }
        return list;
    }

    private Collection<String> getInstitutos() {
        Collection<String> newList = new ArrayList<>();
        listaAgremiados.forEach(agremiado -> {
            String i = agremiado.getInstitucion();
            if (newList.isEmpty() || !newList.stream().
                    filter(obj -> obj.equalsIgnoreCase(i)).findFirst().isPresent()) {
                mapInstitutoWeb.put(i, 0);
                newList.add(i);
            }
        });
        listaProxWeb.forEach(ProWeb -> {
            String i = ProWeb.getInstitucion();
            if (newList.isEmpty() || !newList.stream().
                    filter(obj -> obj.equalsIgnoreCase(i)).findFirst().isPresent()) {
                mapInstitutoWeb.put(i, 0);
                newList.add(i);
            }
        });
        listaWebR.forEach(webRealizado -> {
            String webR = webRealizado.getInstitucion();
            if (mapInstitutoWeb.containsKey(webR)) {
                mapInstitutoWeb.replace(webR, mapInstitutoWeb.get(webR) + 1);
            }
        });
        return newList.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
    }

    public String getCountConstanciasInst(List<AsistenciaWebinar> data) {
        Map<String, InstitutoRecord> institutoMap = new HashMap<>();
        Map<String, InstitutoRecord> institutoMapRanked = new HashMap<>();
        String highestInst = "";
        InstitutoRecord obj;
        highestRecord = 0;
        int cont = 0;
        try {
            data.sort((o1, o2) -> {
                return o1.getObjAgremiado().getInstitucion().compareToIgnoreCase(o2.getObjAgremiado().getInstitucion());
            });
            for (AsistenciaWebinar a : data) {
                String loopInstituto = a.getObjAgremiado().getInstitucion();
                LocalDate loopDate = a.getObjWebinarRealizado().getFecha().toLocalDate();
                cont++;
                if (cont > highestRecord) {
                    highestRecord = cont;
                }
                if (institutoMap.containsKey(loopInstituto)) {
                    obj = institutoMap.get(loopInstituto);
                    obj.setTotal(obj.getTotal() + 1);
                    if (loopDate.compareTo(obj.getOldestReg()) < 0) {
                        obj.setOldestReg(loopDate);
                    }
                    institutoMap.replace(loopInstituto, obj);
                } else {
                    cont = 0;
                    obj = new InstitutoRecord(1, loopDate);
                    institutoMap.put(loopInstituto, obj);
                }
            }
            institutoMap.entrySet().stream().filter((Map.Entry<String, InstitutoRecord> entrySet)
                    -> {
                return entrySet.getValue().getTotal() == highestRecord;
            }).forEachOrdered(entrySet -> {
                institutoMapRanked.put(entrySet.getKey(), entrySet.getValue());
            });
            highestInst = getRankConstancias(institutoMapRanked);
        } catch (Exception ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return highestInst;
    }

    private String getRankConstancias(Map<String, InstitutoRecord> map) {
        String results;
        Stream<Map.Entry<String, InstitutoRecord>> sortedStream = map.entrySet().stream().
                sorted(Map.Entry.comparingByValue());
        results = getTextRanked(sortedStream);
        return results;
    }

    private String getTextRanked(Stream<Map.Entry<String, InstitutoRecord>> sortedStream) {
        String results = "";
        Map<String, InstitutoRecord> newMap = new HashMap<>();
        sortedStream.forEach(entry -> {
            newMap.put(entry.getKey(), entry.getValue());
        });
        for (Map.Entry<String, InstitutoRecord> entrySet : newMap.entrySet()) {
            if (results.isEmpty()) {
                results = "- " + entrySet.getKey();
            } else {
                results = results + "<br>" + "- " + entrySet.getKey();
            }
        }
        results = results + ".";
        return results;
    }

    private ObjectHolderWebinars getInfoWebinars() {
        ObjectHolderWebinars obj = new ObjectHolderWebinars();
        YearWeek currentYearWeek = YearWeek.from(currentDate);
        listaWebR.stream().map(web -> web.getFecha().toLocalDate()).map(date -> {
            YearWeek webinarYearWeek = YearWeek.from(date);
            if (currentYearWeek.equals(webinarYearWeek)) {
                obj.setWeek(obj.getWeek() + 1);
            }
            if (currentDate.getYear() == date.getYear()) {
                obj.setYear(obj.getYear() + 1);
                if (currentDate.getMonthValue() == date.getMonthValue()) {
                    obj.setMonth(obj.getMonth() + 1);
                }
            }
            return date;
        }).forEachOrdered(_item -> {
            obj.setTotal(obj.getTotal() + 1);
        });
        return obj;
    }

    public List<Agremiado> sortFilteredList(String sortBy) {
        List<Agremiado> reorderedList = new ArrayList<>(filteredList);
        reorderedList.sort((Agremiado a1, Agremiado a2) -> {
            if (sortBy.compareTo("institucion") == 0) {
                return a1.getInstitucion().compareToIgnoreCase(a2.getInstitucion());
            } else {
                return a1.getObjPais().getNombre().compareTo(a2.getObjPais().getNombre());
            }
        });
        return reorderedList;
    }

    private void setLayoutContentChart(Component component) {
        contentChart.removeAllComponents();
        Panel p = new Panel();
        p.setResponsive(true);
        p.addStyleName("captionColor");
        p.setCaption("Vista del gráfico");
        p.setContent(component);
        contentChart.addComponent(p);
    }

    private StreamResource.StreamSource createPdfStreamSource(boolean fullReport, int year) {
        StreamResource.StreamSource ss
                = () -> {
                    File result = doExportPDF(fullReport, year);
                    if (result != null) {
                        try {
                            return new FileInputStream(result);
                        } catch (FileNotFoundException e) {
                            Logger.getLogger(webinarRealizadoChart.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                    return null;
                };
        return ss;
    }

    private File doExportPDF(boolean fullReport, int yearSelected) {
        File file = null;
        ReporteChartWebRealizado reporte = new ReporteChartWebRealizado();
        try {
            if (fullReport) {
                file = reporte.writePdf("Webinar_realizados_completo", getSvgStrings(0), listYears, orderedByYearList);
            } else {
                file = reporte.writePdf("webRealizados", getSvgStrings(yearSelected), yearSelected, orderedByYearList, listYears);
            }
        } catch (Exception ex) {
            Logger.getLogger(webinarRealizadoChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    private List<String> getSvgStrings(int yearSel) {
        List<String> list = new ArrayList<>();
        int month = 0;
        int countYears = 0;
        listYears = new ArrayList<>();
        copyListWebR = new ArrayList<>(listaWebR);
        List<WebinarRealizado> yearList;
        orderedByYearList = new ArrayList<>();
        copyListWebR.sort((WebinarRealizado w1, WebinarRealizado w2) -> {
            int y1 = w1.getFecha().getYear(), y2 = w2.getFecha().getYear(),
                    m1 = w1.getFecha().getMonthValue(), m2 = w2.getFecha().getMonthValue();
            if (y2 < y1) {
                return -1;
            }
            if (y2 > y1) {
                return 1;
            }
            if (m1 < m2) {
                return -1;
            }
            if (m1 > m2) {
                return 1;
            }
            return 0;
        });
        if (yearSel == 0) {
            countYears = copyListWebR.stream().map(web -> web.getFecha().getYear()).
                    filter(year -> (listYears.isEmpty() || !listYears.contains(year))).map((Integer year) -> {
                listYears.add(year);
                return year;
            }).map(_item -> 1).reduce(countYears, Integer::sum);
            for (int i = 0; i < countYears; i++) {
                yearList = new ArrayList<>(copyListWebR);
                final int yearToCheck = listYears.get(i);
                yearList.removeIf((Predicate<WebinarRealizado>) web -> {
                    return web.getFecha().getYear() != yearToCheck;
                });
                orderedByYearList.add(yearList);
                list.add(buildChartPieSvg(ChartType.PIE, "Webinars Realizados " + yearToCheck,
                        "Webinars realizados durante el año por las instituciones", yearList));
            }
        } else {
            copyListWebR.removeIf((Predicate<WebinarRealizado>) web -> {
                return web.getFecha().getYear() != yearSel;
            });
            for (WebinarRealizado web : copyListWebR) {
                int m = web.getFecha().getMonthValue();
                if (month == 0 || month != m) {
                    listYears.add(m);
                    yearList = new ArrayList<>(copyListWebR);
                    final int monthToCheck = m;
                    yearList.removeIf((Predicate<WebinarRealizado>) w -> {
                        return w.getFecha().getMonthValue() != monthToCheck;
                    });
                    orderedByYearList.add(yearList);
                    list.add(buildChartPieSvg(ChartType.PIE, "Webinars Realizados de "
                            + Month.of(m).getDisplayName(TextStyle.FULL, locale).toUpperCase(),
                            "Webinars realizados durante el mes por las instituciones", yearList));
                    month = m;
                }
            }
        }
        return list;
    }

    private List<Agremiado> getListInstituto(List<Agremiado> sourceList) {
        sourceList.sort((Agremiado a1, Agremiado a2) -> {
            return a1.getInstitucion().compareToIgnoreCase(a2.getInstitucion());
        });
        return sourceList;
    }

    private List<Agremiado> getListCountries(List<Agremiado> sourceList) {
        sourceList.sort((Agremiado o1, Agremiado o2) -> {
            if (o1.getPais() < o2.getPais()) {
                return -1;
            } else if (o1.getPais() > o2.getPais()) {
                return 1;
            }
            return 0;
        });
        return sourceList;
    }

    private String buildChartPieSvg(ChartType charType, String titleChart, String subTitleChart, List<WebinarRealizado> data) {
        Configuration config = createChart(charType, titleChart, subTitleChart, false).getConfiguration();
        plotOptPie(config, true, true, LABEL_FORMATTER, 0);
        fillChartDataPie(config, data, titleChart);
        return SVGGenerator.getInstance().generate(config);
    }

    // Configuration config,List<Agremiado> data,String seriesName,boolean drillDownData
    /*private String buildChart(ChartType charType, String titleChart, String subTitleChart, boolean exporting, List<Agremiado> data,
            String filterBy) {
        Configuration config = createChart(charType, titleChart, subTitleChart, exporting);
        plotOptPie(config, true, true, LABEL_FORMATTER, 0);
        fillChartDataPie(config, data, titleChart, false, filterBy);
        return SVGGenerator.getInstance().generate(config);
    } */
    private Chart createChart(ChartType chartType, String titleChart, String subTitleChart, boolean exporting) {
        Chart chart = new Chart(chartType);
        chart.setWidth(1000, Unit.PIXELS);
        chart.setHeight(472, Unit.PIXELS);
        Configuration config = chart.getConfiguration();
        config.setTitle(titleChart);
        config.setSubTitle(subTitleChart);
        chart.getConfiguration().setExporting(exporting);
        return chart;
    }

    private void plotOptPie(Configuration config, boolean showInLegend, boolean dtLblEnable, String dtLblFormatter, float conectorPadding) {
        PlotOptionsPie plotOpt = new PlotOptionsPie();
        plotOpt.setShowInLegend(showInLegend);
        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(dtLblEnable);
        dataLabels.setFormatter(dtLblFormatter);
        dataLabels.setConnectorPadding(conectorPadding);
        plotOpt.setDataLabels(dataLabels);
        config.setPlotOptions(plotOpt);
    }

    private void plotOptColumn(Configuration config, AxisType axisTypeX, boolean dtLblEnable) {
        XAxis x = new XAxis();
        x.setType(axisTypeX);
        x.setCrosshair(new Crosshair());
        x.setTitle("Mes");
        x.setCategories("Enero", "Febero", "Marzo", "Abril", "Mayo", "junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Nomviembre", "Diciembre");
        Labels labels = new Labels();
        labels.setRotation(-45);
        labels.setAlign(HorizontalAlign.RIGHT);
        x.setLabels(labels);
        config.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Webinars Realizados");
        y.setAllowDecimals(false);
        config.addyAxis(y);

        PlotOptionsColumn column = new PlotOptionsColumn();
        column.setCursor(Cursor.POINTER);
        column.setColorByPoint(true);
        column.setDataLabels(new DataLabels(dtLblEnable));
        config.setPlotOptions(column);

    }

    private DataProviderSeries<Agremiado> setDataSeriesColumn(String seriesName, boolean colorByPoint, List<Agremiado> data) {
        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
        plotOptionsColumn.setColorByPoint(colorByPoint);
        ListDataProvider<Agremiado> dataProvider = new ListDataProvider<>(data);
        DataProviderSeries<Agremiado> series = new DataProviderSeries<>(dataProvider);
        series.setName(seriesName);
        series.setPlotOptions(plotOptionsColumn);
        return series;
    }

    // Configuration config, AxisType axisTypeX, String titleAxisX, String titleAxisY, boolean dtLblEnable
    private Chart getAgremiadoChartColumn(List<WebinarRealizado> data, String title, String subTitle, int filteredYear) {
        Chart chart = createChart(ChartType.COLUMN, title, subTitle, true);
        Configuration config = chart.getConfiguration();
        plotOptColumn(config, AxisType.CATEGORY, true);
        config.getLegend().setEnabled(false);
        fillChartColumn(data, config, filteredYear);
        return chart;
    }

    private boolean yearIsPresent(List<WebinarRealizado> data, int year) {
        boolean currentYearWebs = false;
        for (WebinarRealizado web : data) {
            if (web.getFecha().getYear() == year) {
                currentYearWebs = true;
                break;
            }
        }
        return currentYearWebs;
    }

    private void fillChartColumn(List<WebinarRealizado> data, Configuration config, int filteredYear) {
        try {
            int[] values = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            if (!data.isEmpty()) {
                if (yearIsPresent(data, filteredYear)) {
                    List<WebinarRealizado> copyData = new ArrayList<>(data);
                    copyData.removeIf((WebinarRealizado webinar) -> {
                        return webinar.getFecha().getYear() != filteredYear;
                    });
                    Iterator<WebinarRealizado> it = copyData.iterator();
                    while (it.hasNext()) {
                        WebinarRealizado web = it.next();
                        values[web.getFecha().getMonthValue() - 1] += 1;
                    }

                    ListSeries serie = new ListSeries("Webinars Realizados",
                            values[0], values[1], values[2], values[3], values[4], values[5],
                            values[6], values[7], values[8], values[9], values[10], values[11]);
                    config.addSeries(serie);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(webinarRealizadoChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillChartColumn(List<Agremiado> data, Configuration config, String seriesName) {
        try {
            int cont = 0;
            boolean filterByInstituto = (seriesName.compareTo("Instituciones") == 0);
            mapInstitutions = new HashMap<>();
            if (!data.isEmpty()) {
                String tempValue = "";
                Iterator<Agremiado> it = data.iterator();
                while (it.hasNext()) {
                    Agremiado a = it.next();
                    String objValue = filterByInstituto ? a.getInstitucion() : a.getObjPais().getNombre();
                    if (tempValue.compareToIgnoreCase(objValue) != 0) {
                        if (cont != 0) {
                            mapInstitutions.replace(tempValue, cont);
                        }
                        mapInstitutions.put(objValue, 0);
                        tempValue = objValue;
                        cont = 0;
                    }
                    cont = cont + 1;
                    if (!it.hasNext()) {
                        mapInstitutions.replace(tempValue, cont);
                    }
                }
                DataProviderSeries<Agremiado> series = setDataSeriesColumn(seriesName, true, data);
                if (filterByInstituto) {
                    series.setX(Agremiado::getInstitucion);
                } else {
                    series.setX(agremiadoBean -> (filterByInstituto ? agremiadoBean.getInstitucion() : agremiadoBean.getObjPais().getNombre()));
                }
                series.setY(agremiado -> {
                    if (filterByInstituto) {
                        return mapInstitutions.get(agremiado.getInstitucion());
                    } else {
                        return mapInstitutions.get(agremiado.getObjPais().getNombre());
                    }
                });
                config.addSeries(series);
            }
        } catch (Exception ex) {
            Logger.getLogger(webinarRealizadoChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Chart getAgremiadoChartPie(List<Agremiado> data, String filterBy, String subTitle, boolean drillDown) {
        Chart chart = createChart(ChartType.PIE, "Agremiados", subTitle, true);
        Configuration config = chart.getConfiguration();
        plotOptPie(config, true, true, LABEL_FORMATTER, 0);
        //fillChartDataPie(config, data, "Agremiados", drillDown, filterBy);
        return chart;
    }

    private void fillChartDataPie(Configuration config, List<WebinarRealizado> data, String seriesName) {
        DataSeries series = new DataSeries();
        series.setName(seriesName);
        data.sort((WebinarRealizado w1, WebinarRealizado w2) -> {
            return w1.getInstitucion().compareToIgnoreCase(w2.getInstitucion());
        });
        if (!data.isEmpty()) {
            Iterator<WebinarRealizado> it = data.iterator();
            String i = data.get(0).getInstitucion();
            int cont = 0;
            while (it.hasNext()) {
                WebinarRealizado web = it.next();
                if (i.compareToIgnoreCase(web.getInstitucion()) != 0) {
                    series.add(new DataSeriesItem(i, cont));
                    cont = 0;
                    i = web.getInstitucion();
                }
                cont++;
                if (!it.hasNext()) {
                    series.add(new DataSeriesItem(i, cont));
                }
            }
        }

        config.addSeries(series);

    }


    /*private void setDataPie(Configuration config, String seriesName, List<Agremiado> data, String filterBy) {
        DataSeries series = new DataSeries();
        series.setName(seriesName);
        switch (filterBy) {
            case "instituciones":
                dataPieInstitutos(config, series, data);
                break;
            case "paises":
                dataPiePaises(config, series, data);
                break;
            case "genero":
                dataPieGenero(config, series, data);
                break;
        }
    }*/
    private void dataPiePaises(Configuration config, DataSeries series, List<Agremiado> data) {
        try {
            int cont = 0, maxReg = 0;
            List<String> paises = new ArrayList<>();
            List<Integer> totalPais = new ArrayList<>();
            String pais = data.get(0).getObjPais().getNombre();
            paises.add(pais);
            for (Agremiado a : data) {
                if (pais.compareToIgnoreCase(a.getObjPais().getNombre()) != 0) {
                    paises.add(a.getObjPais().getNombre());
                    pais = a.getObjPais().getNombre();
                    totalPais.add(cont);
                    if (cont > maxReg) {
                        maxReg = cont;
                    }
                    cont = 0;
                }
                cont = cont + 1;
            }
            totalPais.add(cont);

            for (int i = 0; i < totalPais.size(); i++) {
                DataSeriesItem item = new DataSeriesItem(paises.get(i), totalPais.get(i));
                if (totalPais.get(i) == maxReg) {
                    item.setSliced(true);
                }
                series.add(item);

            }
            config.addSeries(series);
        } catch (Exception e) {
            Logger.getLogger(webinarRealizadoChart.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void dataPieGenero(Configuration config, DataSeries series, List<Agremiado> data) {
        try {
            List<Integer> totalGenero = new ArrayList<>();
            int cont = 0, contM = 0, contF = 0;
            if (data.size() > 0) {
                for (Agremiado a : data) {
                    if (a.getSexo() == 'H') {
                        contM = contM + 1;
                    } else {
                        contF = contF + 1;
                    }
                    cont = cont + 1;
                }
                totalGenero.add(contM);
                totalGenero.add(contF);
            }
            DataSeriesItem itemSliced = new DataSeriesItem("Hombres", totalGenero.get(0));
            itemSliced.setSliced(true);
            series.add(itemSliced);
            series.add(new DataSeriesItem("Mujeres", totalGenero.get(1)));
            config.addSeries(series);
        } catch (Exception e) {
            //
        }
    }

    private void dataPieInstitutos(Configuration config, DataSeries series, List<Agremiado> data) {
        try {
            int cont = 0, maxReg = 0;
            List<String> instituciones = new ArrayList<>();
            List<Integer> totalInst = new ArrayList<>();
            String ins = data.get(0).getInstitucion();
            instituciones.add(data.get(0).getInstitucion());
            for (Agremiado a : data) {
                if (ins.compareToIgnoreCase(a.getInstitucion()) != 0) {
                    instituciones.add(a.getInstitucion());
                    ins = a.getInstitucion();
                    totalInst.add(cont);
                    if (cont > maxReg) {
                        maxReg = cont;
                    }
                    cont = 0;
                }
                cont = cont + 1;
            }
            totalInst.add(cont);

            for (int i = 0; i < totalInst.size(); i++) {
                DataSeriesItem item = new DataSeriesItem(instituciones.get(i), totalInst.get(i));
                if (totalInst.get(i) == maxReg) {
                    item.setSliced(true);
                }
                series.add(item);
            }
            config.addSeries(series);
        } catch (Exception e) {
            //
        }
    }

    private void setDrillDownDataPie(Configuration config, String seriesName, List<Agremiado> data) {
        DataSeries series = new DataSeries();
        series.setName(seriesName);
        try {
            List<String> instituciones = new ArrayList<>();
            List<Integer> totalInst = new ArrayList<>();
            List<int[]> totalGenero = new ArrayList<>();
            int cont = 0, maxRegistros = 0, contM = 0, contF = 0;
            if (data.size() > 0) {
                String ins = data.get(0).getInstitucion();
                instituciones.add(data.get(0).getInstitucion());
                for (Agremiado a : data) {
                    if (ins.compareToIgnoreCase(a.getInstitucion()) != 0) {
                        instituciones.add(a.getInstitucion());
                        ins = a.getInstitucion();
                        totalInst.add(cont);
                        totalGenero.add(new int[]{contM, contF});
                        if (cont > maxRegistros) {
                            maxRegistros = cont;
                        }
                        cont = 0;
                        contM = 0;
                        contF = 0;
                    }
                    if (a.getSexo() == 'H') {
                        contM = contM + 1;
                    } else {
                        contF = contF + 1;
                    }
                    cont = cont + 1;
                }
                totalInst.add(cont);
                totalGenero.add(new int[]{contM, contF});
                for (int i = 0; i < totalInst.size(); i++) {
                    DataSeries drillDownSeries = new DataSeries();
                    drillDownSeries.setName("Género");
                    DataSeriesItem mainItem = new DataSeriesItem(instituciones.get(i), totalInst.get(i));
                    DataSeriesItem subItemM = new DataSeriesItem("Hombres", totalGenero.get(i)[0]);
                    DataSeriesItem subItemF = new DataSeriesItem("Hombres", totalGenero.get(i)[1]);
                    drillDownSeries.add(subItemM);
                    drillDownSeries.add(subItemF);
                    series.addItemWithDrilldown(mainItem);
                    if (totalInst.get(i) == maxRegistros) {
                        mainItem.setSliced(true);
                    }
                }
                config.addSeries(series);
            }
            //setDrillDownCallBackPie(chart, totalGenero, instituciones);
            //setDrillUpListener(data);
        } catch (Exception ex) {
            Logger.getLogger(webinarRealizadoChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    private void setDrillUpListener(List<Agremiado> data) {
        chart.addChartDrillupListener((ChartDrillupListener) event -> {
            setLayoutContentChart(getAgremiadoChartPie(data, "instituciones", "Agremiados registrados por institución", true));
        });
    }*/
    private void setDrillDownCallBackPie(Chart chart, List<int[]> totalGenero, List<String> instituciones) {
        chart.setDrilldownCallback((DrilldownEvent event) -> {
            DataSeries drillDownSeries = new DataSeries("Género");

            int totalM = totalGenero.get(event.getItemIndex())[0];
            int totalF = totalGenero.get(event.getItemIndex())[1];

            DataSeriesItem dataItemM = new DataSeriesItem("Hombres", totalM);
            DataSeriesItem dataItemF = new DataSeriesItem("Mujeres", totalF);

            drillDownSeries.add(dataItemM);
            drillDownSeries.add(dataItemF);

            if (totalM > totalF) {
                dataItemM.setSliced(true);
            } else {
                dataItemF.setSliced(true);
            }
            Configuration confDrill = chart.getConfiguration();
            confDrill.setTitle("Agremiados de " + instituciones.get(event.getItemIndex()));
            confDrill.setSubTitle("Agremiados registrados por género de la institción: "
                    + instituciones.get(event.getItemIndex()));
            plotOptPie(confDrill, true, true, LABEL_FORMATTER, 1);
            chart.setConfiguration(confDrill);
            return drillDownSeries;
        });
    }

    private class InstitutoRecord implements Comparable {

        int total;
        LocalDate oldestReg;

        public InstitutoRecord(int total, LocalDate oldestReg) {
            this.total = total;
            this.oldestReg = oldestReg;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public LocalDate getOldestReg() {
            return oldestReg;
        }

        public void setOldestReg(LocalDate oldestReg) {
            this.oldestReg = oldestReg;
        }

        @Override
        public int compareTo(Object o) {
            InstitutoRecord obj = (InstitutoRecord) o;
            return this.oldestReg.compareTo(obj.getOldestReg());
        }
    }

    private class ObjectHolderWebinars {

        private int week, month, year, total;

        ObjectHolderWebinars() {
            week = 0;
            month = 0;
            year = 0;
            total = 0;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

    }
}

/*   callback function
    private Function<Agremiado,Object> callback(){
        Function<Agremiado,Object> call = null;
        call = agremiado ->{
            
        };
        return call;
    } */
 /* getInstituciones from listaAg_INST
    List<String> instituciones = new ArrayList<>();
            List<Integer> totalInst = new ArrayList<>();
    String ins = listaAg_INST.get(0).getInstitucion();
                instituciones.add(listaAg_INST.get(0).getInstitucion());
                for (Agremiado a : listaAg_INST) {
                    if (ins.compareToIgnoreCase(a.getInstitucion()) != 0) {
                        instituciones.add(a.getInstitucion());
                        ins = a.getInstitucion();
                        totalInst.add(contInst);
                        contInst = 0;
                    }
                    contInst = contInst + 1;
                }
                totalInst.add(contInst);

                
                for (int i = 0; i < totalInst.size(); i++) {
                    dataSeries.add(new DataSeriesItem(instituciones.get(i), totalInst.get(i)));

                }
                config.addSeries(dataSeries); */

/*data.sort((WebinarRealizado w1, WebinarRealizado w2) -> {
                    int y1 = w1.getFecha().getYear(), y2 = w2.getFecha().getYear(),
                            m1=w1.getFecha().getMonthValue(),m2=w2.getFecha().getMonthValue();
                    if(y1<y2)
                        return -1;
                    if(y1>y2)
                        return 1;
                    if(y1==y2){
                        if(m1<m2)
                            return -1;
                        if(m1>m2){
                            return 1;
                        }
                    }
                    return 0;
                });*/
