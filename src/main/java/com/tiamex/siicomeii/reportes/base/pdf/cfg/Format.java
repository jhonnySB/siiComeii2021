package com.tiamex.siicomeii.reportes.base.pdf.cfg;

import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;

/** @author cerimice **/
public class Format{
    
    /*Titles*/
    private Font fontTitleBold;
    public Font getFontTitleBold(){return fontTitleBold;}
    private Font fontTitleItalic;
    public Font getFontTitleItalic(){return fontTitleItalic;}
    private Font fontTitleBoldItalic;
    public Font getFontTitleBoldItalic(){return fontTitleBoldItalic;}
    private Font fontTitleSimple;
    public Font getFontTitleSimple(){return fontTitleSimple;}
    
    /*Subtitles*/
    private Font fontSubtitleBold;
    public Font getFontSubtitleBold(){return fontSubtitleBold;}
    private Font fontSubtitleItalic;
    public Font getFontSubtitleItalic(){return fontSubtitleItalic;}
    private Font fontSubtitleBoldItalic;
    public Font getFontSubtitleBoldItalic(){return fontSubtitleBoldItalic;}
    private Font fontSubtitleSimple;
    public Font getFontSubtitleSimple(){return fontSubtitleSimple;}
    
    /*Text*/
    private Font fontTextBold;
    public Font getFontTextBold(){return fontTextBold;}
    private Font fontTextItalic;
    public Font getFontTextItalic(){return fontTextItalic;}
    private Font fontTextBoldItalic;
    public Font getFontTextBoldItalic(){return fontTextBoldItalic;}
    private Font fontTextSimple;
    public Font getFontTextSimple(){return fontTextSimple;}
    
    /*small*/
    private Font fontSmallBold;
    public Font getFontSmallBold(){return fontSmallBold;}
    private Font fontSmallItalic;
    public Font getFontSmallItalic(){return fontSmallItalic;}
    private Font fontSmallBoldItalic;
    public Font getFontSmallBoldItalic(){return fontSmallBoldItalic;}
    private Font fontSmallSimple;
    public Font getFontSmallSimple(){return fontSmallSimple;}
    
    private final BaseColor color;
    public BaseColor getColor(){return color;}
    
    private final Font.FontFamily font;
    public Font.FontFamily getFont(){return font;}
    
    private int size;
    public int getSize(){return size;}
    public void setSize(int valor){size = valor; creatingFonts();}
    
    public Format(){
        font = Font.FontFamily.HELVETICA;
        color = BaseColor.BLACK;
        size = 12;
        creatingFonts();
        }
    
    public Format(int sizeFont){
        font = Font.FontFamily.HELVETICA;
        color = BaseColor.BLACK;
        size = sizeFont;
        creatingFonts();
        }
    
    public Format(Font.FontFamily font,BaseColor color,int sizeFont){
        this.font = font;
        this.color = color;
        this.size = sizeFont;
        creatingFonts();
        }
    
    private void creatingFonts(){
        fontTitleBold = this.generarFont(size,Font.BOLD,color);
        fontTitleItalic =  this.generarFont(size,Font.ITALIC,color);
        fontTitleBoldItalic =  this.generarFont(size,Font.BOLDITALIC,color);
        fontTitleSimple = this.generarFont(size,Font.NORMAL,color);
        
        fontSubtitleBold = this.generarFont(size-2,Font.BOLD,color);
        fontSubtitleItalic =  this.generarFont(size-2,Font.ITALIC,color);
        fontSubtitleBoldItalic =  this.generarFont(size-2,Font.BOLDITALIC,color);
        fontSubtitleSimple = this.generarFont(size-2,Font.NORMAL,color);
        
        fontTextBold = this.generarFont(size-4,Font.BOLD,color);
        fontTextItalic =  this.generarFont(size-4,Font.ITALIC,color);
        fontTextBoldItalic =  this.generarFont(size-4,Font.BOLDITALIC,color);
        fontTextSimple = this.generarFont(size-4,Font.NORMAL,color);
        
        fontSmallBold = this.generarFont(size-6,Font.BOLD,color);
        fontSmallItalic =  this.generarFont(size-6,Font.ITALIC,color);
        fontSmallBoldItalic =  this.generarFont(size-6,Font.BOLDITALIC,color);
        fontSmallSimple = this.generarFont(size-6,Font.NORMAL,color);
        }

    private Font generarFont(int size,int estilo,BaseColor color){
        Font font =  new Font(this.font);
            font.setSize(size);
            font.setStyle(estilo);
            font.setColor(color);
        return font;
        }
    }