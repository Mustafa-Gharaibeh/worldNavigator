package worldnavigator.controler.command.lightingroomcommands.switchlightscommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.room.Room;

import java.util.Objects;

public final class SwitchLightsCommand implements Command {
    private final Room room;
    private final Printer printer;

    private SwitchLightsCommand(Room room, Printer printer) {
        this.room = room;
        this.printer = printer;
    }

    public static SwitchLightsCommand createSwitchLightsCommand(Room room, Printer printer) {
        return new SwitchLightsCommand(Objects.requireNonNull(room), Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.room.getRoomLightingStatus().isARoomHasLights()) {
            this.room
                    .getRoomLightingStatus()
                    .switchLights(!this.room.getRoomLightingStatus().isARoomLit());
            if (this.room.getRoomLightingStatus().isARoomLit()) {
                this.printer.print(String.format("The %s is Lit", this.room.getRoomName()));
            } else {
                this.printer.print(String.format("The %s is Dark", this.room.getRoomName()));
            }
        } else {
            this.printer.print(
                    String.format(
                            "The %s does not have a lights %s",
                            this.room.getRoomName(), "will use the FlashLight to Lit the room"));
        }
    }
}
