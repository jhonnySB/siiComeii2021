package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.tiamex.siicomeii.Main;
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
            PdfWriter writer = PdfWriter.getInstance(document, pdfFile);

            writer.setPageEvent(new FormatoPagina());
            document.open();
            ///////  ------Inicio del doc------ ////////////

            Image logoHeader = Image.getInstance(Main.getBaseDir() + "/logoTiamex2.png");
            logoHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(logoHeader);
            document.add(elementsPDF.getParagraph("Listado de Agremiados".toUpperCase(), format.getFontTitleBold(), Element.ALIGN_CENTER));

            //
            DottedLineSeparator separator = new DottedLineSeparator();
            separator.setPercentage(59500f / 523f);
            Chunk linebreak = new Chunk(separator);
            document.add(linebreak);
            
            
            int totalAgremiados = ControladorAgremiado.getInstance().getAll().size();

            if (totalAgremiados > 0) {
                PdfPTable tableAgremiados;
                PdfPCell cell;
                String pais = "";
                int cont = 0;
                encabezados = new Format(15);
                contenido = new Format(15);
                for (Agremiado agremiado : ControladorAgremiado.getInstance().getAllSorted("pais")) {
                    totalAgremiados--;
                    tableAgremiados = new PdfPTable(2);
                    if (pais.compareTo(agremiado.getObjPais().getNombre()) != 0) {
                        if (cont != 0) {
                            document.add(elementsPDF.getParagraph("Registros: " + cont, format.getFontSmallSimple(), Element.ALIGN_RIGHT));
                            cont = 0;
                        }
                        tableAgremiados.setSpacingBefore(5);
                        pais = agremiado.getObjPais().getNombre();
                        document.add(elementsPDF.getParagraph(pais.toUpperCase(), format.getFontTitleBold(), Element.ALIGN_CENTER));
                        cell = new PdfPCell(elementsPDF.getParagraph("Nombre", encabezados.getFontTitleBold(), Element.ALIGN_JUSTIFIED));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        tableAgremiados.addCell(cell);
                        cell = new PdfPCell(elementsPDF.getParagraph("Institución", encabezados.getFontSubtitleBold(), Element.ALIGN_JUSTIFIED));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        tableAgremiados.addCell(cell);
                        cell.setBorder(5);
                    }
                    cont++;

                    tableAgremiados.addCell(elementsPDF.getParagraph(agremiado.getNombre(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    tableAgremiados.addCell(elementsPDF.getParagraph(agremiado.getInstitucion(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    tableAgremiados.setWidthPercentage(100);
                    document.add(tableAgremiados);

                    if (totalAgremiados == 0) {
                        document.add(elementsPDF.getParagraph("Registros: " + cont, format.getFontSmallSimple(), Element.ALIGN_RIGHT));
                    }
                }

                document.add(elementsPDF.getParagraph("Total agremiados registrados: " + ControladorAgremiado.getInstance().getAll().size(),
                         format.getFontTitleBold(), Element.ALIGN_RIGHT));
            }else{
                contenido = new Format(42);
                document.add(elementsPDF.getParagraph("SIN REGISTROS", contenido.getFontTitleBold(), Element.ALIGN_CENTER));
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
