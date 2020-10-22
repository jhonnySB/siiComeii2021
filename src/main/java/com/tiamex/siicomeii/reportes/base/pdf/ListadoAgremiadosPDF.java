package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.tiamex.siicomeii.utils.Utils;
import java.util.logging.Logger;

/** @author cerimice **/
public final class ListadoAgremiadosPDF extends BasePDF{
    
    public ListadoAgremiadosPDF() throws Exception{
        create();
    }

    @Override
    protected void create() throws Exception{
        try{
        document = new Document();
            document.setMargins(10,10,10,10);
            document.setPageSize(PageSize.LETTER);
            document.addCreationDate();
            PdfWriter writer = PdfWriter.getInstance(document,pdfFile);
            document.open();
            
            document.add(elementsPDF.getParagraph("Listado de Agremiado".toUpperCase(),format.getFontTitleBold(),Element.ALIGN_CENTER));
            
            document.close();
        }catch(DocumentException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }finally{
            document.close();
        }
    }
}
