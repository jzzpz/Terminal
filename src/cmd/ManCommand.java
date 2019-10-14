package cmd;

import exceptions.CommandError;
import java.util.List;
import java.util.Map;

/**
 * This class represents the ManCommand, it can get the documentation of a
 * certain command.
 */
public class ManCommand extends UsageCommand {

  /**
   * This is the command parameter
   */
  private Command command;
  /**
   * This is the HashMap for mapping command name to an actual command
   */
  private Map<String, Command> cmdMap;

  /**
   * This is the constructor for the ManCommand It instantiate a HashMap.
   *
   * @param cmdMap is the HashMap used to map the Command Name to a actual
   * Command.
   */
  public ManCommand(Map<String, Command> cmdMap) {
    this.cmdMap = cmdMap;
  }

  /**
   * This method finish the functionality of getting documentation for different
   * commands.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (command == null) {
      return new Output("", "");
    }
    String usage = command.getUsage();
    Output output = new Output(usage, "");
    restorePrintStream();
    command = null;
    return output;
  }

  /**
   * This method print the documentation for the ManCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    return "\nman CMD\n"
        + "\tPrint Documentation for CMD\n";
  }

  /**
   * This method is used to set up the command which the ManCommand will print
   * its documentation for.
   *
   * @param args is the commands names that typed in by user.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args == null || args.size() == 0) {
      command = null;
      throw new CommandError("What manual page do you want?");
    } else if (args.size() == 1) {

      if (cmdMap.get(args.get(0)) != null) {
        command = cmdMap.get(args.get(0));
      } else {
        throw new CommandError("No manual entry for " + args.get(0));
      }
    } else {
      command = null;
      throw new CommandError("man: too many arguments");
    }
  }
}
