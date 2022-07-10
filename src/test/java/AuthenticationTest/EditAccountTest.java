package AuthenticationTest;

import static Utils.HelperMethods.AuthHelper.*;
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

        if (email != null)
            requestBody.put("email", email);
        if (password != null)
            requestBody.put("password", password);
        if (re_pass != null)
            requestBody.put("re_pass", re_pass);
        if (address != null)
            requestBody.put("address", address);
        if (name != null)
            requestBody.put("name", name);
        if (phone != null)
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
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccount,
                authToken
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }


    @Test //NAME: khong dien ten
    public void TestEditAccountFailNoName() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                "tra8@gmail.com",
                "Tra",
                "Tra",
                "address12",
                null,
                "0940",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //NAME: ten qua dai
    public void TestEditAccountFailLongName() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                "tra8@gmail.com",
                "Tra",
                "Tra",
                "address12",
                generateRandomLongString(0),
                "0940",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //EMAIL: No email
    public void TestEditAccountFailNoEmail() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                null,
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //EMAIL: wrong type email
    public void TestEditAccountFailWrongTypeEmail() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                "not_an_email",
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //EMAIL: email too long
    public void TestEditAccountFailTooLongEmail() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                generateRandomLongString(1),
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //EMAIL: same email
    public void TestEditAccountFailSameEmail() throws Exception{
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
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //PHONE: no phone
    public void TestEditAccountFailNoPhone() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                "address12",
                "tt",
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
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //PHONE: too long phone
    public void TestEditAccountFailTooLongPhone() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                "address12",
                "tt",
                generateRandomLongString(0),
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //ADDRESS: too long address
    public void TestEditAccountFailTooLongAddress() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                generateRandomLongString(0),
                "tt",
                "0090",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //PASSWORD: too long password
    public void TestEditAccountFailTooLongPassword() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(//password dai qua thi khong chay
                generateRandomEmail(),
                generateRandomLongString(0),
                "Tra",
                "address12",
                "tt",
                "0090",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //PASSWORD: no password
    public void TestEditAccountFailNoPassword() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody( //khong co pass khong chay
                generateRandomEmail(),
                "",
                "tra",
                "address12",
                "tt",
                "0090",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("1001", response.getResponseMessage());
            //assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //RE_PASS: no repass
    public void TestEditAccountFailNoRepass() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                "tra8@gmail.com",
                "tra",
                null,
                "address12",
                "tt",
                "0090",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //RE_PASS: re_pass doesn't match password
    public void TestEditAccountFailNoMatchingRepass() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                "tra8@gmail.com",
                "tra",
                "abc",
                "address12",
                "tt",
                "0090",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }

    @Test //RE_PASS: no repass
    public void TestEditAccountFailTooLongRepass() throws Exception{
        JSONObject requestBody = buildEditAccountRequestBody(
                "tra8@gmail.com",
                "tra",
                generateRandomLongString(0),
                "address12",
                "tt",
                "0090",
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
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on editing account");
        }
    }
}
