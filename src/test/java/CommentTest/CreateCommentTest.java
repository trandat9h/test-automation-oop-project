package CommentTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.AuctionHelper;
import Utils.HelperMethods.DateHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CreateCommentTest extends BaseTest {
    private static String auctionId = AuctionHelper.getAuctionId();
    private static String endpoint = "/comments/create/" + auctionId;

    @BeforeEach
    public void Setup() {
        auctionId = AuctionHelper.getAuctionId(createdAccount);
        endpoint = "/comments/create/" + auctionId;
    }

    private static JSONObject buildCreateCommentRequestBody(
            String content,
            Integer comment_last_id
    ) {
        JSONObject requestBody = new JSONObject();

        if (content != null)
            requestBody.put("content", content);
        if (comment_last_id != null)
            requestBody.put("comment_last_id", comment_last_id);

        return requestBody;
    }

    @Test
    public void TestCreateCommentSuccessfully()  throws Exception {
        JSONObject requestBody = buildCreateCommentRequestBody(
                "abc",
                1
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating comment request.");
        }
    }

    @Test
    public void TestCreateCommentFailNotLogin()  throws Exception {
        JSONObject requestBody = buildCreateCommentRequestBody(
                "abc",
                1
        );

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint,
                requestBody
        );
        try {
            CustomResponse response = httpRequest.post();

            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getResponseMessage());
            assertEquals("1004", response.GetResponseCode());
            assertNull(response.getResponseData());
        } catch (Exception e) {
            throw new Exception("Error on creating comment request.");
        }
    }
}
