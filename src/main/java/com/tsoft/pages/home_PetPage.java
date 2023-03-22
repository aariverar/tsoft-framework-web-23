package com.mibanco.pages;

import com.mibanco.base.Browser;
import com.mibanco.base.Report;
import com.mibanco.base.Utils;
import com.mibanco.objects.home_PetObjects;
import org.junit.Assert;

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
            String expectedName1 = "George Franklin";
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
        }catch (AssertionError ae) {
            Report.onFail(null, "Error : [ Step : " + step + " ] : " + ae.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;


    }

}
