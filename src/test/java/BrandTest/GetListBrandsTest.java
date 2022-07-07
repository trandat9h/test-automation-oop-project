package BrandTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetListBrandsTest extends BaseTest {
    private static final String endpoint = "/brands";
    @Test
    void getListBrandsSuccessfully() throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on get list brands");
        }
    }
}
