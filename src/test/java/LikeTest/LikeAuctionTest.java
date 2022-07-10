package LikeTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LikeAuctionTest extends BaseTest  {
    private static String endpoint = "/updateLike/";

    @Test
    public void TestLikeAuction_NoToken() throws Exception { //chưa đăng nhập

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint+ "136"
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Updating Like.");
        }
    }
    @Test
    public void TestLikeAution_Successfully() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "136",
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertEquals("OK",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Updating Like.");
        }
    }
    @Test//Test fail do server
    public void TestLikeAuction_NotExistedAuctionIDFailed() throws Exception { //AuctionID ko tồn tại tuy nhiên vẫn trả về OK

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "100000",
                authToken
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(404, response.getStatusCode());
            assertNotEquals("OK",response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Updating Like.");
        }
    }
}
