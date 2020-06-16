package worldnavigator.controler.command.wall.lockable.opencommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.TypeOnWall;

import java.util.Objects;

public final class OpenCommand implements Command {
    private final OnWall onWall;
    private final Printer printer;

    private OpenCommand(OnWall onWall, Printer printer) {
        this.onWall = onWall;
        this.printer = printer;
    }

    public static OpenCommand createOpenCommand(OnWall onWall, Printer printer) {
        return new OpenCommand(Objects.requireNonNull(onWall), Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.onWall.typeOnWall().equals(TypeOnWall.DOOR)) {
            this.printer.print(this.onWall.getLockable().status());
        } else {
            this.printer.print("Incorrect command");
        }
    }
}
