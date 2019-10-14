package cmd;

import exceptions.CommandError;
import java.util.List;

/**
 * This represents the ExitCommand, when user type in exit in shell. Then the
 * shell program will be exterminated.
 */
public class ExitCommand extends JCommand {

  /**
   * This is the parameter that shows whether the parameter is valid
   */
  private boolean valid;

  /**
   * This is the constructor of the ExitCommand which set valid to be false at
   * first, because it haven't been validated yet.
   */
  public ExitCommand() {
    valid = false;
  }

  /**
   * This is the execute method used to finish the functionality of the
   * ExitCommand class.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (valid) {
      System.exit(0);
    }
    return null;
  }

  /**
   * This method is used to print the documentation for the ExitCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("exit:");
    System.out.println("\tQuit the program");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * This method is used to set up the parameters typed in by user that will be
   * used by the execute method later.
   *
   * @param args this is the parameters got from user for ExitCommand.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() != 0) {
      throw new CommandError("exit: too many arguments");
    } else {
      valid = true;
    }
  }
}
