package trackerClient.gateway;

import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trackerClient.model.Item;
import trackerClient.model.Token;
import trackerClient.model.User;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ItemGatewayTest {
    private ItemGateway itemGateway;
    private Token token;

    @BeforeTest
    public void setUp() {
        itemGateway = new ItemGateway(new RestTemplate());
        UserGateway userGateway = new UserGateway(new RestTemplate());
        token = userGateway.loginUser(createUser());
    }

    @Test
    public void testCreateItem() {
        boolean isCreated = itemGateway.createItem(createItem(), token);
        assertTrue(isCreated);
    }

    @Test
    public void testGetAllMyItems() {
        Item[] allMyItems = itemGateway.getAllMyItems(token);
        assertEquals(allMyItems.length, 1);
    }

    @Test
    public void testGetMyItemsByName() {
        Item[] myItemsByName = itemGateway.getMyItemsByName("First", token);
        assertEquals(myItemsByName.length, 1);
    }

    @Test
    public void testUpdateItem() {
        Item updatedItem = itemGateway.updateItem("1", "New first item", token);
        assertNotNull(updatedItem);
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

    private Item createItem() {
        Item item = new Item();
        item.setItemName("First item");
        item.setItemType("Task");
        return item;
    }
}