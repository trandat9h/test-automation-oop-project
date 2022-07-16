package AuthenticationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static Utils.HelperMethods.AuthHelper.getAuthToken;
import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest extends BaseTest {
    private static final String endpoint = "/logout";

    @Test
    void logoutSuccessfully() throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                authToken
        );

        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on logout");
        }
    }
}
