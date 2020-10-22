package com.tiamex.siicomeii.reportes.base.pdf.cfg;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import java.util.List;

/** @author cerimice **/
public class ElementsPDF{
    
    public Paragraph getParagraph(String text,Font font,int horizontalAling){
        Paragraph paragraph = new Paragraph(text,font);
            paragraph.setAlignment(horizontalAling);
        return paragraph;
        }
    
    public Phrase getPhrase(String text,Font font){
        Phrase paragraph = new Phrase(text,font);
            paragraph.setFont(font);
        return paragraph;
        }
    
    public PdfPCell getCell(String text,Font font,int border,int colSpan,int rowSpan,int horizontalAling,int verticalAling){
        PdfPCell cell = new PdfPCell();
        Paragraph content = new Paragraph(text,font);
            content.setAlignment(horizontalAling);
        cell.addElement(content);
        cell.setBorder(border);
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setVerticalAlignment(verticalAling);
        cell.setHorizontalAlignment(horizontalAling);
        return cell;
        }
    
    public PdfPCell getCell(String text,Font font,int border,int colSpan,int rowSpan,int horizontalAling,int verticalAling,BaseColor color){
        PdfPCell cell = new PdfPCell();
        Paragraph content = new Paragraph(text,font);
            content.setAlignment(horizontalAling);
        cell.addElement(content);
        cell.setBorder(border);
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setVerticalAlignment(verticalAling);
        cell.setHorizontalAlignment(horizontalAling);
        cell.setBackgroundColor(color);
        return cell;
        }
    
    public PdfPCell getCell(List<String> text,List<Font> font,int border,int colSpan,int rowSpan,int horizontalAling,int verticalAling){
        PdfPCell cell = new PdfPCell();
        Paragraph content;
        for(int x=0;x<text.size();x++){
            content = new Paragraph(text.get(x),font.get(x));
            content.setAlignment(horizontalAling);
            cell.addElement(content);
            }
        cell.setBorder(border);
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setVerticalAlignment(verticalAling);
        cell.setHorizontalAlignment(horizontalAling);
        return cell;
        }
    
    public PdfPCell getCell(Image imagen,int border,int colSpan,int rowSpan,int horizontalAling,int verticalAling){
        PdfPCell cell = new PdfPCell(imagen,true);
        cell.setBorder(border);
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setVerticalAlignment(verticalAling);
        cell.setHorizontalAlignment(horizontalAling);
        return cell;
        }
    
    public PdfPCell getCell(Paragraph content,Font font,int border,int colSpan,int rowSpan,int horizontalAling,int verticalAling){
        PdfPCell cell = new PdfPCell();
        cell.addElement(content);
        cell.setBorder(border);
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setVerticalAlignment(verticalAling);
        cell.setHorizontalAlignment(horizontalAling);
        return cell;
        }
    
    public Image createBarcode128(String stringCode,PdfContentByte pdfContentByte,int horizontalAling){
        Barcode128 barcode = new Barcode128();
            barcode.setFont(null);
            barcode.setCode(stringCode);
            barcode.setCodeType(Barcode128.CODE128);
        Image codeImage = barcode.createImageWithBarcode(pdfContentByte,BaseColor.BLACK,BaseColor.GRAY);
            codeImage.scalePercent(200);
            codeImage.setAlignment(Element.ALIGN_CENTER);
        return codeImage;
    }
    
    public Image createBarcode128(String stringCode,PdfContentByte pdfContentByte,int horizontalAling,float scalePercent){
        Barcode128 barcode = new Barcode128();
            barcode.setFont(null);
            barcode.setCode(stringCode);
            barcode.setCodeType(Barcode128.CODE128);
        Image codeImage = barcode.createImageWithBarcode(pdfContentByte,BaseColor.BLACK,BaseColor.GRAY);
            codeImage.scalePercent(scalePercent);
            codeImage.setAlignment(Element.ALIGN_CENTER);
        return codeImage;
    }
    
    public Image createBarcodeEAN(String stringCode,PdfContentByte pdfContentByte,int horizontalAling){
        BarcodeEAN barcode = new BarcodeEAN();
            barcode.setCodeType(BarcodeEAN.EAN13);
            barcode.setCode(stringCode);
        Image codeImage = barcode.createImageWithBarcode(pdfContentByte, null, null);
            codeImage.scalePercent(100);
            codeImage.setAlignment(Element.ALIGN_CENTER);
        return codeImage;
    }
    
    public Image createBarcodeQR(String stringCode,int horizontalAling) throws BadElementException{
        BarcodeQRCode barcode = new BarcodeQRCode(stringCode,1,1,null);
        Image codeImage = barcode.getImage();
            codeImage.scalePercent(100);
            codeImage.setAlignment(Element.ALIGN_CENTER);
        return codeImage;
    }
}
