package com.mibanco.pages;

import com.mibanco.base.Browser;
import com.mibanco.base.Report;
import com.mibanco.base.Utils;
import com.mibanco.objects.home_PetObjects;

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

}
