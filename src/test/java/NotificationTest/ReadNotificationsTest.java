package NotificationTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Sử dụng tk devUser3@gmail.com (pass 123456) để tạo các phiên đấu giá có ID:
//  +Admin đã từ chối: 242, 243, 244, 245, 475
//  +Chờ duyệt: 474, 476
//Sử dụng tk devUser2@gmail.com (pass 123456) để tạo các phiên đấu giá có ID:
//  +Đã chấp nhận: 137
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
    public void TestReadNotificationsNotYetApprovedAuctionFailed() throws Exception {//auctionID=474 là phiên đấu giá "chờ phê duyệt" bởi admin

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "474",     //notifications/read/{auctionDenyId}
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertNotNull(response.GetResponseCode());
        } catch (Exception e) {
            throw new Exception("Error on Reading Notifications.");
        }
    }
    @Test //Test failed do server
    public void TestReadNotificationsNoAuthorFailed() throws Exception {//auctionID=162 là phiên đấu giá của tài khoản khác(đã bị từ chối)

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "162",     //notifications/read/{auctionDenyId}
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertEquals("1006", response.GetResponseCode()); //khong co quyen
            assertNotNull(response.getResponseMessage());
        } catch (Exception e) {
            throw new Exception("Error on Reading Notifications.");
        }
    }
    @Test //Test failed do server
    public void TestReadNotificationsApprovedAuctionFailed() throws Exception {// auctionId = 137 đã được chấp nhận bởi Admin

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137",//notifications/read/{auctionDenyId}
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.get();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertNotNull(response.GetResponseCode());
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
