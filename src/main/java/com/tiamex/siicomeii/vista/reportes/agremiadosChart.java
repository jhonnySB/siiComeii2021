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
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.reportes.base.pdf.ReporteCompletoChart;
import com.tiamex.siicomeii.vista.utils.Element;
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
import com.vaadin.addon.charts.model.Lang;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.themes.VaadinTheme;
import com.vaadin.addon.charts.util.SVGGenerator;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jhon
 * @param <T>
 */
public class agremiadosChart<T> extends Panel {

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
    protected ResponsiveLayout contentChart;
    protected ResponsiveRow row1;
    TextField fileName;
    protected Button btnFullReport;
    protected Button btnReportChart;
    Exporting exporting;
    protected Grid<T> grid;
    public VerticalLayout main;
    Chart chart;
    CheckBox checkData;
    List<Agremiado> listaAg_INST = ControladorAgremiado.getInstance().getAllSorted("institucion");
    List<Agremiado> listaAg_PAIS = ControladorAgremiado.getInstance().getAllSorted("pais");
    List<Agremiado> filteredList = new ArrayList<>();
    HashMap<String, Integer> mapInstitutions;
    LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
    final String LABEL_FORMATTER = "'<b>'+ this.point.name +'</b> ('+this.point.y+') : '+ this.percentage.toFixed(2) +'%'";
    NativeSelect<String> selectBy,selectGraph;

    public VerticalLayout getMain() {
        return main;
    }

    public agremiadosChart(List<Agremiado> filteredList) throws Exception {
        this.filteredList = filteredList;
        initDlg();
    }

    public agremiadosChart() throws Exception {
        initDlg();
    }

