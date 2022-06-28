package SliderTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import static org.junit.jupiter.api.Assertions.*;

public class GetSliderTest extends BaseTest {
    private static final String endpoint = "/slider";

    @Test
    public void TestGetSliderNoToken () throws Exception{
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting slider");

        }
    }

}