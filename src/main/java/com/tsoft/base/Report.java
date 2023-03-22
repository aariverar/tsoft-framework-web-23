package com.tsoft.base;

import com.tsoft.helpers.Listener;
import com.tsoft.utility.ExcelHandler;
import com.tsoft.utility.ExtentReport;
import com.tsoft.utility.PropertiesFile;
import com.tsoft.utility.WordReport;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Report {

    public static final WordReport wreport = new WordReport();
    public static final ExtentReport ereport = new ExtentReport();
    public static String onWordMode;

    public static void onWordReport(Map<String, String> data){
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();

        String ReportDoc = properties.getProperty("report.doc");
        String ReportSheet = properties.getProperty("report.sheet");
        String ColKey = properties.getProperty("report.colkey");
        String ColVal = properties.getProperty("report.colval");

        String ReportTitle = properties.getProperty("report.title");
        String TestCode = properties.getProperty("report.test.code");
        String TestDesc = properties.getProperty("report.test.desc");
        String TestFunc = properties.getProperty("report.test.func");
        String TestUser = System.getProperty("user.name");
        String TestDate = Utils.generarFecha() + " " + Utils.generarHora();

        TestCode = data.get(TestCode).trim();
        TestDesc = data.get(TestDesc).trim();
        TestFunc = data.get(TestFunc);

        Map<String, String> word = new HashMap<>();
        if (!ReportDoc.isEmpty()){
            ExcelHandler eh = new ExcelHandler();
            List<Map<String, String>> dataReport = eh.getTestData(ReportDoc, ReportSheet);
            String DGReportTitle = dataReport.get(1 - 1).get(ColVal);
            String DG1 = dataReport.get(2 - 1).get(ColVal);
            String DG2 = dataReport.get(3 - 1).get(ColVal);
            String DG3 = dataReport.get(4 - 1).get(ColVal);
            String DG4 = dataReport.get(5 - 1).get(ColVal);
            String DG5 = dataReport.get(6 - 1).get(ColVal);

            if (!DGReportTitle.isEmpty())  ReportTitle = DGReportTitle;
            if (DG2.isEmpty()) DG2 = ReportTitle;
            if (DG3.isEmpty()) DG3 = TestUser;
            if (DG4.isEmpty()) DG4 = Utils.generarFecha();

            word.put("DG1", DG1);
            word.put("DG2", DG2);
            word.put("DG3", DG3);
            word.put("DG4", DG4);
            word.put("DG5", DG5);
            word.put("TesterProject", DG3);
        }
        word.put("TitleProject", ReportTitle);
        word.put("CodeProject", TestCode);
        word.put("DateProject", TestDate);
        word.put("FunctionProject", TestFunc);
        word.put("DescriptionProject", TestDesc);

        System.out.println("TestCode: " + TestCode);
        System.out.println("Secuencia: " + TestDate);
        System.out.println("Descripción: " + TestDesc);

        wreport.onWordLoad(word, data);
        Listener.wreporte.onWordLoad(word, data);
        onWordMode(null);
    }

    public static void onWordMode(String mode){
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();
        String ImageModel = properties.getProperty("report.image.mode");
        if (mode != null){
            onWordMode = mode;
        }else{
            onWordMode = ImageModel;
        }
        Utils.println("[onWordMode] - " + onWordMode);
    }

    public static void onWordAddLine(String newLine){
        wreport.onAddLine(newLine, false);
        Listener.wreporte.onAddLine(newLine, false);
    }

    public static void onWordAddLine(String newLine, boolean bold){
        wreport.onAddLine(newLine, bold);
        Listener.wreporte.onAddLine(newLine, bold);
    }

    public static void onWordAddBreak(){
        Utils.println("[onWordAddBreak]");
        wreport.onAddBreak();
        Listener.wreporte.onAddBreak();
    }

    public static void onWordAddImage(WebDriver driver){
        Utils.println("[onWordAddImage] - " + true);
        wreport.onAddImage(driver);
        Listener.wreporte.onAddImage(driver);
    }

    public static void appendData(String data){
        Utils.println("[appendData] - " + data);
        wreport.appendData(data);
        Listener.wreporte.appendData(data);
    }

    public static void appendData(JSONObject data){
        Utils.println("[appendData] - " + data);
        wreport.appendData(data);
        Listener.wreporte.appendData(data);
    }

    public static void onStep(String key, String description){
        Utils.println("[onStep] - "+key+ " "+description);
        try {
            ereport.onStep(key, description);
            onWordAddLine(key+ " "+description, true);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onPass(WebDriver driver, String description){
        Utils.println("[onPass] - "+description);
        Listener.status = true;
        try {
            ereport.onPass(driver, description + " - Pass");
            onWordAddLine(description);
            //onWordAddImage(driver);
           if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onPass(WebDriver driver, String description, String value){
        Utils.println("[onPass] - "+description);
        Listener.status = true;
        try {
            ereport.onPass(driver, description + " " + value + " - Pass");
            onWordAddLine(description);
            onWordAddLine(value);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onFail(WebDriver driver, String description){
        Utils.println("[onFail] - "+description);
        Listener.status = false;
        try {
            ereport.onFail(driver, description + " - Fail");
            onWordAddLine(description);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onFail(WebDriver driver, String description, String value){
        Utils.println("[onFail] - "+description);
        Listener.status = true;
        try {
            ereport.onFail(driver, description + " " + value + " - Pass");
            onWordAddLine(description);
            onWordAddLine(value);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onWarning(WebDriver driver, String description){
        Utils.println("[onWarning] - "+description);
        Listener.status = false;
        try {
            ereport.onWarning(driver, description + " - Waning");
            onWordAddLine(description);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onWarning(WebDriver driver, String description, String value){
        Utils.println("[onWarning] - "+description);
        Listener.status = true;
        try {
            ereport.onWarning(driver, description + " " + value + " - Pass");
            onWordAddLine(description);
            onWordAddLine(value);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onInfo(WebDriver driver, String description){
        Utils.println("[onInfo] - "+description);
        Listener.status = true;
        try {
            ereport.onInfo(driver, description + " - Info");
            onWordAddLine(description);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onInfo(WebDriver driver, String description, String value){
        Utils.println("[onInfo] - "+description);
        Listener.status = true;
        try {
            ereport.onInfo(driver, description + " " + value + " - Pass");
            onWordAddLine(description);
            onWordAddLine(value);
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onDesc(WebDriver driver, String description){
        Utils.println("[onDesc] - "+description);
        Listener.status = true;
        try {
            ereport.onInfo(driver, description + " - Descripción");
            if (driver != null) onWordAddImage(driver);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
