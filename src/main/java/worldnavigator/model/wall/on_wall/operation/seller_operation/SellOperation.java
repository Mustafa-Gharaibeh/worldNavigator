package worldnavigator.model.wall.on_wall.operation.seller_operation;

import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;

public interface SellOperation {
    Gold sell(Item item);
}
