package worldnavigator.controler.command.wall.seller.listcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.material.item.Item;
import worldnavigator.model.wall.on_wall.seller.Seller;

import java.util.Map;
import java.util.Objects;

public final class ListCommand implements Command {
    private final Seller seller;
    private final Printer printer;

    private ListCommand(Seller seller, Printer printer) {
        this.seller = seller;
        this.printer = printer;
    }

    public static ListCommand createListCommand(Seller seller, Printer printer) {
        return new ListCommand(Objects.requireNonNull(seller), Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        StringBuilder sd = new StringBuilder();
        Map<String, Item> itemMap = this.seller.getItems();
        for (Map.Entry<String, Item> entry : itemMap.entrySet()) {
            sd.append("* ").append(entry.getKey()).append(".\n");
        }
        if (sd.length() == 0) {
            this.printer.print("The seller has nothing");
        } else {
            this.printer.print(String.format("The seller has:%s%s", "\n", sd.toString()));
        }
    }
}
