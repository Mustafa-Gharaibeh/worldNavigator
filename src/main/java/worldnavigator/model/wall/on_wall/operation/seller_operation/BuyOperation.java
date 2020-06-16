package worldnavigator.model.wall.on_wall.operation.seller_operation;

import worldnavigator.model.material.item.Item;

import java.util.Map;

public interface BuyOperation {
    Map<String, Item> getItems();

    Item buy(String name);
}
