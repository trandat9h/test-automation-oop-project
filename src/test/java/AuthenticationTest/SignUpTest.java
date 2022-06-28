package AuthenticationTest;

import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static Utils.HelperMethods.AuthHelper.generateRandomEmail;
import static Utils.HelperMethods.AuthHelper.generateRandomLongString;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import Base.BaseTest;

public class SignUpTest extends BaseTest {
    private static final String endpoint = "/signup";

    private static JSONObject buildSignUpRequestBody(
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

//    @BeforeAll
//    private static void Setup() throws Exception{
//
//    }

    @Test
    public void TestSignUpSuccessfully() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
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

//    @ParameterizedTest
//    @MethodSource("accountProvider")
//    public void TestSignUpWithIncorrectInformation (
//            String email,
//            String password,
//            String re_pass,
//            String address,
//            String name,
//            String phone,
//            String avatar
//    ) {
//        JSONObject requestBody = buildSignUpRequestBody(email, password, re_pass, address, name, phone, avatar);
//        assertEquals(1, 1);
//
//    }

    @Test //NAME: khong dien ten
    public void TestSignUpFailNoName() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                "tra8@gmail.com",
                "Tra",
                "Tra",
                "address12",
                null,
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //NAME: ten qua dai
    public void TestSignUpFailLongName() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                "tra8@gmail.com",
                "Tra",
                "Tra",
                "address12",
                generateRandomLongString(0),
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //EMAIL: No email
    public void TestSignUpFailNoEmail() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                null,
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }
    @Test //EMAIL: wrong type email
    public void TestSignUpFailWrongTypeEmail() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                "not_an_email",
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //EMAIL: email too long
    public void TestSignUpFailTooLongEmail() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                generateRandomLongString(1),
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //EMAIL: same email
    public void TestSignUpFailSameEmail() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                "tra8@gmail.com",
                "Tra",
                "Tra",
                "address12",
                "tt",
                "0940",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //PHONE: no phone
    public void TestSignUpFailNoPhone() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                "address12",
                "tt",
                null,
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //PHONE: too long phone
    public void TestSignUpFailTooLongPhone() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                "address12",
                "tt",
                generateRandomLongString(0),
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //ADDRESS: too long address
    public void TestSignUpFailTooLongAddress() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(
                generateRandomEmail(),
                "Tra",
                "Tra",
                generateRandomLongString(0), //somehow too long string does not work on this ?
                "tt",
                "0090",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            //assertEquals("1001", response.getResponseMessage());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //PASSWORD: too long password
    public void TestEditAccountFailTooLongPassword() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(//password dai qua thi khong chay
                generateRandomEmail(),
                "000000000000000000000000000000000000000000000000000",
                "Tra",
                "address12",
                "tt",
                "0090",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //PASSWORD: no password
    public void TestEditAccountFailNoPassword() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody( //khong co pass khong chay
                generateRandomEmail(),
                "",
                "tra",
                "address12",
                "tt",
                "0090",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }

    @Test //RE_PASS: no repass
    public void TestEditAccountFailNoRepass() throws Exception{
        JSONObject requestBody = buildSignUpRequestBody(//re_pass nhap nhu the nao cung duoc, server luon tra ve dung :v
                generateRandomEmail(),
                "tra",
                generateRandomLongString(0),
                "address12",
                "tt",
                "0090",
                null);

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");        }
    }
}
