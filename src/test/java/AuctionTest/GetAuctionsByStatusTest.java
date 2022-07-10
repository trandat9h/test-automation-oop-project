package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAuctionsByStatusTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/auctions/listAuctionsByStatus/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/auctions/listAuctionsByStatus/" + auctionId;
    }
    private static JSONObject buildGetAuctionsByStatusRequestBody(
            String index,
            String count) {
        JSONObject requestBody = new JSONObject();
        return requestBody;
    }

    @Test
    public void TestGetAuctionByStatusSuccessfully()  throws Exception {
        JSONObject requestBody = buildGetAuctionsByStatusRequestBody(
                "1",
                "2"
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
            throw new Exception("Error on get auctions by status request.");
        }
    }
}
