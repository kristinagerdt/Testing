package testNG.gitHub;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class UserTest {
    private CloseableHttpClient client;
    private String baseURL = "https://api.github.com/";
    private HttpGet request;
    private HttpResponse response;
    private HttpPost postRequest;
    private HttpDelete deleteRequest;

    private String email = "email";
    private String user = "user";
    private String pass = "pass";
    // https://github.com/settings/tokens create token: delete_repo
    private String token = "token";

    @BeforeTest //before every test
    public void init() {
        client = HttpClientBuilder.create().build();
    }

    @Test
    public void testExistentUser200() throws IOException {
        request = new HttpGet(baseURL + "users/" + user);
        response = client.execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testNotExistentUser404() throws IOException {
        request = new HttpGet(baseURL + "users/" + "hjgfkdhf");
        response = client.execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testNotFoundUser404() throws IOException {
        request = new HttpGet(baseURL + user);
        response = client.execute(request);
        String actual = String.valueOf(response.getStatusLine().getStatusCode());
        String expected = String.valueOf(HttpStatus.SC_NOT_FOUND);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "users")
    public Object[][] data() {
        return new Object[][]{{user, "200"},
                {"hjgfkdhf", "404"}};
    }

    @Test(dataProvider = "users")
    public void testGetUserGeneric(String user, String status) throws IOException {
        request = new HttpGet(baseURL + "users/" + user);
        response = client.execute(request);
        String actual = String.valueOf(response.getStatusLine().getStatusCode());
        Assert.assertEquals(actual, status);
    }

    @DataProvider(name = "endpoints")
    public Object[][] url() {
        return new Object[][]{{"user"}, {"user/followers"}};
    }

    @Test(dataProvider = "endpoints")
    public void testNotAuthenticated403Generic(String endpoint) throws IOException {
        request = new HttpGet(baseURL + endpoint);
        response = client.execute(request);
        String actual = String.valueOf(response.getStatusLine().getStatusCode());
        String expected = String.valueOf(HttpStatus.SC_UNAUTHORIZED);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCheckResponseBody() throws IOException {
        request = new HttpGet(baseURL + "users/" + user);
        response = client.execute(request);
        User resource = RetrieveUtil.retrieveResourceFromResponse(response, User.class);
        Assert.assertEquals(user, resource.getLogin());
    }

    @Test
    public void testCheckResponseBodyJSON() throws IOException {
        request = new HttpGet(baseURL + "users/" + user);
        response = client.execute(request);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine()) != null) {
            result.append(line);
        }
        JSONObject o = new JSONObject(result.toString());
        System.out.println(o);
        Assert.assertEquals(user, o.getString("login"));
    }

    @Test
    public void checkResponseBodyStream() {
        Response response = RestAssured
                .given()
                .when()
                .get(baseURL + "users/" + user)
                .then()
                .statusCode(200)
                .extract()
                .response();
        JSONObject json = new JSONObject(response.asString());
        System.out.println(json);
        Assert.assertEquals(user, json.getString("login"));
    }

    private String makeHeader() {
        String auth = email + ":" + pass;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    @Test
    public void testPostRepo() throws IOException {
        postRequest = new HttpPost(baseURL + "/user/repos");

        String authHeader = makeHeader();
        System.out.println("AuthHeader " + authHeader);
        postRequest.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        String json = "{\"name\":\"newRepo_deleteMe\"}";
        postRequest.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        response = client.execute(postRequest);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 201);
    }

    @Test
    public void testPostRepoStream() {
        String authHeader = makeHeader();
        RestAssured.given()
                .header("Authorization", authHeader)
                .body("{\"name\":\"newRepo_deleteMeAgain\"}")
                .header("Content-type", "application/json")
                .when()
                .post(baseURL + "user/repos")
                .then()
                .statusCode(201)
                .and()
                .contentType("application/json")
                .extract()
                .response();
    }

    @Test(priority = 1) //0 - most important, default
    public void testDeleteRepo() throws IOException {
        deleteRequest = new HttpDelete(baseURL + "repos/" + user + "/newRepo_deleteMe");
        deleteRequest.setHeader(HttpHeaders.AUTHORIZATION, "token " + token);

        response = client.execute(deleteRequest);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
    }
}