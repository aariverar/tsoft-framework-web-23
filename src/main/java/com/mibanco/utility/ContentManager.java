package com.mibanco.utility;

import io.cucumber.java.Scenario;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import com.mibanco.base.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class ContentManager {

    private String param1; //feature
    private String param2; //scenario
    private String param3; //tags
    private String param4; //date Inicio
    private String param5; //date Fin
    private String param6; //time
    private String param7; //estado

    public void onStart(Scenario scenario, String estado) {
        String Feature = scenario.getId().split(";")[0].replace("-"," ");
        ArrayList<String> Tags = new ArrayList<>(scenario.getSourceTagNames());

        this.param1 = Feature.substring(0, 1).toUpperCase() + Feature.substring(1);
        this.param2 = scenario.getName();
        this.param3 = Arrays.toString(Tags.toArray()).replace("[", "").replace("]", "");
        this.param7 = estado;
    }

    public void onDateI(long dateInicio) {
        Date dateInicioC = new Date();
        dateInicioC.setTime(dateInicio);
        this.param4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dateInicioC);
    }

    public void onDateF(long dateFin) {
        Date dateFinC = new Date();
        dateFinC.setTime(dateFin);
        this.param5 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dateFinC);
    }

    public void onTime(double time) {
        this.param6 = String.valueOf(time/1000.0);
    }

    public void onExecute() {
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();

        JSONObject data = new JSONObject();
        data.put(properties.getProperty("cm.param1"), this.param1);
        data.put(properties.getProperty("cm.param2"), this.param2);
        data.put(properties.getProperty("cm.param3"), this.param3);
        data.put(properties.getProperty("cm.param4"), this.param4);
        data.put(properties.getProperty("cm.param5"), this.param5);
        data.put(properties.getProperty("cm.param6"), this.param6);
        data.put(properties.getProperty("cm.param7"), this.param7);

        StringEntity entity;
        if (properties.getProperty("cm.param_body").isEmpty()){
            try {
                entity = new StringEntity(data.toJSONString());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }else{
            JSONObject sendTest = new JSONObject();
            sendTest.put("data", data);
            try {
                entity = new StringEntity(sendTest.toJSONString());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        String Url = properties.getProperty("cm.url");
        String Table = properties.getProperty("cm.table");
        boolean ContentManager = Boolean.parseBoolean(properties.getProperty("content.manager"));
        int HeaderCount = Integer.parseInt(properties.getProperty("cm.header_count"));
        if (ContentManager) {
            HttpPost request = new HttpPost(Url + '/' + Table);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            if (HeaderCount != 0) {
                for (int i = 1; i < HeaderCount + 1; i++) {
                    request.setHeader(
                            properties.getProperty("cm.header" + 1),
                            properties.getProperty("cm.header_val" + 1)
                    );
                }
            }

            request.setEntity(entity);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int status = httpResponse.getStatusLine().getStatusCode();
            Utils.println("[LOG] Content Manager Status Code " + status);
            Utils.println("[LOG] Content Manager Status Code " + httpResponse.getStatusLine().getReasonPhrase());
        }else{
            Utils.println("[LOG] Content Manager Off");
        }
    }
}
