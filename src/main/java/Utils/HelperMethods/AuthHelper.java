package Utils.HelperMethods;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;

import java.util.Objects;
import java.util.UUID;

public class AuthHelper {

    public static String getAuthToken(JSONObject account){
        HTTPRequest httpRequest = new HTTPRequest(
                "/login",
                 account
                );

        try {
            CustomResponse response = httpRequest.post();
            return response.getResponseData().get("access_token").toString();
        } catch (Exception e) {
            System.out.println("Error on mocking a login session.");
            return null;
        }
    }

    public static JSONObject createNewExistedRandomUser(){

        JSONObject requestBody = generateNewRandomUser();

        HTTPRequest httpRequest = new HTTPRequest(
                "/signup",
                requestBody
        );

        try {
            CustomResponse response = httpRequest.post();
            if (Objects.equals(response.GetResponseCode(), "1000"))
                return requestBody;
            else throw new Exception("Error on creating mock user request.");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public static JSONObject generateNewRandomUser(){

        JSONObject requestBody = new JSONObject();

        requestBody.put("email", generateRandomEmail());
        requestBody.put("password", "password");
        requestBody.put("re_pass", "password");
        requestBody.put("name", "randomUser");
        requestBody.put("phone", "123456789");

        return requestBody;

    }

    public static String generateRandomEmail() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        // Random email format: "randomemail<uuid>@gmail.com"
        return "randomemail" + uuidAsString + "@gmail.com";
    }

    public static String generateRandomLongString(int isEmail) {
        String str = "";
        for(int i = 0;i < 257; i++){
            str += "a";
        }
        if(isEmail == 1){
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            return str + uuidAsString + "@gmail.com";
        }
        return str;
    }


}
