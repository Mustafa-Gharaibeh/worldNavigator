package worldnavigator.controler.commandfactory.sellercommandfactory;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.controler.command.incorrectcommand.IncorrectCommand;
import worldnavigator.controler.command.wall.seller.buycommand.BuyCommand;
import worldnavigator.controler.command.wall.seller.listcommand.ListCommand;
import worldnavigator.controler.command.wall.seller.sellcommand.SellCommand;
import worldnavigator.controler.command.wall.seller.tradecommand.TradeCommand;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.wall.on_wall.seller.Seller;

public class SellerCommandFactory {
    private final Seller seller;
    private final Gamer gamer;
    private final Printer printer;

    public SellerCommandFactory(Seller seller, Gamer gamer, Printer printer) {
        this.seller = seller;
        this.gamer = gamer;
        this.printer = printer;
    }

    public Command createSellerCommand(String arg, StringBuilder sd) {
        if ("trade".equals(arg)) {
            return TradeCommand.createTradeCommand(this.seller, this.printer);
        } else if ("list".equals(arg)) {
            return ListCommand.createListCommand(this.seller, this.printer);
        } else if (arg.contains("buy")) {
            return BuyCommand.createBuyCommand(this.seller, this.gamer, sd.toString(), this.printer);
        } else if (arg.contains("sell")) {
            return SellCommand.createSellCommand(this.seller, this.gamer, sd.toString(), this.printer);
        }
        return IncorrectCommand.createIncorrectCommand(this.printer);
    }
}
