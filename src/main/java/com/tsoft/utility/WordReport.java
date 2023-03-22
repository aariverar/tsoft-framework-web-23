package com.tsoft.utility;

import java.io.*;
import com.tsoft.base.Report;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.util.*;
import static com.tsoft.base.Utils.*;

public class WordReport {

    private XWPFDocument document;
    private Map<String, String> data;
    private Map<String, String> post;
    private StringBuilder dataTest;
    private List<Map<String, String>> ListImage;
    private List<Map<String, String>> ListText;
    private List<Map<String, String>> ListJSON;
    private int countImage;
    private int countJSON;

    private static final String DIR = System.getProperty("user.dir");
    private static final String TEMPLATE =  DIR + "/src/main/resources/template/TestCaseTemplate.docx";
    private static final String TEMPLATE_PORTADA =  DIR + "/src/main/resources/template/PortadaTemplate.docx";
    private final String SAVE_FILE = DIR + "/target/resultado/reporte-doc-";

    public void onWordStart(){
        try {
            document = new XWPFDocument(new FileInputStream(TEMPLATE_PORTADA));
            FileUtils.forceMkdir(new File(SAVE_FILE+"single/"));
            FileUtils.forceMkdir(new File(SAVE_FILE+"all/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onWordLoad(Map<String, String> data, Map<String, String> post){
        this.data = data;
        this.post = post;
        this.dataTest = new StringBuilder();
        this.ListImage = new ArrayList<>();
        this.ListText = new ArrayList<>();
        this.ListJSON = new ArrayList<>();
        this.countImage = 0;
        this.countJSON = 0;
    }

    public void onAddLine(String addLine, boolean bold) {
        Map<String, String> Text = new HashMap<>();
        Text.put("text", parseJSON(addLine) != null ? "${JSON"+countJSON+"}" : addLine);
        Text.put("bold", String.valueOf(bold));
        ListText.add(Text);

        if (parseJSON(addLine) != null) {
            Map<String, String> Json = new HashMap<>();
            Json.put("${JSON"+countJSON+"}", addLine);
            ListJSON.add(Json);
            countJSON++;
        }
    }

    public void appendData(String param){
        String dto = post.get(param).trim();
        dataTest.append(param+": "+dto).append("\n");
    }

    public void appendData(JSONObject param){
        dataTest.append(prettyJSON(param.toJSONString())).append("\n");
    }

    public void onAddBreak() {
        document.createParagraph().createRun().addBreak();
    }

    public void onAddImage(WebDriver driver){
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        String path = source.getPath();

        onAddLine("${Image"+countImage+"}", false);

        Map<String, String> Image = new HashMap<>();
        Image.put("${Image"+countImage+"}", path);

        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();

        String ImageModel = Report.onWordMode;
        String Width = null, Height = null;
        if (ImageModel.equalsIgnoreCase("mobile")){
            Width = properties.getProperty("report.mobile.width");
            Height = properties.getProperty("report.mobile.height");
        }

        if (ImageModel.equalsIgnoreCase("browser")){
            Width = properties.getProperty("report.browser.width");
            Height = properties.getProperty("report.browser.height");
        }
        Image.put("${Image-Width-"+countImage+"}", Width);
        Image.put("${Image-Height-"+countImage+"}", Height);

        ListImage.add(Image);
        countImage++;
    }

    public void onWordProcess(String status) {
        try {
            Utils utils = new Utils(document);
            //Header
            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document);
            XWPFHeader header = policy.getDefaultHeader();
            XWPFTable tableHeader = header.getTables().get(0);
            utils.setFontFamily("Calibri");
            utils.setBold(false);
            utils.setFontSize(11);
            utils.replace(tableHeader, "${DateHeader}", this.data.get("DG4"));

            //Portada
            utils.setFontFamily("Arial");
            utils.setBold(true);
            utils.setFontSize(22);
            utils.replace("${TitleProject}", this.data.get("TitleProject"));

            utils.setFontFamily("Arial");
            utils.setBold(false);
            utils.setFontSize(18);
            String Month = generarFechaFormat("MMMM");
            utils.replace("${MonthProject}", ucFirst(Month));

            utils.setFontFamily("Arial");
            utils.setBold(false);
            utils.setFontSize(18);
            utils.replace("${YearProject}", generarFechaFormat("yyyy"));

            //Portada Tabla DG
            XWPFTable tableP = document.getTables().get(0); //Datos Generales
            XWPFTable tableA = document.getTables().get(1); //Alcance
            XWPFTable tableM = document.getTables().get(2); //Tabla Aplicativo

            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(false);
            utils.replace(tableP, "${DG1}", this.data.get("DG1"));

            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(true);
            utils.replace(tableP, "${DG2}", this.data.get("DG2"));

            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(false);
            utils.replace(tableP, "${DG3}", this.data.get("DG3"));

            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(false);
            utils.replace(tableP, "${DG4}", this.data.get("DG4"));

            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(false);
            utils.add(tableA, this.data.get("DG5"), 0);

            //Modulo
            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(false);
            utils.setStyle("Módulo");
            utils.add(tableM,this.data.get("TitleProject"), 1, 0);

            //Portada Template
            XWPFDocument TempDocument = new XWPFDocument(new FileInputStream(TEMPLATE));
            XWPFTable FuncTable = TempDocument.getTables().get(0);
            XWPFTable BaseTable = TempDocument.getTables().get(1);

            //Funcionalidad
            String funcion = this.data.get("FunctionProject");
            utils.setFontFamily("Arial");
            utils.setFontSize(11);
            utils.setBold(false);
            utils.setStyle("Funcionalidad");

            ArrayList lista = new ArrayList();
            int TotalTable = document.getTables().size();
            if (TotalTable >= 4){
                for (int i = document.getTables().size() - 1; i > 2 ; i--) {
                    XWPFTable tbl = document.getTables().get(i);
                    XWPFTableCell cell0 = tbl.getRow(0).getCell(0);

                    if (cell0.getText().equals("Funcionalidad")){
                        XWPFTableCell cell1 = tbl.getRow(1).getCell(0);
                        lista.add(cell1.getText());

                        if (!cell1.getText().equals(funcion)) {
                            XWPFTable tableF = document.createTable(); //table funcionalidad
                            utils.clone(FuncTable, tableF);
                            utils.add(tableF, funcion, 1, 0);
                        }
                        break;
                    }
                }
                /*if (!lista.contains(funcion)){
                    XWPFTable tableF = document.createTable(); //table funcionalidad
                    utils.clone(FuncTable, tableF);
                    utils.add(tableF, funcion, 1, 0);
                }*/
            }else{
                XWPFTable tableF = document.createTable(); //table funcionalidad
                utils.clone(FuncTable, tableF);
                utils.add(tableF, funcion, 1, 0);
            }
            contentTable(utils, BaseTable, status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void contentTable(Utils utils, XWPFTable BaseTable, String status){
        //TestCase
        XWPFTable table = document.createTable(); //table testcase
        utils.clone(BaseTable, table);

        //Contenido TestCase
        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setBold(true);
        utils.replace(table,"${CodeProject}", this.data.get("CodeProject"));

        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setBold(false);
        utils.replace(table,"${TesterProject}", this.data.get("TesterProject"));

        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setColor("000080");
        utils.setBold(true);
        utils.replace(table,"${DateProject}", this.data.get("DateProject"));

        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setBold(true);
        utils.replace(table,"${StatusProject}", status);

        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setBold(false);
        utils.setStyle("#CDP");
        String description = this.data.get("CodeProject") + " - " + this.data.get("DescriptionProject");
        utils.add(table, description, 6 - 3);

        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setBold(false);
        utils.add(table, this.dataTest.toString(), 8 - 3);

        utils.setFontFamily("Arial");
        utils.setFontSize(11);
        utils.setText(table, 10 - 3, 0, ListText);

        //setImage
        utils.replaceImg(table, ListImage, 10 - 3, 0);
        //setJSON
        utils.replace(table, ListJSON, 10 - 3, 0);

        //Salto de Pagina
        document.createParagraph().createRun().addBreak();
        //document.createParagraph().setPageBreak(true);
    }

    public void tableOfContent(){
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (StringUtils.contains(paragraph.getText(), "CASOS DE PRUEBAS")) {
                CTP ctP = paragraph.getCTP();
                CTSimpleField toc = ctP.addNewFldSimple();
                toc.setInstr("TOC \\h \\z \\t\"#CDP;3;Módulo;1;Funcionalidad;2\"");
                toc.setDirty(STOnOff1.ON);
            }
        }
    }

    public void addCellTableCase(){
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (StringUtils.contains(paragraph.getText(), "DATOS GENERALES")) {
                paragraph.setPageBreak(true);
            }
        }
    }

    public void onWordFinish(){
        addCellTableCase();
        tableOfContent();
        String file = "reporte-"+ generarSecuencia() + ".docx";
        try {
            FileOutputStream out = new FileOutputStream(SAVE_FILE + "all/" + file);
            document.write(out);
            out.close();
            document.close();
        } catch (Exception e) {
            System.out.println("Problemas al ubicar la ruta del archivo");
        }
    }

    public void onWordFinish(Scenario scenario, String estado){
        addCellTableCase();
        tableOfContent();
        String file = scenario.getName() +"-"+ generarSecuencia() + estado + ".docx";
        try {
            FileOutputStream out = new FileOutputStream(SAVE_FILE+"single/"+ file);
            document.write(out);
            out.close();
            document.close();
        } catch (Exception e) {
            System.out.println("Problemas al ubicar la ruta del archivo");
        }
    }
}

class Utils {
    private XWPFDocument document;
    private String FontFamily = "Arial";
    private String Style;
    private boolean Bold = false;
    private int FontSize = 11;
    private String Color;

    public Utils(XWPFDocument document) { this.document = document; }
    public String getFontFamily() { return FontFamily; }

    public String getStyle() { return Style; }
    public boolean getBold() { return Bold; }
    public int getFontSize() { return FontSize; }
    public String getColor() { return Color; }

    public void setFontFamily(String fontFamily) { FontFamily = fontFamily; }
    public void setBold(boolean bold) { Bold = bold; }
    public void setFontSize(int fontSize) { FontSize = fontSize; }
    public void setColor(String color) { Color = color; }
    public void setStyle(String style) { Style = style; }

    public void replaceImg(XWPFTable tbl, List<Map<String, String>> img, int row, int col) {
        XWPFTableRow rows = tbl.getRow(row);
        XWPFTableCell cells = rows.getCell(col);
        List<XWPFRun> runs = cells.getParagraphs().get(0).getRuns();

        for (int j = 0; j < img.size(); j++) {
            String temp = "${Image"+j+"}";
            String path = img.get(j).get(temp);
            int Width = Integer.parseInt(img.get(j).get("${Image-Width-"+j+"}"));
            int Height = Integer.parseInt(img.get(j).get("${Image-Height-"+j+"}"));

            for (int i = 0; i < runs.size(); i++) {
                String text = runs.get(i).text().trim();

                if (text.equals(temp)){
                    XWPFParagraph paragraph = runs.get(i).getParagraph();
                    paragraph.removeRun(i);

                    XWPFRun newRun = paragraph.insertNewRun(i);
                    PropertiesFile pf = new PropertiesFile();
                    Properties properties = pf.getProperty();
                    int Format = Integer.parseInt(properties.getProperty("report.image.format"));

                    InputStream inputStream = null;
                    try {
                        File file = new File(path);
                        inputStream = new FileInputStream(file);

                        newRun.getParagraph().setAlignment(ParagraphAlignment.CENTER);

                        newRun.addPicture(inputStream, Format, "1", Units.toEMU(Width), Units.toEMU(Height));

                        newRun.addBreak();
                        //newRun.addBreak();

                        newRun.getParagraph().setAlignment(ParagraphAlignment.LEFT);
                    } catch (IOException | InvalidFormatException e) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        }
    }

    public void replace(XWPFTable tbl, List<Map<String, String>> json, int row, int col) {
        XWPFTableRow rows = tbl.getRow(row);
        XWPFTableCell cells = rows.getCell(col);
        List<XWPFRun> runs = cells.getParagraphs().get(0).getRuns();

        for (int j = 0; j < json.size(); j++) {
            String temp = "${JSON"+j+"}";
            String path = json.get(j).get(temp);

            for (int i = 0; i < runs.size(); i++) {
                String text = runs.get(i).text().trim();

                if (text.equals(temp)){
                    XWPFParagraph paragraph = runs.get(i).getParagraph();
                    paragraph.removeRun(i);

                    String[] lines = path.split("\n");
                    for (int x = 0; x < lines.length; x++) {
                        String line = lines[x];
                        XWPFRun newRun = paragraph.insertNewRun(i + x);
                        newRun.setText(line, 0);
                        newRun.setFontSize(getFontSize());
                        newRun.setFontFamily(getFontFamily());
                        if (getStyle() != null) {
                            newRun.setStyle(getStyle());
                            this.setStyle(null);
                        }
                        newRun.addBreak();

                        if (x == lines.length - 1) newRun.addBreak();
                    }
                }
            }
        }
    }

    public void replace(String searchValue, String replacement) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (StringUtils.contains(paragraph.getText(), searchValue)) {
                String replacedText = StringUtils.replace(paragraph.getText(), searchValue, replacement);

                int size = paragraph.getRuns().size();
                for (int i = 0; i < size; i++) {
                    paragraph.removeRun(0);
                }

                String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n");

                for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
                    String part = replacementTextSplitOnCarriageReturn[j];

                    XWPFRun newRun = paragraph.insertNewRun(j);
                    newRun.setText(part);
                    if (getFontFamily() != null){
                        newRun.setFontFamily(getFontFamily());
                        this.setFontFamily(null);
                    }

                    if (getFontSize() != 0){
                        newRun.setFontSize(getFontSize());
                        this.setFontSize(0);
                    }

                    if (getColor() != null) {
                        newRun.setColor(getColor());
                    }

                    if (getStyle() != null) {
                        newRun.setStyle(getStyle());
                        this.setStyle(null);
                    }

                    newRun.setBold(getBold());
                    this.setBold(false);

                    if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
                        newRun.addCarriageReturn();
                    }
                }
            }
        }
    }

    public void replace(XWPFTable tbl, String searchValue, String replacement) {
        for (XWPFTableRow row : tbl.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    if (StringUtils.contains(paragraph.getText(), searchValue)) {
                        String replacedText = StringUtils.replace(paragraph.getText(), searchValue, replacement);

                        int size = paragraph.getRuns().size();
                        for (int i = 0; i < size; i++) {
                            paragraph.removeRun(0);
                        }

                        String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n");

                        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
                            String part = replacementTextSplitOnCarriageReturn[j];

                            XWPFRun newRun = paragraph.insertNewRun(j);
                            newRun.setText(part);

                            if (getFontFamily() != null){
                                newRun.setFontFamily(getFontFamily());
                                this.setFontFamily(null);
                            }

                            if (getFontSize() != 0){
                                newRun.setFontSize(getFontSize());
                                this.setFontSize(0);
                            }

                            if (getColor() != null) {
                                newRun.setColor(getColor());
                                this.setColor(null);
                            }

                            if (getStyle() != null) {
                                newRun.setStyle(getStyle());
                                this.setStyle(null);
                            }

                            newRun.setBold(getBold());
                            this.setBold(false);

                            if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
                                newRun.addCarriageReturn();
                            }
                        }
                    }
                }
            }
        }
    }

    public void clone(XWPFTable source, XWPFTable target) {
        target.getCTTbl().setTblPr(source.getCTTbl().getTblPr());
        target.getCTTbl().setTblGrid(source.getCTTbl().getTblGrid());
        for (int r = 0; r<source.getRows().size(); r++) {
            XWPFTableRow targetRow = target.createRow();
            XWPFTableRow row = source.getRows().get(r);
            targetRow.getCtRow().setTrPr(row.getCtRow().getTrPr());
            for (int c=0; c<row.getTableCells().size(); c++) {
                //newly created row has 1 cell
                XWPFTableCell targetCell = c==0 ? targetRow.getTableCells().get(0) : targetRow.createCell();
                XWPFTableCell cell = row.getTableCells().get(c);
                targetCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
                XmlCursor cursor = targetCell.getParagraphArray(0).getCTP().newCursor();
                for (int p = 0; p < cell.getBodyElements().size(); p++) {
                    IBodyElement elem = cell.getBodyElements().get(p);
                    if (elem instanceof XWPFParagraph) {
                        XWPFParagraph targetPar = targetCell.insertNewParagraph(cursor);
                        cursor.toNextToken();
                        XWPFParagraph par = (XWPFParagraph) elem;
                        clone(par, targetPar);
                    } else if (elem instanceof XWPFTable) {
                        XWPFTable targetTable = targetCell.insertNewTbl(cursor);
                        XWPFTable table = (XWPFTable) elem;
                        clone(table, targetTable);
                        cursor.toNextToken();
                    }
                }
                //newly created cell has one default paragraph we need to remove
                targetCell.removeParagraph(targetCell.getParagraphs().size()-1);
            }
        }
        //newly created table has one row by default. we need to remove the default row.
        target.removeRow(0);
    }

    public void clone(XWPFParagraph source, XWPFParagraph target) {
        target.getCTP().setPPr(source.getCTP().getPPr());
        for (int i=0; i<source.getRuns().size(); i++ ) {
            XWPFRun run = source.getRuns().get(i);
            XWPFRun targetRun = target.createRun();
            //copy formatting
            targetRun.getCTR().setRPr(run.getCTR().getRPr());
            //no images just copy text
            targetRun.setText(run.getText(0));
        }
    }

    public void add(XWPFTable table, String contenido, int pos) {
        XWPFTableRow ContenidoRowTemplate = table.getRow(pos);
        if (ContenidoRowTemplate == null) try {
            throw new Exception("Table template does not match: No title row.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (ContenidoRowTemplate.getTableCells().size() != 1) try {
            throw new Exception("Table template does not match: Wrong title row column count.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        XWPFTableRow ContenidoRow = ContenidoRowTemplate;
        XWPFTableCell cell;

        for (int i = 0; i < 1; i++) {
            if (i > 0) {
                ContenidoRow = new XWPFTableRow((CTRow)ContenidoRowTemplate.getCtRow().copy(), table);
            }
            cell = ContenidoRow.getCell(0);
            setText(cell, contenido);

            if (i > 0) {
                table.addRow(ContenidoRow);
            }
        }
    }

    public void add(XWPFTable table, String contenido, int row, int col) {
        XWPFTableRow ContenidoRowTemplate = table.getRow(row);
        if (ContenidoRowTemplate == null) try {
            throw new Exception("Table template does not match: No title row.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
        if (ContenidoRowTemplate.getTableCells().size() != row) try {
            throw new Exception("Table template does not match: Wrong title row column count.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        XWPFTableRow ContenidoRow = ContenidoRowTemplate;
        XWPFTableCell cell;

        for (int i = 0; i < 1; i++) {
            if (i > 0) {
                ContenidoRow = new XWPFTableRow((CTRow)ContenidoRowTemplate.getCtRow().copy(), table);
            }
            cell = ContenidoRow.getCell(col);
            setText(cell, contenido);

            if (i > 0) {
                table.addRow(ContenidoRow);
            }
        }
    }

    public void setText(XWPFTableCell cell, String text) {
        String[] lines = text.split("\n");
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            XWPFParagraph paragraph = null;
            if (paragraphs.size() > i) paragraph = paragraphs.get(i);
            if (paragraph == null) paragraph = cell.addParagraph();
            XWPFRun run = null;
            if (paragraph.getRuns().size() > 0) run = paragraph.getRuns().get(0);
            if (run == null) run = paragraph.createRun();

            run.setText(line, 0);
            run.setFontSize(getFontSize());
            run.setFontFamily(getFontFamily());

            if (getStyle() != null) {
                run.setStyle(getStyle());
                this.setStyle(null);
            }
        }

        for (int i = paragraphs.size()-1; i >= lines.length; i--) {
            cell.removeParagraph(i);
        }
    }

    public void setText(XWPFTable table, int row, int col, List<Map<String, String>> text) {
        XWPFTableRow rows = table.getRow(row);
        XWPFTableCell cell = rows.getCell(col);

        for (int i = 0; i < text.size(); i++) {
            String texto = text.get(i).get("text");
            boolean bold = Boolean.parseBoolean(text.get(i).get("bold"));
            cell.setText("");

            List<XWPFRun> runs = cell.getParagraphs().get(0).getRuns();
            runs.get(runs.size()-1).setFontFamily(getFontFamily());
            runs.get(runs.size()-1).setFontSize(getFontSize());
            runs.get(runs.size()-1).setBold(bold);
            runs.get(runs.size()-1).setText(texto, 0);
            runs.get(runs.size()-1).addBreak();
        }
    }
}