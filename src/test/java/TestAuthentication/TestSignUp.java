package TestAuthentication;

import Utils.CustomResponse;
import Utils.HTTPRequest;
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

public class TestSignUp {
    private static final String endpoint = "/signup";
    private static JSONObject createdAccount = buildSignUpRequestBody(
            "existed@gmail.com",
            "123",
            "123",
            "k",
            "existed",
            "12",
            null
    );

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
//        // Create an existed account
//        JSONObject existedAccount = createdAccount;
//
//        HTTPRequest httpRequest = new HTTPRequest(
//                endpoint,
//                null,
//                existedAccount,
//                null);
//        try {
//            CustomResponse response = httpRequest.post();
//        } catch (Exception e) {
//            throw new Exception("Error on creating existed account");
//        }
//    }

    @Test
    public void TestSignUpSuccessfully() throws Exception{
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
                null,
                requestBody,
                null
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(response.getStatusCode(), 200);
            assertEquals(response.getResponseMessage(), "OK");
            assertEquals(response.GetResponseCode(), "1000");
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
                null,
                createdAccount,
                null
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(response.getStatusCode(), 200);
            assertNotNull(response.getResponseMessage());
            assertEquals(response.GetResponseCode(), "1001");
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating new account");
        }

    }


}
