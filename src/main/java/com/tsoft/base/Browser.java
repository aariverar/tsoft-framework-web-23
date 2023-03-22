package com.tsoft.base;

import com.tsoft.helpers.Hook;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Browser {

    protected WebDriver driver = Hook.getDriver();
    private final int DURATION = 10;

    protected WebElement element(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION));
        return wait.until(driver1 -> driver.findElement(locator));
    }

    protected void click(By locator) {
        Utils.println("[Click] - " + locator);
        try {
            element(locator).click();
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void clear(By locator) {
        try {
            Utils.println("[Clear] - " + locator);
            element(locator).clear();
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void typeText(By locator , String inputText) {
        try {
            Utils.println("[TypeText] - " + locator + " => " + inputText);
            element(locator).sendKeys(inputText);
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected String getValue(By locator) {
        try {
            Utils.println("[GetValue] - " + locator);
            return element(locator).getAttribute("value");
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected String getAttribute(By locator, String attribute) {
        try {
            Utils.println("[GetAttribute] - " + locator);
            return element(locator).getAttribute(attribute);
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected String getText(By locator) {
        try {
            Utils.println("[GetText] - " + locator);
            return element(locator).getText();
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
            return null;
        }
    }

    protected Boolean isDisplayed(By locator){
        Utils.println("[isDisplayed] - " + locator);
        try {
            boolean is = element(locator).isDisplayed();
            Utils.println("[isDisplayed] - " + locator + " => " + is);
            return is;
        }catch (RuntimeException we){
            Utils.println("[isDisplayed] - " + locator + " => " + false);
            return false;
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        try {
            Utils.println("[SelectByVisibleText] - " + locator+ " => " + text);
            Select typeSelect = new Select(element(locator));
            typeSelect.selectByVisibleText(text);
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void selectByValue(By locator, String text) {
        try {
            Utils.println("[selectByValue] - " + locator+ " => " + text);
            Select typeSelect = new Select(element(locator));
            typeSelect.selectByValue(text);
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void scrollByVisibleElement(By locator) {
        try {
            Utils.println("[ScrollByVisibleElement] - " + locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element(locator));
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void sleep(Double seg) {
        Utils.println("[Sleep] - " + seg + " seg");
        try {
            if (seg <= 0) return;
            Thread.sleep((long) (seg * 1000));
        } catch (InterruptedException we) {
            Thread.currentThread().interrupt();
        }
    }

    protected void sleep(int seg) {
        Utils.println("[Sleep] - " + seg + " seg");
        try {
            if (seg <= 0) return;
            Thread.sleep(seg * 1000);
        } catch (InterruptedException we) {
            Thread.currentThread().interrupt();
        }
    }

    protected void frameContent(By locator){
        try {
            Utils.println("[FrameContent] - " + locator);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(element(locator));
        } catch (RuntimeException we) {
            ErrorInteraction(String.valueOf(locator));
        }
    }

    protected void scroll(int x, int y) {
        try {
            Utils.println("[Scroll] - X: " + x + " => Y: " + y);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected void zoom(int size) {
        try {
            Utils.println("[Zoom] - size" + size);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom = '" + size + "%'");
        } catch (RuntimeException we) {
            ErrorInteraction(we.getMessage());
        }
    }

    protected String script(String script){
        Utils.println("[Script] - " + script);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script).toString();
    }

    protected boolean loadPage(String url) {
        boolean response = false;
        Utils.println("[LoadPage] - " + url);
        try {
            if (StringUtils.isNotEmpty(url)) {
                driver.get(url);
                Report.onPass(null, "Cargo correctamente la URL: '"+url.replaceAll("https://", "")+"'");
                sleep(0.5);
                response = true;
            } else {
                Report.onFail(driver, "Error al cargar la página, NO existe el parámetro URL del aplicativo ");
            }
        } catch (NoSuchWindowException | NoSuchSessionException e ){
            Report.onFail(null, "[Error al cargar Browser] : Navegador se cerro inesperandamente : " + e.getMessage());
            driver.close();
        } catch ( Throwable t ) {
            Report.onFail(driver, "[Error al cargar Browser] : " + t.getMessage());
            driver.close();
        }
        return response;
    }

    protected void isPresent(By locator, int Seg) {
        Utils.println("[isPresent] - " + locator + " => " + Seg);
        sleep(2);
        for (int i = 1; i <= Seg; i++) {
            sleep(1);
            boolean response = isDisplayed(locator);
            if (response){
                break;
            }
            Utils.println("Validando :" + locator + " "+i + " isPresent: " + response);
        }
    }

    public boolean isElementPresent(By locator) {
        Utils.println("[IsElementPresent] => Pendiente - " + locator);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION + 5));
        List<WebElement> e = wait.until(driver1 -> driver.findElements(locator));

        if (e.size() == 0) {
            Utils.println("[IsElementPresent] => " + false);
            return false;
        } else {
            Utils.println("[IsElementPresent] => " + true);
            return true;
        }
    }

    public boolean isElementPresent(By locator, int sec) {
        Utils.println("[IsElementPresent] => Pendiente - " + locator);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        List<WebElement> e = wait.until(driver1 -> driver.findElements(locator));

        if (e.size() == 0) {
            Utils.println("[IsElementPresent] => " + false);
            return false;
        } else {
            Utils.println("[IsElementPresent] => " + true);
            return true;
        }
    }

    private void ErrorInteraction(String description) {
        Report.onFail(driver, "Error Interaction : " + description);
    }

    //SelectReact
    protected void selectByTextReact(By locator, String text) {
        try {
            Utils.println("[SelectByTextReact] - " + locator + " => "+text);
            click(locator);

            WebElement option= element(By.xpath("//*[text()='"+text+"']"));
            Utils.println("Long e: " + option.getText());

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", option);
            option.click();
        }catch (RuntimeException we){
            ErrorInteraction(String.valueOf(we.getMessage()));
        }
    }

    //Select2
    private String ITEMS_SELECTOR = "//ul[@class='select2-results']//li//span";
    protected void select2ByVisibleText(By locator, String value) {
        Utils.println("[Select2ByVisibleText] - " + locator + " => "+value);
        click(locator);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION + 5));
        List<WebElement> item = wait.until(driver1 -> driver.findElements(By.xpath(ITEMS_SELECTOR)));
        for (WebElement i : item){
            if (i.getText().equalsIgnoreCase(value)){
                i.click();
                break;
            }
        }
    }

    protected void select2BySearchText(By locator, String value) {
        Utils.println("[Select2BySearchText] - " + locator + " => "+value);
        click(locator);

        WebElement search = element(By.cssSelector("#select2-drop:not([style*='display: none']) .select2-input"));
        search.sendKeys(value);
        sleep(0.5);
        List<WebElement> item = driver.findElements(By.xpath(ITEMS_SELECTOR));
        for (WebElement i : item){
            if (i.getText().equalsIgnoreCase(value)){
                i.click();
                break;
            }
        }
    }
}
