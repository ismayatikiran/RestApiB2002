import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SampleApiTest {

    public static void dologin() throws IOException {
        String url = "http://localhost:8090/rest/auth/1/session";
        String loginPayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/payloads/login.json")));

        RequestSpecification requestSpecification = new RestAssured().given().body(loginPayload);
        requestSpecification.contentType(ContentType.JSON);
        Response response = requestSpecification.post(url);
//        System.out.println(response);
       System.out.println("Status Code: "+response.getStatusCode()+"\n"
             +response.asString());

        String stringResponse = response.asString();
        JsonPath jsonPath = new JsonPath(stringResponse);
        String sessionId = jsonPath.get("session.value");
        System.out.println("Session Id: "+sessionId);
        String loginPrevioustime = jsonPath.get("loginInfo.previousLoginTime");
        System.out.println("Login previous time: "+loginPrevioustime);
    }





    public static void creatIssueTest() throws IOException {
        String url = "http://localhost:8090/rest/auth/2/session";
        String creatIssuePayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/payloads/createIssue.json")));
        RequestSpecification requestSpecification = RestAssured.given().body(creatIssuePayload);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("");


      //  dologin();
    }







    public static void main(String[] args) throws IOException {
        creatIssueTest();
    }
}