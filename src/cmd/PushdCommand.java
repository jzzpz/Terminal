package cmd;

import containers.DirectoryStack;
import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import fs.FileSystemManager;
import java.util.List;

/**
 * This represents the PushdCommand class and it push a directory specified by
 * user into the directory stack.
 */
public class PushdCommand extends DirectoryStackCommand {

  /**
   * This is the name of the directory to push into the stack
   */
  private String directoryName;
  /**
   * This is the parameter that shows whether the parameter is valid
   */
  private boolean valid;

  /**
   * This is the constructor for PushCommand and it get a stack to store
   * directories and a FileSystemManager to take control of FileSystem.
   *
   * @param stackd is a stack used for storing directories.
   * @param fsm is used to change FileSystem.
   */
  public PushdCommand(DirectoryStack stackd, FileSystemManager fsm) {
    super(stackd, fsm);
    valid = false;
  }

  /**
   * This methods set up the directory to be pushed into directory stack.
   *
   * @param args this is the parameters got from user for PushdCommand.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() != 1) {
      valid = false;
      throw new CommandError("pushd: accept only one filename");
    }
    this.directoryName = args.get(0);
    valid = true;
  }

  /**
   * This method is used to push a specified directory into the directory
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
      if (fsm.getDirectoryByPath(directoryName) != null) {
        stackd.push(fsm.getCurrent());
        fsm.changeCurrent(
            fsm.getDirectoryByPath(directoryName).getAbsolutePath());
      } else {
        throw new DirectoryNotFoundError("Directory: " +
            directoryName + " is not found");
      }
    } catch (DirectoryNotFoundError e) {
      System.err.println(e.getMessage());
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * this method is used to print the documentation of the PushdCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: PushdCommand");
    System.out.println("\tPush a Directory into the stack");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

}
