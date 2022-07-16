package BidTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateBidTest extends BaseTest {
        private static String auctionId = AuctionHelper.getAuctionId();
        private static String endpoint = "/bids/create/" + auctionId;

        @BeforeEach
        public void Setup() {
            auctionId = AuctionHelper.getAuctionId(createdAccount);
            endpoint = "/bids/create/" + auctionId;
        }

        private static JSONObject buildCreateBidRequestBody(
                Number price,
                Integer bid_last_id
        ) {
            JSONObject requestBody = new JSONObject();

            if (price != null)
                requestBody.put("price", price);
            if (bid_last_id!= null)
                requestBody.put("bid_last_id", bid_last_id);

            return requestBody;
        }

    private static JSONObject buildCreateBidNonNumberRequestBody(
            String price,
            Integer bid_last_id
    ) {
        JSONObject requestBody = new JSONObject();

        if (price != null)
            requestBody.put("price", price);
        if (bid_last_id!= null)
            requestBody.put("bid_last_id", bid_last_id);

        return requestBody;
    }

    @Test
    public void TestCreateBidSuccessfully()  throws Exception {
        JSONObject requestBody = buildCreateBidRequestBody(
                100000,
                2
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating bids request.");
        }
    }

    @Test
    public void TestCreateBidFailNoPrice()  throws Exception {
        JSONObject requestBody = buildCreateBidRequestBody(
                null,
                2
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating bids request.");
        }
    }

    @Test
    public void TestCreateBidFailIsNumber()  throws Exception {
        JSONObject requestBody = buildCreateBidNonNumberRequestBody(
                "bbbbbb",
                2
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating bids request.");
        }
    }

    @Test
    public void TestCreateBidFailLowerPrice()  throws Exception {
        JSONObject requestBody = buildCreateBidRequestBody(
                0,
                1
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1001", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating bids request.");
        }
    }


    @Test
    public void TestCreateFailAuctionEnd()  throws Exception {
        JSONObject requestBody = buildCreateBidRequestBody(
                100000,
                2
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull( response.getResponseMessage());
            assertEquals("1008", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating bids request.");
        }
    }
}
