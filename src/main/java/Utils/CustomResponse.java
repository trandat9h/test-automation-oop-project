package Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CustomResponse {
    private final int statusCode;
    private final JSONObject body;

    public CustomResponse (int statusCode, JSONObject body) {
        this.statusCode = statusCode;
        this.body = body;
    }


    private JSONObject getBody(){
        // Not used
        return this.body;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String GetResponseCode() {
        String responseCode = this.body.get("code").toString();
        // Exception handling required
        return responseCode;
    }

    public String getResponseMessage() {
        String responseMessage = this.body.get("message").toString();
        // Exception handling required
        return responseMessage;
    }

    public JSONObject getResponseData () {
        JSONObject data = (JSONObject) this.body.get("data");
        // Exception handling required
        return data;
    }

    public JSONArray getResponseDataArray () {
        JSONArray data = (JSONArray) this.body.get("data");
        // Exception handling required
        return data;
    }
}
