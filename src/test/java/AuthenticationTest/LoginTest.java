package AuthenticationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static Utils.HelperMethods.AuthHelper.generateRandomEmail;
import static Utils.HelperMethods.AuthHelper.generateRandomLongString;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTest extends BaseTest {
    private static final String endpoint = "/login";

    private static JSONObject buildLoginRequestBody(
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

    @Test
    void loginSuccessfully() throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccount
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }
    }

    @Test
    void loginFailNoEmail() throws Exception{
        JSONObject requestBody = buildLoginRequestBody(
                null,
                "random",
                null,
                null,
                null,
                null,
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
        CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("email: 7000 &password: ", response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }
    }

    @Test
    void loginFailNoPassword() throws Exception{
        JSONObject requestBody = buildLoginRequestBody(
                generateRandomEmail(),
                null,
                null,
                null,
                null,
                null,
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("email:  &password: 7000", response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }
    }

    @Test
    void loginFailEmailTooLong() throws Exception{
        JSONObject requestBody = buildLoginRequestBody(
                generateRandomLongString(1),
                "random",
                null,
                null,
                null,
                null,
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("email: 7002 &password: ", response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }
    }

    @Test
    void loginFailPasswordTooLong() throws Exception{
        JSONObject requestBody = buildLoginRequestBody(
                generateRandomEmail(),
                generateRandomLongString(0),
                null,
                null,
                null,
                null,
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("email:  &password: 7001", response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }
    }
}
