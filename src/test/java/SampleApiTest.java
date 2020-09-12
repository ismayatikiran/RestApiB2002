import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SampleApiTest {


    public static String dologin() throws IOException {
        String url = "http://localhost:8090/rest/auth/1/session";
        String loginPayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/payloads/login.json")));

        RequestSpecification requestSpecification = new RestAssured().given().body(loginPayload);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.post(url);
        //System.out.println(response);

       System.out.println("Status Code: "+response.getStatusCode()+"\n"
             +response.asString());

        String stringResponse = response.asString();
        JsonPath jsonPath = new JsonPath(stringResponse);

        //String has been created to get the SessionID
        String sessionId = jsonPath.get("session.value");
        System.out.println("Session Id: "+sessionId);

        //Printing Json Path
        String loginPrevioustime = jsonPath.get("loginInfo.previousLoginTime");
        System.out.println("Login previous time: "+loginPrevioustime);

        return sessionId;

    }





    public static Response createIssue() throws IOException {
        String url = "http://localhost:8090/rest/api/2/issue";
        String createIssuePayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/payloads/createIssue.json")));
        RequestSpecification requestSpecification = RestAssured.given().body(createIssuePayload);

        //Specify headers
         requestSpecification.contentType(ContentType.JSON);

        //Setting the SessionID by passing through login
         String sessionID= "JSESSIONID="+dologin();
         requestSpecification.header("Cookie",sessionID);

        //Making a call
        Response response = requestSpecification.post(url);
        System.out.println("Status Code: "+response.statusCode());

        return response;

    }

@Test
public static void createIssueTest() throws IOException {
        int statusCode = createIssue().statusCode();
    Assert.assertEquals(201,statusCode);
    System.out.println("Test Passed with Status code 201: "+statusCode);


}





    }
