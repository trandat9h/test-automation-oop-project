package NewsTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReadNewsTest extends BaseTest {
    private static String endpoint(String newId) {
        return "/news/read/" + newId;
    }
    @Test
    public void TestReadNewsSuccessfully () throws Exception{
        String newID = "1";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(newID),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            JSONObject data = response.getResponseData();
            long is_read_expected = 1;
            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertEquals(is_read_expected, data.get("is_read"));
        }catch(Exception ex){
            throw new Exception("Error on Reading News");

        }
    }
    @Test   //Failed Test do Server (Token NOTNULL)
    public void TestReadNewsNoTokenFailed () throws Exception{
        String newID = "1";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(newID)
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertEquals("1000", response.GetResponseCode());
            assertEquals("OK", response.getResponseMessage());
            assertNotNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on Reading News");

        }
    }
    @Test
    public void TestReadNoNewIDFailed () throws Exception{
        String newID = "";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(newID),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(404,response.getStatusCode());
        }catch(Exception ex){
            throw new Exception("Error on Reading News");

        }
    }

    @Test
    public void TestReadNotFoundNewsIDFailed () throws Exception{
        String newID = "4";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(newID),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(404,response.getStatusCode());
        }catch(Exception ex){
            throw new Exception("Error on Reading News");

        }
    }

}
