/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.reportes;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartDrillupListener;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.DrilldownCallback;
import com.vaadin.addon.charts.DrilldownEvent;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Crosshair;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Exporting;
import com.vaadin.addon.charts.model.Lang;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.themes.VaadinTheme;
import com.vaadin.addon.charts.util.SVGGenerator;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jhon
 * @param <T>
 */
public class agremiadosChart<T> extends Panel{
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
    protected Button exportBtn;
    Exporting exporting;
    protected Grid<T> grid;
    public VerticalLayout main;
    Configuration config;

    public VerticalLayout getMain() {
        return main;
    }

    public agremiadosChart() throws Exception {
        initDlg();
        
    }
    
    private String getDefaultFileName(){
        return "agremiados_graph_"+LocalDate.now(ZoneId.systemDefault()).toString();
    }
    
    private void initDlg() throws Exception {
        ui = Element.getUI();
        content = new ResponsiveLayout();
        content.setSizeFull();
        content.setSpacing(); 
        
        
        ResponsiveRow r1= content.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        List<Integer> scales = Arrays.asList(1,2);
        RadioButtonGroup radioBtn = new RadioButtonGroup("",scales);
        radioBtn.setCaption("<span style=\"font-size:14px\">Escala del gráfico"); radioBtn.setCaptionAsHtml(true);
        
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setResponsive(true); vLayout.setMargin(true);
        
        ResponsiveLayout rLayoutGraph = new ResponsiveLayout();
        rLayoutGraph.setResponsive(true); 
        ResponsiveRow rowGraph = rLayoutGraph.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        NativeSelect<String> selectGraph = new NativeSelect<>("Tipo de gráfico:",Arrays.asList("Columna","Pastel"));
        selectGraph.setEmptySelectionAllowed(false); selectGraph.setEmptySelectionCaption("Selecicone una opción...");
        selectGraph.setSelectedItem("Pastel");
        
        selectGraph.addValueChangeListener((ValueChangeListener) e ->{
            String selected = selectGraph.getValue();
            switch(selected){
                case "Pastel":
                    contentChart.removeAllComponents();
                    contentChart.addComponent(getAgremiadoChartPie());
                    break;
                case "Columna":
                    contentChart.removeAllComponents();
                    contentChart.addComponent(getAgremiadoChartColumn());                    
                    break;
            }
            selectGraph.setSelectedItem(selected);
        });
        
        exportBtn = createExportButton(VaadinIcons.DOWNLOAD.getHtml(),"reporte.pdf", createPdfStreamSource());
        
        
        rowGraph.addColumn().withComponent(selectGraph);
        vLayout.addComponent(rLayoutGraph);
        
        ResponsiveLayout downloadLayout = new ResponsiveLayout(); 
        String htmlIcon = VaadinIcons.INFO_CIRCLE.getHtml();
        downloadLayout.setCaption("<span style=\"font-size:12px\">Opciones de descarga "+htmlIcon+"</span>");
        downloadLayout.setDescription("Se muestran algunas opciones para descargar el gráfico y el reporte en PDF");
        downloadLayout.setCaptionAsHtml(true);
        
        ResponsiveRow rr = downloadLayout.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        rr.addColumn().withComponent(radioBtn).withDisplayRules(12,12,12,12);
        rr.addColumn().withComponent(exportBtn).withDisplayRules(12,12,12,12);
        
        
        vLayout.addComponent(downloadLayout);
        r1.addColumn().withComponent(vLayout).withDisplayRules(2, 2, 2, 2);
        r1.setMargin(true);
        
        contentChart = new ResponsiveLayout(); 
        contentChart.setSizeFull();
        contentChart.addComponent(getAgremiadoChartPie());
        //ResponsiveRow rowChart = contentChart.addRow();
        //rowChart.addColumn().withComponent(getAgremiadoChartPie());
        r1.addColumn().withComponent(contentChart).withDisplayRules(10,10,10,10);
        
        
        
        Lang lang=new Lang();
        lang.setNoData("No hay datos que mostrar.");
        lang.setPrintChart("Imprimir gráfico");
        lang.setDownloadPNG("Descargar en PNG");
        lang.setDownloadJPEG("Descargar en JPG");
        lang.setDownloadPDF("Descargar en PDF");
        lang.setDownloadSVG("Descargar en SVG");
        lang.setContextButtonTitle("Opciones de descarga (Gráfico)"); lang.setDrillUpText("Regresar al gráfico principal");
        lang.setLoading("Cargando...");
        ChartOptions.get().setLang(lang); 
        ChartOptions.get().setTheme(new VaadinTheme());
        
        this.setCaption("Reportes agremiados");
        this.setSizeFull();
        this.setContent(content);
        this.setCaptionAsHtml(true);
        this.setHeight(Element.windowHeightPx());  
    }
    
