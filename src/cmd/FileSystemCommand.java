package cmd;

import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.List;

/**
 * This is the abstract class for all classes that may use FileSystemManager to
 * change the FileSystem.
 */
public abstract class FileSystemCommand extends JCommand {

  /**
   * This is the FileSystemManager to control the FileSystem
   */
  FileSystemManager fsm;

  /**
   * This is the first kind of constructor for the FileSystemCommand class that
   * does not instantiate the FileSystemManager first.
   */
  public FileSystemCommand() {
  }

  /**
   * This is the second kind of constructor for FileSystemCommand class that
   * instantiate the the FileSystemManager.
   *
   * @param fsm is the FileSystemManager used to make changes to the
   * FileSystem.
   */
  public FileSystemCommand(FileSystemManager fsm) {
    this.fsm = fsm;
  }

  /**
   * This method is used to finish the functionality of a Command that deal with
   * the FileSystem.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public abstract Output execute();

  /**
   * This method prints the documentation of a command that deal with the
   * FileSystem.
   *
   * @return a string of the documentation.
   */
  @Override
  public abstract String getUsage();

  /**
   * This method set up the parameter for the FileSystemCommand that will be
   * used by the execute method later.
   *
   * @param args this is the parameters got from user for FileSystemCommand.
   */
  @Override
  public abstract void setUp(List<String> args) throws CommandError;

}
