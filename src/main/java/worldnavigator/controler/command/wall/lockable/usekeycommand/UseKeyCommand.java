package worldnavigator.controler.command.wall.lockable.usekeycommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.material.item.key.Key;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.operation.Lockable.Lockable;

import java.util.Objects;
import java.util.Optional;

public final class UseKeyCommand implements Command {
    private final OnWall onWall;
    private final Key key;
    private final Printer printer;

    private UseKeyCommand(OnWall onWall, Key key, Printer printer) {
        this.onWall = onWall;
        this.key = key;
        this.printer = printer;
    }

    public static UseKeyCommand createUseKeyCommand(OnWall onWall, Key openKey, Printer printer) {
        return new UseKeyCommand(
                Objects.requireNonNull(onWall), openKey, Objects.requireNonNull(printer));
    }

    @Override
    public void execute() {
        if (this.onWall.typeOnWall().isLockable()) {
            Lockable lockable = this.onWall.getLockable();
            Optional<Key> optional = Optional.ofNullable(this.key);
            optional.ifPresent(lockable::useKey);
            this.printer.print(lockable.status());
        } else {
            this.printer.print("Incorrect command");
        }
    }
}
