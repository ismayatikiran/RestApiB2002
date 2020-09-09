import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SampleApiTest {

    public void dologin() throws IOException {
        String url = "http://localhost:8090/rest/auth/1/session";
        String loginPayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"payloads/login.json")));



    }
}