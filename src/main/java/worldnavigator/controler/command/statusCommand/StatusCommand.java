package worldnavigator.controler.command.statusCommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.material.Material;

import java.util.Map;
import java.util.Objects;

public final class StatusCommand implements Command {
    private final Gamer gamer;
    private final Printer printer;

    private StatusCommand(Gamer gamer, Printer printer) {
        this.gamer = gamer;
        this.printer = printer;
    }

    public static StatusCommand createStatusCommand(Gamer gamer, Printer printer) {
        return new StatusCommand(Objects.requireNonNull(gamer), Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        StringBuilder sd = new StringBuilder("The player status :\n");
        sd.append("-")
                .append("The Current Direction is ")
                .append(this.gamer.getCurrentDirection())
                .append(".\n");
        if (!this.gamer.getMaterialMap().isEmpty()) {
            sd.append("-Material: \n");
            for (Map.Entry<String, Material> entry : this.gamer.getMaterialMap().entrySet()) {
                sd.append("*").append(entry.getValue().toString()).append(".\n");
            }
            this.printer.print(sd.toString());
        }
    }
}
