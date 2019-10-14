package cmd;

import containers.CommandHistory;
import exceptions.CommandError;
import java.util.List;

/**
 * This represents the HistoryCommand that will deal with the history of command
 * typed by user.
 */
public class HistoryCommand extends CommandHistoryCommand {

  /**
   * This is the number of history record to shown
   */
  private int num;
  /**
   * This is the getSize of total history commands
   */
  private int size;

  /**
   * This is to determine if the command is setup valid.
   */
  private boolean valid;

  /**
   * This is the constructor for the HistoryCommand class that instantiate the
   * super class's cmdHistory property.
   *
   * @param cmdHistory is the history of command that typed in by user.
   */
  public HistoryCommand(CommandHistory cmdHistory) {
    super(cmdHistory);
    valid = false;
  }

  /**
   * This method is used to finish the functionality of the HistoryCommand by
   * using the parameters set up by setUp method.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    setPrintStream();
    for (int i = size - num; i < size; i++) {
      System.out.println(i + ". " + cmdHistory.getByIndex(i));
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This is the method that prints the documentation for HistoryCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: HistoryCommand");
    System.out.println("\tGet user's input histories");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * This is the method that setUp the parameters that will be used by
   * HistoryCommand.
   *
   * @param args this is the parameters got from user for HistoryCommands.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    this.size = cmdHistory.getSize();
    if (args.isEmpty()) {
      num = size;
      valid = true;
    } else if (args.size() == 1) {
      try {
        num = Math.min(Integer.parseInt(args.get(0)), cmdHistory.getSize());
      } catch (NumberFormatException e) {
        valid = false;
        throw new CommandError("history: can't recognize " + args.get(0));
      }
      valid = num > 0;
      if (!valid) {
        throw new CommandError("history: can't accept negative number");
      }
    } else {
      valid = false;
      throw new CommandError("history: accept at most one parameter");
    }
  }
}
