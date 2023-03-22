package com.mibanco.utility;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentLoggerReporter;
import com.mibanco.base.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ExtentReport {

    private final boolean multithread;
    private final ExtentReports extent;
    private Map<String, ExtentTest> threadTestMap;
    private String currentReportName;
    private String dir = System.getProperty("user.dir");
    boolean onKlov = false;
    private KlovReport kreport;

    public ExtentReport() {
        // spark
        ExtentHtmlReporter spark = new ExtentHtmlReporter(dir + "/target/resultado/reporte-"+Utils.generarSecuencia()+".html");
        spark.loadXMLConfig("src/main/resources/settings/html-config.xml");

        ExtentLoggerReporter logger = new ExtentLoggerReporter(dir + "/target/resultado/reporte-logger/");
        logger.loadXMLConfig("src/main/resources/settings/html-config.xml");

        extent = new ExtentReports();

        extent.attachReporter(spark, logger);
        extent.setAnalysisStrategy(AnalysisStrategy.BDD);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        // por default
        multithread = false;
        currentReportName = StringUtils.EMPTY;

        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();
        onKlov = Boolean.parseBoolean(properties.getProperty("klov.report"));

        if (onKlov) kreport = new KlovReport();
    }

    ExtentTest scenario = null;
    public void onTestStart(String nombreCaso) throws Throwable {
        String threadTestID = multithread ? getCurrentThreadID() : nombreCaso;
        currentReportName = nombreCaso;
        ExtentTest feature = extent.createTest(new GherkinKeyword("Feature"), nombreCaso, StringUtils.EMPTY);
        scenario = feature.createNode(new GherkinKeyword("Scenario"), nombreCaso, StringUtils.EMPTY);

        String carpetaCreada = StringHelper.createFileNameFixed(threadTestID, 10);
        String carpeta = dir + "/target/resultado/img/" + carpetaCreada;
        FileUtils.forceMkdir(new File(carpeta));

        if (onKlov) kreport.onTestStart(nombreCaso);
    }

    public void onStep(String key,String description) throws Throwable {
        description = key.equals("") ? description : "<b>"+key+"</b>" + description;
        key = key.equals("") ? "And" : key;

        ExtentTest logInfo = scenario.createNode(new GherkinKeyword(key), description);
        getThreadTestMap().put(currentReportName, logInfo);

        if (onKlov) kreport.onStep(key, description);
    }

    public void onPass(WebDriver driver, String description) throws Throwable {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String nombreFichero = "sec" + generarSecuencia() + "-" + StringHelper.createFileNameFixed(description, 10) + ".png";
            String carpetaCreada = getCarpetaGenerada();
            String ficheroGeneradoPath = takeScreenShot(driver, carpetaCreada, nombreFichero);
            getCurrentTest().pass(description, MediaEntityBuilder.createScreenCaptureFromPath(ficheroGeneradoPath).build());
            getCurrentTest().pass(m).addScreenCaptureFromPath(ficheroGeneradoPath);
        }

        if (driver == null){
            getCurrentTest().pass(m);
        }

        if (onKlov) kreport.onPass(driver, description);
    }

    public void onFail(WebDriver driver, String description) throws Throwable {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String nombreFichero = "sec" + generarSecuencia() + "-" + StringHelper.createFileNameFixed(description, 10) + ".png";
            String carpetaCreada = getCarpetaGenerada();
            String ficheroGeneradoPath = takeScreenShot(driver, carpetaCreada, nombreFichero);
            getCurrentTest().fail(m).addScreenCaptureFromPath(ficheroGeneradoPath);
        }

        if (driver == null){
            getCurrentTest().fail(m);
        }

        if (onKlov) kreport.onFail(driver, description);
    }

    public void onWarning(WebDriver driver, String description) throws Throwable {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String nombreFichero = "sec" + generarSecuencia() + "-" + StringHelper.createFileNameFixed(description, 10) + ".png";
            String carpetaCreada = getCarpetaGenerada();
            String ficheroGeneradoPath = takeScreenShot(driver, carpetaCreada, nombreFichero);
            getCurrentTest().warning(m).addScreenCaptureFromPath(ficheroGeneradoPath);
        }

        if (driver == null){
            getCurrentTest().warning(m);
        }

        if (onKlov) kreport.onWarning(driver, description);
    }

    public void onInfo(WebDriver driver, String description) throws Throwable {
        Markup m = MarkupHelper.createCodeBlock(description);

        if (driver != null){
            String nombreFichero = "sec" + generarSecuencia() + "-" + StringHelper.createFileNameFixed(description, 10) + ".png";
            String carpetaCreada = getCarpetaGenerada();
            String ficheroGeneradoPath = takeScreenShot(driver, carpetaCreada, nombreFichero);
            getCurrentTest().info(m).addScreenCaptureFromPath(ficheroGeneradoPath);
        }

        if (driver == null){
            getCurrentTest().info(m);
        }

        if (onKlov) kreport.onInfo(driver, description);
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

    private String takeScreenShot(WebDriver driver, String nombreCarpeta, String nombreFichero) throws IOException {
        String carpeta = dir + "/target/resultado/img/" + StringUtils.trimToEmpty(nombreCarpeta);
        FileUtils.forceMkdir(new File(carpeta));
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String rutaArchivoGenerado = carpeta + "/" + nombreFichero;
        FileUtils.copyFile(source, new File(rutaArchivoGenerado));
        return "./img/" + StringUtils.trimToEmpty(nombreCarpeta) + "/" + nombreFichero;
    }

    public void onFinish(String status) {
        try {
            onStep("", "Estado de la Ejecución");
            if (status.equals("PASSED")){
                onPass(null,"Passed");
            }else{
                onFail(null, "Failed");
            }
            extent.flush();


        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (onKlov) kreport.onFinish(status);
    }

}
