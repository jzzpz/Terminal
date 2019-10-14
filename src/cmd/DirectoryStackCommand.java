package cmd;

import containers.DirectoryStack;
import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.List;

/**
 * This is the abstract class for all classes that deal with with a stack of
 * directory.
 */
public abstract class DirectoryStackCommand extends JCommand {

  /**
   * This is the stack to store all the directories
   */
  DirectoryStack stackd;
  /**
   * This is the FileSystemManager to control the FileSystem
   */
  FileSystemManager fsm;

  /**
   * This is the constructor of the DirectoryStackCommand
   * class it instantiate a stack used to store the directories
   * and a FileSystemManager to control the
   * files and directories in the FileSystem.
   *
   * @param stackd a stack used for storing directories.
   * @param fsm a FileSystemManager to control the FileSystem.
   */
  public DirectoryStackCommand(DirectoryStack stackd, FileSystemManager fsm) {
    this.stackd = stackd;
    this.fsm = fsm;
  }

  /**
   * This is the execute method for finishing work of directory stack commands.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public abstract Output execute();

  /**
   * This is the getUsage method inherited from Command interface.
   * Because it is
   * a abstract class, so it actually has no comment.
   *
   * @return a string of the documentation.
   */
  @Override
  public abstract String getUsage();

  /**
   * This is the method for setting up parameters for directory stack command.
   *
   * @param args this is the parameters got from user for
   * DirectoryStackCommand.
   */
  @Override
  public abstract void setUp(List<String> args) throws CommandError;
}
