package AuthenticationTest;

import static Utils.HelperMethods.AuthHelper.getAuthToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import Base.BaseTest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuthHelper;

public class EditAccountTest extends BaseTest {
    private static final String endpoint = "/edit";

    private static JSONObject buildEditAccountRequestBody(
            String email,
            String password,
            String re_pass,
            String address,
            String name,
            String phone,
            String avatar) {
        JSONObject requestBody = new JSONObject();

        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("re_pass", re_pass);
        requestBody.put("name", name);
        requestBody.put("phone", phone);

        return requestBody;
    }
    static Stream<Arguments> accountProvider() {
        // Add more tests here
        return Stream.of(
                arguments("dat@gmail.com", "", "", "", "", null, null),
                arguments("lemon", "", "", "", "", "", ""),
                arguments("lemon", "", "", "", "", "", null)
        );
    }

    @Test
    public void TestEditAccountSuccessfully() throws Exception{
        String token = getAuthToken(createdAccount);


        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccount,
                token
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(response.getStatusCode(), 200);
            assertEquals("OK", response.getResponseMessage());
            assertEquals(response.GetResponseCode(), "1000");
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }


    @Test
    public void TestEditAccountFail() throws Exception{
        String token = getAuthToken(createdAccount);
        JSONObject requestBody = buildEditAccountRequestBody(
                "tra8@gmail.com",
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                token
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(response.getStatusCode(), 200);
            assertNotNull(response.getResponseMessage());
            assertEquals(response.GetResponseCode(), "1001");
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }
}
