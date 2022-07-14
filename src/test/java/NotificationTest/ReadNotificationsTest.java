package NotificationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Sử dụng tk devUser3@gmail.com (pass 123456) để tạo các phiên đấu giá có ID: 242, 243, 244, 245
// sau đó dùng tk Admin từ chối 2 AuctionID: 242, 243
public class ReadNotificationsTest extends BaseTest {
    private static String endpoint = "/notifications/read/";

    @Test
    public void TestReadNotificationsSuccessfully() throws Exception { //// auctionId = 242 đã bị từ chối bởi Admin, thuộc sở hữu devUser3

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "242",    //notifications/read/{auctionDenyId}
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertEquals("OK",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Reading Notifications.");
        }
    }
    @Test //Test failed do server
    public void TestReadNotificationsNotYetApprovedAuctionFailed() throws Exception {//auctionID=15 là phiên đấu giá "chờ phê duyệt" bởi admin

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "15",     //notifications/read/{auctionDenyId}
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotEquals("OK",response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
        } catch (Exception e) {
            throw new Exception("Error on Reading Notifications.");
        }
    }
    @Test //Test failed do server
    public void TestReadNotificationsApprovedAuctionFailed() throws Exception {// auctionId = 137 đã được chấp nhận bởi Admin

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137",//notifications/read/{auctionDenyId}
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotEquals("OK",response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
        } catch (Exception e) {
            throw new Exception("Error on Reading Notifications.");
        }
    }
    @Test
    public void TestReadNotificationsNoTokenFailed() throws Exception {// chưa đăng nhập

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "242"    //notifications/read/{auctionDenyId}
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Reading Notifications.");
        }
    }

}
