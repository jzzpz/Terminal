package cmd;

import exceptions.CommandError;
import java.util.List;

/**
 * This is the abstract class for all classes that deal with with the
 * documentation of commands.
 */
public abstract class UsageCommand extends JCommand {

  /**
   * this method is used for finishing functionality of class that is a
   * UsageCommand.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public abstract Output execute();

  /**
   * This method is used to print the documentation of a class that is a
   * UsageCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public abstract String getUsage();

  /**
   * This method is used to setup parameters given by user for class that is a
   * UsageCommand.
   *
   * @param args this is the parameters got from user for UsageCommand.
   */
  @Override
  public abstract void setUp(List<String> args) throws CommandError;

}
