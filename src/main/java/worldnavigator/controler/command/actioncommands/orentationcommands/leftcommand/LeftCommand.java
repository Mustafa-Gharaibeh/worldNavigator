package worldnavigator.controler.command.actioncommands.orentationcommands.leftcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.gamer.Direction;
import worldnavigator.model.gamer.Gamer;

import java.util.List;
import java.util.Objects;

public final class LeftCommand implements Command {
    private final Gamer gamer;
    private final Printer printer;

    private LeftCommand(Gamer gamer, Printer printer) {
        this.gamer = gamer;
        this.printer = printer;
    }

    public static LeftCommand createLeftCommand(Gamer gamer, Printer printer) {
        return new LeftCommand(Objects.requireNonNull(gamer), Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        this.gamer.setCurrentDirection(this.getNewDirection());
        this.printer.print(String.format("You look to %s direction", this.gamer.getCurrentDirection()));
    }

    private Direction getNewDirection() {
        List<Direction> directions = this.gamer.getDirections();
        int indexOfCurrentDirection = directions.indexOf(this.gamer.getCurrentDirection());
        if (indexOfCurrentDirection == 0) {
            return directions.get(directions.size() - 1);
        }
        return directions.get(indexOfCurrentDirection - 1);
    }
}
