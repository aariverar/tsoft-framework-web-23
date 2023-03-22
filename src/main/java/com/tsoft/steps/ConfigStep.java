package com.mibanco.steps;

import com.mibanco.base.Fixtures;
import com.mibanco.base.Report;
import com.mibanco.pages.InticoPage;
import io.cucumber.java.en.Given;

import java.util.Map;

public class ConfigStep {

    //private ConfigPage cfp = new ConfigPage();
    private InticoPage ip = new InticoPage();
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
/*
    @Given("Se realiza la configuracion de Enrollment {int}, {string}")
    public void seRealizaLaConfiguracionDeEnrollment(int cp, String hoja) {
        initial(cp, hoja);
        Report.onStep("Given", "Se realiza la configuracion de Enrollment");

        status = cfp.clickProlongadoEnLlamanos();
        if (status) status = cfp.clickVersion();
        if (status) status = cfp.ingresaConfig();
        if (status) status = cfp.desactivarMutualAuth();
        if(status) status = cfp.desactivarSSLPiningyEnrollment();
        //if (status) status = cfp.desactivarEnroll();
        //if (status) status = cfp.desactivarSSLPining();
        if (status) status = cfp.clickOcultar();
    }*/

    @Given("Se realiza el login de intico {int}, {string}")
    public void seRealizaElLoginDeIntico(int cp, String hoja) {

        initial(cp, hoja);
        Report.onStep("Given", "Se realiza la configuracion de Enrollment");
        Report.onWordMode("browser");
        //status = true;
        status = ip.ingresarPagina();
        if (status) status =  ip.iniciarSesion();
        //if (status) status =  ip.accederReporte();
        //if (status) status = ip.buscarCodigoSMS(data.get("Id"),"modulo1");
        // data = Fixtures.onLoadData("TEOC").get(index - 1);


    }


}
