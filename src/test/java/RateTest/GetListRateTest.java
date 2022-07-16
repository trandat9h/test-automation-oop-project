package RateTest;

import Base.BaseTest;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import Utils.HelperMethods.DateHelper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class GetListRateTest extends BaseTest {
    private static final String endpoint = "/rates/";
    private static String params(
            String index,
            String count){
        if((index == null || index.equals("")) && (count == null|| count.equals(""))) return "";
        if(index == null || index.equals("")) return "?count=" + count;
        if(count == null|| count.equals("")) return "?index=" + index;
        return "?index=" + index +"&count="+count;
    }
    @Test
    public void TestGetListRatesSuccessfully () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137" + params("1","3"),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on getting list rates");

        }
    }
    @Test
    public void TestGetListRatesNoTokenSuccessfully () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137"+ params("1","3")
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertEquals("OK", response.getResponseMessage());
            assertEquals("1000", response.GetResponseCode());
            assertNotNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on getting list rates");

        }
    }

    @Test//Failed Test do Server (Index notnull), server nhả ra 1000 ok
    public void TestGetListRatesNoIndexFail () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137"+ params(null,"3"),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotEquals("OK", response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
            assertNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on getting list rates");

        }
    }

    @Test//Failed Test do Server (Count notnull), server nhả ra 1000 ok
    public void TestGetListRatesNoCountFail () throws Exception{

        HTTPRequest httpRequest = new HTTPRequest(
                endpoint + "137"+ params("1",null),
                authToken
        );

        try{
            CustomResponse response = httpRequest.get();

            assertEquals(200,response.getStatusCode());
            assertNotEquals("OK", response.getResponseMessage());
            assertNotEquals("1000", response.GetResponseCode());
            assertNull(response.getResponseData());
        }catch(Exception ex){
            throw new Exception("Error on getting list rates");

        }
    }

}
