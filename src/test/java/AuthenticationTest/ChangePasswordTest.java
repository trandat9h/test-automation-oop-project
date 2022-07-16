package AuthenticationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static Utils.HelperMethods.AuthHelper.generateRandomLongString;
import static org.junit.jupiter.api.Assertions.*;

public class ChangePasswordTest extends BaseTest {
    private static final String endpoint = "/changepass";
    private static JSONObject buildChangPasswordRequestBody(
            String old_pass,
            String new_pass,
            String re_pass) {
        JSONObject requestBody = new JSONObject();

        if (old_pass != null)
            requestBody.put("old_pass", old_pass);
        if (new_pass != null)
            requestBody.put("new_pass", new_pass);
        if (re_pass != null)
            requestBody.put("re_pass", re_pass);

        return requestBody;
    }

    @Test
    public void TestChangePasswordSuccessfully() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                "123456",
                "123456"
                );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailNoOldPass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                null,
                "123456",
                "123456"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull( response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailToolongOldPass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                generateRandomLongString(0),
                "123456",
                "123456"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailNoNewPass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                null,
                "123456"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull( response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailToolongNewPass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                generateRandomLongString(0),
                "123456"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailNoRePass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                "123456",
                null
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailTooLongRePass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                generateRandomLongString(0),
                generateRandomLongString(0)
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailNotMatchRePass() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                "123456",
                "abcxyz"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("OK", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }

    @Test
    public void TestChangePasswordFailNotLogin() throws Exception{
        JSONObject requestBody = buildChangPasswordRequestBody(
                "password",
                "123456",
                "123456"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());

        } catch (Exception e) {
            throw new Exception("Error on change password");
        }
    }
}
