package worldnavigator.controler.command.wall.lookcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.wall.Wall;

import java.util.Objects;

public final class LookCommand implements Command {
    private final Wall wall;
    private final Printer printer;
    private final Boolean roomIsLit;

    private LookCommand(Wall wall, Boolean roomIsLit, Printer printer) {
        this.wall = wall;
        this.roomIsLit = roomIsLit;
        this.printer = printer;
    }

    public static LookCommand createLookCommand(Wall wall, Boolean roomIsLit, Printer printer) {
        return new LookCommand(
                Objects.requireNonNull(wall),
                Objects.requireNonNull(roomIsLit),
                Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.roomIsLit) {
            String onWall = this.wall.getOnWall().typeOnWall().getLookDescription();
            this.printer.print(onWall);
        } else {
            this.printer.print("Dark");
        }
    }
}
