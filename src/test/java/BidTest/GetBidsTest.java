package BidTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetBidsTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/bids/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/bids/" + auctionId;
    }
    private static JSONObject buildGetBidsRequestBody(
            String index,
            String count) {
        JSONObject requestBody = new JSONObject();
        return requestBody;
    }
    @Test
    public void TestGetListBidsSuccessfully() throws Exception{
        JSONObject requestBody = buildGetBidsRequestBody("0", "2");

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get bids auctions");
        }
    }
}
