package TestAuthentication;

import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import Base.TestBase;
import Utils.HelperMethods.AuthHelper;

public class TestSignUp extends TestBase{
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

//    @BeforeAll
//    private static void Setup() throws Exception{
//
//    }

    @Test
    public void TestSignUpSuccessfully() throws Exception{
        JSONObject requestBody = AuthHelper.createNewRandomUser();

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

    @Test
    public void TestSignUpWithExistedAccount () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccount
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }

    }


}
