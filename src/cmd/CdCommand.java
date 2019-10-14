package cmd;

import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import fs.FileSystemManager;

import java.util.List;

/**
 * This represents the CdCommand
 *
 * It will change the current directory to the directory specified by user
 *
 * If there is no directory mentioned by user in shell then go to the root
 * directory
 */
public class CdCommand extends FileSystemCommand {

  /**
   * This is the path property to move to
   */
  private String path;
  /**
   * This is the valid property to check whether the given parameter is valid
   * for CatCommand
   */
  private boolean valid;

  /**
   * This is the constructor of the CdCommand, it instantiate the fsm parameter
   * inside its parent.
   *
   * @param fsm This is parameter used to instantiate the FileSystemManager
   * inside its parent class
   */
  public CdCommand(FileSystemManager fsm) {
    super(fsm);
    valid = false;
  }

  /**
   * This method is called when user type cd in the shell It will change the
   * current pointer to another directory.
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
      this.fsm.changeCurrent(this.path);
    } catch (DirectoryNotFoundError e) {
      System.err.println(e.getMessage());
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This method is used to print the documentation for the CdCommand.
   *
   * @return a string of the documentation.
   */
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("getUsage: cd CMD");
    System.out.println(
        "\tChange directory to DIR, which may be relative\n" +
            "to the current directory or may be a full path.\n");
    System.out.println(
        "\tAs with Unix, .. means a parent directory and a .means\n" +
            "\tthe current directory. The directory must be /,\n" +
            "\tthe forward slash.\n" +
            "\tThe foot of the file system is a single slash: /.");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * This method is used to check and set up the parameter for the CdCommand
   *
   * if there is no parameter, then change the current directory to the root, if
   * there are more than one parameter, then give errors
   *
   * @param args this is the parameters got from user for CdCommand
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    this.valid = true;
    if (args.size() == 0) {
      this.path = "/";
    } else if (args.size() == 1) {
      this.path = args.get(0);
    } else {
      this.valid = false;
      throw new CommandError("cd: too many arguments");
    }
  }
}