    private Button createExportButton(String caption, String filename,
            StreamResource.StreamSource ss) {
        Button b = new Button(caption);
        b.setCaptionAsHtml(true);
        FileDownloader downloader = new FileDownloader(new StreamResource(ss,
                filename));
        downloader.extend(b);
        return b;
    }
    
    private StreamResource.StreamSource createPdfStreamSource() {
        StreamResource.StreamSource ss
                = () -> {
                    File result = doExportPDF();
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
    
    private File doExportPDF() {
        Configuration exportConfig = config;
        exportConfig.setExporting(false);
        String svg = SVGGenerator.getInstance().generate(exportConfig);
        PdfExportDemo pdfExp = new PdfExportDemo();
        File file = pdfExp.writePdf("reporte", svg, Float.parseFloat("300"), Float.parseFloat("200"));
        return file;
    }
    
    private Chart getAgremiadoChartColumn() {
        Chart chart = new Chart(ChartType.COLUMN);
        config = chart.getConfiguration();
        config.setTitle("Agremiados Columna");

        config.getLegend().setEnabled(false);

        XAxis x = new XAxis();
        x.setType(AxisType.CATEGORY);
        x.setCrosshair(new Crosshair());
        x.setTitle("Instituciones");
        config.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Agremiados");
        config.addyAxis(y);

        PlotOptionsColumn column = new PlotOptionsColumn();
        column.setCursor(Cursor.POINTER);
        column.setDataLabels(new DataLabels(true));
        config.setPlotOptions(column);
        DataSeries dataSeries = new DataSeries();
        dataSeries.setName("Instituciones");
        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
        plotOptionsColumn.setColorByPoint(true);
        dataSeries.setPlotOptions(plotOptionsColumn);

        try {
            List<Agremiado> lista = ControladorAgremiado.getInstance().getAllSorted("institucion");
            List<String> instituciones = new ArrayList<>();
            List<Integer> totalInst = new ArrayList<>();
            int cont = 0;

            if (lista.size() > 0) {
                String ins = lista.get(0).getInstitucion();
                instituciones.add(lista.get(0).getInstitucion());
                for (Agremiado a : lista) {
                    if (ins.compareToIgnoreCase(a.getInstitucion()) != 0) {
                        instituciones.add(a.getInstitucion());
                        ins = a.getInstitucion();
                        totalInst.add(cont);
                        cont = 0;
                    }
                    cont = cont + 1;
                }
                totalInst.add(cont);

                for (int i = 0; i < totalInst.size(); i++) {
                    dataSeries.add(new DataSeriesItem(instituciones.get(i), totalInst.get(i)) );
                    
                }
                
                config.addSeries(dataSeries);
                config.setTitle("Agremiados");
                config.setSubTitle("Agremiados registrados por institución");

            }
        } catch (Exception ex) {
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, ex);
        }

        return chart;
    }
   

    private Chart getAgremiadoChartPie(){
        
        Chart chart=new Chart(ChartType.PIE); 
        config = chart.getConfiguration();
        PlotOptionsPie plotOpt=new PlotOptionsPie();
        plotOpt.setInnerSize("0");
        DataLabels dataLabels = new DataLabels(); 
        dataLabels.setEnabled(true);
        dataLabels.setFormatter("'<b>'+ this.point.name +'</b> : '+ this.point.y");
        // return this.point.name + ' (' + this.point.y + ')'+' : '+fixedValue+'%'
        plotOpt.setDataLabels(dataLabels);
        config.setPlotOptions(plotOpt);
        
        DataSeries series = new DataSeries();
        try {
            List<Agremiado> lista = ControladorAgremiado.getInstance().getAllSorted("institucion");
            List<String> instituciones = new ArrayList<>();
            List<Integer> totalInst= new ArrayList<>();
            List<int []> totalGenero = new ArrayList<>();
            int cont=0,maxRegistros=0,contM=0,contF=0;
            
            if(lista.size()>0){
            
                String ins = lista.get(0).getInstitucion();
                instituciones.add(lista.get(0).getInstitucion());
                for(Agremiado a:lista){
                   if(ins.compareToIgnoreCase(a.getInstitucion())!=0){
                       instituciones.add(a.getInstitucion());
                       ins=a.getInstitucion();
                       totalInst.add(cont);
                       totalGenero.add(new int[]{contM,contF});
                       if(cont>maxRegistros){
                           maxRegistros=cont;
                       }
                       cont=0;
                       contM=0;
                       contF=0;
                   }
                   if(a.getSexo()=='M'){
                       contM=contM+1;
                   }else{
                       contF=contF+1;
                   }
                   
                   cont=cont+1; 
                }
                totalInst.add(cont);  
                totalGenero.add(new int[]{contM,contF});
                
                Map<Integer,String> mapSeries = new HashMap<>();
                for(int j=0;j<totalInst.size();j++){
                    mapSeries.put(totalInst.get(j), instituciones.get(j));
                }

                series.setName("Agremiados");
                for(int i=0;i<totalInst.size();i++){
                    
                    DataSeries drillDownSeries = new DataSeries(); 
                    drillDownSeries.setName("Género");
                    drillDownSeries.setId("subSeriesGenre");
                    
                    DataSeriesItem mainItem = new DataSeriesItem(instituciones.get(i),totalInst.get(i));
                    DataSeriesItem subItemM = new DataSeriesItem("Hombres",totalGenero.get(i)[0]);
                    DataSeriesItem subItemF = new DataSeriesItem("Hombres",totalGenero.get(i)[1]);
                    drillDownSeries.add( subItemM );
                    drillDownSeries.add( subItemF );

                    series.addItemWithDrilldown(mainItem);                  
                    if (totalInst.get(i) == maxRegistros) {
                        mainItem.setSliced(true);
                    }
                }

                config.setTitle("Agremiados");
                config.setSubTitle("Agremiados registrados por institución");
                config.addSeries(series);
            }
            
            exporting = new Exporting(true);
            //exporting.setScale(cont);
            chart.getConfiguration().setExporting(true);
            chart.getConfiguration().setExporting(exporting);
            
            setDrillDownCallBackPie(chart,totalGenero,instituciones);
            setDrillUpListener(chart);
        } catch (Exception ex) {
            Logger.getLogger(agremiadosChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        return chart;
    }
    
   private void setDrillUpListener(Chart chart){
       chart.addChartDrillupListener((ChartDrillupListener) event->{
                contentChart.removeAllComponents();
                Chart newChart = getAgremiadoChartPie();
                contentChart.addComponent(newChart);
            });
   } 
    
   private void setDrillDownCallBackPie(Chart chart,List<int[]> totalGenero,List<String> instituciones){
       chart.setDrilldownCallback(new DrilldownCallback() {
           @Override
           public Series handleDrilldown(DrilldownEvent event) {

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

               chart.setConfiguration(confDrill);

               return drillDownSeries;
           }

       });
   }
    
   
}
