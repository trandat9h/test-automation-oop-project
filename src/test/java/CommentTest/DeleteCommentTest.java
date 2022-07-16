package CommentTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Sử dụng tk devUser3@gmail.com (pass 123456) để tạo các comment, hệ thống lưu các cmt của tất cả người dùng vào cùng 1 bảng phân biệt bằng
// commentID, tk devUser3@gmail.com có quyền với các commentID: 19,20,21,22,23,24,25
// sau đó dùng tk devUser2@gmail.com (pass 123456) để tạo các comment có commentID: 12:dã bị xóa,(13,14,15,16):chưa bị xóa
public class DeleteCommentTest extends BaseTest {
    private static String endpoint = "/comments/delete/";

    @Test
    public void TestDeleteCmtSuccessfully() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "18",
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertEquals("OK",response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Deleting Comment Test.");
        }
    }
    @Test
    public void TestDeleteCmtNoTokenFailed() throws Exception {//1004: chưa đăng nhập

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "13"
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Deleting Comment Test.");
        }
    }
    @Test
    public void TestDeleteCmtNoAuthorFailed() throws Exception {//1006: không có quyền

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "13",
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1006", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on Deleting Comment Test.");
        }
    }
    @Test
    public void TestDeleteCmtNotExistedFailed() throws Exception {//CommentID đã bị xóa và ko còn tồn tại trong database
        HTTPRequest httpRequest = new HTTPRequest(                // CommentID =17 sẽ bị xóa sau khi chạy TestDeleteCmt_Successfully
                endpoint + "17",
                devUser3_Token
        );

        try {
            CustomResponse response = httpRequest.post();
            assertEquals(404, response.getStatusCode());
        } catch (Exception e) {
            throw new Exception("Error on Deleting Comment Test.");
        }
    }
}
