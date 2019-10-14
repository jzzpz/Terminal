package cmd;

import containers.DirectoryStack;
import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.EmptyContainerError;
import fs.Directory;
import fs.FileSystemManager;
import java.util.List;

/**
 * This represents the PopdCommand that get the most recently saved directory
 * out of stack.
 */
public class PopdCommand extends DirectoryStackCommand {

  /**
   * This is to determine if the input is executable.
   */
  private boolean valid;

  /**
   * This is the constructor of PopdCommand that instantiate the stack that
   * stores directories and FileSystemManager to control FileSystem.
   *
   * @param stackd is the stack that store directories.
   * @param fsm is the FileSystemManager to control the FileSystem.
   */
  public PopdCommand(DirectoryStack stackd, FileSystemManager fsm) {
    super(stackd, fsm);
    valid = false;
  }

  /**
   * This method pop out the most recently saved directory from the directory
   * stack.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    setPrintStream();
    try {
      Directory directory = stackd.pop();
      fsm.changeCurrent(directory.getAbsolutePath());
    } catch (EmptyContainerError |
        DirectoryNotFoundError e) {
      System.err.println(e.getMessage());
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This method prints the documentation for the PopdCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: PopdCommand");
    System.out.println("\tPop the last directory that pushed "
        + "into the directory stack");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * There is nothing here to setup actually.
   *
   * Only check if nothing is input ot it.
   *
   * @param args this is the parameters got from user for
   * @throws CommandError is the input is invalid.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    valid = true;
    if (args.size() != 0) {
      valid = false;
      throw new CommandError("popd: can't recognize " + args);
    }
  }

}
