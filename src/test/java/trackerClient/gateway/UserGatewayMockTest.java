package trackerClient.gateway;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trackerClient.model.Token;
import trackerClient.model.User;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

public class UserGatewayMockTest {
    private String registerUserUrl = "http://localhost:8080/user/register";
    private String loginUserUrl = "http://localhost:8080/user/login";

    private RestTemplate restTemplate;
    private MockRestServiceServer server;
    private UserGateway userGateway;
    private User user;

    @BeforeTest
    public void setUp() {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        server = MockRestServiceServer.bindTo(restTemplate).build();
        userGateway = new UserGateway(restTemplate);
        user = createUser();
    }

    @Test
    public void testRegisterUser() {
        server.expect(ExpectedCount.manyTimes(), requestTo(registerUserUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(registerUserJson))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        boolean isRegistered = userGateway.registerUser(user);
        assertTrue(isRegistered);
        server.verify();
    }

    @Test
    public void testTwiceRegisterUser() {
        server.expect(ExpectedCount.manyTimes(), requestTo(registerUserUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withServerError()); // Response 500 INTERNAL_SERVER_ERROR

        boolean isRegistered = userGateway.registerUser(user);
        assertFalse(isRegistered);
        server.verify();
    }

    @Test
    public void testLoginUser() {
        server.expect(ExpectedCount.manyTimes(), requestTo(loginUserUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(loginUserJson))
                .andRespond(withSuccess(tokenJson, MediaType.APPLICATION_JSON));

        Token token = userGateway.loginUser(user);
        assertNotEquals(token.getToken(), "");
    }

    @Test
    public void testFailedLoginUser() {
        server.expect(ExpectedCount.manyTimes(), requestTo(loginUserUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(loginUserJson))
                .andRespond(withUnauthorizedRequest()); // Response 401 UNAUTHORIZED

        Token token = userGateway.loginUser(createUser());
        assertNull(token);
        server.verify();
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("Smith");
        user.setUsername("alex");
        user.setPassword("123");
        user.setRepeatPassword("123");
        return user;
    }

    static String registerUserJson = "{\"firstName\":\"Alex\", \"lastName\":\"Smith\", \"password\":\"123\", " +
            "\"repeatPassword\":\"123\", \"username\":\"alex\"}";
    static String loginUserJson = "{ \"password\":\"123\", \"username\":\"alex\"}";
    static String tokenJson = "{\"token\":\"76d3bcfe-55f9-4110-9dd0-4fba27c1b26d\"}";
}