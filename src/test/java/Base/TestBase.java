package Base;

import Utils.HelperMethods.AuthHelper;
import org.json.simple.JSONObject;

public class TestBase {
    protected static final JSONObject createdAccount = AuthHelper.createNewExistedRandomUser();
}
