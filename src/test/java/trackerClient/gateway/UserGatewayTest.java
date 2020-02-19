package trackerClient.gateway;

import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trackerClient.model.Token;
import trackerClient.model.User;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;

public class UserGatewayTest {
    private UserGateway userGateway;
    private User user;

    @BeforeTest
    public void setUp() {
        userGateway = new UserGateway(new RestTemplate());
        user = createUser();
    }

    @Test
    public void testRegisterUser() {
        boolean isRegistered = userGateway.registerUser(user);
        assertTrue(isRegistered);
    }

    @Test
    public void testLoginUser() {
        Token token = userGateway.loginUser(user);
        assertNotEquals(token.getToken(), "");
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("Smith");
        user.setUserName("alex");
        user.setPassword("123");
        user.setRepeatPassword("123");
        return user;
    }
}