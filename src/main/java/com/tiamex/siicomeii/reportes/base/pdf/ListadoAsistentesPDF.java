package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.Format;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.FormatoPagina;
import com.tiamex.siicomeii.utils.Utils;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
public final class ListadoAsistentesPDF extends BasePDF {

    private final WebinarRealizado webinar;

    public ListadoAsistentesPDF(WebinarRealizado webinar) throws Exception {
        this.webinar = webinar;
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
            /*
            Image logoHeader = Image.getInstance(Main.getBaseDir() + "/logoTiamex.png");
            logoHeader.setAlignment(Element.ALIGN_LEFT);
            logoHeader.scalePercent(35);
            logoHeader.setSpacingAfter(0);
            logoHeader.setSpacingBefore(0);
            Locale varRegional = new Locale("es", "MX");
            LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Mexico_City"));
            String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", varRegional));
            Paragraph fecha = new Paragraph("Fecha de creación: " + fechaFormateada, new Font(FontFamily.HELVETICA, 9));
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(0);
            fecha.setSpacingBefore(0);
            PdfPTable tableHeader = new PdfPTable(2);
            PdfPCell cell1 = new PdfPCell();
            cell1.addElement(logoHeader);
            cell1.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.addElement(fecha);
            cell1.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cell1);
            tableHeader.setWidthPercentage(100);
            tableHeader.setSpacingAfter(5);
            tableHeader.setSpacingBefore(0);
            document.add(tableHeader);
            DottedLineSeparator separator = new DottedLineSeparator();
            separator.setPercentage(59500f / 523f);
            Chunk linebreak = new Chunk(separator);
            document.add(linebreak);
            document.add(elementsPDF.getParagraph("ASISTENTES AL WEBINAR: " + webinar.getNombre().toUpperCase() + " " + webinar.getFecha().getYear(),
                    format.getFontSubtitleBold(), Element.ALIGN_CENTER));
            document.add(new Chunk()); */

            List<AsistenciaWebinar> listaAsistentes = ControladorAsistenciaWebinar.getInstance().getByWebinar(webinar.getId());
            int totalAsistentes = listaAsistentes.size();
            System.out.println(totalAsistentes);
            if (listaAsistentes.size() > 0) {
                encabezados = new Format(15);
                contenido = new Format(15);
                PdfPTable tableAsistentes = new PdfPTable(3);
                PdfPCell cell;
                cell = new PdfPCell(elementsPDF.getParagraph("Nombre", encabezados.getFontTitleBold(), Element.ALIGN_JUSTIFIED));
                BaseColor bc = new BaseColor(130, 219, 255);
                cell.setBackgroundColor(bc);
                tableAsistentes.addCell(cell);
                cell = new PdfPCell(elementsPDF.getParagraph("Institución", encabezados.getFontSubtitleBold(), Element.ALIGN_JUSTIFIED));
                cell.setBackgroundColor(bc);
                tableAsistentes.addCell(cell);
                cell = new PdfPCell(elementsPDF.getParagraph("País", encabezados.getFontSubtitleBold(), Element.ALIGN_JUSTIFIED));
                cell.setBackgroundColor(bc);
                tableAsistentes.addCell(cell);
                for (AsistenciaWebinar asistente : listaAsistentes) {
                    tableAsistentes.addCell(elementsPDF.getParagraph(asistente.getObjAgremiado().getNombre(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    tableAsistentes.addCell(elementsPDF.getParagraph(asistente.getObjAgremiado().getInstitucion(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    tableAsistentes.addCell(elementsPDF.getParagraph(asistente.getObjAgremiado().getObjPais().getNombre(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    tableAsistentes.setWidthPercentage(100);
                }
                document.add(tableAsistentes);
                document.add(elementsPDF.getParagraph("Total asistentes: " + totalAsistentes,
                        format.getFontSubtitleBold(), Element.ALIGN_RIGHT));
            } else {
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
