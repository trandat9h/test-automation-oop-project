package LikeTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetTotalLikesOfAuctionTest extends BaseTest {
    private static String endpoint = "/totalLikes/";

    @Test
    public void TestGetTotalLikesSuccessfully() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "136",//  /totalLikes/{auctionID}
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertEquals("OK",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Getting Total Like.");
        }
    }
    @Test
    public void TestGetTotalLikesNoTokenSuccessfully() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "136"
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertEquals("OK",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Getting Total Like.");
        }
    }
    @Test
    public void TestGetTotalLikesNotExistedAuctionID() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "100000",
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(404, response.getStatusCode());
            assertNotEquals("OK",response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting Total Like.");
        }
    }

}