    private void initDlg() throws Exception {
        ui = Element.getUI();
        content = new ResponsiveLayout();
        content.setSizeFull();
        content.setSpacing();

        ResponsiveRow r1 = content.addRow();
        List<Integer> scales = Arrays.asList(1, 2);
        RadioButtonGroup radioBtn = new RadioButtonGroup("", scales);
        radioBtn.setCaption("<span style=\"font-size:14px\">Escala del gráfico");
        radioBtn.setCaptionAsHtml(true);

        /*VerticalLayout vLayout = new VerticalLayout();
        vLayout.setResponsive(true);
        vLayout.setMargin(true); */
        ResponsiveLayout rLayoutGraph = new ResponsiveLayout();
        rLayoutGraph.setResponsive(true);
        ResponsiveRow rowGraph = rLayoutGraph.addRow();
        rowGraph.setResponsive(true);
        
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
                        switch(filterValue){
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
                                setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por institución","Instituciones"));
                                break;
                            case "País":
                                reorderedList = sortFilteredList("pais");
                                setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por país","Paises"));
                                break;
                            case "Género":
                                setLayoutContentChart(getAgremiadoChartColumn(filteredList,"Agremiados registrados por género","Género"));
                                break;
                        }
                    } else {
                        switch (filterValue) {
                            case "Institución":
                                setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST,"Agremiados registrados por institución","Instituciones"));
                                break;
                            case "País": System.out.println("column no filter");
                                setLayoutContentChart(getAgremiadoChartColumn(listaAg_PAIS,"Agremiados registrados por país","Países"));
                                break;
                            case "Género":
                                setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST,"Agremiados registrados por género","Género"));
                                break;
                        }
                    }
                    break;
            }
            selectGraph.setSelectedItem(selected);
        });
        
        selectBy = new NativeSelect<>("Mostrar por:", Arrays.asList("Institución", "País","Género"));
        selectBy.setEmptySelectionAllowed(false);selectBy.setResponsive(true);selectBy.setSelectedItem("Institución");
        selectBy.addValueChangeListener((ValueChangeListener) e -> {
            String selected = e.getValue().toString(); 
            switch (selected) {
                case "Institución": 
                    if (checkData.getValue()) {
                        sortFilteredList("institucion");
                        setLayoutContentChart(getAgremiadoChartPie(filteredList, "instituciones","Agremiados registrados por institución",true));
                    } else {
                        setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "instituciones","Agremiados registrados por institución",true));
                    }
                    break;
                case "País":
                    if (checkData.getValue()) {
                        sortFilteredList("pais");
                        setLayoutContentChart(getAgremiadoChartPie(filteredList, "paises","Agremiados registrados por país",false));
                    } else {
                        setLayoutContentChart(getAgremiadoChartPie(listaAg_PAIS, "paises","Agremiados registrados por país",false));
                    }
                    break;
                case "Género":
                    if (checkData.getValue()) {
                        setLayoutContentChart(getAgremiadoChartPie(filteredList, "genero","Agremiados registrados por género",false));
                    } else {
                        setLayoutContentChart(getAgremiadoChartPie(listaAg_INST, "genero","Agremiados registrados por género",false));
                    }
                    break;
            }
            selectGraph.setSelectedItem("Pastel");
            selectBy.setSelectedItem(selected);
        });
        

        btnFullReport = createExportButton(VaadinIcons.FILE_TEXT_O.getHtml(), currentDate.toString() + ".pdf", createPdfStreamSource(true),
                "Descargar reporte de todos los datos registrados");
        btnReportChart = createExportButton(VaadinIcons.BAR_CHART_H.getHtml(), currentDate.toString() + ".pdf", createPdfStreamSource(false),
                "Descargar reporte de los datos filtrados en la tabla");
        btnReportChart.addClickListener((ClickListener) listener->{
            if(filteredList.isEmpty()){
                Notification.show(null, "No hay datos filtrados en la tabla", Notification.Type.ERROR_MESSAGE);
            }
        });

        checkData = new CheckBox();
        checkData.setResponsive(true);
        checkData.setDescription("Seleccionar para mostrar solo los resultados filtrados en la tabla");
        checkData.setCaption("Datos filtrados");

        checkData.addValueChangeListener((ValueChangeListener) event -> {
            boolean chartTypePie = selectGraph.getValue().compareTo("Pastel") == 0;
            String filteredValue = selectBy.getValue(); List<Agremiado> reorderedList;
            if ((boolean) event.getValue()) {
                if (chartTypePie) { 
                    switch(filteredValue){
                        case "Institución":
                            reorderedList = sortFilteredList("institucion");
                            setLayoutContentChart(getAgremiadoChartPie(reorderedList, "instituciones","Agremiados registrados por institución",true));
                            break;
                        case "País":
                            reorderedList = sortFilteredList("pais");
                            setLayoutContentChart(getAgremiadoChartPie(reorderedList, "paises","Agremiados registrados por país",false));
                            break;
                        case "Género":
                            setLayoutContentChart(getAgremiadoChartPie(filteredList, "genero","Agremiados registrados por institución",false));
                            break;
                    }
                } else {
                    switch (filteredValue) {
                        case "Institución":
                            reorderedList = sortFilteredList("institucion");
                            setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por institución","Instituciones"));
                            break;
                        case "País": 
                            reorderedList = sortFilteredList("pais");
                            setLayoutContentChart(getAgremiadoChartColumn(reorderedList,"Agremiados registrados por país","Países"));
                            break;
                        case "Género":
                            setLayoutContentChart(getAgremiadoChartColumn(filteredList,"Agremiados registrados por institución","Género"));
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
                            setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST, "Agremiados registrados por institución", "Instituciones"));
                            break;
                        case "País":
                            setLayoutContentChart(getAgremiadoChartColumn(listaAg_PAIS, "Agremiados registrados por país", "Países"));
                            break;
                        case "Género":
                            setLayoutContentChart(getAgremiadoChartColumn(listaAg_INST, "Agremiados registrados por institución", "Género"));
                            break;
                    }
                }
            }
        });
        updateCheckBoxData();

        ResponsiveLayout chartOptionsLayout = new ResponsiveLayout();
        ResponsiveRow rowChart = chartOptionsLayout.addRow(); //  bottom_left
        rowChart.addColumn().withComponent(checkData);
        rowChart.addColumn().withComponent(selectBy);
        rowChart.addColumn().withComponent(selectGraph);

        //rowGraph.addColumn().withComponent(checkData);
        //rowGraph.addColumn().withComponent(selectGraph);
        rowGraph.addColumn().withComponent(chartOptionsLayout);

        //rowGraph.addColumn().withComponent(selectGraph).withOffset(ResponsiveLayout.DisplaySize.LG, 2);
        //vLayout.addComponent(rLayoutGraph);
        ResponsiveLayout downloadLayout = new ResponsiveLayout();
        String htmlIcon = VaadinIcons.INFO_CIRCLE.getHtml();
        downloadLayout.setCaption("<span style=\"font-size:12px\">Opciones de descarga " + htmlIcon + "</span>");
        downloadLayout.setDescription("Se muestran algunas opciones para descargar el gráfico y el reporte en PDF");
        downloadLayout.setCaptionAsHtml(true);

        ResponsiveRow rr = downloadLayout.addRow();
        //rr.addColumn().withComponent(radioBtn).withDisplayRules(12,12,12,12);
        rr.addColumn().withComponent(btnFullReport).withDisplayRules(12, 12, 6, 6);
        rr.addColumn().withComponent(btnReportChart).withDisplayRules(12, 12, 6, 6);
        //rowGraph.addColumn().withComponent(downloadLayout);
        ResponsiveRow rowDownload = rLayoutGraph.addRow().withAlignment(Alignment.BOTTOM_CENTER);
        rowDownload.addColumn().withComponent(downloadLayout);

        //vLayout.addComponent(downloadLayout);
        r1.addColumn().withComponent(rLayoutGraph).withDisplayRules(2, 2, 2, 2);
        r1.setMargin(true);

        contentChart = new ResponsiveLayout();
        contentChart.setSizeFull();
        contentChart.addComponent(getAgremiadoChartPie(listaAg_INST, "instituciones","Agremiados registrados por institución",true));
        //ResponsiveRow rowChart = contentChart.addRow();
        //rowChart.addColumn().withComponent(getAgremiadoChartPie());
        //r1.addColumn().withComponent(contentChart).withDisplayRules(12, 12, 10, 10);
        r1.addColumn().withComponent(contentChart, ResponsiveColumn.ColumnComponentAlignment.CENTER).withDisplayRules(12, 12, 10, 10);
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
        this.setSizeFull();
        this.setContent(content);
        this.setCaptionAsHtml(true);
        this.setHeight(Element.windowHeightPx());
    }
    
    public List<Agremiado> sortFilteredList(String sortBy){
        List<Agremiado> reorderedList = new ArrayList<>(filteredList);
        reorderedList.sort((Agremiado a1,Agremiado a2)->{
            if(sortBy.compareTo("institucion")==0){
                return a1.getInstitucion().compareToIgnoreCase(a2.getInstitucion());
            }else{
                return a1.getObjPais().getNombre().compareTo(a2.getObjPais().getNombre());
            }
        });
        return reorderedList;
    }

    private void setLayoutContentChart(Component component) {
        contentChart.removeAllComponents();
        contentChart.addComponent(component);
    }

    private void updateCheckBoxData() {
        if (filteredList.isEmpty()) {
            checkData.setEnabled(false);
        }
    }

    private Button createExportButton(String caption, String filename, StreamResource.StreamSource ss, String desc) {
        Button b = new Button(caption);
        b.setCaptionAsHtml(true);
        b.setDescription(desc);
        FileDownloader downloader = new FileDownloader(new StreamResource(ss, filename));
        downloader.extend(b);
        return b;
    }

    private StreamResource.StreamSource createPdfStreamSource(boolean fullReport) {
        StreamResource.StreamSource ss
                = () -> {
                    File result = doExportPDF(fullReport);
                    if (result != null) {
                        try {
                            return new FileInputStream(result);
                        } catch (FileNotFoundException e) {
                            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                    return null;
                };
        return ss;
    }

    private File doExportPDF(boolean fullReport) {
        File file = null;
        ReporteCompletoChart reporte = new ReporteCompletoChart();
        try {
            if (fullReport) {
                Configuration exportConfig = chart.getConfiguration(); exportConfig.setExporting(false);
                String svgMain = SVGGenerator.getInstance().generate(exportConfig);
                String svgGenre = buildChart(ChartType.PIE, "Género", "Agremiados hombres y mujeres registrados", false, listaAg_INST, "genero");
                String svgCountries = buildChart(ChartType.PIE, "Países", "Lugares de procedencia de los agremiados registrados", false, 
                        listaAg_PAIS, "paises");
                String svgInst = buildChart(ChartType.PIE, "Instituciones", "Instituciones de procedencia de los agremiados registrados", false, 
                        listaAg_INST, "instituciones");
                file = reporte.writePdf("reporte_" + currentDate.toString(), svgMain, svgGenre, svgCountries, svgInst, 300F, 200F,null,false);
            } else {
                if(!filteredList.isEmpty()){
                    String svgGenre = buildChart(ChartType.PIE, "Género", "Agremiados hombres y mujeres registrados", false, filteredList, "genero");
                    String svgCountries = buildChart(ChartType.PIE, "Países", "Lugares de procedencia de los agremiados registrados", 
                            false,getListCountries(filteredList), "paises");
                    String svgInst = buildChart(ChartType.PIE, "Instituciones", "Instituciones de procedencia de los agremiados registrados", 
                            false,getListInstituto(filteredList), "instituciones");
                    file = reporte.writePdf("reporte_" + currentDate.toString(), null, svgGenre, svgCountries, svgInst, 300F, 200F,filteredList,true);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }
    
    private List<Agremiado> getListInstituto(List<Agremiado> sourceList){
        sourceList.sort((Agremiado a1, Agremiado a2) -> {
            return a1.getInstitucion().compareToIgnoreCase(a2.getInstitucion());
        });
        return sourceList;
    }
    
    private List<Agremiado> getListCountries(List<Agremiado> sourceList){
        sourceList.sort((Agremiado o1, Agremiado o2) -> {
            if(o1.getPais()< o2.getPais()){return -1;}else if(o1.getPais()>o2.getPais()){return 1;}
            return 0;
        });
        return sourceList;
    }

    // Configuration config,List<Agremiado> data,String seriesName,boolean drillDownData
    private String buildChart(ChartType charType, String titleChart, String subTitleChart, boolean exporting, List<Agremiado> data,
            String filterBy) {
        Configuration config = createChart(charType, titleChart, subTitleChart, exporting);
        plotOptPie(config, true, true, LABEL_FORMATTER, 0);
        fillChartDataPie(config, data, titleChart, false, filterBy);
        return SVGGenerator.getInstance().generate(config);
    }

    private Configuration createChart(ChartType chartType, String titleChart, String subTitleChart, boolean exporting) {
        chart = new Chart(chartType);
        Configuration config = chart.getConfiguration();
        config.setTitle(titleChart);
        config.setSubTitle(subTitleChart);
        chart.getConfiguration().setExporting(exporting);
        return config;
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

    private void plotOptColumn(Configuration config, AxisType axisTypeX, String titleAxisX, String titleAxisY, boolean dtLblEnable) {
        XAxis x = new XAxis();
        x.setType(axisTypeX);
        x.setCrosshair(new Crosshair());
        x.setTitle(titleAxisX);
        config.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle(titleAxisY);
        config.addyAxis(y);

        PlotOptionsColumn column = new PlotOptionsColumn();
        column.setCursor(Cursor.POINTER);
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
    private Chart getAgremiadoChartColumn(List<Agremiado> data,String subTitle,String seriesName) {
        Configuration config = createChart(ChartType.COLUMN, "Agremiados", subTitle, true);
        plotOptColumn(config, AxisType.CATEGORY, seriesName, "Agremiados", true);
        config.getLegend().setEnabled(false);
        if(seriesName.compareTo("Género")==0){
            fillChartColumn(data,config);
        }else
            fillChartColumn(data,config,seriesName);
        return chart;
    }
    
    private void fillChartColumn(List<Agremiado> data, Configuration config) {
        try {
            int contH = 0,contM = 0; mapInstitutions = new HashMap<>();
            if (data.size() > 0) {
                Iterator<Agremiado> it = data.iterator();
                while (it.hasNext()) {
                    Agremiado a = it.next();
                    if(a.getSexo() == 'H'){
                        contH = contH + 1;
                    }else{
                        contM = contM + 1;
                    }
                }
                DataSeries series = new DataSeries();
                series.setName("Género");
                series.add(new DataSeriesItem("Hombres",contH));
                series.add(new DataSeriesItem("Mujeres",contM));
                PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
                plotOptionsColumn.setColorByPoint(true); series.setPlotOptions(plotOptionsColumn);
                config.addSeries(series);
            }
        } catch (Exception ex) {
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private void fillChartColumn(List<Agremiado> data,Configuration config,String seriesName){
        try {
            int cont = 0; boolean filterByInstituto = (seriesName.compareTo("Instituciones")==0); System.out.println("filter "+filterByInstituto);
            mapInstitutions = new HashMap<>();
            if (data.size() > 0) {
                String tempValue = "";
                Iterator<Agremiado> it = data.iterator();
                while (it.hasNext()) {
                    Agremiado a = it.next();
                    String objValue = filterByInstituto ? a.getInstitucion() : a.getObjPais().getNombre();
                    System.out.println(objValue);
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
                if(filterByInstituto){
                    series.setX(Agremiado::getInstitucion);
                }else{
                    series.setX(agremiadoBean -> (filterByInstituto ? agremiadoBean.getInstitucion() : agremiadoBean.getObjPais().getNombre()));
                }
                series.setY(agremiado -> {
                    if(filterByInstituto){
                        return mapInstitutions.get(agremiado.getInstitucion());
                    }else
                        return mapInstitutions.get(agremiado.getObjPais().getNombre());
                });
                config.addSeries(series);
            }
        } catch (Exception ex) {
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Chart getAgremiadoChartPie(List<Agremiado> data, String filterBy,String subTitle,boolean drillDown) {
        Configuration config = createChart(ChartType.PIE, "Agremiados", subTitle, true);
        plotOptPie(config, true, true, LABEL_FORMATTER, 0);
        fillChartDataPie(config, data, "Agremiados", drillDown, filterBy);
        return chart;
    }

    private void fillChartDataPie(Configuration config, List<Agremiado> data, String seriesName, boolean drillDownData, String filterBy) {
        if (drillDownData==true) {
            setDrillDownDataPie(config, seriesName, data);
        } else { 
            setDataPie(config, seriesName, data, filterBy);
        }
    }

    private void setDataPie(Configuration config, String seriesName, List<Agremiado> data, String filterBy) {
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
    }

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
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, e);
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
            int cont = 0 , maxReg=0;
            List<String> instituciones = new ArrayList<>();
            List<Integer> totalInst = new ArrayList<>();
            String ins = data.get(0).getInstitucion();
            instituciones.add(data.get(0).getInstitucion());
            for (Agremiado a : data) {
                if (ins.compareToIgnoreCase(a.getInstitucion()) != 0) {
                    instituciones.add(a.getInstitucion());
                    ins = a.getInstitucion();
                    totalInst.add(cont);
                    if(cont>maxReg){
                        maxReg = cont;
                    }
                    cont = 0;
                }
                cont = cont + 1;
            }
            totalInst.add(cont);

            for (int i = 0; i < totalInst.size(); i++) {
                DataSeriesItem item = new DataSeriesItem(instituciones.get(i),totalInst.get(i));
                if(totalInst.get(i)==maxReg){
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
            setDrillDownCallBackPie(chart, totalGenero, instituciones);
            setDrillUpListener(data);
        } catch (Exception ex) {
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDrillUpListener(List<Agremiado> data) {
        chart.addChartDrillupListener((ChartDrillupListener) event -> {
            setLayoutContentChart(getAgremiadoChartPie(data, "instituciones","Agremiados registrados por institución",true));
        });
    }

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

    private void dataProviderEvent() {
        /*dataProvider.addDataProviderListener((DataProviderListener<Agremiado>) eventData -> {
                
            }); */
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
}
