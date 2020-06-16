package worldnavigator.controler.command.wall.checkCommand;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.material.Material;
import worldnavigator.model.material.gold.Gold;
import worldnavigator.model.material.item.Item;
import worldnavigator.model.wall.on_wall.OnWall;
import worldnavigator.model.wall.on_wall.operation.acquire.Acquirable;

import java.util.Map;
import java.util.Objects;

public final class CheckCommand implements Command {
  private final OnWall onWall;
  private final Gamer gamer;
  private final String arg;
  private final Printer printer;

  private CheckCommand(OnWall onWall, Gamer gamer, String arg, Printer printer) {
    this.onWall = onWall;
    this.gamer = gamer;
    this.arg = arg;
    this.printer = printer;
  }

  public static CheckCommand createCheckCommand(
          OnWall onWall, Gamer gamer, String arg, Printer printer) {
    return new CheckCommand(
            Objects.requireNonNull(onWall),
            Objects.requireNonNull(gamer),
            Objects.requireNonNull(arg),
            Objects.requireNonNull(printer));
  }

  @Override
  public void execute() {
    if (this.arg.contains(this.onWall.typeOnWall().getCheckName())) {
      if (this.onWall.typeOnWall().isLockable()) {
        this.printer.print(this.onWall.getLockable().status());
        if (this.onWall.typeOnWall().isAcquirable() && !this.onWall.getLockable().isLock()) {
          this.acquire(this.onWall.getAcquirable());
        }
      } else if (this.onWall.typeOnWall().isAcquirable()) {
        this.acquire(this.onWall.getAcquirable());
      }
    } else {
      this.printer.print("Incorrect arg Command");
    }
  }

  private void acquire(Acquirable acquirable) {
    StringBuilder sd = new StringBuilder();
    for (Map.Entry<String, Material> entry : acquirable.acquire().entrySet()) {
      if (entry.getKey().equals("Gold")) {
        this.gamer.addAmountOfGold((Gold) entry.getValue());
      } else {
        this.gamer.addItem((Item) entry.getValue());
      }
      sd.append("* ").append(entry.getValue().toString()).append(".\n");
    }
    if (sd.length() == 0) {
      this.printer.print(String.format("The %s is empty", this.onWall.typeOnWall().getCheckName()));
    } else {
      this.printer.print(String.format("You acquired:%s %s", "\n", sd.toString()));
    }
  }
}
