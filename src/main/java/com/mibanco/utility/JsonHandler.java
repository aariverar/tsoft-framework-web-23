package com.mibanco.utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonHandler {

    public JSONObject getTestData(String rutaRelativa){
        JSONParser jsonParser = new JSONParser();
        try {
            String ruta = System.getProperty("user.dir") + "/src/main/resources/fixtures/" + rutaRelativa;
            FileReader reader = new FileReader(ruta);

            //Read JSON file
            Object parseObject = jsonParser.parse(reader);

            JSONObject  data = (JSONObject) parseObject;

            return data;
        } catch (IOException | ParseException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    public String getTestData(String rutaRelativa, String index, String param) {
        JSONParser jsonParser = new JSONParser();
        try {
            String ruta = System.getProperty("user.dir") + "/src/main/resources/fixtures/" + rutaRelativa;
            FileReader reader = new FileReader(ruta);

            //Read JSON file
            Object parseObject = jsonParser.parse(reader);

            String response;
            JSONObject current;

            try {
                JSONArray  data = (JSONArray) parseObject;

                current = (JSONObject) data.get(Integer.parseInt(index));
            }catch (ClassCastException c){
                current = (JSONObject) parseObject;
            }
            response = (String) current.get(param);
            return response;
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return null;
        } catch (ParseException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

}
