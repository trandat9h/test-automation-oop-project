package Utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;



public class HTTPRequest {
    public String baseUrl = "https://auction-app3.herokuapp.com/api";
    private String endpoint;
    private JSONObject headers;
    private ArrayList<BasicNameValuePair> params = null;
    private JSONObject requestBody = null;
    private JSONObject responseBody;

    public HTTPRequest(String endpoint, ArrayList <BasicNameValuePair> params, JSONObject requestBody, String token) {
        // Setup default header
        JSONObject headers = new JSONObject();
        //headers.put("Authorization", "Bearer " + token);
        headers.put("Content-Type", "application/json");

        // Init all request attributes
        this.endpoint = this.baseUrl + endpoint;
        this.headers = headers;
        if (params != null)
            this.params = params;
        if (requestBody != null)
            this.requestBody = requestBody;
    }

    public CustomResponse get() throws Exception {
        HttpGet request = new HttpGet(this.endpoint);

        // add request headers
        for (BasicNameValuePair param: this.params)
            request.addHeader(param.getName(), param.getValue());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request);) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            // return it as a String
            this.responseBody = this.convertStringToJSONObject(EntityUtils.toString(response.getEntity()));
            return new CustomResponse(response.getStatusLine().getStatusCode(), responseBody);
        }
    }

    public CustomResponse post() throws Exception {
        HttpPost postRequest = new HttpPost(this.endpoint);

        postRequest.addHeader("Content-Type","application/json" );

        postRequest.setEntity(new StringEntity(this.requestBody.toString()));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(postRequest);
           ) {

            this.responseBody = this.convertStringToJSONObject(EntityUtils.toString(response.getEntity()));
            return new CustomResponse(response.getStatusLine().getStatusCode(), this.responseBody);
        }

    }
    public JSONObject convertStringToJSONObject(String body) throws Exception {
        if (body== null) return null;

        JSONParser parser = new JSONParser();

        try {
            return (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            throw new Exception(e);
        }
    }

}


