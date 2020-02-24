package trackerClient.gateway;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trackerClient.model.Item;
import trackerClient.model.Token;

import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class ItemGatewayMockTest {
    private String createItemUrl = "http://localhost:8080/items/create";
    private String allMyItemsUrl = "http://localhost:8080/items/all/me";
    private String myItemsByNameUrl = "http://localhost:8080/items/all?name=";
    private String updateItemUrl = "http://localhost:8080/items/update/";

    private RestTemplate restTemplate;
    private MockRestServiceServer server;
    private ItemGateway itemGateway;
    private Token token;

    @BeforeTest
    public void setUp() {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        server = MockRestServiceServer.bindTo(restTemplate).build();
        itemGateway = new ItemGateway(restTemplate);
        token = new Token();
        token.setToken("76d3bcfe-55f9-4110-9dd0-4fba27c1b26d");
    }

    @Test
    public void testCreateItem() {
        server.expect(ExpectedCount.manyTimes(), requestTo(createItemUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", token.getToken()))
                .andExpect(content().json(createItemJson))
                .andRespond(withSuccess(createItemJsonResponse, MediaType.APPLICATION_JSON));

        boolean isCreated = itemGateway.createItem(createItem(), token);
        assertTrue(isCreated);
        server.verify();
    }

    @Test
    public void testUnauthorizedUserCreateItem() {
        server.expect(ExpectedCount.manyTimes(), requestTo(createItemUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(createItemJson))
                .andRespond(withStatus(HttpStatus.FORBIDDEN)); // Response 403 FORBIDDEN
        //.andRespond(withUnauthorizedRequest()); // Response 401 UNAUTHORIZED
        //.andRespond(withBadRequest()); // Response 400 BAD_REQUEST
        //.andRespond(withServerError()); // Response 500 INTERNAL_SERVER_ERROR

        boolean isCreated = itemGateway.createItem(createItem(), token);
        assertFalse(isCreated);
        server.verify();
    }

    @Test
    public void testGetAllMyItems() {
        server.expect(ExpectedCount.manyTimes(), requestTo(allMyItemsUrl))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", token.getToken()))
                .andRespond(withSuccess(allMyItemsJsonResponse, MediaType.APPLICATION_JSON));

        Item[] allMyItems = itemGateway.getAllMyItems(token);
        assertEquals(allMyItems.length, 1);
        server.verify();
    }

    @Test
    public void testUnauthorizedUserGetAllMyItems() {
        server.expect(ExpectedCount.manyTimes(), requestTo(allMyItemsUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.FORBIDDEN)); // Response 403 FORBIDDEN

        Item[] allMyItems = itemGateway.getAllMyItems(token);
        assertNull(allMyItems);
        server.verify();
    }

    @Test
    public void testGetMyItemsByName() {
        String itemName = "First";
        server.expect(ExpectedCount.manyTimes(), requestTo(myItemsByNameUrl + itemName))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", token.getToken()))
                .andRespond(withSuccess(myItemsByNameJsonResponse, MediaType.APPLICATION_JSON));

        Item[] myItemsByName = itemGateway.getMyItemsByName(itemName, token);
        assertEquals(myItemsByName.length, 1);
        server.verify();
    }

    @Test
    public void testUnauthorizedUserGetMyItemsByName() {
        String itemName = "First";
        server.expect(ExpectedCount.manyTimes(), requestTo(myItemsByNameUrl + itemName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.FORBIDDEN)); // Response 403 FORBIDDEN

        Item[] myItemsByName = itemGateway.getMyItemsByName(itemName, token);
        assertNull(myItemsByName);
        server.verify();
    }

    @Test
    public void testUpdateItem() {
        String id = "1";
        server.expect(ExpectedCount.manyTimes(), requestTo(updateItemUrl + id))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(header("Authorization", token.getToken()))
                .andExpect(content().json(updateItemJson))
                .andRespond(withSuccess(updateItemJsonResponse, MediaType.APPLICATION_JSON));

        Item updatedItem = itemGateway.updateItem(id, "New first item", token);
        assertEquals(updatedItem.getItemName(), "New first item");
        server.verify();
    }

    @Test
    public void testUnauthorizedUserUpdateItem() {
        String id = "1";
        server.expect(ExpectedCount.manyTimes(), requestTo(updateItemUrl + id))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().json(updateItemJson))
                .andRespond(withStatus(HttpStatus.FORBIDDEN)); // Response 403 FORBIDDEN

        Item updatedItem = itemGateway.updateItem(id, "New first item", token);
        assertNull(updatedItem);
        server.verify();
    }

    private Item createItem() {
        Item item = new Item();
        item.setItemName("First item");
        item.setItemType("Task");
        return item;
    }

    // Request
    static String createItemJson = "{\"itemName\":\"First item\",\"itemType\":\"Task\"}";
    static String updateItemJson = "{\"itemName\":\"New first item\",\"itemType\":\"Task\"}";

    // Response
    static String createItemJsonResponse = "{\"id\": 1,\"itemName\": \"First item\",\"itemType\": \"Task\"," +
            "\"itemStatus\": \"To Do\",\"createdDate\": \"2020-02-19T21:23:24.065\"," +
            "\"createdBy\": \"alex\"}";
    static String allMyItemsJsonResponse = "[{\"id\": 1,\"itemName\": \"First item\", \"itemType\": \"Task\"," +
            "\"itemStatus\": \"To Do\",\"createdDate\": \"2020-02-19T21:23:24.065\"," +
            "\"createdBy\": \"alex\"}]";
    static String myItemsByNameJsonResponse = "[{\"id\": 1,\"itemName\": \"First item\", \"itemType\": \"Task\"," +
            "\"itemStatus\": \"To Do\",\"createdDate\": \"2020-02-19T21:23:24.065\"," +
            "\"createdBy\": \"alex\"}]";
    static String updateItemJsonResponse = "{\"id\": 1,\"itemName\": \"New first item\",\"itemType\": \"Task\"," +
            "\"itemStatus\": \"To Do\",\"createdDate\": \"2020-02-19T21:24:24.065\"," +
            "\"createdBy\": \"alex\"}";
}