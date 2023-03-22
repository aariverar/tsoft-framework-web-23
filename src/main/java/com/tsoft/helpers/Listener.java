package com.tsoft.helpers;

import com.tsoft.base.Report;
import com.tsoft.base.Utils;
import com.tsoft.utility.ContentManager;
import com.tsoft.utility.WordReport;
import io.cucumber.java.*;

public class Listener {

    public static final WordReport wreporte = new WordReport();
    private static final ContentManager content = new ContentManager();

    private static long startTime;
    private static long endTime;
    public static boolean status;

    @BeforeAll
    public static void onStart() {
        System.out.println("Execution started on UAT...");
        Report.wreport.onWordStart();
    }

    @Before(order = 1)
    public static void onBefore(Scenario scenario) throws Throwable {
        Report.ereport.onTestStart(scenario.getName());

        //Individual Word
        wreporte.onWordStart();

        //Strapi
        startTime = System.currentTimeMillis();
        status = false;
    }

    @After(order = 1)
    public static void onAfter(Scenario scenario) {
        String estado;
        if (status){
            if (String.valueOf(scenario.getStatus()).equals("PASSED")){
                estado = "PASSED";
            }else{ estado = "FAILED"; }
        }else{ estado = "FAILED"; }

        //Strapi
        endTime = System.currentTimeMillis();
        content.onStart(scenario, estado);
        content.onDateI(startTime);
        content.onDateF(endTime);
        content.onTime(endTime-startTime);
        content.onExecute();

        Utils.println(estado);
        Report.ereport.onFinish(estado);
        Report.wreport.onWordProcess(estado);

        //Individual Word
        wreporte.onWordProcess(estado);
        wreporte.onWordFinish(scenario, estado);
    }

    @AfterAll
    public static void onFinish(){
        System.out.println("Execution completed on UAT...");
        Report.wreport.onWordFinish();
    }
}
