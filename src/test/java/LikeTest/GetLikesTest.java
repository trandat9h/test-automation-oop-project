package LikeTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetLikesTest extends BaseTest {
    private static String endpoint = "/likes/";
    private static String params(
            String index,
            String count){
        if((index == null || index.equals("")) && (count == null|| count.equals(""))) return "";
        if(index == null || index.equals("")) return "?count=" + count;
        if(count == null|| count.equals("")) return "?index=" + index;
        return "?index=" + index +"&count="+count;
    }
    @Test
    public void TestGetListLike_Successfully() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1" + params("1","10"),
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertEquals("OK",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
    @Test
    public void TestGetListLike_NoTokenFailed() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1" + params("1","10")
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
    @Test
    public void TestGetListLike_NoCount() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1"+ params("1",null),
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(500, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
    @Test
    public void TestGetListLike_NoIndexFailed() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1"+params(null,"10"),
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(500, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
    @Test
    public void TestGetListLike_NoCountNoIndexFailed() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1" +params(null,null),
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(500, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
    @Test
    public void TestGetListLike_NocountNoIndexNoTokenFailed() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1"+params(null,null)
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }

}
