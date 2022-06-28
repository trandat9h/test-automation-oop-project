package Utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




public class HTTPRequest {
    public String baseUrl = "https://auction-app3.herokuapp.com/api";
    private final String endpoint;
    private JSONObject requestBody = null;
    private JSONObject responseBody;
    private String authenticationHeader = null;

    public HTTPRequest(String endpoint, JSONObject requestBody, String token) {
        this.authenticationHeader = "Bearer " + token;

        // Init all request attributes
        this.endpoint = this.baseUrl + endpoint;

        if (requestBody != null)
            this.requestBody = requestBody;
    }

    public HTTPRequest(String endpoint, String token) {
        this.authenticationHeader = "Bearer " + token;

        // Init all request attributes
        this.endpoint = this.baseUrl + endpoint;
    }

    public HTTPRequest(String endpoint, JSONObject requestBody) {
        // Init all request attributes
        this.endpoint = this.baseUrl + endpoint;

        if (requestBody != null)
            this.requestBody = requestBody;
    }

    public CustomResponse get() throws Exception {
        HttpGet request = new HttpGet(this.endpoint);

        // Add headers
        request.addHeader("Content-Type","application/json" );
        if (this.authenticationHeader != null)
            request.addHeader("Authorization", this.authenticationHeader);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request);) {

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            // return it as a String
            this.responseBody = this.convertStringToJSONObject(EntityUtils.toString(response.getEntity()));
            return new CustomResponse(response.getStatusLine().getStatusCode(), responseBody);
        }
    }

    public CustomResponse post() throws Exception {
        HttpPost postRequest = new HttpPost(this.endpoint);

        // Add headers
        postRequest.addHeader("Content-Type","application/json" );
        if (this.authenticationHeader != null)
            postRequest.addHeader("Authorization", this.authenticationHeader);

        if (this.requestBody != null)
            postRequest.setEntity(new StringEntity(this.requestBody.toString()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(postRequest);
           ) {

            this.responseBody = this.convertStringToJSONObject(EntityUtils.toString(response.getEntity()));
            return new CustomResponse(response.getStatusLine().getStatusCode(), this.responseBody);
        }

    }
    public JSONObject convertStringToJSONObject(String body){
        if (body== null) return null;

        JSONParser parser = new JSONParser();

        try {
            return (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            return null;
        }
    }

}


