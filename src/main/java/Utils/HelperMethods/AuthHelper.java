package Utils.HelperMethods;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class AuthHelper {

    public String getAuthToken(String email, String password) {
        // do something
        JSONObject requestBody = new JSONObject();

        requestBody.put("email", email);
        requestBody.put("password", password);

        HTTPRequest httpRequest = new HTTPRequest(
                "/login",
                 requestBody
                );

        try {
            CustomResponse response = httpRequest.post();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static JSONObject createNewExistedRandomUser(){

        JSONObject requestBody = createNewRandomUser();

        HTTPRequest httpRequest = new HTTPRequest(
                "/signup",
                requestBody
        );

        try {
            CustomResponse response = httpRequest.post();
            if (Objects.equals(response.GetResponseCode(), "1000"))
                return requestBody;
            else throw new Exception();
        } catch (Exception e) {
            // Do nothing
            return null;
        }

    }

    public static JSONObject createNewRandomUser(){

        JSONObject requestBody = new JSONObject();

        requestBody.put("email", generateRandomEmail());
        requestBody.put("password", "password");
        requestBody.put("re_pass", "password");
        requestBody.put("name", "randomUser");
        requestBody.put("phone", "123456789");

        return requestBody;

    }

    private static String generateRandomEmail() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        // Random email format: "randomemail<uuid>@gmail.com"
        return "randomemail" + uuidAsString + "@gmail.com";
    }



}
