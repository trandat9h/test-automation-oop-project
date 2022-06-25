package Base;

import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;

public class BaseTest {
    protected static final JSONObject createdAccount = AuthHelper.createNewExistedRandomUser();
    protected static final String authToken = AuthHelper.getAuthToken(createdAccount);
    protected static final JSONObject createdAccountNoEmail = AuthHelper.createNewExistedRandomUserNoEmail();
    protected static final JSONObject createdAccountNoPassword = AuthHelper.createNewExistedRandomUserNoPassword();
    protected static final JSONObject createdAccountEmailLong = AuthHelper.createNewExistedRandomUserEmailLong();
}
