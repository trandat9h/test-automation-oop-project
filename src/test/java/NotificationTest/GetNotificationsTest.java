package NotificationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuthHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetNotificationsTest extends BaseTest {
    private static String endpoint(
            String index,
            String count,
            String is_not_read){
        if((index == null || index.equals("")) && (count == null || count.equals("")) && (is_not_read == null || is_not_read.equals(""))) return "/notifications";
        if((is_not_read == null || is_not_read.equals("")) && (index == null || index.equals(""))) return "/notifications?count=" + count;
        if((is_not_read == null || is_not_read.equals("")) && (count == null || count.equals(""))) return "/notifications?index=" + index;
        if((count == null || count.equals("")) && (index == null || index.equals(""))) return "/notifications?is_not_read=" + is_not_read;
        if(index == null || index.equals("")) return "/notifications?count=" + count + "&is_not_read="+is_not_read;
        if(count == null|| count.equals("")) return "/notifications?index=" + index + "&is_not_read="+is_not_read;
        if(is_not_read == null || is_not_read.equals("")) return "/notifications?index=" + index +"&count="+count;
        return "/notifications?index=" + index +"&count="+count + "&is_not_read=" + is_not_read;
    }

    @BeforeEach
    public void Setup() {
        createdAccount = AuthHelper.createNewExistedRandomUser();
        authToken = AuthHelper.getAuthToken(createdAccount);
    }

    @Test
    public void TestGetNotificationsSuccessfully () throws Exception{
        String index = "1";
        String count = "3";
        String is_not_read = "";

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(index,count,is_not_read),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }
    @Test
    public void TestGetNotificationsNoTokenFailed () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint("1","2","")
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }
    @Test
    public void TestGetNotificationsNoIndexNoTokenFailed () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(null,"2","")
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }
    @Test
    public void TestGetNotificationsNoCountNoTokenFailed () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint("1","2","")
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }
    @Test
    public void TestGetNotificationsNoIndexNoCountNoTokenFailed () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint("1","2",null)
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }

    @Test  //test failed do server (count NOTNULL)
    public void TestGetNotificationsNoCountFailed () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint("1",null,null),
                authToken
        );


        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertNotEquals("OK",response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }

    @Test //test failed do server (index NOTNULL)
    public void TestGetNotificationsNoIndexFailed () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint(null,"2",null),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200, response.getStatusCode());
            assertNotEquals("OK",response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
        }catch(Exception ex){
            throw new Exception("Error on getting Notifications");

        }
    }
}
