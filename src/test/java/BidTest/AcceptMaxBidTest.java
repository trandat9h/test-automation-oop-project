package BidTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptMaxBidTest extends BaseTest {

    // Sử dụng 2 tài khoản devUser2@gmail.com (pass 123456) để tạo phiên đấu giá và devUser3@gmail.com (pass 123456) để trả giá với các phiên đấu
    // giá của devUser2@gmail.com

    //Vì test này dựa thời gian thực mà 17/07 bảo vệ nên sẽ tạo các phiên đấu giá để có đủ các case

    // Các phiên đấu giá do devUser2@gmail.com tạo có auctionID:
        // 134: từ 9/7 - 10/7 đến hôm 17 trạng thái phiên đấu giá "đã kết thúc" đã có người trả giá (10/7 chạy thử test)(thành công) -- 1000: OK, vẫn dùng để test tiếp 1010: đã bán
        // 166: giống 134 để chạy test vào hôm bảo vệ 17/07
        // 136: từ 9/7 - 14/7 đến hôm 17 trạng thái phiên đấu giá "đã kết thúc" nhưng chưa có ai trả giá (15/7 chạy thử test) --1011: chưa có trả giá nào
        // 137: từ 9/7 - 19/7 đến hôm 17 trạng thái phiên đấu giá "đang diễn ra" (chạy dc rồi) --1009: phiên đấu giá chưa kết thúc
        // 138: từ 19/7 - 25/7 đến hôm 17 trạng thái phiên đấu giá "chưa diễn ra" (chạy dc rồi) --1009: phiên đấu giá chưa kết thúc
    // Sử dụng phiên đấu giá có autionID = 8 không do devUser2@gmail.com tạo để test lỗi-- 1006: Không có quyền
    // Không sử dụng Token  --1004: chưa đăng nhập
    private static String auctionId;
    private static String endpoint = "/accept/";
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/accept/" + auctionId;
    }


    private static JSONObject buildAcceptMaxBidRequestBody(
            String selling_info
    ) {
        JSONObject requestBody = new JSONObject();

        if (selling_info != null)
            requestBody.put("selling_info", selling_info);
        return requestBody;
    }
    @Test
    public void TestAcceptMaxBid_NoToken() throws Exception { //chưa đăng nhập
        JSONObject requestBody = buildAcceptMaxBidRequestBody(
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "8",
                requestBody
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test
    public void TestAcceptMaxBid_NoAuthor() throws Exception { //Sử dụng phiên đấu giá có autionID = 8 không do devUser2@gmail.com tạo để test lỗi-- 1006: Không có quyền
        JSONObject requestBody = buildAcceptMaxBidRequestBody(
                "I accepted"
        );
        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "8",
                requestBody,
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1006", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test
    public void TestAcceptMaxBid_Successfully() throws Exception { //chấp nhận giá cao nhất cho phiên đấu giá 134: từ 9/7 - 10/7 đến hôm 17 trạng thái
        JSONObject requestBody = buildAcceptMaxBidRequestBody(     //phiên đấu giá "đã kết thúc" đã có người trả giá (11/7 chạy thử test) -- 1000: OK
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "134",
                requestBody,
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertEquals("Ok",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test
    public void TestAcceptMaxBid_SoldOut() throws Exception {        //chấp nhận LẠI giá cao nhất cho phiên đấu giá 134 sau khi có đã chấp nhận rồi
        JSONObject requestBody = buildAcceptMaxBidRequestBody(// Chạy sau TestAcceptMaxBid_Successfully --1010: đã bán
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "134",
                requestBody,
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1010", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test
    public void TestAcceptMaxBid_HaveNoBid() throws Exception { //chấp nhận giá cao nhất cho phiên đấu giá chưa có trả giá nào --1011: chưa có trả giá nào
        JSONObject requestBody = buildAcceptMaxBidRequestBody(
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "136",
                requestBody,
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1011", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }


    @Test
    public void TestAcceptMaxBid_OnGoing() throws Exception { //chấp nhận giá cao nhất cho phiên đấu giá đang diễn ra --1009: phiên đấu giá chưa kết thúc
        JSONObject requestBody = buildAcceptMaxBidRequestBody(
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137",
                requestBody,
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1009", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test
    public void TestAcceptMaxBid_NotYetStart() throws Exception { //chấp nhận giá cao nhất cho phiên đấu giá chưa diễn ra nhưng đã được chấp thuân --1009: phiên đấu giá chưa kết thúc
        JSONObject requestBody = buildAcceptMaxBidRequestBody(
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "138",
                requestBody,
                devUser2_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1009", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test
    public void TestAcceptMaxBid_AnUnapprovedAuction() throws Exception { //chấp nhận giá cao nhất cho phiên đấu giá chưa được phê duyệt
        Setup();
        JSONObject requestBody = buildAcceptMaxBidRequestBody(
                "I accepted"
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1009", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
    @Test // Test fail do server
    public void TestAcceptMaxBid_NoSellingInfo() throws Exception { //requestBody là params bắt buộc tuy nhiên bodyRequest ko có vẫn ko lỗi
        Setup();

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                authToken
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(500, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Accepting Max Bid request.");
        }
    }
}
