import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class OmdbApiTests extends Base {

//    have to get your own api key
//    http://www.omdbapi.com/apikey.aspx

    String apiKeyParam = "apikey";
    String apiKey = "7fa9faeb";

    @Test
    public void movieNotFound() {
        String titleParam = "t";
        String searchTitle = "qazwsxedc";

        Response response = given()
                .param(titleParam, searchTitle)
                .param(apiKeyParam, apiKey)
                .when()
                .get(server.getHost())
                .then()
                .statusCode(200)
                .extract()
                .response();

        String errorMessage = JsonPath.read(response.asString(), "$.Error");
        Assert.assertEquals(errorMessage, "Movie not found!");
    }

    @Test
    public void verifySearchIsCorrect() {
        String titleParam = "t";
        String searchTitle = "blade+runner";

        Response response = given()
                .param(titleParam, searchTitle)
                .param(apiKeyParam, apiKey)
                .when()
                .get(server.getHost())
                .then()
                .statusCode(200)
                .extract()
                .response();

        String actualTitle = JsonPath.read(response.asString(), "$.Title");
        Assert.assertTrue(actualTitle.contains("Blade") || actualTitle.contains("Runner"));
        String actualYear = JsonPath.read(response.asString(), "$.Year");
        Assert.assertEquals(actualYear, "1982");
    }

    @Test
    public void requestWithoutApiKey() {
        String titleParam = "t";
        String searchTitle = "blade+runner";

        given()
                .param(titleParam, searchTitle)
                .when()
                .get(server.getHost())
                .then()
                .statusCode(401)
                .extract()
                .response();
    }
}
