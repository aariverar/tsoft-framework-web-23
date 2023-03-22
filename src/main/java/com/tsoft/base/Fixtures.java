package com.tsoft.base;

import com.tsoft.utility.ExcelHandler;
import com.tsoft.utility.JsonHandler;
import com.tsoft.utility.PropertiesFile;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Fixtures {

    public static List<Map<String, String>> onLoadData(String file) {
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();
        String DataFile = properties.getProperty("fix.data.file");
        String Sheet1 = properties.getProperty("fix.sheet1");
        String Sheet2 = properties.getProperty("fix.sheet2");
        String Sheet3 = properties.getProperty("fix.sheet3");
        String Sheet4 = properties.getProperty("fix.sheet4");
        String Sheet5 = properties.getProperty("fix.sheet5");
        String Sheet6 = properties.getProperty("fix.sheet6");
        String Sheet7 = properties.getProperty("fix.sheet7");
        String Sheet8 = properties.getProperty("fix.sheet8");

        List<Map<String, String>> response = null;
        ExcelHandler eh = new ExcelHandler();
        switch (file) {
            case "Config":
                response = eh.getTestData(DataFile, Sheet1);
                break;
            case "Login":
                response = eh.getTestData(DataFile, Sheet2);
                break;

            case "AC":
                response = eh.getTestData(DataFile, Sheet3);
                break;

            case "TEMC":
                response = eh.getTestData(DataFile, Sheet4);
                break;

            case "TEOC":
                response = eh.getTestData(DataFile, Sheet5);
                break;

            case "CC":
                response = eh.getTestData(DataFile, Sheet6);
                break;
            case "DPF":
                response = eh.getTestData(DataFile, Sheet7);
                break;
            case "PP":
                response = eh.getTestData(DataFile, Sheet8);
                break;
        }
        Utils.println("Data Cargada");
        return response;
    }

    public static JSONObject onLoadData(int file) {
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();
        String DataFile1 = properties.getProperty("fix.data.file");
        String DataFile2 = properties.getProperty("fix.data.bcp.token");
        String DataFile3 = properties.getProperty("fix.data.bcp.url");
        String DataFile4 = properties.getProperty("fix.data.rys.token");
        String DataFile5 = properties.getProperty("fix.data.rys.URL");
        String DataFile6 = properties.getProperty("fix.data.sftp");

        JSONObject response = null;
        JsonHandler jh = new JsonHandler();
        switch (file) {
            case 1:
                response = jh.getTestData(DataFile1);
                break;
            case 2:
                response = jh.getTestData(DataFile2);
                break;
            case 3:
                response = jh.getTestData(DataFile3);
                break;
            case 4:
                response = jh.getTestData(DataFile4);
                break;
            case 5:
                response = jh.getTestData(DataFile5);
                break;
            case 6:
                response = jh.getTestData(DataFile6);
                break;
        }
        Utils.println("Data Cargada " + file);
        return response;
    }

    //Si trabajas con otra hoja entonces duplicas este metodo
    public static void onSetData(String sheet, String colum, String value, String id) {
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();
        //String Sheet5 = properties.getProperty("fix.sheet2");
        String DataFile = properties.getProperty("fix.data.file");

        ExcelHandler eh = new ExcelHandler();
        eh.setTestData(DataFile, sheet, colum, value, id);
        Utils.println("Data Insertado");
    }

    public void onDataFinish() {
    }
}
