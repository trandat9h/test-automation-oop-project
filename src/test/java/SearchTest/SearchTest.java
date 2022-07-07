package SearchTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest extends BaseTest {
    private static final String endpoint = "/search";

    private static JSONObject buildSearchRequestBody(
            String type,
            String key) {
        JSONObject requestBody = new JSONObject();

        if (type != null)
            requestBody.put("type", type);
        if (key != null)
            requestBody.put("key", key);

        return requestBody;
    }
    @Test
    public void TestSearchSuccess () throws Exception{
        JSONObject requestBody = buildSearchRequestBody(
                "1",
                "Tìm kiếm theo giá khởi điểm");
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on searching success");

        }
    }

    @Test
    public void TestSearchNoResult () throws Exception{
        JSONObject requestBody = buildSearchRequestBody(
                "1",
                "Daugia");
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("9998", response.GetResponseCode());
            assertNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on searching result");

        }
    }
}
