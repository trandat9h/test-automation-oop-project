package ChatTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetListChatTest extends BaseTest {

    private static final String endpoint = "/chat";
    @Test
    void GetListChatSuccessfully() throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get list chat");
        }
    }

    @Test
    void GetListChatFailNotLogin() throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertNotNull( response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get list chat");
        }
    }
}
