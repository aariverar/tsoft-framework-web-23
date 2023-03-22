package com.mibanco.objects;

import org.openqa.selenium.By;

public class InticoObjects {
    public By username = By.id("userLogin");
    public By pass = By.id("passLogin");
    public By btn_iniciar = By.id("login");
    public By lnk_reportes = By.xpath("//li/a/p[contains(text(), 'Reportes')]");
    public By rad_reportesdia = By.xpath("//a[@href='reporte-dia']");
    public By btn_generaReporte = By.id("doReport");
    public By sms = By.xpath("//*[@id='resultado2']/tbody/tr[1]/td[2]");

}
