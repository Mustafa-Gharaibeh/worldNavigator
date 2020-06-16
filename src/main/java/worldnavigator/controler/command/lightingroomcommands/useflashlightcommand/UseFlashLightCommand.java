package worldnavigator.controler.command.lightingroomcommands.useflashlightcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.room.Room;

import java.util.Objects;

public final class UseFlashLightCommand implements Command {
    private final Room room;
    private final Printer printer;

    private UseFlashLightCommand(Room room, Printer printer) {
        this.room = room;
        this.printer = printer;
    }

    public static UseFlashLightCommand createUseFlashLightCommand(Room room, Printer printer) {
        return new UseFlashLightCommand(Objects.requireNonNull(room), Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.room.getRoomLightingStatus().isARoomDark()) {
            this.room.getRoomLightingStatus().switchLights(true);
            this.printer.print(
                    String.format("The %s is Lit by Using FlashLight", this.room.getRoomName()));
        } else {
            this.printer.print(String.format("The %s is already Lit", this.room.getRoomName()));
        }
    }
}
