package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.ImgTemplate;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Rectangle;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.FormatoPagina;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.svg.SVGDocument;

/**
 * @author
 */
public final class ReporteChartAgremiado extends BasePDF {

    private PdfWriter writer;
    private String svgStrMain, svgStrGenre, svgStrCountries, svgStrInst;
    private float width;
    private float height;
    final Paragraph BR = new Paragraph(Chunk.NEWLINE);
    List<AsistenciaWebinar> listAsistencias = ControladorAsistenciaWebinar.getInstance().getAll();
    List<Agremiado> filterList;
    int highestAgremiadosI;
    boolean filteredList;
    LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

    public List<Agremiado> getSortedListAgremiados(String filterField) {
        List<Agremiado> list = null;
        try {
            list = ControladorAgremiado.getInstance().getAllSorted(filterField);
        } catch (Exception ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public File writePdf(String pdffilename, String svgStrMain, float width, float height) {
        this.svgStrMain = svgStrMain;
        this.width = width;
        this.height = height;
        document = new Document();
        document.setMargins(10, 10, 10, 10);
        document.setPageSize(PageSize.LETTER);
        document.addCreationDate();
        document.addLanguage("es-MX");
        document.addTitle("Reporte agremiados");
        document.addCreator("TIAMEX SA de CV");
        File file = null;
        try {
            file = writeToFile(pdffilename, document);
            document.open();
            writePdfContent();
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File writePdf(String pdffilename, String svgStrMain, String svgStrGenre, String svgStrCountries, String svgInst,
            float width, float height, List<Agremiado> filterList, boolean filteredList) {
        this.filteredList = filteredList;
        this.filterList = filterList;
        this.svgStrMain = svgStrMain;
        this.svgStrGenre = svgStrGenre;
        this.svgStrCountries = svgStrCountries;
        this.svgStrInst = svgInst;
        this.width = width;
        this.height = height;
        document = new Document();
        document.setMargins(10, 10, 10, 30);
        document.setPageSize(PageSize.LETTER);
        document.addCreationDate();
        document.addLanguage("es-MX");
        document.addTitle("Reporte agremiados");
        document.addCreator("TIAMEX SA de CV");
        File file = null;
        try {
            file = writeToFile(pdffilename, document);
            document.open();
            writePdfContent();
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private File writeToFile(String filename, Document document) {
        File file = null;
        try {
            file = File.createTempFile(filename, ".pdf");
            file.deleteOnExit();
            FileOutputStream fileOut = new FileOutputStream(file);
            writer = PdfWriter.getInstance(document, fileOut);
            writer.setPageEvent(new FormatoPagina());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (DocumentException ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    private void writePdfContent() throws DocumentException, IOException {
        writeGeneralInfo();
        document.add(BR);
        if (writer.getVerticalPosition(true) < 100) {
            document.newPage();
        }
        createDetailedSection();
        document.add(BR);
        if (writer.getVerticalPosition(true) < 100) {
            document.newPage();
        }
        createConstanciasSection();
    }

    private Image createSvgImg(String svgStrChart,float fixedWidth) {
        ImgTemplate imgTemp = null;
        try {
            File tempFile = File.createTempFile("tempChartExport", ".svg");
            try (BufferedWriter out = new BufferedWriter(new FileWriter(tempFile))) {
                out.write(svgStrChart);
                out.close();
            }
            SVGDocument svgDoc = new SAXSVGDocumentFactory(null).createSVGDocument(null, new FileReader(tempFile.getAbsolutePath()));
            float svgWidth = Float.parseFloat(svgDoc.getDocumentElement().getAttribute("width").replaceAll("[^0-9.,]", ""));
            float svgHeight = Float.parseFloat(svgDoc.getDocumentElement().getAttribute("height").replaceAll("[^0-9.,]", ""));
            PdfTemplate svgTempl = PdfTemplate.createTemplate(writer, fixedWidth!= 0 ? fixedWidth : svgWidth, svgHeight);
            Graphics2D g2d = svgTempl.createGraphics(svgTempl.getWidth(), svgTempl.getHeight());
            GraphicsNode chartGfx = (new GVTBuilder()).build(new BridgeContext(new UserAgentAdapter()), svgDoc);
            chartGfx.paint(g2d);
            g2d.dispose();
            imgTemp = new ImgTemplate(svgTempl);
        } catch (IOException | BadElementException ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imgTemp;
    }

    protected void writeGeneralInfo() {
        try {
            Paragraph p = customParagraph("Información General", FontFamily.HELVETICA, 14, Font.NORMAL, baseColorRgbh(77, 195, 255),
                    Element.ALIGN_CENTER, false, 0, 0);
            document.add(p);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.addCell(createImageCell(svgStrGenre));
            table.addCell(createImageCell(svgStrCountries));
            document.add(table);
            Image chartInstitutos = createSvgImg(svgStrInst,700);
            //chartInstitutos.scaleAbsolute(table.getTotalWidth() / 1.5F, table.getTotalHeight() * 1.5F);
            chartInstitutos.scaleToFit(700, 400);
            document.add(chartInstitutos);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDetailedSection() {
        try {
            document.add(customParagraph("Agremiados por País", FontFamily.HELVETICA, 14, Font.NORMAL, baseColorRgbh(77, 195, 255),
                    Element.ALIGN_CENTER, false, 0, 0));
            document.add(filteredList ? setInfo(filterList) : setInfo(getSortedListAgremiados("pais")));
            document.add(BR);
            if (writer.getVerticalPosition(true) < 100) {
                document.newPage();
            }
            document.add(customParagraph("Agremiados por Institución", FontFamily.HELVETICA, 14, Font.NORMAL, baseColorRgbh(77, 195, 255),
                    Element.ALIGN_CENTER, false, 0, 0));
            fitTableI(getInfoI());
            if (writer.getVerticalPosition(true) < 100) {
                document.newPage();
            }
            document.add(customParagraph("Agremiados registrados", FontFamily.HELVETICA, 14, Font.NORMAL, baseColorRgbh(77, 195, 255),
                    Element.ALIGN_CENTER, false, 0, 0));
            document.add(infoAgremiados());
        } catch (DocumentException ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PdfPTable infoAgremiados() {
        PdfPTable table = new PdfPTable(new float[]{3, 2, 2, 1.7F, 1, 1, 1});
        String tempString = "";
        InstitutoGeneralInfo obj;  // mes,año,total
        BaseColor bgColorRow = new BaseColor(237, 237, 237), fontColor = new BaseColor(112, 112, 112);
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorder(Rectangle.NO_BORDER);
        emptyCell.setColspan(3);
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);
        boolean zebra = false;
        List<Agremiado> listAgremiados = filteredList ? filterList : getSortedListAgremiados("id");
        BaseColor bgColor = new BaseColor(196, 219, 255);
        int constThisMonth = 0, constThisYear = 0, totalSum = 0;
        listAgremiados.sort((Agremiado a1, Agremiado a2) -> {
            return a1.getNombre().compareToIgnoreCase(a2.getNombre());
        });
        createHeaders(table);
        for (Agremiado a : listAgremiados) {
            String fechaReg = a.getFechaReg().format(DateTimeFormatter.ISO_DATE);
            if (tempString.compareToIgnoreCase(a.getNombre()) != 0) {
                table.addCell(buildParaCell(a.getNombre(), FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                table.addCell(buildParaCell(a.getInstitucion(), FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                table.addCell(buildParaCell(a.getObjPais().getNombre(), FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                table.addCell(buildParaCell(fechaReg, FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                obj = getRecordConstancias(a.getId());
                constThisMonth += obj.getAgremiados();
                constThisYear += obj.getHombres();
                totalSum += obj.getMujeres();
                table.addCell(buildParaCell(String.valueOf(obj.getAgremiados()), FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                table.addCell(buildParaCell(String.valueOf(obj.getHombres()), FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                table.addCell(buildParaCell(String.valueOf(obj.getMujeres()), FontFamily.COURIER, 10, Font.NORMAL, fontColor, Element.ALIGN_CENTER, zebra, bgColorRow));
                zebra = !zebra;
            }
        }
        table.addCell(emptyCell);
        emptyCell = buildParaCell("Total", FontFamily.HELVETICA, 11, Font.NORMAL, fontColor, Element.ALIGN_RIGHT, zebra, bgColorRow);
        emptyCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        table.addCell(emptyCell);
        table.addCell(buildParaCell(String.valueOf(constThisMonth), FontFamily.HELVETICA, 10, Font.NORMAL, fontColor, Element.ALIGN_LEFT, zebra, bgColorRow));
        table.addCell(buildParaCell(String.valueOf(constThisYear), FontFamily.HELVETICA, 10, Font.NORMAL, fontColor, Element.ALIGN_LEFT, zebra, bgColorRow));
        table.addCell(buildParaCell(String.valueOf(totalSum), FontFamily.HELVETICA, 10, Font.NORMAL, fontColor, Element.ALIGN_LEFT, zebra, bgColorRow));
        return table;
    }

    private void createHeaders(PdfPTable table) {
        BaseColor fontColor = new BaseColor(112, 112, 112), bgColorHeader = new BaseColor(196, 219, 255);
        FontFamily fontFamily = FontFamily.HELVETICA;
        int fontSize = 11, fontType = Font.NORMAL, aligment = Element.ALIGN_CENTER;
        boolean bgColor = true;
        PdfPCell cell;
        cell = new PdfPCell(buildParaCell("Agremiado", fontFamily, fontSize, fontType, fontColor, Element.ALIGN_CENTER, true, new BaseColor(213, 243, 254)));
        cell.setColspan(4);
        cell.setPadding(7);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = buildParaCell("Constancias", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, new BaseColor(213, 243, 254));
        cell.setColspan(3);
        cell.setPadding(7);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        table.addCell(buildParaCell("Nombre", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
        table.addCell(buildParaCell("Instituto", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
        table.addCell(buildParaCell("País", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
        table.addCell(buildParaCell("Fecha registro", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
        table.addCell(buildParaCell("Este mes", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
        table.addCell(buildParaCell("Este año", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
        table.addCell(buildParaCell("Total recibidas", fontFamily, fontSize, fontType, fontColor, aligment, bgColor, bgColorHeader));
    }

    private InstitutoGeneralInfo getRecordConstancias(long id) {
        InstitutoGeneralInfo obj = new InstitutoGeneralInfo(0, 0, 0);
        List<AsistenciaWebinar> list = ControladorAsistenciaWebinar.getInstance().getByAsistencia(id);
        int currentMonth = currentDate.getMonthValue(), currentYear = currentDate.getYear();
        if (!list.isEmpty()) {
            obj.setMujeres(list.size());
            list.stream().map(a -> {
                int loopMonth = a.getObjWebinarRealizado().getFecha().getMonthValue();
                int loopYear = a.getObjWebinarRealizado().getFecha().getYear();
                if (loopMonth == currentMonth && loopYear == currentYear) {
                    obj.setAgremiados(obj.getAgremiados() + 1);
                }
                return loopYear;
            }).filter(loopYear -> (loopYear == currentYear)).forEachOrdered(_item -> {
                obj.setHombres(obj.getHombres() + 1);
            });
        }
        return obj;
    }

    private void fitTableI(Map<String, InstitutoGeneralInfo> map) {
        PdfPTable childTable = new PdfPTable(4);
        boolean zebra = false;
        childTable.setHeaderRows(1);
        BaseColor bgColorHeader = new BaseColor(77, 77, 77), bgColorRow = new BaseColor(237, 237, 237),
                fontColor = new BaseColor(112, 112, 112);
        childTable.setWidthPercentage(70);
        childTable.setSpacingBefore(10);
        childTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        String instituto = "";
        childTable.addCell(buildParaCell("Instituto", FontFamily.HELVETICA, 11, Font.NORMAL, bgColorHeader, Element.ALIGN_CENTER, true,
                new BaseColor(196, 219, 255)));
        childTable.addCell(buildParaCell("Agremiados", FontFamily.HELVETICA, 11, Font.NORMAL, bgColorHeader, Element.ALIGN_CENTER, true,
                new BaseColor(196, 219, 255)));
        childTable.addCell(buildParaCell("Hombres", FontFamily.HELVETICA, 11, Font.NORMAL, bgColorHeader, Element.ALIGN_CENTER, true,
                new BaseColor(196, 219, 255)));
        childTable.addCell(buildParaCell("Mujeres", FontFamily.HELVETICA, 11, Font.NORMAL, bgColorHeader, Element.ALIGN_CENTER, true,
                new BaseColor(196, 219, 255)));
        for (Map.Entry<String, InstitutoGeneralInfo> entrySet : map.entrySet()) {
            String key = entrySet.getKey();
            int agremiados = entrySet.getValue().getAgremiados(), hombres = entrySet.getValue().getHombres(), mujeres = entrySet.getValue().getMujeres();
            if (key.compareToIgnoreCase(instituto) != 0) {
                childTable.addCell(buildParaCell(key, FontFamily.COURIER, 10, Font.NORMAL, baseColorRgbh(112, 112, 112), Element.ALIGN_LEFT, zebra,
                        bgColorRow));
                childTable.addCell(buildParaCell(String.valueOf(agremiados), FontFamily.COURIER, 10, Font.NORMAL, fontColor,
                        Element.ALIGN_CENTER, zebra, bgColorRow));
                childTable.addCell(buildParaCell(String.valueOf(hombres), FontFamily.COURIER, 10, Font.NORMAL, fontColor,
                        Element.ALIGN_CENTER, zebra, bgColorRow));
                childTable.addCell(buildParaCell(String.valueOf(mujeres), FontFamily.COURIER, 10, Font.NORMAL, fontColor,
                        Element.ALIGN_CENTER, zebra, bgColorRow));
            }
            zebra = !zebra;
        }
        try {
            document.add(childTable);
        } catch (DocumentException ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PdfPCell buildParaCell(String text, FontFamily fontFamily, int fontSize, int fontType, BaseColor baseColor, int align, boolean bgColor, BaseColor colorBg) {
        PdfPCell cell = new PdfPCell(customParagraph(text, fontFamily, fontSize, fontType, baseColor, align, false, 0, 0));
        if (bgColor) {
            cell.setBackgroundColor(colorBg);
        }
        return cell;
    }

    public void createConstanciasSection() {
        try {
            document.add(customParagraph("Constancias", FontFamily.HELVETICA, 14, Font.NORMAL, baseColorRgbh(77, 195, 255), Element.ALIGN_LEFT,
                    false, 0, 0));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            Map<Long, List<AsistenciaWebinar>> listAsistenciasFiltered = new HashMap<>();
            int sumTotalAsis = 0;
            if (filteredList) {
                listAsistencias.clear();
                filterList.forEach(a -> {
                    long idAgremiado = a.getId();
                    List<AsistenciaWebinar> obj = ControladorAsistenciaWebinar.getInstance().getByAsistencia(idAgremiado);
                    if (!obj.isEmpty()) {
                        listAsistenciasFiltered.put(idAgremiado, obj);
                    }
                });
                if (!listAsistenciasFiltered.isEmpty()) {
                    for (Map.Entry<Long, List<AsistenciaWebinar>> entrySet : listAsistenciasFiltered.entrySet()) {
                        sumTotalAsis = sumTotalAsis + entrySet.getValue().size();
                        listAsistencias.addAll(entrySet.getValue());
                    }
                }
            } 
            int constancias = filteredList ? sumTotalAsis : listAsistencias.size();
            int agremiados = filteredList ? listAsistenciasFiltered.size() : getSortedListAgremiados("id").size();
            Paragraph p = customParagraph("Constancias enviadas a agremiados:", FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.LIGHT_GRAY,
                    Element.ALIGN_BASELINE, true, 0.5f, -2.5f);
            p.add(" " + (constancias == 0 ? "Ninguna" : String.valueOf(filteredList ? sumTotalAsis : constancias)));
            p.setIndentationRight(10);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell.addElement(p);  // 
            p = customParagraph("Institución(es) con mayor contancias recibidas: ", FontFamily.HELVETICA, 10, Font.NORMAL,
                    BaseColor.LIGHT_GRAY, Element.ALIGN_BASELINE, true, 0.5f, -2.5f);
            p.add(" " + getCountConstanciasInst(listAsistencias));
            cell.setBorder(Rectangle.NO_BORDER);
            p.setIndentationRight(10);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell.addElement(p);  // 
            p = customParagraph("Agremiado(s) con mayor contancias recibidas:", FontFamily.HELVETICA, 10, Font.NORMAL,
                    BaseColor.LIGHT_GRAY, Element.ALIGN_BASELINE, true, 0.5f, -2.5f);
            p.add(" " + getRankAgremiados());
            p.setIndentationRight(10);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell.addElement(p);
            table.addCell(cell); // 
            
            p = customParagraph("Institución(es) con mayor agremiados:", FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.LIGHT_GRAY, Element.ALIGN_RIGHT,
                    true, 0.5f, -2.5f);
            p.add(" " + setTextRank());
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            p.setIndentationLeft(10);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell.addElement(p);  // 
            p = customParagraph("Agremiados registrados:", FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.LIGHT_GRAY, Element.ALIGN_RIGHT, 
                    true, 0.5f, -2.5f);
            p.add(" " + agremiados);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            p.setIndentationLeft(10);
            cell.addElement(p);
            table.addCell(cell); //
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Paragraph customParagraph(String text, FontFamily fontFamily, int fontSize, int fontType, BaseColor baseColor, int aligment,
            boolean underline, float thickness, float yPosition) {
        Font font = new Font(fontFamily, fontSize, fontType, baseColor);
        Chunk chunkText = new Chunk(text, font);
        if (underline) {
            chunkText.setUnderline(thickness, yPosition);
        }
        Paragraph p = new Paragraph(chunkText);
        p.setAlignment(aligment);
        return p;
    }

    public BaseColor baseColorRgbh(int red, int green, int blue) {
        return new BaseColor(red, green, blue);
    }

    public PdfPCell createImageCell(String svgChart) throws DocumentException, IOException {
        Image scaledImg = createSvgImg(svgChart,0);
        scaledImg.scaleToFit(600, 400);
        PdfPCell cell = new PdfPCell(scaledImg, true);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Function<? super InstitutoRecord, ? extends InstitutoRecord> keyValue() {
        Function<? super InstitutoRecord, ? extends InstitutoRecord> keyMapper = key -> {
            return key;
        };
        return keyMapper;
    }

    private String getRankAgremiados() {
        String results = "";
        Map<String, InstitutoRecord> map = new HashMap<>();
        Map<String, InstitutoRecord> mapRanked = new HashMap<>();
        InstitutoRecord obj;
        int highestRecord = 0, cont = 0;
        try {
            listAsistencias.sort((a1, a2) -> {
                if (a1.getAgremiado() < a2.getAgremiado()) {
                    return -1;
                } else if (a1.getAgremiado() > a2.getAgremiado()) {
                    return 1;
                }
                return 0;
            });
            for (AsistenciaWebinar a : listAsistencias) {
                String loopName = a.getObjAgremiado().getNombre();
                LocalDate loopDate = a.getObjWebinarRealizado().getFecha().toLocalDate();
                cont++;
                if (cont > highestRecord) {
                    highestRecord = cont;
                }
                if (map.containsKey(loopName)) {
                    obj = map.get(loopName);
                    obj.setTotal(obj.getTotal() + 1);
                    if (loopDate.compareTo(obj.getOldestReg()) < 0) {
                        obj.setOldestReg(loopDate);
                    }
                    map.replace(loopName, obj);
                } else {
                    cont = 0;
                    obj = new InstitutoRecord(1, loopDate);
                    map.put(loopName, obj);
                }
            }
            for (Map.Entry<String, InstitutoRecord> entrySet : map.entrySet()) {
                if (entrySet.getValue().getTotal() == highestRecord) {
                    mapRanked.put(entrySet.getKey(), entrySet.getValue());
                }
            }
            results = getTextRanked(mapRanked.entrySet().stream(), highestRecord);
        } catch (Exception ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    private PdfPTable setInfo(List<Agremiado> listAgremiados) {
        PdfPTable table = new PdfPTable(2);
        table.setHeaderRows(1);
        boolean zebra = false;
        table.setWidthPercentage(70);
        table.setSpacingBefore(10);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        int cont = 0, listSize = listAgremiados.size();
        listAgremiados.sort((Agremiado a1, Agremiado a2) -> {
            return a1.getObjPais().getNombre().compareToIgnoreCase(a2.getObjPais().getNombre());
        });
        String tempCountry = listAgremiados.get(0).getObjPais().getNombre();
        table.addCell(buildParaCell("País", FontFamily.HELVETICA, 11, Font.NORMAL, baseColorRgbh(77, 77, 77), Element.ALIGN_CENTER, true,
                new BaseColor(196, 219, 255)));
        table.addCell(buildParaCell("Agremiados", FontFamily.HELVETICA, 11, Font.NORMAL, baseColorRgbh(77, 77, 77), Element.ALIGN_CENTER, true,
                new BaseColor(196, 219, 255)));
        for (Agremiado a : listAgremiados) {
            String loopCountry = a.getObjPais().getNombre();
            if (loopCountry.compareToIgnoreCase(tempCountry) != 0) {
                addTableCell(table, tempCountry, cont, zebra);
                tempCountry = loopCountry;
                cont = 0;
                zebra = !zebra;
            }
            listSize--;
            cont++;
            if (listSize == 0) {
                addTableCell(table, tempCountry, cont, zebra);
            }

        }
        return table;
    }

    private void addTableCell(PdfPTable table, String country, int total, boolean zebra) {
        table.addCell(buildParaCell(country, FontFamily.COURIER, 11, Font.NORMAL, new BaseColor(112, 112, 112), Element.ALIGN_LEFT, zebra,
                new BaseColor(237, 237, 237)));
        table.addCell(buildParaCell(String.valueOf(total), FontFamily.COURIER, 11, Font.NORMAL, new BaseColor(112, 112, 112), Element.ALIGN_LEFT, zebra,
                new BaseColor(237, 237, 237)));
    }

    private Map<String, InstitutoGeneralInfo> getInfoI() {
        String tempValueI = "";
        List<Agremiado> listAgremiados = filteredList ? filterList : getSortedListAgremiados("institucion");
        Map<String, InstitutoGeneralInfo> mapInstitutos = new HashMap<>();
        Map<String, InstitutoGeneralInfo> sortedMap;
        InstitutoGeneralInfo obj;
        int contRegs, mujer = 0, hombre = 0;
        contRegs = listAgremiados.size();
        for (Agremiado a : listAgremiados) {
            String loopInstituto = a.getInstitucion();
            char genre = a.getSexo();
            if (genre == 'H') {
                hombre = 1;
            } else {
                mujer = 1;
            }
            if (mapInstitutos.containsKey(loopInstituto)) {
                obj = mapInstitutos.get(loopInstituto);
                obj.setAgremiados(obj.getAgremiados() + 1);
                obj.setHombres(obj.getHombres() + hombre);
                obj.setMujeres(obj.getMujeres() + mujer);
                mapInstitutos.replace(loopInstituto, obj);
            } else {
                obj = new InstitutoGeneralInfo(1, hombre, mujer);
                mapInstitutos.put(loopInstituto, obj);
                hombre = 0;
                mujer = 0;
            }
            if (contRegs == 0) {
                mapInstitutos.put(tempValueI, obj);
            }
        }
        sortedMap = new TreeMap<>(); //sortedMap.forEach((key,value)->{System.out.println(key+","+value.getAgremiados()+","+value.getHombres()+","+value.getMujeres());});
        mapInstitutos.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
        return (sortedMap);
    }

    private String setTextRank() {
        String results;
        Map<String, InstitutoRecord> institutos = getRankInstitutos();
        Stream<Map.Entry<String, InstitutoRecord>> sortedStream = institutos.entrySet().stream().
                sorted(Map.Entry.comparingByValue());
        results = getTextRanked(sortedStream, highestAgremiadosI);
        return results;
    }

    public String getCountConstanciasInst(List<AsistenciaWebinar> data) {
        Map<String, InstitutoRecord> institutoMap = new HashMap<>();
        Map<String, InstitutoRecord> institutoMapRanked = new HashMap<>();
        String highestInst = "";
        InstitutoRecord obj;
        int highestRecord = 0, cont = 0;
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
                    cont = 1;
                    obj = new InstitutoRecord(1, loopDate);
                    institutoMap.put(loopInstituto, obj);
                }
            }
            for (Map.Entry<String, InstitutoRecord> entrySet : institutoMap.entrySet()) {
                if (entrySet.getValue().getTotal() == highestRecord) {
                    institutoMapRanked.put(entrySet.getKey(), entrySet.getValue());
                }
            }
            highestInst = getRankConstancias(institutoMapRanked, highestRecord);
        } catch (Exception ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return highestInst;
    }

    private String getRankConstancias(Map<String, InstitutoRecord> map, int maxEntry) {
        String results;
        Stream<Map.Entry<String, InstitutoRecord>> sortedStream = map.entrySet().stream().
                sorted(Map.Entry.comparingByValue());
        results = getTextRanked(sortedStream, maxEntry);
        return results;
    }

    private String getTextRanked(Stream<Map.Entry<String, InstitutoRecord>> sortedStream, int highestRecord) {
        String results = "";
        Map<String, InstitutoRecord> newMap = new HashMap<>();
        sortedStream.forEach(entry -> {
            newMap.put(entry.getKey(), entry.getValue());
        });
        for (Map.Entry<String, InstitutoRecord> entrySet : newMap.entrySet()) {
            if (results.compareTo("") == 0) {
                results = entrySet.getKey();
            } else {
                
                    results = results + "," + entrySet.getKey();
                
            }
            
        }
        
            results = results + ".";
        
        results = results + "(" + highestRecord + ")";
        return results;
    }

    private Map<String, InstitutoRecord> getRankInstitutos() {
        String tempValueI;
        LocalDate oldestDate;
        List<Agremiado> listAgremiados = filteredList ? filterList : getSortedListAgremiados("institucion");
        Map<String, InstitutoRecord> mapInstitutos = new HashMap<>();
        Map<String, InstitutoRecord> mapRankInstitutos = new HashMap<>();
        InstitutoRecord institutoRec = new InstitutoRecord();
        int contInst = 0, contRegs;
        highestAgremiadosI = 0;
        try {
            contRegs = listAgremiados.size();
            tempValueI = listAgremiados.get(0).getInstitucion();
            oldestDate = listAgremiados.get(0).getFechaReg();
            institutoRec.setTotal(0);
            institutoRec.setOldestReg(oldestDate);
            for (Agremiado a : listAgremiados) {
                String loopValueI = a.getInstitucion();
                LocalDate loopValueDate = a.getFechaReg();
                if (tempValueI.compareToIgnoreCase(loopValueI) != 0) {
                    mapInstitutos.put(tempValueI, institutoRec);
                    tempValueI = loopValueI;
                    oldestDate = loopValueDate;
                    contInst = 0;
                    institutoRec = new InstitutoRecord(0, loopValueDate);
                } else {
                    if (loopValueDate.compareTo(oldestDate) < 0) {
                        oldestDate = loopValueDate;
                        institutoRec.setOldestReg(oldestDate);
                        mapInstitutos.replace(loopValueI, institutoRec);
                    }
                }
                contInst = contInst + 1;
                institutoRec.setTotal(contInst);
                contRegs--;
                if (contInst > highestAgremiadosI) {
                    highestAgremiadosI = contInst;
                }
                if (contRegs == 0) {
                    mapInstitutos.put(tempValueI, institutoRec);
                }
            }
            mapInstitutos.entrySet().forEach(set -> {
                if (set.getValue().getTotal() == highestAgremiadosI) {
                    mapRankInstitutos.put(set.getKey(), set.getValue());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ReporteChartAgremiado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapRankInstitutos;
    }

    @Override
    protected void create() throws Exception {
    }

    private class InstitutoRecord implements Comparable {

        int total;
        LocalDate oldestReg;

        public InstitutoRecord() {
        }

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

    private class InstitutoGeneralInfo {

        int agremiados, hombres, mujeres;

        public InstitutoGeneralInfo() {
        }

        public InstitutoGeneralInfo(int a, int h, int m) {
            this.agremiados = a;
            this.hombres = h;
            this.mujeres = m;
        }

        public int getAgremiados() {
            return agremiados;
        }

        public void setAgremiados(int agremiados) {
            this.agremiados = agremiados;
        }

        public int getHombres() {
            return hombres;
        }

        public void setHombres(int hombres) {
            this.hombres = hombres;
        }

        public int getMujeres() {
            return mujeres;
        }

        public void setMujeres(int mujeres) {
            this.mujeres = mujeres;
        }

    }

}

/*         
HTMLWorker htmlWorker = new HTMLWorker(document);
            String htmlStr = "<html>"
                    +"<head>\n"
                    + "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n"
                    + "</head>"
                    + "<body>"
                    + "<b>Constancias</b> "+VaadinIcons.DIPLOMA.getHtml()
                    + "<i class=\"material-icons\">cloud</i>\n"
                    + "<i class=\"material-icons\">favorite</i>\n"
                    + "<i class=\"material-icons\">attachment</i>\n"
                    + "<i class=\"material-icons\">computer</i>\n"
                    + "<i class=\"material-icons\">traffic</i>"
                    + "</body>"
                    + "</html>";  
            String path = Main.getBaseDir()+"/xmlWorker.html";
            ByteArrayInputStream bis= new ByteArrayInputStream(Files.readAllBytes(Paths.get(path)));
            String css = Main.getBaseDir()+"/css_file.css";
            ByteArrayInputStream cis= new ByteArrayInputStream(Files.readAllBytes(Paths.get(css)));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bis, cis); 
 */
