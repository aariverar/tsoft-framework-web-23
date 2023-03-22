package com.mibanco.steps;

import com.mibanco.base.Fixtures;
import com.mibanco.base.Report;
import com.mibanco.pages.home_PetPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class home_PetStep {
    private home_PetPage hpp = new home_PetPage();
    public static Map<String, String> data;
    public static boolean status = false;
    public static int index;
    public static String sheet;

    public void initial(int cp, String hoja){
        data = Fixtures.onLoadData(hoja).get(cp - 1);
        index = cp;
        sheet = hoja;
        Report.onWordReport(data);
    }
    @Given("Se ingresa a la web de PET CLINIC {int}, {string}")
    public void seIngresaALaWebDePETCLINICCaso_prueba(int cp, String hoja) {
        initial(cp, hoja);
        Report.onStep("Given", "Se ingresa a la web de PET CLINIC");
        Report.onWordMode("browser");
        status = hpp.ingresarPagina();
    }


    @When("Ingreso a la opcion find owner")
    public void ingresoALaOpcionFindOwner() {
        Report.onStep("When", "Ingreso a la opcion find owner");
        if (status) status =  hpp.clickLinkFindOwners();
        if (status) status =  hpp.clickBotonFindOwners();
    }

    @Then("Valido los owners")
    public void validoLosOwners() {
    }
}
