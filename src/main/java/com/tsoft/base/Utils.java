package com.tsoft.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String H_ZONE = "America/Bogota";
    private static final Locale locale = new Locale("es", "PE");

    public static String stepSecuencia(){
        DateFormat df = new SimpleDateFormat("YY-MM-dd HH:mm:ss", locale);
        df.setTimeZone(TimeZone.getTimeZone(H_ZONE));
        return df.format(new Date());
    }

    public static String generarSecuencia() {
        DateFormat df = new SimpleDateFormat("dd-MM-YY_HH-mm-ss", locale);
        df.setTimeZone(TimeZone.getTimeZone(H_ZONE));
        return df.format(new Date());
    }

    public static String generarFecha() {
        DateFormat df = new SimpleDateFormat("dd/MM/YY", locale);
        df.setTimeZone(TimeZone.getTimeZone(H_ZONE));
        return df.format(new Date());
    }

    public static String generarFecha(boolean plusday) {
        Date dia = new Date();
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        String date = fecha.format(dia);

        if (plusday){
            Date dia2 = new Date(dia.getTime() + (1000 * 60 * 60 * 24));
            date = fecha.format(dia2);
        }
        return date;
    }

    public static String generarFechaFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern, locale);
        df.setTimeZone(TimeZone.getTimeZone(H_ZONE));
        return df.format(new Date());
    }

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            //La primera letra en mayuscula y las demas en minuscula.
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    public static String AddCeros(String string, int largo)
    {
        String ceros = "";
        int cantidad = largo - string.length();
        if (cantidad >= 1)
        {
            for(int i=0;i<cantidad;i++)
            {
                ceros += "0";
            }
            return (ceros + string);
        }
        else
            return string;
    }

    public static String generarHora() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss", locale);
        df.setTimeZone(TimeZone.getTimeZone(H_ZONE));
        return df.format(new Date());
    }

    public static int generarNumero(int lenght){
        StringBuffer min_s = new StringBuffer(lenght);
        StringBuffer max_s = new StringBuffer(lenght);
        min_s.append(1);
        max_s.append(9);

        for (int i=0; i< lenght - 1; i++){
            min_s.append(0);
            max_s.append(9);
        }

        int min = Integer.parseInt(min_s.toString());
        int max = Integer.parseInt(max_s.toString());
        int b = (int)(Math.random()*(max-min+1)+min);
        return b;
    }

    public static void println(String description){
        System.out.println(stepSecuencia() + " " + description);
    }


    public static void printJSON(JSONObject response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(response);
        System.out.println(prettyJsonString);
    }

    public static JSONObject parseJSON(String response){
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String prettyJSON(String response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(response);
        return gson.toJson(je);
    }

    public static JSONObject getJSON(JSONObject data, String param, int index) {
        JSONArray temp = (JSONArray) data.get("data");
        JSONObject current = (JSONObject) temp.get(index);
        return current;
    }

    public static boolean isValidJSON(String json) {
        try {
            new org.json.JSONObject(json);
        } catch (org.json.JSONException e) {
            return false;
        }
        return true;
    }

    public static String isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
        } catch (NumberFormatException nfe){
            return "false";
        }
        return "true";
    }

    public static String isDate(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("MM/YY");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (Exception e) {
            return "false";
        }
        return "true";
    }

    public static String isString(String cadena){
        Pattern cond = Pattern.compile("[a-zA-Z]+");
        Matcher val = cond.matcher(cadena);
        if (val.matches()) {
            return "true";
        }else {
            return "false";
        }
    }

    public static String isEmail(String cadena){
        Pattern cond = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher val = cond.matcher(cadena);
        if (val.matches()) {
            return "true";
        }else {
            return "false";
        }
    }
}
