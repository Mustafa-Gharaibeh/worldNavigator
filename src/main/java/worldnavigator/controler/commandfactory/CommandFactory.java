package worldnavigator.controler.commandfactory;

import worldnavigator.controler.Printer.Printer;
import worldnavigator.controler.command.Command;
import worldnavigator.controler.command.incorrectcommand.IncorrectCommand;
import worldnavigator.controler.command.lightingroomcommands.switchlightscommand.SwitchLightsCommand;
import worldnavigator.controler.command.lightingroomcommands.useflashlightcommand.UseFlashLightCommand;
import worldnavigator.controler.command.statusCommand.StatusCommand;
import worldnavigator.controler.command.wall.checkCommand.CheckCommand;
import worldnavigator.controler.command.wall.lockable.opencommand.OpenCommand;
import worldnavigator.controler.command.wall.lockable.usekeycommand.UseKeyCommand;
import worldnavigator.controler.command.wall.lookcommand.LookCommand;
import worldnavigator.controler.commandfactory.actioncommandfactory.ActionCommandFactory;
import worldnavigator.controler.commandfactory.sellercommandfactory.SellerCommandFactory;
import worldnavigator.model.gamer.Gamer;
import worldnavigator.model.material.item.flashlight.Flashlight;
import worldnavigator.model.material.item.key.Key;
import worldnavigator.model.maze.Maze;
import worldnavigator.model.room.Room;
import worldnavigator.model.wall.Wall;
import worldnavigator.model.wall.on_wall.seller.Seller;

import java.util.Objects;

public class CommandFactory {
  private final Maze maze;
  private final Gamer gamer;
  private final Printer printer;
  private Room currentRoom;
  private Wall wall;

  private CommandFactory(Maze maze, Gamer gamer, Printer printer) {
    this.maze = maze;
    this.gamer = gamer;
    this.printer = printer;
    this.setCurrentRoom().setWall();
  }

  public static CommandFactory createCommandFactory(Maze maze, Gamer gamer, Printer printer) {
    return new CommandFactory(maze, gamer, printer);
  }

  protected CommandFactory setCurrentRoom() {
    this.currentRoom = this.maze.getCurrentRoom();
    return this;
  }

  protected CommandFactory setWall() {
    this.wall = this.currentRoom.getWalls().get(this.gamer.getCurrentDirection());
    return this;
  }

  public Command createCommand(String arg) {
    String[] argSplit = arg.split("\\s");
    StringBuilder sd = new StringBuilder();
    for (int i = 1; i < argSplit.length; i++) {
      sd.append(argSplit[i]).append(" ");
    }
    if (this.isActionCommand(arg)) {
      return this.createActionCommand(arg);
    } else if (this.isSellerCommand(arg)) {
      return this.createSellerCommand(arg, sd);
    } else if ("look".equals(arg)) {
      return this.createLookCommand();
    } else if (this.isASwitchLightCommand(arg)) {
      return createSwitchLightCommand(arg);
    } else if (arg.contains("use") && arg.contains("key")) {
      return createUseKeyCommand(sd.toString());
    } else if ("status".equals(arg)) {
      return createStatusCommand();
    } else if ("check".equals(argSplit[0])) {
      return createCheckCommand(sd.toString());
    } else if ("open".equals(arg)) {
      return createOpenCommand();
    } else if ("finish trade".equals(arg)) {
      return IncorrectCommand.createIncorrectCommand(s -> System.out.println("finish trade"));
    }
    return IncorrectCommand.createIncorrectCommand(this.printer);
  }

  private Command createActionCommand(String arg) {
    return new ActionCommandFactory(this.maze, this.gamer, this.printer).createActionCommand(arg);
  }

  private boolean isActionCommand(String arg) {
    return arg.equals("left")
            || arg.equals("right")
            || arg.equals("forward")
            || arg.equals("backward");
  }

  private Command createSellerCommand(String arg, StringBuilder split) {
    return new SellerCommandFactory((Seller) this.wall.getOnWall(), this.gamer, this.printer)
            .createSellerCommand(arg, split);
  }

  private boolean isSellerCommand(String arg) {
    return arg.equals("trade") || arg.equals("list") || arg.contains("buy") || arg.contains("sell");
  }

  private Command createCheckCommand(String arg) {
    return CheckCommand.createCheckCommand(this.wall.getOnWall(), this.gamer, arg, this.printer);
  }

  private Command createLookCommand() {
    return LookCommand.createLookCommand(
            this.wall, this.currentRoom.getRoomLightingStatus().isARoomLit(), this.printer);
  }

  private Command createUseKeyCommand(String arg) {
    Key key = (Key) this.gamer.getMaterialMap().get(arg.trim());
    return UseKeyCommand.createUseKeyCommand(this.wall.getOnWall(), key, this.printer);
  }

  private Command createOpenCommand() {
    return OpenCommand.createOpenCommand(this.wall.getOnWall(), this.printer);
  }

  private Command createStatusCommand() {
    return StatusCommand.createStatusCommand(this.gamer, this.printer);
  }

  private boolean isASwitchLightCommand(String arg) {
    if ("switchLights".equalsIgnoreCase(arg)) {
      return true;
    } else {
      return arg.contains("use") && arg.contains("flashlight");
    }
  }

  private Command createSwitchLightCommand(String arg) {
    if ("switchLights".equalsIgnoreCase(arg)) {
      return SwitchLightsCommand.createSwitchLightsCommand(this.currentRoom, this.printer);
    } else if (arg.contains("use") && arg.contains("flashlight")) {
      Flashlight flashlight = (Flashlight) this.gamer.getMaterialMap().get("flashlight");
      if (Objects.nonNull(flashlight)) {
        return UseFlashLightCommand.createUseFlashLightCommand(this.currentRoom, this.printer);
      }
    }
    return IncorrectCommand.createIncorrectCommand(
            s -> System.out.println("You don't have FlashLight"));
  }
}
