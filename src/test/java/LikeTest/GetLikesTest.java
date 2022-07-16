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
    public void TestGetListLikeSuccessfully() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1" + params("1","10"), //    /likes/{statusId}
                authToken
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
    public void TestGetListLikeNoTokenFailed() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1" + params("1","10")    //    /likes/{statusId}
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
    @Test //fail do server
    public void TestGetListLikeNoCount() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1"+ params("1",null),   //    /likes/{statusId}
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();
            assertNotEquals(500, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
    @Test //fail do server
    public void TestGetListLikeNoIndexFailed() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "1"+ params(null,"10"), //    /likes/{statusId}
                authToken
        );

        try {
            CustomResponse response = httpRequest.get();

            assertNotEquals(500, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Getting List Like.");
        }
    }
}
