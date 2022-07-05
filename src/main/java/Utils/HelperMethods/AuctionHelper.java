package Utils.HelperMethods;

import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class AuctionHelper {
    public static String generateRandomAuctionTitle() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        // Random title format: "randomtitle<uuid>"
        return "randomtitle" + uuidAsString;
    }

    public static JSONObject createAuction(JSONObject account) {
        String authToken = AuthHelper.getAuthToken(account);

        JSONObject requestBody  =new JSONObject();

        requestBody.put("category_id", 1);
        requestBody.put("start_date", DateHelper.convertDateTimeToJSONFormat(DateHelper.generateOneDayAfterCurrentDate()));
        requestBody.put("end_date", DateHelper.convertDateTimeToJSONFormat(DateHelper.generateFutureDate()));
        requestBody.put("title_ni", AuctionHelper.generateRandomAuctionTitle());

        HTTPRequest httpRequest = new HTTPRequest(
                "/auctions/create",
                requestBody,
                authToken
        );
        try {
            CustomResponse response = httpRequest.post();
            return response.getResponseData();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAuctionId () {
        JSONObject existedAccount = AuthHelper.createNewExistedRandomUser();
        JSONObject auction = createAuction(existedAccount);
        if (auction != null)
            return auction.get("auction_id").toString();

        return null;
    }

    public static String getAuctionId(JSONObject account) {
        JSONObject auction = createAuction(account);
        if (auction != null)
            return auction.get("auction_id").toString();

        return null;

    }


    public static Integer getRandomCategoryId(){
        HTTPRequest httpRequest = new HTTPRequest("/categories");

        try {
            CustomResponse response = httpRequest.get();

            if (response.getStatusCode() != 500)
                throw new Exception();

            JSONObject responseData = response.getResponseData();

            JSONArray categories = (JSONArray) responseData.get("categories");
            if (categories != null && categories.size() != 0 ) {
                JSONObject firstCategory = (JSONObject) categories.get(0);
                Integer firstCategoryId = (Integer) firstCategory.get("category_id");

                System.out.println(firstCategoryId);

                return firstCategoryId;
            }
            else
                return null;
        } catch (Exception e) {
            System.out.println("Error on getting mock category id.");
            return null;
        }
    }
}
