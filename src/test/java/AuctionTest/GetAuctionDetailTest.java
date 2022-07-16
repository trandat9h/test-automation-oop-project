package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAuctionDetailTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/auctions/detail/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/auctions/detail/" + auctionId;
    }

    @Test
    public void TestGetAuctionDetailSuccessfully()  throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint
        );
        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode()); //code:500 server error(postman check)
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get auctions detail request.");
        }
    }
}
