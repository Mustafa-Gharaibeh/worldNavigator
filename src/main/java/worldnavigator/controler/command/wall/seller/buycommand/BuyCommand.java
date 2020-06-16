package worldnavigator.controler.command.wall.seller.buycommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;
import worldnavigator.model.wall.on_wall.operation.seller_operation.BuyOperation;
import worldnavigator.model.wall.on_wall.seller.Seller;

import java.util.Map;
import java.util.Objects;

public final class BuyCommand implements Command {
    private final BuyOperation buyOperation;
    private final Gamer gamer;
    private final String itemName;
    private final Printer printer;

    private BuyCommand(BuyOperation buyOperation, Gamer gamer, String itemName, Printer printer) {
        this.buyOperation = buyOperation;
        this.gamer = gamer;
        this.itemName = itemName.trim();
        this.printer = printer;
    }

    public static BuyCommand createBuyCommand(
            Seller seller, Gamer gamer, String itemName, Printer printer) {
        return new BuyCommand(
                Objects.requireNonNull(seller),
                Objects.requireNonNull(gamer),
                Objects.requireNonNull(itemName),
                Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        Map<String, Item> itemMap = this.buyOperation.getItems();
        Gold gamerGold = (Gold) this.gamer.getMaterialMap().get("Gold");
        if (itemMap.containsKey(this.itemName)
                && gamerGold.compareTo(itemMap.get(this.itemName).price().getAmount()) >= 0) {
            Item item = buyOperation.buy(this.itemName);
            if (Objects.nonNull(item)) {
                this.gamer.addItem(item);
                this.gamer.addAmountOfGold(-1 * item.price().getAmount());
                this.printer.print(String.format("You got %s", this.itemName));
            } else {
                this.printer.print(String.format("You cannot get %s", this.itemName));
            }
        } else {
            this.printer.print(String.format("You cannot get %s", this.itemName));
        }
    }
}
