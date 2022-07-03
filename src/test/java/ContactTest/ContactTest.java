package ContactTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;

import static Utils.HelperMethods.AuthHelper.generateRandomEmail;
import static Utils.HelperMethods.AuthHelper.generateRandomLongString;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest extends BaseTest {
    private static final String endpoint = "/contactUs";
    private static JSONObject buildContactRequestBody(
            String name,
            String phone,
            String email,
            String content,
            String file,
            String report_type) {
        JSONObject requestBody = new JSONObject();

        if (email != null)
            requestBody.put("email", email);
        if (report_type != null)
            requestBody.put("report_type", report_type);
        if (content != null)
            requestBody.put("content", content);
        if (name != null)
            requestBody.put("name", name);
        if (phone != null)
            requestBody.put("phone", phone);

        return requestBody;
    }

    @Test
    public void TestContactSuccessfully() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                "010",
                "tra8@gmail.com",
                "random",
                null,
                "1");
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode()); //fail code:500
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on contact");
        }
    }
    @Test
    public void TestContactFailNoName() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                null,
                "010",
                "tra8@gmail.com",
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailLongName() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                generateRandomLongString(0),
                "010",
                "tra8@gmail.com",
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailNoPhone() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                null,
                "tra8@gmail.com",
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailLongPhone() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                generateRandomLongString(0),
                "tra8@gmail.com",
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailNoEmail() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                "090",
                null,
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailLongEmail() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                "090",
                generateRandomLongString(1),
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailWrongTypeEmail() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                "090",
                "abc.xyz",
                "random",
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailNoContent() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                "090",
                "tra8@gmail.com",
                null,
                null,
                "1");
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
            throw new Exception("Error on contact");
        }
    }

    @Test
    public void TestContactFailNoReportType() throws Exception{
        JSONObject requestBody = buildContactRequestBody(
                "Tra",
                "090",
                "tra8@gmail.com",
                "random",
                null,
                null);
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
            throw new Exception("Error on contact");
        }
    }
}
