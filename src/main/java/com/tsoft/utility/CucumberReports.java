package com.tsoft.utility;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CucumberReports {

    public static void main(String[] args) {
        generateCucumberReport();
    }

    public static void generateCucumberReport() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber.json");

        Configuration configuration = new Configuration(reportOutputDirectory, "Selenium WebDriver");

        configuration.addClassifications("Plataforma", "Windows");
        configuration.addClassifications("Navegador", "-");
        configuration.addClassifications("Versión del navegador", "-");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();
    }
}