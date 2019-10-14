package cmd;

import containers.CommandHistory;
import exceptions.CommandError;
import java.util.List;

/**
 * This is the abstract class for all classes that deal with with the history of
 * commands typed by user.
 */
public abstract class CommandHistoryCommand extends JCommand {

  /**
   * This is the CommandHistory property that store the history of all commands
   * typed in by user
   */
  CommandHistory cmdHistory;

  /**
   * This is the constructor of the CommandHistoryCommand class it instantiate
   * the cmdHistory parameter which used for getting history of command that
   * user typed in.
   *
   * @param cmdHistory the history of the command that user typed in.
   */
  public CommandHistoryCommand(CommandHistory cmdHistory) {
    this.cmdHistory = cmdHistory;
  }

  /**
   * This is the execute method for finishing work of CommandHistoryCommand.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public abstract Output execute();

  /**
   * This method is used to print documentation of CommandHistoryCommand Because
   * CommandHistoryCommand is a abstract class, so it has no documentation for
   * it.
   *
   * @return a string of the documentation.
   */
  @Override
  public abstract String getUsage();

  /**
   * This method is used to setup the parameters of CommandHistoryCommand.
   *
   * @param args this is the parameters got from user for
   * CommandHistoryCommand.
   */
  @Override
  public abstract void setUp(List<String> args) throws CommandError;
}
