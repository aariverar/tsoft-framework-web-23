package com.mibanco.helpers;

import com.mibanco.utility.PropertiesFile;
import com.mibanco.utility.sqlite3Manager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Properties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import java.time.Duration;
import java.time.Instant;

public class Hook {

    private Instant startTime;
    String framework = "SELENIUM WEBDRIVER";
    private static WebDriver driver = null;
    //private static AndroidDriver appiumDriver = null;

    @Before(order = 2)
    public void BeforeScenario() {
        startTime = Instant.now();
        PropertiesFile obj = new PropertiesFile();
        Properties properties = obj.getProperty();

        String DriverBrowser = properties.getProperty("hook.driver.browser");
        String DriverCode = properties.getProperty("hook.driver.code");
        String DriverFile = properties.getProperty("hook.driver.file");

        boolean DriverOnly = Boolean.parseBoolean(String.valueOf(properties.getProperty("hook.driver.only")));
        int DELAY = Integer.parseInt(properties.getProperty("hook.delay"));
        boolean DriverHeadless = Boolean.parseBoolean(String.valueOf(properties.getProperty("hook.driver.headless")));

        boolean GridDriver = Boolean.parseBoolean((properties.getProperty("hook.grid.driver")));
        String GridUrl = properties.getProperty("hook.grid.url");

        /*String DriverMobile = properties.getProperty("hook.app.driver");
        String AppiumUrl = properties.getProperty("hook.appium.URL");
        String AppiumPN = properties.getProperty("hook.appium.PLATFORM_NAME");
        String AppiumPV = properties.getProperty("hook.appium.PLATFORM_VERSION");
        String AppiumDN = properties.getProperty("hook.appium.DEVICE_NAME");
        String AppiumUDID = properties.getProperty("hook.appium.UDID");
        String AppPackage = properties.getProperty("hook.app.package");
        String AppActivity = properties.getProperty("hook.app.activity");
        String AppPath = properties.getProperty("hook.app.path");

        String Uft1 = properties.getProperty("hook.uft.oauthClientId");
        String Uft2 = properties.getProperty("hook.uft.oauthClientSecret");
        String Uft3 = properties.getProperty("hook.uft.tenantId");
        String Uft4 = properties.getProperty("hook.uft.mcWorkspaceName");*/

        if (!DriverOnly){
            String dir = System.getProperty("user.dir") + "/src/main/resources/drivers/";
            System.setProperty(DriverCode, dir + DriverFile);
        }

        try {
            if (!GridDriver){
                switch (DriverBrowser) {
                    case "IE":
                        if (DriverOnly) {
                            WebDriverManager.iedriver().setup();
                        }
                        InternetExplorerOptions options = new InternetExplorerOptions();
                        options.introduceFlakinessByIgnoringSecurityDomains();
                        options.setCapability("requireWindowFocus", true);
                        options.setCapability("enablePersistentHover", false);
                        options.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
                        options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                        driver = new InternetExplorerDriver(options);
                        driver.manage().window().maximize();
                        break;
                    case "Chrome":
                        if (DriverOnly) {
                            WebDriverManager.chromedriver().setup();
                        }
                        System.setProperty("webdriver.chrome.silentOutput", "true");
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (DriverHeadless) chromeOptions.addArguments("--headless", "window-size=1366,768");
                        driver = new ChromeDriver(chromeOptions);
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                        break;
                    case "Firefox":
                        if (DriverOnly) {
                            WebDriverManager.firefoxdriver().setup();
                        }
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (DriverHeadless) firefoxOptions.addArguments("--headless", "window-size=1366,768");
                        driver = new FirefoxDriver(firefoxOptions);
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                        break;
                    case "Edge":
                        if (DriverOnly) {
                            WebDriverManager.edgedriver().setup();
                        }
                        System.setProperty("webdriver.edge.silentOutput", "true");
                        EdgeOptions edgeOptions = new EdgeOptions();
                        if (DriverHeadless) edgeOptions.addArguments("--headless", "window-size=1366,768");
                        driver = new EdgeDriver(edgeOptions);
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                        break;
                }
            }

            if (GridDriver){
                switch (DriverBrowser){
                    case "IE":
                        driver = new RemoteWebDriver(new URL(GridUrl), new InternetExplorerOptions());
                        driver.manage().window().maximize();
                        break;
                    case "Chrome":
                        driver = new RemoteWebDriver(new URL(GridUrl), new ChromeOptions());
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                        break;
                    case "Firefox":
                        driver = new RemoteWebDriver(new URL(GridUrl), new FirefoxOptions());
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                        break;
                    case "Edge":
                        driver = new RemoteWebDriver(new URL(GridUrl), new EdgeOptions());
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                        break;
                }
            }
            /*
            switch (DriverMobile){
                case "Appium":
                    DesiredCapabilities caps = new DesiredCapabilities();

                    if (!Uft1.isEmpty())caps.setCapability("uftm:oauthClientId", Uft1);
                    if (!Uft2.isEmpty())caps.setCapability("uftm:oauthClientSecret", Uft2);
                    if (!Uft3.isEmpty())caps.setCapability("uftm:tenantId", Uft3);
                    if (!Uft4.isEmpty()) caps.setCapability("uftm:mcWorkspaceName", Uft4);

                    caps.setCapability("unicodeKeyboard", true);
                    caps.setCapability("resetKeyboard", true);
                    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, AppiumPN);
                    caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, AppiumPV);
                    caps.setCapability(MobileCapabilityType.DEVICE_NAME, AppiumDN);
                    caps.setCapability(MobileCapabilityType.UDID, AppiumUDID);

                    if (!AppPath.isEmpty()){
                        caps.setCapability(MobileCapabilityType.APP,System.getProperty("user.dir") + "/src/main/resources/"+AppPath);
                    }

                    caps.setCapability("appPackage", AppPackage);
                    caps.setCapability("appActivity", AppActivity);
                    //caps.setCapability("automationName", "UiAutomator2");

                    URL url = new URL(AppiumUrl);

                    //appiumDriver = new AndroidDriver(url, caps);
                    //appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DELAY));
                    break;
            }*/
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @After(order = 2)
    public void AfterScenario(Scenario scenario) throws IOException, SQLException {
        if (driver != null) driver.quit();
        //if (appiumDriver != null) appiumDriver.quit();

        /*String scenarioName = scenario.getName();
        String formattedDateTime = getCurrentDateTime();
        long totalTimeInMs = getTotalTimeInMs(startTime, Instant.now());
        String status = scenario.isFailed() ? "ERROR" : "OK";
        String hostname = getHostName();
        double totalTimeInSecs = totalTimeInMs / 1000.0;
        String time = totalTimeInSecs + " seg.";

        printScenarioInfo(scenarioName, formattedDateTime, totalTimeInMs, status, framework, hostname);
        sqlite3Manager.insertData(scenarioName, formattedDateTime, time, status, framework, hostname);*/
    }
/*
    private String getCurrentDateTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(formatter);
    }

    private long getTotalTimeInMs(Instant startTime, Instant endTime) {
        Duration totalTime = Duration.between(startTime, endTime);
        return totalTime.toMillis();
    }

    private String getHostName() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostName();
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private void printScenarioInfo(String scenarioName, String formattedDateTime, long totalTimeInMs, String status, String framework, String hostname) {
        System.out.println("El nombre del escenario actual es: " + scenarioName);
        System.out.println("La fecha y hora de la ejecución es: " + formattedDateTime);
        System.out.println("El escenario ha tardado en ejecutarse " + totalTimeInMs + " ms");
        System.out.println("El estado de ejecución del escenario es: " + status);
        System.out.println("El Framework usado es: " + framework);
        System.out.println("Hostname: " + hostname);
    }*/

    public static WebDriver getDriver() { return driver; }
    //public static AndroidDriver getAppiumDriver() { return appiumDriver; }
}
