package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InfoAuctionTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/auctions/info/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/auctions/info/" + auctionId;
    }

    @Test
    public void TestGetInfoAuctionSuccessfully()  throws Exception {

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
            throw new Exception("Error on get info auctions request.");
        }
    }

    @Test
    public void TestGetInfoFailNotLogin()  throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint
        );
        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertNotNull( response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
        } catch (Exception e) {
            throw new Exception("Error on get info auctions request.");
        }
    }
}
