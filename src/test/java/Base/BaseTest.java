package Base;

import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;

public class BaseTest {
    protected static  JSONObject createdAccount = AuthHelper.createNewExistedRandomUser();
    protected static String authToken = AuthHelper.getAuthToken(createdAccount);
    private static JSONObject Account (
            String email,
            String password){
        JSONObject Body = new JSONObject();

        if (email != null)
            Body.put("email", email);
        if (password != null)
            Body.put("password", password);
        return Body;
    }
    protected static final String devUser2_Token = AuthHelper.getAuthToken(Account("devUser2@gmail.com","123456"));
    protected static final String devUser3_Token = AuthHelper.getAuthToken(Account("devUser3@gmail.com","123456"));
}
