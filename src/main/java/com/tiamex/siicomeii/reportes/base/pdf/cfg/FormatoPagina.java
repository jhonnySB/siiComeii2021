package com.tiamex.siicomeii.reportes.base.pdf.cfg;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.utils.Utils;
import com.vaadin.server.FileResource;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author cerimice **/
/** @company tiamex **/

public class FormatoPagina extends PdfPageEventHelper{
    //private final long idCompany;
    protected Image logoHeader; 

    /*
    public FormatoPagina() {
    } */
    
    /*
    public FormatoPagina(long idCompany) throws IOException, Exception{
        this.idCompany = idCompany;
    } */

    @Override
    public void onStartPage (PdfWriter writer, Document document){
        /*
            try{
            File fondo = new File(Main.getBaseDir()+"/media/background/company"+idCompany+".png");
            if(!fondo.exists()){fondo = new File(Main.getBaseDir()+"/media/background/company0.png");}
            
            String pathFondoPagina = fondo.getPath();
            Image fondoPagina = Image.getInstance(pathFondoPagina);
            fondoPagina.scaleAbsolute(document.getPageSize());
            fondoPagina.setAbsolutePosition(0,0);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(fondoPagina);
            }catch(DocumentException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            }catch (IOException ex){
            Logger.getLogger(FormatoPagina.class.getName()).log(Utils.nivelLoggin(), null, ex);
            } */
        try {
            
            logoHeader = Image.getInstance(Main.getBaseDir()+"/logoTiamex.png");
            logoHeader.setAbsolutePosition(100, 100);
        } catch (BadElementException ex) {
            Logger.getLogger(FormatoPagina.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormatoPagina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onEndPage (PdfWriter writer, Document document){
        try {
            writer.addDirectImageSimple(logoHeader);
        } catch (DocumentException ex) {
            Logger.getLogger(FormatoPagina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
