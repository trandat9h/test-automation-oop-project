package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import Utils.HelperMethods.DateHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EditAuctionTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/auctions/edit/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/auctions/edit/" + auctionId;
    }

    private static JSONObject buildEditAuctionRequestBody(
            Integer categoryId,
            Date startDate,
            Date endDate,
            String title
    ) {
        JSONObject requestBody = new JSONObject();

        if (categoryId != null)
            requestBody.put("category_id", categoryId);
        if (startDate != null)
            requestBody.put("start_date", DateHelper.convertDateTimeToJSONFormat(startDate));
        if (endDate != null)
            requestBody.put("end_date", DateHelper.convertDateTimeToJSONFormat(endDate));
        if (title != null)
            requestBody.put("title_ni", title);

        return requestBody;
    }


    @Test
    public void TestEditSuccessfully()  throws Exception {
        // category_id : 2 is a hardcode id, this might not work if we change the url
        JSONObject requestBody = buildEditAuctionRequestBody(
                2,
                DateHelper.generateOneDayAfterCurrentDate(),
                DateHelper.generateFutureDate(),
                AuctionHelper.generateRandomAuctionTitle()
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
            throw new Exception("Error on creating auction request.");
        }
    }

    @Test
    public void TestEditAuctionWithUnauthorizedUser() throws Exception {
        // category_id : 2 is a hardcode id, this might not work if we change the url
        JSONObject requestBody = buildEditAuctionRequestBody(
                2,
                null,
                null,
                null
        );

        String newAuctionId = AuctionHelper.getAuctionId();
        endpoint = "/auctions/edit/" + newAuctionId;

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1006", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating auction request.");
        }
    }

    @Test
    public void TestEditAuctionWithNoData() throws Exception {
        JSONObject requestBody = buildEditAuctionRequestBody(
                null,
                null,
                null,
                null
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
            throw new Exception("Error on creating auction request.");
        }
    }

    @Test
    public void TestEditStartDateWithPastDate() throws Exception {
        JSONObject requestBody = buildEditAuctionRequestBody(
                null,
                null,
                DateHelper.generatePassDate(),
                null
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
            throw new Exception("Error on creating auction request.");
        }
    }

    @Test
    public void TestEditTitleWithExistedTitle() throws Exception {
        JSONObject createdAuction = AuctionHelper.createAuction(createdAccount);

        String existedTitle = createdAuction.get("title").toString();

        JSONObject requestBody = buildEditAuctionRequestBody(
                null,
                null,
                null,
                existedTitle
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
            throw new Exception("Error on creating auction request.");
        }




    }

}
