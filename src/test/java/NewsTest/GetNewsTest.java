package NewsTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetNewsTest extends BaseTest {
    private static String endpoint(
            String index,
            String count){
        if((index == null || index.equals("")) && (count == null|| count.equals(""))) return "/news";
        if(index == null || index.equals("")) return "/news?count=" + count;
        if(count == null|| count.equals("")) return "/news?index=" + index;
        return "/news?index=" + index +"&count="+count;
    }
    @Test
    public void TestGetNewsSuccessfully () throws Exception {

        String index = "1";
        String count = "3";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(index,count),
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception ex) {
            throw new Exception("Error on getting News");

        }
    }
    @Test  //Failed Test do Server (Token notnull), server nhả ra 1000 ok
    public void TestGetNewsNoTokenFailed () throws Exception{

        String index = "1";
        String count = "3";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(index,count)
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on getting News");

        }
    }
    @Test// fail do server, server nhả ra 500
    public void TestGetNewsNoIndexFailed () throws Exception {
        String index = null;
        String count = "3";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(index, count),
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertNotEquals("1000", response.GetResponseCode());
            assertNotEquals("OK", response.getResponseMessage());
            assertNull(response.getResponseData());
        } catch (Exception ex) {
            throw new Exception("Error on getting News");

        }
    }

    @Test // fail do server, server nhả ra 500
    public void TestGetNewsNoCountFailed () throws Exception {
        String index = "1";
        String count = null;

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(index, count) ,
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertNotEquals("1000", response.GetResponseCode());
            assertNotEquals("OK", response.getResponseMessage());
            assertNull(response.getResponseData());
        } catch (Exception ex) {
            throw new Exception("Error on getting News");

        }
    }
}
