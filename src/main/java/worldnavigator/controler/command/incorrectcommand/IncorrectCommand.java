package worldnavigator.controler.command.incorrectcommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;

import java.util.Objects;

public final class IncorrectCommand implements Command {
    private final Printer printer;

    private IncorrectCommand(Printer printer) {
        this.printer = printer;
    }

    public static IncorrectCommand createIncorrectCommand(Printer printer) {
        return new IncorrectCommand(Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        this.printer.print("Incorrect command");
    }
}
