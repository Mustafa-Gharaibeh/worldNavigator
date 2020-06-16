package worldnavigator.main;

import worldnavigator.controler.Printer.ConsolePrinter;
import worldnavigator.main.classgame.ClassGame;
import worldnavigator.model.dao.mazedao.file.MazeDAOFileImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String gamerName;
    String mazeName;
    ClassGame classGame = null;
    System.out.println(
            "The maze file in the src\\main\\resources\\mazeFile\n you will update the the absolute path in "
                    + "(world_navigator.model.dao.mazedao.file.MazeDAOFileImpl) ");
    System.out.print("Enter Your Name:");
    gamerName = scanner.nextLine();
    System.out.println("You can choose one of these maze:");
    System.out.println("maze1");
    System.out.print("Enter Maze Name:");
    mazeName = scanner.nextLine();
    boolean classGameNull = true;
    while (classGameNull) {
      try {
        classGame =
                ClassGame.createClassGame(
                        mazeName, gamerName, MazeDAOFileImpl.createMazeDAOFileImpl(), new ConsolePrinter());
        classGameNull = false;
      } catch (NullPointerException e) {
        System.out.println("You Enter Incorrect Maze Name");
        System.out.print("Enter Maze Name Again:");
        mazeName = scanner.nextLine();
      }
    }

    while (true) {
      String arg = " ";
      while (!"restart".equalsIgnoreCase(arg)) {
        List<String> commands = classGame.commandList(arg);
        System.out.println("******************");
        System.out.println("You can Use One of these commands:");
        System.out.println(commands);
        System.out.println("******************");
        System.out.print("Enter Your Command:");
        arg = scanner.nextLine();
        System.out.println("******************");
        if ("restart".equalsIgnoreCase(arg)) {
          break;
        }
        if (isCommandAvailable(arg, commands)) {
          classGame.inputCommand(arg);
        } else {
          System.out.println("Invalid Command");
        }
        if (classGame.isDone()) {
          break;
        }
      }
      if ("restart".equalsIgnoreCase(arg)) {
        classGame =
                ClassGame.createClassGame(
                        mazeName, gamerName, MazeDAOFileImpl.createMazeDAOFileImpl(), new ConsolePrinter());
        System.out.println("The Game restarted");
      } else {
        break;
      }
    }
    classGame.printIfFinish();
  }

  private static boolean isCommandAvailable(String arg, List<String> commands) {
    if (commands.contains(arg)) {
      return true;
    } else if (arg.contains("use") && commands.contains("use <name> key")) {
      return true;
    } else if (arg.contains("buy") && commands.contains("buy <item>")) {
      return true;
    } else {
      return arg.contains("sell") && commands.contains("sell <item>");
    }
  }
}
