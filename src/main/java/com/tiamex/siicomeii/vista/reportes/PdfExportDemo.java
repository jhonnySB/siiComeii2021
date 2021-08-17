package com.tiamex.siicomeii.vista.reportes;

import com.itextpdf.awt.PdfGraphics2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
//import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.svg.SVGDocument;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.ImgTemplate;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

class PdfExportDemo {

    private String fontDirectory = null;
    private final String baseFont = "Arial";

    private PdfWriter writer;
    private Document document;

    private Font captionFont;
    private Font normalFont;

    private String svgStr;
    private float width;
    private float height;
    final Paragraph BR = new Paragraph(Chunk.NEWLINE);
    

    public PdfExportDemo() {

    }

    public File writePdf(String pdffilename, String svg,float width,float height) {
        svgStr = svg;
        this.width = width; this.height = height;
        document = new Document();
        document.addTitle("Reporte agremiados");
        document.addCreator("TIAMEX");
        initFonts();

        File file = null;
        try {
            file = writeToFile(pdffilename, document);

            document.open();

            writePdfContent();

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Get Font directory that will be checked for custom fonts.
     *
     * @return Path to fonts
     */
    public String getFontDirectory() {
        return fontDirectory;
    }

    /**
     * Set Font directory that will be checked for custom fonts.
     *
     * @param fontDirectory Path to fonts
     */
    public void setFontDirectory(String fontDirectory) {
        this.fontDirectory = fontDirectory;
    }

    private void initFonts() {
        if (fontDirectory != null) {
            FontFactory.registerDirectory(fontDirectory);
        }

        captionFont = FontFactory.getFont(baseFont, 10, Font.BOLD, new Color(0,
                0, 0));
        normalFont = FontFactory.getFont(baseFont, 10, Font.NORMAL, new Color(
                0, 0, 0));
    }

    private File writeToFile(String filename, Document document)
            throws DocumentException {
        File file = null;
        try {
            file = File.createTempFile(filename, ".pdf");
            file.deleteOnExit();
            FileOutputStream fileOut = new FileOutputStream(file);
            writer = PdfWriter.getInstance(document, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void writePdfContent() throws DocumentException, IOException {
        Paragraph caption = new Paragraph();
        caption.add(new Chunk("Vaadin Charts test ", captionFont));
        document.add(caption);

        
        document.add(BR);

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk("This PDF is rendered with iText 2.1.7.",
                normalFont));
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph
                .add(new Chunk(
                        "Chart below is originally an SVG image created with Vaadin Charts and rendered with help of Batik SVG Toolkit.",
                        normalFont));
        document.add(paragraph);
        //document.add(createSvgImage(writer.getDirectContent(), 400, 400));
        
        /******** Chart SVG ***************/
        Image scaledImg = createSvgImg();
        int indentation = 0;
        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()- document.rightMargin() - indentation) 
                / scaledImg.getWidth()) * 100;
        System.out.println("Scaler: "+scaler);
        scaledImg.scalePercent(scaler);
        //scaledImg.scaleAbsolute(width, height);
        /******** Chart SVG ***************/
        document.add(scaledImg);

        document.add(createExampleTable());
    }

    private Image createSvgImg() throws IOException, BadElementException {
        File tempFile = File.createTempFile("tempchartExport", ".svg");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(tempFile))) {
            out.write(svgStr);
            out.close();
        }
        SVGDocument svgDoc = new SAXSVGDocumentFactory(null).createSVGDocument(null, new FileReader(tempFile.getAbsolutePath()));

        // Try to read embedded height and width
        float svgWidth = Float.parseFloat(svgDoc.getDocumentElement().getAttribute("width").replaceAll("[^0-9.,]", ""));
        float svgHeight = Float.parseFloat(svgDoc.getDocumentElement().getAttribute("height").replaceAll("[^0-9.,]", ""));

        PdfTemplate svgTempl = PdfTemplate.createTemplate(writer, svgWidth, svgHeight);
        Graphics2D g2d = svgTempl.createGraphics(svgTempl.getWidth(), svgTempl.getHeight());
        GraphicsNode chartGfx = (new GVTBuilder()).build(new BridgeContext(new UserAgentAdapter()), svgDoc);
        chartGfx.paint(g2d);
        g2d.dispose();

        return new ImgTemplate(svgTempl);
    }

