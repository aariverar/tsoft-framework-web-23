package com.tsoft.pages;

import com.tsoft.base.Browser;
import com.tsoft.base.Report;
import com.tsoft.base.Utils;
import com.tsoft.objects.home_PetObjects;
import org.junit.Assert;
import org.junit.ComparisonFailure;

public class home_PetPage extends Browser {
    private home_PetObjects hp = new home_PetObjects();

    public boolean ingresarPagina()
    {
        boolean response = false;
        String step = "Se ingresa a la web de PET CLINIC.";
        Utils.println(step);
        try {
            loadPage("http://pet-clinic-staging.us-east-1.elasticbeanstalk.com/");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickLinkFindOwners()
    {
        boolean response = false;
        String step = "Se selecciona link FIND OWNERS.";
        Utils.println(step);
        try {
            click(hp.lnk_findOwners);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickBotonFindOwners()
    {
        boolean response = false;
        String step = "Se selecciona boton FIND OWNERS.";
        Utils.println(step);
        try {
            click(hp.btn_findOwners);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean validacionOwners()
    {
        boolean response = false;
        String step = "Se verifican los 10 owners.";
        Utils.println(step);
        try {
            String expectedName1 = "Abraham Rivera";
            Assert.assertEquals(getText(hp.val_name1), expectedName1);
            String expectedName2 = "Betty Davis";
            Assert.assertEquals(getText(hp.val_name2), expectedName2);
            String expectedName3 = "Eduardo Rodriquez";
            Assert.assertEquals(getText(hp.val_name3), expectedName3);
            String expectedName4 = "Harold Davis";
            Assert.assertEquals(getText(hp.val_name4), expectedName4);
            String expectedName5 = "Peter McTavish";
            Assert.assertEquals(getText(hp.val_name5), expectedName5);
            String expectedName6 = "Jean Coleman";
            Assert.assertEquals(getText(hp.val_name6), expectedName6);
            String expectedName7 = "Jeff Black";
            Assert.assertEquals(getText(hp.val_name7), expectedName7);
            String expectedName8 = "Maria Escobito";
            Assert.assertEquals(getText(hp.val_name8), expectedName8);
            String expectedName9 = "David Schroeder";
            Assert.assertEquals(getText(hp.val_name9), expectedName9);
            String expectedName10 = "Carlos Estaban";
            Assert.assertEquals(getText(hp.val_name10), expectedName10);

            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (ComparisonFailure ae) {
            Report.onFail(null, "Error : [ Step : " + step + " ] : " + ae.getMessage());
            driver.navigate().refresh();
            driver.close();
            throw new AssertionError(ae.getMessage(), ae);
        }
        catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;


    }

    public boolean clickFirstOwner()
    {
        boolean response = false;
        String step = "Se da click en el primer Owner.";
        Utils.println(step);
        try {
            click(hp.val_name1);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickEditOwner()
    {
        boolean response = false;
        String step = "Se da click en editar Owner.";
        Utils.println(step);
        try {
            click(hp.btn_editOwner);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean editarFormularioOwner()
    {
        boolean response = false;
        String step = "Se editan los datos del Owner por uno nuevo.";
        Utils.println(step);
        try {
            clear(hp.txt_firstName);
            clear(hp.txt_lastName);
            clear(hp.txt_address);
            clear(hp.txt_city);
            clear(hp.txt_telephone);
            sleep(1);
            typeText(hp.txt_firstName, "Abraham");
            typeText(hp.txt_lastName, "Rivera");
            typeText(hp.txt_address, "Av. Lima 333");
            typeText(hp.txt_city, "Lima");
            typeText(hp.txt_telephone, "999888777");
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickUpdateOwner()
    {
        boolean response = false;
        String step = "Se da click en update Owner.";
        Utils.println(step);
        try {
            click(hp.btn_updateOwner);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean validacionNewUser()
    {
        boolean response = false;
        String step = "Se verifica la informacion del nuevo Owner.";
        Utils.println(step);
        try {
            String expectedNewName = "Abraham Rivera";
            Assert.assertEquals(getText(hp.val_newName), expectedNewName);
            String expectedNewAddress = "Av. Lima 333";
            Assert.assertEquals(getText(hp.val_newAddress), expectedNewAddress);
            String expectedNewCity = "Lima";
            Assert.assertEquals(getText(hp.val_newCity), expectedNewCity);
            String expectedNewTelephone = "999888777";
            Assert.assertEquals(getText(hp.val_newTelephone), expectedNewTelephone);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (AssertionError ae) {
            Report.onFail(null, "Error : [ Step : " + step + " ] : " + ae.getMessage());
            driver.navigate().refresh();
            driver.close();
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean rollBackFormulario()
    {
        boolean response = false;
        String step = "Se hace rollback de los datos del primer Owner.";
        Utils.println(step);
        try {
            clear(hp.txt_firstName);
            clear(hp.txt_lastName);
            clear(hp.txt_address);
            clear(hp.txt_city);
            clear(hp.txt_telephone);
            sleep(1);
            typeText(hp.txt_firstName, "George");
            typeText(hp.txt_lastName, "Franklin");
            typeText(hp.txt_address, "110 W. Liberty St.");
            typeText(hp.txt_city, "Madison");
            typeText(hp.txt_telephone, "6085551023");
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickMenuHome()
    {
        boolean response = false;
        String step = "Se ingresa al menu HOME.";
        Utils.println(step);
        try {
            click(hp.link_home);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickMenuFindOwner()
    {
        boolean response = false;
        String step = "Se ingresa al menu FIND OWNER.";
        Utils.println(step);
        try {
            click(hp.link_find);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickMenuVeterinarians()
    {
        boolean response = false;
        String step = "Se ingresa al menu VETERINARIANS.";
        Utils.println(step);
        try {
            click(hp.link_veter);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean clickMenuError()
    {
        boolean response = false;
        String step = "Se ingresa al menu ERROR.";
        Utils.println(step);
        try {
            click(hp.link_error);
            sleep(1);
            Report.onPass(driver, step+" correctamente.");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

}
