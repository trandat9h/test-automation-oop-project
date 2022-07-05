package AuctionTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import Utils.HelperMethods.DateHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CreateItemTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/items/create/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/items/create/" + auctionId;
    }

    private static JSONObject buildCreateItemRequestBody(
            String name,
            Integer starting_price,
            Integer brand_id,
            String description,
            String series
    ) {
        JSONObject requestBody = new JSONObject();

        if (name != null)
            requestBody.put("name", name);
        if (starting_price != null)
            requestBody.put("starting_price", starting_price)  ;
        if (brand_id != null)
            requestBody.put("brand_id", brand_id);
        if (description != null)
            requestBody.put("description", description);
        if (series != null)
            requestBody.put("series", series);

        return requestBody;
    }

    private static String  getRandomSeries() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000000));
    }

    @Test
    public void TestCreateItemSuccessfully() throws Exception {
        JSONObject requestBody = buildCreateItemRequestBody(
                "Random Item Name",
                100,
                1,
                "Random Address",
                getRandomSeries()
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
            throw new Exception("Error on creating item request.");
        }
    }

    @Test
    public void TestCreateItemWithNoAuthHeader() throws Exception {
        JSONObject requestBody = buildCreateItemRequestBody(
                "Random Item Name",
                100,
                1,
                "Random Address",
                getRandomSeries()
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
            throw new Exception("Error on creating item request.");
        }
    }

    static Stream<Arguments> provider() {
        Date startDate = DateHelper.generateOneDayAfterCurrentDate();
        Date endDate = DateHelper.generateFutureDate();
        String title = AuctionHelper.generateRandomAuctionTitle();
        // Add more tests here
        return Stream.of(
                arguments(null, 100, 1, "description", getRandomSeries()),
                arguments("random name", null, 1, "description", getRandomSeries()),
                arguments("random name", 100, null, "description", getRandomSeries()),
                arguments("random name", 100, 1, null, getRandomSeries())
                // Do not test 'series' as it is a nullable value
        );
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void TestCreateItemWithMissingData(
            String name,
            Integer starting_price,
            Integer brand_id,
            String description,
            String series
    ) throws Exception {
        JSONObject requestBody = buildCreateItemRequestBody(
                name,
                starting_price,
                brand_id,
                description,
                series
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
            throw new Exception("Error on creating item request.");
        }
    }

    @Test
    public void TestCreateItemWithTooLongName() throws Exception {
        // Generate long name
        String longName = "longname";
        for(int i = 0;i < 255;i++)
            longName += "e"; // random character

        JSONObject requestBody = buildCreateItemRequestBody(
                longName,
                100,
                1,
                "Random Address",
                getRandomSeries()
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
            throw new Exception("Error on creating item request.");
        }
    }

    @Test
    public void TestCreateItemWithInvalidStartingPrice() throws Exception {
        JSONObject requestBody = new JSONObject();

        requestBody.put("name", "Random Item name");
        requestBody.put("starting_price", "1"); // This value is expected to be in integer type
        requestBody.put("brand_id", 1);
        requestBody.put("description", "description");
        requestBody.put("series", getRandomSeries());

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
            throw new Exception("Error on creating item request.");
        }
    }

    @Test
    public void TestCreateItemWithTooLongSeries() throws Exception {
        // Generate long random series

        String randomSeries = getRandomSeries();
        randomSeries += "12345678910"; // Make sure to be more than 10 character and unique;


        JSONObject requestBody = buildCreateItemRequestBody(
                "Random Item Name",
                100,
                1,
                "Random Address",
                randomSeries
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
            throw new Exception("Error on creating item request.");
        }
    }

    @Test
    public void TestCreateItemWithExistedSeries() throws Exception {
        String randomSeries  = getRandomSeries();
        JSONObject requestBody = buildCreateItemRequestBody(
                "Random Item Name",
                100,
                1,
                "Random Address",
                randomSeries
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse mock_response = httpRequest.post();
            CustomResponse second_response = httpRequest.post();

            assertEquals(200, second_response.getStatusCode());
            assertNotNull(second_response.getResponseMessage());
            assertEquals("1001", second_response.GetResponseCode());
            assertNull(second_response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating item request.");
        }
    }

    @Test
    public void TestCreateItemWithInvalidSeries() throws Exception {
        JSONObject requestBody = new JSONObject();

        requestBody.put("name", "Random Item name");
        requestBody.put("starting_price", "1");
        requestBody.put("brand_id", 1);
        requestBody.put("description", "description");
        requestBody.put("series", 1); // This value is expected to be in String type

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
            throw new Exception("Error on creating item request.");
        }

    }




}
