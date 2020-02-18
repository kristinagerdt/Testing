package trackerClient.gateway;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import trackerClient.model.Item;
import trackerClient.model.Token;

public class ItemGateway {
    private String createItemUrl = "http://localhost:8080/items/create";
    private String allMyItemsUrl = "http://localhost:8080/items/all/me";
    private String myItemsByNameUrl = "http://localhost:8080/items/all?name=";
    private String updateItemUrl = "http://localhost:8080/items/update/{1}";

    private RestTemplate rest;

    public ItemGateway(RestTemplate rest) {
        this.rest = rest;
    }

    public boolean createItem(Item item, Token token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token.getToken());
        HttpEntity<Item> request = new HttpEntity<>(item, headers);
        ResponseEntity<Void> exchange = rest.exchange(createItemUrl, HttpMethod.POST, request, Void.class);
        return exchange.getStatusCode() == HttpStatus.OK;
    }

    public Item[] getAllMyItems(Token token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token.getToken());
        HttpEntity<Item> request = new HttpEntity<>(headers);
        ResponseEntity<Item[]> exchange = rest.exchange(allMyItemsUrl, HttpMethod.GET, request, Item[].class);
        return exchange.getBody();
    }

    public Item[] getMyItemsByName(String itemName, Token token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token.getToken());
        HttpEntity<Item> request = new HttpEntity<>(headers);
        ResponseEntity<Item[]> exchange = rest.exchange(myItemsByNameUrl + itemName, HttpMethod.GET, request, Item[].class);
        return exchange.getBody();
    }

    public Item updateItem(String id, String newName, Token token) {
        Item requestItem = new Item();
        requestItem.setItemName(newName);
        requestItem.setItemType("Task");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token.getToken());
        HttpEntity<Item> request = new HttpEntity<>(requestItem, headers);
        ResponseEntity<Item> exchange = rest.exchange(updateItemUrl, HttpMethod.PUT, request, Item.class, id);
        return exchange.getBody();
    }
}