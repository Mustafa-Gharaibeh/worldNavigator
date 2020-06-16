package worldnavigator.controler.Printer;

public final class ConsolePrinter implements Printer {
  @Override
  public void print(String out) {
    System.out.println(out);
  }
}
