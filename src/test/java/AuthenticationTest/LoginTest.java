package AuthenticationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTest extends BaseTest {
    private static final String endpoint = "/login";
    
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
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccountNoEmail
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
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccountNoPassword
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
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                createdAccountEmailLong
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
}
