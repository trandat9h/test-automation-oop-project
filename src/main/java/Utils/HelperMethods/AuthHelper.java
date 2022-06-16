package Utils.HelperMethods;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;

public class AuthHelper {

    String getAuthToken(String email, String password) {
        // do something
        JSONObject requestBody = new JSONObject();

        requestBody.put("email", email);
        requestBody.put("password", password);

        HTTPRequest httpRequest = new HTTPRequest(
                "/login",
                null,
                 requestBody,
                null);

        try {
            CustomResponse response = httpRequest.post();
        } catch (Exception e) {
            System.out.println(e);
        }





        return null;
    }

    String createNewUser(String email, String password) {
        // create new user for mocking
        return null;
    }

}
