package trackerClient.gateway;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import trackerClient.model.Token;
import trackerClient.model.User;

public class UserGateway {
    private String registerUserUrl = "http://localhost:8080/user/register";
    private String loginUserUrl = "http://localhost:8080/user/login";

    private RestTemplate rest;

    public UserGateway(RestTemplate rest) {
        this.rest = rest;
    }

    public boolean registerUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user, null);
        ResponseEntity<Void> exchange = rest.exchange(registerUserUrl, HttpMethod.POST, request, Void.class);
        return exchange.getStatusCode() == HttpStatus.OK;
    }

    public Token loginUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user, null);
        ResponseEntity<Token> exchange = rest.exchange(loginUserUrl, HttpMethod.POST, request, Token.class);
        return exchange.getBody();
    }
}