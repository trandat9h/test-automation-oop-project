package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetAuctionsByUserTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/auctions/listAuctionsByUser/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/auctions/listAuctionsByUser/" + auctionId;
    }
    private static JSONObject buildGetAuctionsByUserRequestBody(
            String index,
            String count) {
        JSONObject requestBody = new JSONObject();
        return requestBody;
    }

    @Test
    public void TestGetAuctionByUserSuccessfully()  throws Exception {
        JSONObject requestBody = buildGetAuctionsByUserRequestBody(
                "1",
                "9"
        );
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());//code:500(postman check)
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get auctions by user request.");
        }
    }


    @Test
    public void TestGetAuctionByUserFailNotLogin()  throws Exception {
        JSONObject requestBody = buildGetAuctionsByUserRequestBody(
                "1",
                "9"
        );
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());//this test worked but the test above didn't
            assertNotNull( response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get auctions by user request.");
        }
    }
}
