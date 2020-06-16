package worldnavigator.controler.command.wall.seller.sellcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.wall.on_wall.operation.seller_operation.SellOperation;
import worldnavigator.model.wall.on_wall.seller.Seller;

import java.util.Objects;

public final class SellCommand implements Command {
    private final SellOperation sellOperation;
    private final Gamer gamer;
    private final String itemName;
    private final Printer printer;

    private SellCommand(SellOperation sellOperation, Gamer gamer, String itemName, Printer printer) {
        this.sellOperation = sellOperation;
        this.gamer = gamer;
        this.itemName = itemName.trim();
        this.printer = printer;
    }

    public static SellCommand createSellCommand(
            Seller seller, Gamer gamer, String itemName, Printer printer) {
        return new SellCommand(
                Objects.requireNonNull(seller),
                Objects.requireNonNull(gamer),
                Objects.requireNonNull(itemName),
                Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.gamer.getMaterialMap().containsKey(this.itemName)) {
            this.gamer
                    .deleteItem(this.itemName)
                    .ifPresent(
                            item -> {
                                Gold gold = this.sellOperation.sell(item);
                                this.gamer.addAmountOfGold(gold);
                                this.printer.print(
                                        String.format(
                                                "You sell %s and you got %s gold", this.itemName, gold.getAmount()));
                            });
        } else {
            this.printer.print(String.format("You cannot sell %s", this.itemName));
        }
    }
}
