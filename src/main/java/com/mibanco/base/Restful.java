package com.mibanco.base;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import java.util.Map;

public class Restful {

    private Map<Integer, String> getHeader;

    public void setHeader(Map<Integer, String> header){
        this.getHeader = header;
    }

    public JSONObject post(String url, String data) {
        JSONObject responseJSON = null;
        try {
            StringEntity entity = new StringEntity(data);

            HttpPost request = new HttpPost(url);
            for (int i = 0; i < getHeader.size(); i++) {
                String value = getHeader.get(i);
                String[] header = value.split("=");
                request.setHeader(header[0], header[1]);
            }
            request.setEntity(entity);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();

            String response = EntityUtils.toString(httpEntity);
            if (Utils.parseJSON(response) != null){
                responseJSON = Utils.parseJSON(response);
            }
        } catch (Throwable e){
            responseJSON = null;
        }
        return responseJSON;
    }

    protected JSONObject get(String url) {
        JSONObject responseJSON = null;
        try {
            HttpGet request = new HttpGet(url);
            for (int i = 0; i < getHeader.size(); i++) {
                String value = getHeader.get(i);
                String[] header = value.split("=");
                request.setHeader(header[0], header[1]);
            }

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();

            String response = EntityUtils.toString(httpEntity);
            if (Utils.parseJSON(response) != null){
                responseJSON = Utils.parseJSON(response);
            }
        } catch (Throwable e){
            responseJSON = null;
        }
        return responseJSON;
    }
}
