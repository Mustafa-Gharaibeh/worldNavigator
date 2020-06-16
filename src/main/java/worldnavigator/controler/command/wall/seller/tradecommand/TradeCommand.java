package worldnavigator.controler.command.wall.seller.tradecommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.controler.command.wall.seller.listcommand.ListCommand;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;
import worldnavigator.model.wall.on_wall.seller.Seller;

import java.util.Objects;

public final class TradeCommand implements Command {
  private final OnWall onWall;
  private final Printer printer;

  private TradeCommand(OnWall onWall, Printer printer) {
    this.onWall = onWall;
    this.printer = printer;
  }

  public static TradeCommand createTradeCommand(OnWall onWall, Printer printer) {
    return new TradeCommand(Objects.requireNonNull(onWall), Objects.requireNonNull(printer));
  }

  @Override
  public void execute() {
    if (this.onWall.typeOnWall().equals(TypeOnWall.SELLER)) {
      ListCommand.createListCommand((Seller) this.onWall, this.printer).execute();
    } else {
      this.printer.print("You don't look to Seller");
    }
  }
}
