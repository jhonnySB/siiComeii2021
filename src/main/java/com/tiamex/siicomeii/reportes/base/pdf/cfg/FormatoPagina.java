package com.tiamex.siicomeii.reportes.base.pdf.cfg;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import static com.lowagie.text.ElementTags.FONT;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.utils.Utils;
import com.vaadin.server.FileResource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
/**
 * @company tiamex *
 */
public class FormatoPagina extends PdfPageEventHelper {

    final Image logoHeader;

    Font font;
    PdfTemplate t;
    Image total;

    public FormatoPagina() throws IOException, BadElementException {
        this.logoHeader = Image.getInstance(Main.getBaseDir() + "/logoTiamex.png");
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 15);
        try {
            total = Image.getInstance(t);
        } catch (BadElementException ex) {
            Logger.getLogger(FormatoPagina.class.getName()).log(Level.SEVERE, null, ex);
        }
        total.setRole(PdfName.ARTIFACT); // new Font(BaseFont.createFont("C:\Users\jhon\Downloads\Compressed\Montserrat", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
        try {
            //font = new Font(FontFamily.HELVETICA);
            font = new Font(BaseFont.createFont("C:\\Users\\jhon\\Downloads\\Compressed\\Montserrat\\Montserrat-ExtraLight.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 9);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(FormatoPagina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        try {
            table.setWidths(new int[]{24, 24, 2});
            float rightIdent = 10;
            float right = document.right()-rightIdent;
            table.setTotalWidth(right);
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            table.addCell(new Phrase("Reporte Agremiados", font));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Phrase(String.format("Página %d de", writer.getPageNumber()), font));
            PdfPCell cell = new PdfPCell(total);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            table.writeSelectedRows(0, -1, document.left(), 25, canvas);
            canvas.endMarkedContentSequence();
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(t, Element.ALIGN_LEFT,new Phrase(String.valueOf(writer.getPageNumber()), font),2, 4, 0);
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            logoHeader.scalePercent(30);
            float leftIdent = 0,topIdent=20;
            float left = document.left()+leftIdent;
            float top = document.top()-topIdent;
            logoHeader.setAbsolutePosition(left,top);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(logoHeader);
            
            Locale varRegional = new Locale("es", "MX");
            LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Mexico_City"));
            String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", varRegional));
            Paragraph fecha = new Paragraph(fechaFormateada, new Font(Font.FontFamily.HELVETICA, 9));
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(0);
            fecha.setSpacingBefore(0);
            //document.add(fecha);
            
            
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(fechaFormateada ,font), 
                    (document.right()-document.left()/2+document.leftMargin()),document.top()-10, 0);
            document.add(new Paragraph(Chunk.NEWLINE));
            DottedLineSeparator separator = new DottedLineSeparator();
            separator.setPercentage(59500f / 523f);
            Chunk linebreak = new Chunk(separator);
            //document.add(linebreak);
            
        } catch (DocumentException ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    /*
    @Override
    public void onEndPage (PdfWriter writer, Document document){
        try {
            writer.addDirectImageSimple(logoHeader);
        } catch (DocumentException ex) {
            Logger.getLogger(FormatoPagina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}
