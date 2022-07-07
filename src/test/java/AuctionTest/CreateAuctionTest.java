package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import Utils.HelperMethods.DateHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CreateAuctionTest extends BaseTest {
    private static final String endpoint = "/auctions/create";

    private static JSONObject buildCreateAuctionRequestBody(
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
    public void TestCreateAuctionSuccessfully() throws Exception {
        // This server does not have an endpoint for creating new category, so we have to hardcode this id
        //
        JSONObject requestBody = buildCreateAuctionRequestBody(
                1,
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
    public void TestCreateAuctionWithNoAuthToken() throws Exception {
        JSONObject requestBody = buildCreateAuctionRequestBody(
                1,
                DateHelper.generateOneDayAfterCurrentDate(),
                DateHelper.generateFutureDate(),
                AuctionHelper.generateRandomAuctionTitle()
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating auction request.");
        }
    }

    static Stream<Arguments> provider() {
        Date startDate = DateHelper.generateOneDayAfterCurrentDate();
        Date endDate = DateHelper.generateFutureDate();
        String title = AuctionHelper.generateRandomAuctionTitle();
        // Add more tests here
        return Stream.of(
                arguments(null, startDate, endDate, title),
                arguments(1, null, endDate, title),
                arguments(1, startDate, null, title),
                arguments(1, startDate, endDate, ""),
                arguments(1, startDate, endDate, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void TestCreateAuctionWithMissingData(
            Integer categoryId,
            Date startDate,
            Date endDate,
            String title
    ) throws Exception {
        JSONObject requestBody = buildCreateAuctionRequestBody(
                categoryId,
                startDate,
                endDate,
                title
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
    public void TestCreateAuctionWithInvalidCategoryId() throws Exception {
        JSONObject requestBody = buildCreateAuctionRequestBody(
                -1,
                DateHelper.generateOneDayAfterCurrentDate(),
                DateHelper.generateFutureDate(),
                AuctionHelper.generateRandomAuctionTitle()        );

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
    public void TestCreateAuctionWithNotExistedCategoryId() throws Exception {
        JSONObject requestBody = buildCreateAuctionRequestBody(
                1000000,
                DateHelper.generateOneDayAfterCurrentDate(),
                DateHelper.generateFutureDate(),
                AuctionHelper.generateRandomAuctionTitle()        );

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
    public void TestCreateAuctionWithInvalidDateFormat() throws Exception {
        JSONObject requestBody = new JSONObject();

        requestBody.put("category_id", (long) 1);
        requestBody.put("start_date", "");
        requestBody.put("end_date", "");
        requestBody.put("title_ni", "title");

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
    public void TestCreateAuctionWithPassedStartDate() throws Exception {
        JSONObject requestBody = buildCreateAuctionRequestBody(
                1000000,
                DateHelper.generatePassDate(),
                DateHelper.generateFutureDate(),
                AuctionHelper.generateRandomAuctionTitle()        );

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
    public void TestCreateAuctionWithInvalidEndDate() throws Exception {
        JSONObject requestBody = buildCreateAuctionRequestBody(
                1000000,
                DateHelper.generateOneDayAfterCurrentDate(),
                DateHelper.generatePassDate(),
                AuctionHelper.generateRandomAuctionTitle()        );

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