    private PdfPTable createExampleTable() throws BadElementException {
        PdfPTable table = new PdfPTable(2);
        table.setHeaderRows(1);
        table.setWidthPercentage(100);
        table.setTotalWidth(100);

        // Add headers
        table.addCell(createHeaderCell("Browser"));
        table.addCell(createHeaderCell("Percentage"));

        // Add rows
        table.addCell(createCell("Firefox"));
        table.addCell(createCell("45.0"));
        table.addCell(createCell("IE"));
        table.addCell(createCell("26.8"));
        table.addCell(createCell("Chrome"));
        table.addCell(createCell("12.8"));
        table.addCell(createCell("Safari"));
        table.addCell(createCell("8.5"));
        table.addCell(createCell("Opera"));
        table.addCell(createCell("6.2"));
        table.addCell(createCell("Others"));
        table.addCell(createCell("0.7"));

        return table;
    }

    private PdfPCell createHeaderCell(String caption)
            throws BadElementException {
        Chunk chunk = new Chunk(caption, captionFont);
        Paragraph p = new Paragraph(chunk);
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);

        PdfPCell cell = new PdfPCell(p);
        cell.setBorder(0);
        cell.setBorderWidthBottom(1);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        return cell;
    }

    private PdfPCell createCell(String value) throws BadElementException {
        PdfPCell cell = new PdfPCell(new Phrase(new Chunk(value, normalFont)));
        cell.setBorder(0);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        return cell;
    }

    private Image drawUnscaledSvg(PdfContentByte contentByte)
            throws IOException {

        // First, lets create a graphics node for the SVG image.
        GraphicsNode imageGraphics = buildBatikGraphicsNode(svgStr);

        // SVG's width and height
        float width = (float) imageGraphics.getBounds().getWidth();
        float height = (float) imageGraphics.getBounds().getHeight();

        // Create a PDF template for the SVG image
        PdfTemplate template = contentByte.createTemplate(width, height);

        // Create Graphics2D rendered object from the template
        Graphics2D graphics = template.createGraphics(width, height);

        if (graphics != null) {
            System.out.println("Graphics not null");
        }
        try {
            // SVGs can have their corner at coordinates other than (0,0).
            Rectangle2D bounds = imageGraphics.getBounds();
            graphics.translate(-bounds.getX(), -bounds.getY());

            // Paint SVG GraphicsNode with the 2d-renderer.
            imageGraphics.paint(graphics);

            // Create and return a iText Image element that contains the SVG
            // image.
            ImgTemplate imgTemp = new ImgTemplate(template);
            if (imgTemp != null) {
                System.out.println("ImgTemp no null");
            }
            return imgTemp;
        } catch (BadElementException e) {
            throw new RuntimeException("Couldn't generate PDF from SVG", e);
        } finally {
            // Manual cleaning (optional)
            graphics.dispose();
        }
    }

    /**
     * Use Batik SVG Toolkit to create GraphicsNode for the target SVG.
     * <ol>
     * <li>Create SVGDocument</li>
     * <li>Create BridgeContext</li>
     * <li>Build GVT tree. Results to GraphicsNode</li>
     * </ol>
     *
     * @param svg SVG as a String
     * @return GraphicsNode
     * @throws IOException Thrown when SVG could not be read properly.
     */
    private GraphicsNode buildBatikGraphicsNode(String svg) throws IOException {
        UserAgent agent = new UserAgentAdapter();

        SVGDocument svgdoc = createSVGDocument(svg, agent);

        DocumentLoader loader = new DocumentLoader(agent);
        BridgeContext bridgeContext = new BridgeContext(agent, loader);
        bridgeContext.setDynamicState(BridgeContext.STATIC);

        GVTBuilder builder = new GVTBuilder();

        GraphicsNode imageGraphics = builder.build(bridgeContext, svgdoc);

        return imageGraphics;
    }

    private SVGDocument createSVGDocument(String svg, UserAgent agent)
            throws IOException {
        SVGDocumentFactory documentFactory = new SAXSVGDocumentFactory(
                agent.getXMLParserClassName(), true);

        SVGDocument svgdoc = documentFactory.createSVGDocument(null,
                new StringReader(svg));
        if (svgdoc != null) {
            System.out.println("svgdoc not null");
        }
        return svgdoc;
    }

    private Image createSvgImage(PdfContentByte contentByte,
            float maxPointWidth, float maxPointHeight) throws IOException {
        Image image = drawUnscaledSvg(contentByte);
        image.scaleToFit(maxPointWidth, maxPointHeight);
        return image;
    }

}
