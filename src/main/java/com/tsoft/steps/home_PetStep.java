package com.tsoft.steps;

import com.tsoft.base.Fixtures;
import com.tsoft.base.Report;
import com.tsoft.pages.home_PetPage;
import io.cucumber.java.en.And;
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
        Report.onStep("Then", "Valido los owners");
        if(status) status = hpp.validacionOwners();
    }

    @And("Se realiza la edicion del primer Owner")
    public void seRealizaLaEdicionDelPrimerOwner() {
        Report.onStep("And", "Se realiza la edicion del primer Owner");
        if(status) status = hpp.clickFirstOwner();
        if(status) status = hpp.clickEditOwner();
        if(status) status = hpp.editarFormularioOwner();

    }

    @And("Se guardan los cambios validandolos")
    public void seGuardanLosCambiosValidandolos() {
        Report.onStep("And", "Se guardan los cambios validandolos");
        if(status) status = hpp.clickUpdateOwner();
        if(status) status = hpp.validacionNewUser();
    }

    @Then("Se realiza el rollback con la información anterior")
    public void seRealizaElRollbackConLaInformaciónAnterior() {
        Report.onStep("Then", "Se realiza el rollback con la información anterior");
        if(status) status = hpp.clickEditOwner();
        if(status) status = hpp.rollBackFormulario();
        if(status) status = hpp.clickUpdateOwner();
    }

    @Then("Verifico el ingreso a HOME")
    public void verificoElIngresoAHOME() {
        Report.onStep("Then", "Verifico el ingreso a HOME");
        if(status) status = hpp.clickMenuHome();
    }

    @And("Verifico el ingreso a FIND OWNERS")
    public void verificoElIngresoAFINDOWNERS() {
        Report.onStep("And", "Verifico el ingreso a FIND OWNERS");
        if(status) status = hpp.clickMenuFindOwner();
    }

    @And("Verifico el ingreso a VETERINARIANS")
    public void verificoElIngresoAVETERINARIANS() {
        Report.onStep("And", "Verifico el ingreso a VETERINARIANS");
        if(status) status = hpp.clickMenuVeterinarians();
    }

    @And("Verifico el ingreso a ERROR")
    public void verificoElIngresoAERROR() {
        Report.onStep("And", "Verifico el ingreso a ERROR");
        if(status) status = hpp.clickMenuError();
    }
}
