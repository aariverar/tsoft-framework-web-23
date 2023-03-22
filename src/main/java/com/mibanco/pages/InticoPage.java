package com.mibanco.pages;

import com.mibanco.base.Browser;
import com.mibanco.base.Fixtures;
import com.mibanco.base.Report;
import com.mibanco.base.Utils;
import com.mibanco.objects.InticoObjects;
import com.mibanco.steps.ConfigStep;
import org.openqa.selenium.edge.EdgeDriver;

public class InticoPage extends Browser {

    //public static EdgeDriver driver;

    private InticoObjects io = new InticoObjects();

    //cambiar bolean
    public boolean ingresarPagina()
    {
        boolean response = false;
        String step = "Se ingresa al Page Intico correctamente";
        Utils.println(step);
        try {
            loadPage("http://reportes.intico.pe/SMSV5/login");
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean iniciarSesion()
    {
        boolean response = false;
        String step = "Se inicia sesion";
        Utils.println(step);

        try {
            typeText(io.username,"HB_TC_DEWS");
            System.out.println("Ingreso el usuario en intico");
            typeText(io.pass,"Hb$tC%D2017");
            System.out.println("Ingreso el contraseña en intico");
            click(io.btn_iniciar);
            System.out.println("selecciono el boton en intico");
            Report.onPass(driver, step);
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;

    }

    public boolean accederReporte()
    {
        boolean response = false;
        String step = "Se Accede Reportes";
        Utils.println(step);

        try {
            click(io.lnk_reportes);
            System.out.println("Ingreso a Reportes");
            click(io.rad_reportesdia);
            System.out.println("Ingreso Reporte del dia");
            click(io.btn_generaReporte);
            System.out.println("Se genera el reporte del dia");
            Report.onPass(null, step);
            response = true;
        }catch (Exception t) {
            Report.onFail(null, "Error : [ Step : "+step+" ] : " +  t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }

    public boolean buscarCodigoSMS(String id, String modulo) {
        boolean response = false;
        String step = "Se ingresa al Page Buscar codigo SMS";
        Utils.println(step);
        String smsVal = null;
        try {
            getText(io.sms);
            //Mibanco - NO COMPARTAS ESTE CODIGO! Estas a punto de realizar Transferencia a terceros de $ 1.00 (06/02 09:49), EVITA LOS FRAUDES 99840
            if(modulo.equals("modulo1")){
                System.out.println("Obtenido GAA: " + getText(io.sms));
                String smsText = getText(io.sms);
                int par1 = (smsText.length());
                int par2 = (smsText.length()-5);
                System.out.println("primer parametro "+par1);
                System.out.println("segundo parametro"+par2);
                smsVal = smsText.substring(par2, par1);
                System.out.println("Codigo OTP: " + smsVal);
                response = true;
            }
            //Codigo de verificacion para la apertura de una nueva cuenta 67290)
            if(modulo.equals("moduloapertura")){
                System.out.println("Obtenido GAA: " + getText(io.sms));
                String smsText = getText(io.sms);
                smsVal = smsText.substring(60, 65);
                System.out.println("Codigo OTP: " + smsVal);
                response = true;
            }
            //Mibanco: 21040 es tu codigo de verificacion para recuperar tu clave.
            if(modulo.equals("modulo2")){
                System.out.println("Obtenido GAA: " + getText(io.sms));
                String smsText = getText(io.sms);
                smsVal = smsText.substring(9, 14);
                System.out.println("Codigo OTP: " + smsVal);
                response = true;
            }
            //Mibanco TRAN S/ 350.00 CLAVE DINAMICA SMS 50183 (14/02 13:06)
            if(modulo.equals("modulo3")){
                System.out.println("Obtenido GAA: " + getText(io.sms));
                String smsText = getText(io.sms);
                int par1 = (smsText.length()-14);
                int par2 = (smsText.length()-19);
                smsVal = smsText.substring(par2, par1);
                System.out.println("Codigo OTP: " + smsVal);
                response = true;
            }


            if(modulo.equals("modulo1")){
                Fixtures.onSetData(ConfigStep.sheet, "Otp1",smsVal,id);
            }
            if(modulo.equals("moduloapertura")){
                Fixtures.onSetData(ConfigStep.sheet, "Otp",smsVal,id);
            }
            if(modulo.equals("modulo2")){
                Fixtures.onSetData(ConfigStep.sheet, "Otp2",smsVal,id);
            }
            if(modulo.equals("modulo3")){
                Fixtures.onSetData(ConfigStep.sheet, "Otp3",smsVal,id);
            }

        } catch (Exception t) {
            Report.onFail(null, "Error : [ Step : " + step + " ] : " + t.getMessage());
            driver.navigate().refresh();
            driver.close();
        }
        return response;
    }







}
