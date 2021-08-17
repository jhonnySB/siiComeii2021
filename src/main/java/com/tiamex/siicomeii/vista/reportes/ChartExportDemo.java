package com.tiamex.siicomeii.vista.reportes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.vaadin.ui.Layout;
import org.apache.commons.io.IOUtils;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.util.SVGGenerator;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Demo class to show how Vaadin Charts can be exported to image/PDF in the
 * server side. Demonstrates also how to render charts completely in the server.
 * This demo uses PhantomJs to run javascript in server.</br></br> This also
 * demonstrates how to embed chart SVG image to PDF. iText 2.1.7 <i>(notice that
 * this is old version, but it's open source licensed)</i> is used to generate
 * PDF. Batik is used to render SVG.
 * 
 */
public class ChartExportDemo extends VerticalLayout {

    private Button exportButton2;
    private Button exportButton3;
    HorizontalLayout vertical;

    private final HorizontalLayout layout;
    private final Chart chart;
    private final Configuration conf = createChartConf();

    public ChartExportDemo() {
        setMargin(true);

        layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);

        chart = new Chart();
        chart.setConfiguration(conf);

        vertical= new HorizontalLayout();
        layout.addComponent(vertical);
        vertical.addComponent(chart);
        vertical.setSpacing(true);

        addButtons(vertical);
        addText(layout);

        addComponent(new Label(
                "<h1>Vaadin Charts - not <u>just</u> for browsers<h1>",
                ContentMode.HTML));
        addComponent(layout);
    }

    private StreamSource createSVGStreamSource() {
        return new StreamSource() {
            @Override
            public InputStream getStream() {
                String svg = SVGGenerator.getInstance().generate(conf);
                if (svg != null) {
                    return new ByteArrayInputStream(svg.getBytes());
                }
                return null;
            }
        };
    }

    private StreamSource createPdfStreamSource() {
        StreamSource ss = 
         () -> {
             System.out.println("InputStream");
             File result = doExportPDF();
             if (result != null) {
                 try {
                     return new FileInputStream(result);
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 }
             }
             return null;
        };
        System.out.println("SS: "+ss.getStream());
        return ss;
    }

    private void addButtons(Layout layout) {
        exportButton2 = createExportButton("Download as SVG", "chart.svg",
                createSVGStreamSource());
        exportButton3 = createExportButton("PDF CHART",
                "chart.pdf", createPdfStreamSource());

        layout.addComponent(exportButton2);
        layout.addComponent(exportButton3);
        Button jPanelButton = new Button(
                "Show JPanel with chart (needs server running in localhost)");
        jPanelButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                SwingDemo sw = new SwingDemo();
                sw.display(conf);
            }
        });
        layout.addComponent(jPanelButton);
        layout.setWidth("100%");
    }

    private void addText(Layout layout) {
        try {
            String html = IOUtils.toString(ChartExportDemo.class
                    .getResourceAsStream("/introduction.html"));
            layout.addComponent(new Label(html, ContentMode.HTML));
        } catch (IOException e) {
        }
    }

    private Button createExportButton(String caption, String filename,
            StreamSource ss) {
        Button b = new Button(caption);
        FileDownloader downloader = new FileDownloader(new StreamResource(ss,
                filename));
        downloader.extend(b);
        return b;
    }


    private File doExportPDF() {
        String svg = SVGGenerator.getInstance().generate(conf);
        String tempSvg = "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" style=\"font-family:Open Sans, Helvetica Neue, Arial, sans-serif;font-size:14px;\" xmlns=\"http://www.w3.org/2000/svg\" width=\"600\" height=\"400\"><desc>Created with Highstock 4.2.6</desc><defs><clipPath id=\"highcharts-1\"><rect x=\"0\" y=\"0\" width=\"580\" height=\"298\"></rect></clipPath></defs><rect x=\"0\" y=\"0\" width=\"600\" height=\"400\" fill=\"#FFFFFF\" class=\" highcharts-background\"></rect><rect x=\"10\" y=\"87\" width=\"580\" height=\"298\" fill=\"rgb(255,255,255)\" fill-opacity=\"0.00\"></rect><g class=\"highcharts-series-group\"><g class=\"highcharts-series highcharts-series-0 highcharts-tracker\" transform=\"translate(10,87) scale(1 1)\" style=\"cursor:pointer;\"><path fill=\"#3090F0\" d=\"M 289.9763739083714 43.00000240600092 A 116 116 0 0 1 325.9162463146148 269.2996974187505 L 290 159 A 0 0 0 0 0 290 159 Z\" stroke-linejoin=\"round\" transform=\"translate(0,0)\"></path><path fill=\"#EC6464\" d=\"M 325.8059286774577 269.33555850923494 A 116 116 0 0 1 176.34912486696064 182.2266782277254 L 290 159 A 0 0 0 0 0 290 159 Z\" stroke-linejoin=\"round\" transform=\"translate(0,0)\"></path><path fill=\"#98DF58\" d=\"M 176.32595501803684 182.113015758196 A 116 116 0 0 1 194.41902358040022 93.2711863284 L 290 159 A 0 0 0 0 0 290 159 Z\" stroke-linejoin=\"round\" transform=\"translate(-10,-2)\"></path><path fill=\"#F9DD51\" d=\"M 194.48480017360123 93.17563823231468 A 116 116 0 0 1 241.18067994728153 53.773225890031966 L 290 159 A 0 0 0 0 0 290 159 Z\" stroke-linejoin=\"round\" transform=\"translate(0,0)\"></path><path fill=\"#24DCD4\" d=\"M 241.28593111351174 53.724459191498454 A 116 116 0 0 1 284.736685920956 43.119468740839224 L 290 159 A 0 0 0 0 0 290 159 Z\" stroke-linejoin=\"round\" transform=\"translate(0,0)\"></path><path fill=\"#EC64A5\" d=\"M 284.8525690645586 43.114263367898204 A 116 116 0 0 1 289.8388783271837 43.000111897439524 L 290 159 A 0 0 0 0 0 290 159 Z\" stroke-linejoin=\"round\" transform=\"translate(0,0)\"></path></g><g class=\"highcharts-markers highcharts-series-0\" transform=\"translate(10,87) scale(1 1)\"></g></g><text x=\"300\" text-anchor=\"middle\" class=\"highcharts-title\" style=\"color:#197DE1;font-size:26px;font-weight:normal;fill:#197DE1;width:536px;\" y=\"32\"><tspan>Browser market shares at a specific website,</tspan><tspan dy=\"31\" x=\"300\">2010</tspan><title>Browser market shares at a specific website, 2010</title></text><g class=\"highcharts-data-labels highcharts-series-0 highcharts-tracker\" transform=\"translate(10,87) scale(1 1)\" opacity=\"1\" style=\"cursor:pointer;\"><path fill=\"none\" d=\"M 439.2024977268901 136.1605681041263 C 434.2024977268901 136.1605681041263 424.32561432093877 137.7249127545286 414.4487309149874 139.2892574049309 L 404.571847509036 140.85360205533323\" stroke=\"#000000\" stroke-width=\"1\"></path><path fill=\"none\" d=\"M 211.4710125611789 285.13281891015396 C 216.4710125611789 285.13281891015396 221.5072445775365 276.49358473822565 226.54347659389413 267.8543505662973 L 231.57970861025174 259.2151163943689\" stroke=\"#000000\" stroke-width=\"1\"></path><path fill=\"none\" d=\"M 141.94116235189995 129.84234284489662 C 146.94116235189995 129.84234284489662 156.73971287574244 131.83944265004067 166.5382633995849 133.83654245518474 L 176.33681392342737 135.83364226032882\" stroke=\"#000000\" stroke-width=\"1\"></path><path fill=\"none\" d=\"M 175 63 C 180 63 202.32658154664745 55.03187172645963 208.77315643292337 62.676587040690535 L 215.2197313191993 70.32130235492144\" stroke=\"#000000\" stroke-width=\"1\"></path><path fill=\"none\" d=\"M 203.29932680815904 38 C 208.29932680815904 38 257.83613640477324 26.858084323604388 260.2011263750105 36.57440165275112 L 262.5661163452478 46.290718981897854\" stroke=\"#000000\" stroke-width=\"1\"></path><path fill=\"none\" d=\"M 281.78955109049434 13 C 286.78955109049434 13 287.00944485141923 23.032884196570166 287.2293386123443 33.03046624094001 L 287.44923237326935 43.02804828530985\" stroke=\"#000000\" stroke-width=\"1\"></path><g style=\"cursor:pointer;\" transform=\"translate(444,126)\"><text x=\"5\" style=\"font-size:12px;font-weight:bold;font-family:Open Sans, Helvetica Neue, Arial, sans-serif;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;\" y=\"17\"><tspan style=\"font-weight:bold\">Firefox</tspan><tspan dx=\"0\">: 45 %</tspan></text></g><g style=\"cursor:pointer;\" transform=\"translate(144,275)\"><text x=\"5\" style=\"font-size:12px;font-weight:bold;font-family:Open Sans, Helvetica Neue, Arial, sans-serif;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;\" y=\"17\"><tspan style=\"font-weight:bold\">IE</tspan><tspan dx=\"0\">: 26.8 %</tspan></text></g><g style=\"cursor:pointer;\" transform=\"translate(40,120)\"><text x=\"5\" style=\"font-size:12px;font-weight:bold;font-family:Open Sans, Helvetica Neue, Arial, sans-serif;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;\" y=\"17\"><tspan style=\"font-weight:bold\">Chrome</tspan><tspan dx=\"0\">: 12.8 %</tspan></text></g><g style=\"cursor:pointer;\" transform=\"translate(91,53)\"><text x=\"5\" style=\"font-size:12px;font-weight:bold;font-family:Open Sans, Helvetica Neue, Arial, sans-serif;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;\" y=\"17\"><tspan style=\"font-weight:bold\">Safari</tspan><tspan dx=\"0\">: 8.5 %</tspan></text></g><g style=\"cursor:pointer;\" transform=\"translate(118,28)\"><text x=\"5\" style=\"font-size:12px;font-weight:bold;font-family:Open Sans, Helvetica Neue, Arial, sans-serif;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;\" y=\"17\"><tspan style=\"font-weight:bold\">Opera</tspan><tspan dx=\"0\">: 6.2 %</tspan></text></g><g style=\"cursor:pointer;\" transform=\"translate(193,3)\"><text x=\"5\" style=\"font-size:12px;font-weight:bold;font-family:Open Sans, Helvetica Neue, Arial, sans-serif;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;\" y=\"17\"><tspan style=\"font-weight:bold\">Others</tspan><tspan dx=\"0\">: 0.7 %</tspan></text></g></g><g class=\"highcharts-legend\"><rect x=\"0\" y=\"0\" width=\"8\" height=\"8\" rx=\"5\" ry=\"5\" fill=\"rgb(255,255,255)\" fill-opacity=\"0.90\" visibility=\"hidden\"></rect><g><g></g></g></g><g class=\"highcharts-tooltip\" style=\"cursor:default;padding:0;pointer-events:none;white-space:nowrap;\" transform=\"translate(0,-9000000000)\"><path fill=\"none\" d=\"M 5 0 L 11 0 C 16 0 16 0 16 5 L 16 11 C 16 16 16 16 11 16 L 5 16 C 0 16 0 16 0 11 L 0 5 C 0 0 0 0 5 0\"  stroke=\"black\" stroke-opacity=\"0.049999999999999996\" stroke-width=\"5\" transform=\"translate(1, 1)\"></path><path fill=\"none\" d=\"M 5 0 L 11 0 C 16 0 16 0 16 5 L 16 11 C 16 16 16 16 11 16 L 5 16 C 0 16 0 16 0 11 L 0 5 C 0 0 0 0 5 0\"  stroke=\"black\" stroke-opacity=\"0.09999999999999999\" stroke-width=\"3\" transform=\"translate(1, 1)\"></path><path fill=\"none\" d=\"M 5 0 L 11 0 C 16 0 16 0 16 5 L 16 11 C 16 16 16 16 11 16 L 5 16 C 0 16 0 16 0 11 L 0 5 C 0 0 0 0 5 0\"  stroke=\"black\" stroke-opacity=\"0.15\" stroke-width=\"1\" transform=\"translate(1, 1)\"></path><path fill=\"#FFFFFF\" d=\"M 5 0 L 11 0 C 16 0 16 0 16 5 L 16 11 C 16 16 16 16 11 16 L 5 16 C 0 16 0 16 0 11 L 0 5 C 0 0 0 0 5 0\"></path><text x=\"8\" style=\"font-size:12px;color:#333333;fill:#333333;\" y=\"20\"></text></g><text x=\"590\" text-anchor=\"end\" style=\"cursor:pointer;color:#808080;font-size:14px;fill:#808080;\" y=\"395\"></text></svg>";
        System.out.println("svg: "+svg);
        PdfExportDemo pdfExp = new PdfExportDemo(); 
        File file = pdfExp.writePdf("filename",svg,Float.parseFloat("300"),Float.parseFloat("200")); 
        System.out.println("Filename: "+file.getName());
        return file;
    }

    private static Configuration createChartConf() {
        Configuration conf = new Configuration();
        conf.getChart().setType(ChartType.PIE);

        conf.setTitle("Browser market shares at a specific website, 2010");

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        dataLabels.setColor(new SolidColor(0, 0, 0));
        dataLabels.setConnectorColor(new SolidColor(0, 0, 0));
        dataLabels
                .setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Firefox", 45.0));
        series.add(new DataSeriesItem("IE", 26.8));
        DataSeriesItem chrome = new DataSeriesItem("Chrome", 12.8);
        chrome.setSliced(true);
        chrome.setSelected(true);
        series.add(chrome);
        series.add(new DataSeriesItem("Safari", 8.5));
        series.add(new DataSeriesItem("Opera", 6.2));
        series.add(new DataSeriesItem("Others", 0.7));
        conf.setSeries(series);
        return conf;
    }

}
