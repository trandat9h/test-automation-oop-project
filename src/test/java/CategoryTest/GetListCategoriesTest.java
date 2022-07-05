package CategoryTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetListCategoriesTest extends BaseTest {
    private static final String endpoint = "/categories";
    @Test
    void TestGetListCategoriesSuccessfully() throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(//brand works fine but categories doesnt
                endpoint
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertNotNull(response.getResponseDataArray());
        } catch (Exception e) {
            throw new Exception("Error on get list categories");
        }
    }
}
