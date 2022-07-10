package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAuctionsTest extends BaseTest {
    private static final String endpoint = "/auctions";
    private static JSONObject buildGetAuctionsRequestBody(
            String index,
            String count) {
        JSONObject requestBody = new JSONObject();
        return requestBody;
    }
    @Test
    public void TestGetListAuctionsSuccessfully() throws Exception{
        JSONObject requestBody = buildGetAuctionsRequestBody("0", "2"); //url doesn't exist

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertNotNull(response.getResponseDataArray());
        } catch (Exception e) {
            throw new Exception("Error on get list auctions");
        }
    }
}
