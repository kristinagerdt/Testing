package trackerClient;

import org.springframework.web.client.RestTemplate;
import trackerClient.gateway.ItemGateway;
import trackerClient.gateway.UserGateway;
import trackerClient.model.Item;
import trackerClient.model.Token;
import trackerClient.model.User;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        UserGateway userGateway = new UserGateway(new RestTemplate());
        User user = createUser();
        boolean isRegistered = userGateway.registerUser(user);
        System.out.println("Is the user registered? " + isRegistered);

        Token token = userGateway.loginUser(user);
        System.out.println("Token: " + token.getToken());

        ItemGateway itemGateway = new ItemGateway(new RestTemplate());
        Item item = createItem();
        boolean isCreated = itemGateway.createItem(item, token);
        System.out.println("Is the item created? " + isCreated);

        Item[] allMyItems = itemGateway.getAllMyItems(token);
        System.out.println("All my items: " + Arrays.toString(allMyItems));

        Item[] myItemsByName = itemGateway.getMyItemsByName("First", token);
        System.out.println("My items by name: " + Arrays.toString(myItemsByName));

        Item updatedItem = itemGateway.updateItem("1", "New first item", token);
        System.out.println("Updated item: " + updatedItem);
    }

    private static User createUser() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("Smith");
        user.setUserName("alex");
        user.setPassword("123");
        user.setRepeatPassword("123");
        return user;
    }

    private static Item createItem() {
        Item item = new Item();
        item.setItemName("First item");
        item.setItemType("Task");
        return item;
    }
}