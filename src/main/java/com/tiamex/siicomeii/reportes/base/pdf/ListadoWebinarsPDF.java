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
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.Format;
import com.tiamex.siicomeii.utils.Utils;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
public final class ListadoWebinarsPDF extends BasePDF {

    private Agremiado agremiado;

    public ListadoWebinarsPDF() {

    }

    public ListadoWebinarsPDF(Agremiado agremiado) throws Exception {
        this.agremiado = agremiado;
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

            //writer.setPageEvent(new FormatoPagina());
            document.open();
            ///////  ------Inicio del doc------ ////////////
            encabezados = new Format(15);
            contenido = new Format(15);
            Image logoHeader = Image.getInstance(Main.getBaseDir() + "/logoTiamex.png");
            logoHeader.setAlignment(Element.ALIGN_LEFT);
            logoHeader.scalePercent(35);
            logoHeader.setSpacingAfter(0);
            logoHeader.setSpacingBefore(0);
            Locale varRegional = new Locale("es", "MX");
            LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Mexico_City"));
            String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", varRegional));
            Paragraph fecha = new Paragraph("Fecha de creación: " + fechaFormateada, new Font(Font.FontFamily.HELVETICA, 9));
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(0);
            fecha.setSpacingBefore(0);
            PdfPTable tableHeader = new PdfPTable(3);
            PdfPCell cell1 = new PdfPCell();
            cell1.addElement(logoHeader);
            cell1.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cell1);
            cell1 = new PdfPCell();
            Font gray = new Font(FontFamily.HELVETICA, 10, Font.UNDERLINE, BaseColor.GRAY);
            Chunk preText = new Chunk("Webinars asistidos de:", gray);
            Font bold = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
            Chunk name = new Chunk(agremiado.getNombre(), bold);
            Paragraph pa = new Paragraph();
            pa.add(preText);
            pa.add("\n");
            pa.add(name);
            pa.setAlignment(Element.ALIGN_CENTER);
            cell1.addElement(pa);
            //cell1.addElement(elementsPDF.getParagraph(agremiado.getNombre(), encabezados.getFontSubtitleBold(), Element.ALIGN_CENTER));
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

            //System.out.println("Reg: "+ControladorAsistenciaWebinar.getInstance().getByAsistencia(agremiado.getId()).size());
            List<AsistenciaWebinar> listaAsistencias = ControladorAsistenciaWebinar.getInstance().getByAsistencia(agremiado.getId());

            if (!listaAsistencias.isEmpty()) {
                Collections.sort(listaAsistencias, new comparatorClass());
                int fechaTemp = 0;
                Font fontFechas = new Font(FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(226, 232, 237));
                PdfPTable tableWebinars;
                PdfPCell cell;
                for (AsistenciaWebinar a : listaAsistencias) {
                    tableWebinars = new PdfPTable(2);//nombrewebinar,fecha
                    tableWebinars.setSpacingBefore(5);
                    tableWebinars.setSpacingAfter(5);
                    tableWebinars.setWidthPercentage(100);
                    int fechaWebinar = a.getObjWebinarRealizado().getFecha().getYear();
                    if (fechaWebinar != fechaTemp) {
                        Paragraph p = new Paragraph("Webinars del año " + fechaWebinar + "\n");
                        p.setFont(fontFechas);
                        p.setAlignment(Element.ALIGN_CENTER);
                        document.add(p);
                        cell = new PdfPCell(elementsPDF.getParagraph("Nombre del webinar", encabezados.getFontTitleBold(), Element.ALIGN_JUSTIFIED));
                        BaseColor bc = new BaseColor(130, 219, 255);
                        cell.setBackgroundColor(bc);
                        tableWebinars.addCell(cell);
                        cell = new PdfPCell(elementsPDF.getParagraph("Fecha impartido", encabezados.getFontSubtitleBold(), Element.ALIGN_JUSTIFIED));
                        cell.setBackgroundColor(bc);
                        tableWebinars.addCell(cell);
                        fechaTemp = fechaWebinar;
                    }

                    tableWebinars.addCell(elementsPDF.getParagraph(a.getObjWebinarRealizado().getNombre(), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    tableWebinars.addCell(elementsPDF.getParagraph(a.getObjWebinarRealizado().getFecha().format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' y",
                            varRegional)), contenido.getFontTextSimple(), Element.ALIGN_JUSTIFIED));
                    document.add(tableWebinars);
                }

                ///////  ------Fin del doc------ ////////////
                
            } else {
                contenido = new Format(Font.FontFamily.COURIER, BaseColor.GRAY, 42);
                document.add(elementsPDF.getParagraph("SIN REGISTROS", contenido.getFontTitleBold(), Element.ALIGN_CENTER));
            }
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }

    private static class comparatorClass implements Comparator<AsistenciaWebinar> {

        public comparatorClass() {
        }

        @Override
        public int compare(AsistenciaWebinar o1, AsistenciaWebinar o2) {

            int obj1 = o1.getObjWebinarRealizado().getFecha().getYear();
            int obj2 = o2.getObjWebinarRealizado().getFecha().getYear();
            return obj2 - obj1;
        }
    }

}
