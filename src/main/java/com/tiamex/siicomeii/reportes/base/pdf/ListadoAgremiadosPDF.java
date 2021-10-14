package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.Format;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.FormatoPagina;
import com.tiamex.siicomeii.utils.Utils;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
public final class ListadoAgremiadosPDF extends BasePDF {
    
    public ListadoAgremiadosPDF() throws Exception {
        create();
    }

    @Override
    protected void create() throws Exception {
        try {
            document = new Document();
            document.setMargins(10, 10, 10, 10);
            document.setPageSize(PageSize.LETTER);
            document.addCreationDate();
            document.addLanguage("es-MX");
            PdfWriter writer = PdfWriter.getInstance(document, pdfFile);
            //writer.setPageEvent(new Header());
            writer.setPageEvent(new FormatoPagina());
            document.open();
            
            document.add(elementsPDF.getParagraph("AGREMIADOS REGISTRADOS", format.getFontSubtitleBold(), Element.ALIGN_CENTER));
            document.add(new Chunk());
            
            
            int totalAgremiados = ControladorAgremiado.getInstance().getAll().size();

            if (totalAgremiados > 0) {
                PdfPTable table;
                PdfPCell cell;
                String pais = "";
                int cont = 0;
                encabezados = new Format(15);
                contenido = new Format(15);
                boolean fieldColor =false;
                BaseColor bcHeader = new BaseColor(130, 219, 255);
                BaseColor bcGray = new BaseColor(218, 224, 235);
                for (Agremiado agremiado : ControladorAgremiado.getInstance().getAllSorted("pais")) {
                    totalAgremiados--;
                    table = new PdfPTable(2);
                    if (pais.compareTo(agremiado.getObjPais().getNombre()) != 0) {
                        if (cont != 0) {
                            document.add(elementsPDF.getParagraph("Registros: " + cont, format.getFontSmallSimple(), Element.ALIGN_RIGHT));
                            cont = 0;
                        }
                        table.setSpacingBefore(5);
                        pais = agremiado.getObjPais().getNombre();
                        document.add(elementsPDF.getParagraph("País: "+pais, format.getFontTextBold(), Element.ALIGN_LEFT));
                        cell = new PdfPCell(elementsPDF.getParagraph("Nombre", encabezados.getFontTitleBold(), Element.ALIGN_JUSTIFIED));                 
                        cell.setBackgroundColor(bcHeader);
                        table.addCell(cell);
                        cell = new PdfPCell(elementsPDF.getParagraph("Institución", encabezados.getFontSubtitleBold(), Element.ALIGN_JUSTIFIED));
                        cell.setBackgroundColor(bcHeader);
                        table.addCell(cell);
                        cell.setBorder(5);
                    }
                    cont++;
                    cell = new PdfPCell(elementsPDF.getParagraph(agremiado.getNombre(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    cell.setBackgroundColor(fieldColor==true ? bcGray : BaseColor.WHITE);
                    table.addCell(cell);
                    cell = new PdfPCell(elementsPDF.getParagraph(agremiado.getInstitucion(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    cell.setBackgroundColor(fieldColor==true ? bcGray : BaseColor.WHITE);
                    table.addCell(cell);
                    table.setWidthPercentage(100);
                    document.add(table);
                    if (totalAgremiados == 0) {
                        document.add(elementsPDF.getParagraph("Registros: " + cont, format.getFontSmallSimple(), Element.ALIGN_RIGHT));
                    }
                    fieldColor = !fieldColor;
                }
                

                document.add(elementsPDF.getParagraph("Total agremiados registrados: " + ControladorAgremiado.getInstance().getAll().size(),
                         format.getFontSubtitleBold(), Element.ALIGN_RIGHT));
                document.newPage();
            }else{
                contenido = new Format(42);
                document.add(elementsPDF.getParagraph("SIN REGISTROS", contenido.getFontSubtitleBold(), Element.ALIGN_CENTER));
            }

            ///////  ------Fin del doc------ ////////////
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        } finally {
            document.close();
        }
    }
}
