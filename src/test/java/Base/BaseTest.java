package Base;

import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;

public class BaseTest {
    protected static final JSONObject createdAccount = AuthHelper.createNewExistedRandomUser();
    protected static final String authToken = AuthHelper.getAuthToken(createdAccount);


}
