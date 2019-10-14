package cmd;

import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.List;

/**
 * This represent the PwdCommand, this command will show path of current
 * directory when user type pwd.
 */
public class PwdCommand extends FileSystemCommand {

  /**
   * This is the parameter that shows whether the parameter is valid
   */
  private boolean valid;

  /**
   * This is the constructor of the PwdCommand, it instantiate the fsm parameter
   * inside its parent.
   *
   * @param fsm This is parameter used to instantiate the FileSystemManager.
   * inside its parent class.
   */
  public PwdCommand(FileSystemManager fsm) {
    super(fsm);
    valid = false;
  }

  /**
   * This method is called when user types pwd, it will print the path of the
   * current directory.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    setPrintStream();
    String path = fsm.getCurrentPath();
    System.out.println(path);
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This method is used to check whether there are extra parameters for pwd.
   * Because there is no arguments required for pwd. So print error messages
   * when there is any extra parameter.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args == null || args.size() > 0) {
      valid = false;
      throw new CommandError("pwd: no arguments accepted");
    } else {
      valid = true;
    }
  }

  /**
   * This method print the documentation for this command.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: PwdCommand");
    System.out.println("\tCall the FileSystemManager to get the "
        + "absolute path of the current Directory");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }
}
