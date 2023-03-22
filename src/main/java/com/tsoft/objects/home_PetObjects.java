package com.tsoft.objects;

import org.openqa.selenium.By;

public class home_PetObjects {
    public By lnk_findOwners = By.cssSelector("a[title='find owners']");
    public By btn_findOwners = By.cssSelector("button[class='btn btn-success']");
    public By val_name1 = By.cssSelector("a[href='/owners/1']");
    public By val_name2 = By.cssSelector("a[href='/owners/2']");
    public By val_name3 = By.cssSelector("a[href='/owners/3']");
    public By val_name4 = By.cssSelector("a[href='/owners/4']");
    public By val_name5 = By.cssSelector("a[href='/owners/5']");
    public By val_name6 = By.cssSelector("a[href='/owners/6']");
    public By val_name7 = By.cssSelector("a[href='/owners/7']");
    public By val_name8 = By.cssSelector("a[href='/owners/8']");
    public By val_name9 = By.cssSelector("a[href='/owners/9']");
    public By val_name10 = By.cssSelector("a[href='/owners/10']");

    public By btn_editOwner = By.cssSelector("a[class='btn btn-success']");
    public By txt_firstName = By.id("firstName");
    public By txt_lastName = By.id("lastName");
    public By txt_address = By.id("address");
    public By txt_city = By.id("city");
    public By txt_telephone = By.id("telephone");
    public By btn_updateOwner = By.cssSelector("button[class='btn btn-success']");

    public By val_newName = By.xpath("/html/body/div/div/table[1]/tbody/tr[1]/td/b");
    public By val_newAddress = By.xpath("/html/body/div/div/table[1]/tbody/tr[2]/td");
    public By val_newCity = By.xpath("/html/body/div/div/table[1]/tbody/tr[3]/td");
    public By val_newTelephone = By.xpath("/html/body/div/div/table[1]/tbody/tr[4]/td");

    public By link_home = By.xpath("//a[contains(@title, 'home page')]");
    public By link_find = By.xpath("//a[contains(@title, 'find owners')]");
    public By link_veter = By.xpath("//a[contains(@title, 'veterinarians')]");
    public By link_error = By.xpath("//a[contains(@title, 'trigger a RuntimeException to see how it is handled')]");




}
