package com.tsoft.utility;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.mongodb.MongoClientURI;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class KlovReport {

    private final boolean multithread;
    private final ExtentReports extent;
    private Map<String, ExtentTest> threadTestMap;
    private String currentReportName;
    private final String dir = System.getProperty("user.dir");

    public KlovReport() {
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();

        String NameReport = properties.getProperty("report.title");
        String mongoUri = properties.getProperty("mongodb.uri");
        String klovHost = properties.getProperty("klov.host");
        String klovPort = properties.getProperty("klov.port");

        ExtentKlovReporter klov = new ExtentKlovReporter();
        //klov.setReportName(NameReport);
        klov.setProjectName(NameReport);

        MongoClientURI uri = new MongoClientURI(mongoUri);
        klov.initMongoDbConnection(uri);
        klov.initKlovServerConnection(klovHost + "/" + klovPort);
        extent = new ExtentReports();

        extent.attachReporter(klov);
        extent.setAnalysisStrategy(AnalysisStrategy.BDD);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        // por default
        multithread = false;
        currentReportName = StringUtils.EMPTY;
    }

    ExtentTest scenario = null;
    public void onTestStart(String nombreCaso) throws Throwable {
        currentReportName = nombreCaso;
        ExtentTest feature = extent.createTest(new GherkinKeyword("Feature"), nombreCaso, StringUtils.EMPTY);
        scenario = feature.createNode(new GherkinKeyword("Scenario"), nombreCaso, StringUtils.EMPTY);
    }

    public void onStep(String key,String description) throws Throwable {
        key = key.equals("") ? "And" : key;

        ExtentTest logInfo = scenario.createNode(new GherkinKeyword(key), description);
        getThreadTestMap().put(currentReportName, logInfo);
    }

    public void onPass(WebDriver driver, String description) {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String ficheroGeneradoPath = takeScreenShot(driver);
            MediaEntityBuilder media = null;
            try {
                media = MediaEntityBuilder.createScreenCaptureFromBase64String(ficheroGeneradoPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            getCurrentTest().pass("<textarea disabled class='code-block'>"+description+"</textarea>", media.build());
        }

        if (driver == null){
            getCurrentTest().pass(m);
        }
    }

    public void onFail(WebDriver driver, String description) {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String ficheroGeneradoPath = takeScreenShot(driver);
            MediaEntityBuilder media = null;
            try {
                media = MediaEntityBuilder.createScreenCaptureFromBase64String(ficheroGeneradoPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            getCurrentTest().fail("<textarea disabled class='code-block'>"+description+"</textarea>", media.build());
        }

        if (driver == null){
            getCurrentTest().fail(m);
        }
    }

    public void onWarning(WebDriver driver, String description) {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String ficheroGeneradoPath = takeScreenShot(driver);
            MediaEntityBuilder media = null;
            try {
                media = MediaEntityBuilder.createScreenCaptureFromBase64String(ficheroGeneradoPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            getCurrentTest().warning("<textarea disabled class='code-block'>"+description+"</textarea>", media.build());
        }

        if (driver == null){
            getCurrentTest().warning(m);
        }
    }

    public void onInfo(WebDriver driver, String description) {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String ficheroGeneradoPath = takeScreenShot(driver);
            MediaEntityBuilder media = null;
            try {
                media = MediaEntityBuilder.createScreenCaptureFromBase64String(ficheroGeneradoPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            getCurrentTest().info("<textarea disabled class='code-block'>"+description+"</textarea>", media.build());
        }

        if (driver == null){
            getCurrentTest().info(m);
        }
    }

    private String getCurrentThreadID() {
        return Thread.currentThread().getName();
    }

    private String generarSecuencia() {
        return new SimpleDateFormat("MMddHHmmssSSSS").format(new Date());
    }

    private Map<String, ExtentTest> getThreadTestMap() {
        if (threadTestMap == null) {
            threadTestMap = new LinkedHashMap<>();
        }
        return threadTestMap;
    }

    private String getCarpetaGenerada() {
        if (multithread) {
            return StringHelper.createFileNameFixed(getCurrentThreadID(), 10);
        }
        return StringHelper.createFileNameFixed(currentReportName, 10);
    }

    private ExtentTest getCurrentTest() {
        if (multithread) {
            return getThreadTestMap().get(getCurrentThreadID());
        }
        return getThreadTestMap().get(currentReportName);
    }

    private String takeScreenShot(WebDriver driver) {
        try {
            File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileInputStream fis = new FileInputStream(f);
            byte[] bytes = new byte[(int)f.length()];
            fis.read(bytes);
            String base64Img = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
            return base64Img;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onFinish(String status) {
        extent.flush();
    }

}
