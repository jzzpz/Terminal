package cmd;

import exceptions.CommandError;
import fs.FileSystemManager;

import java.util.List;

/**
 * This represents the EchoCommand.
 *
 * This command can print some content typed by user.
 *
 * It can also replace the content of a specified file
 * with new content typed by
 * the user.
 *
 * It can also append some new content typed by the user to the end of a
 * specified file.
 */
public class EchoCommand extends FileSystemCommand {

  /**
   * This is the content to print
   */
  private String text;
  /**
   * This is to determine if command could run
   */
  private boolean valid;

  /**
   * This is the constructor of the EchoCommand and it
   * instantiate the property
   * of FileSystemManager to control the FileSystem.
   *
   * @param fsm is used to make changes to the FileSystem.
   */
  public EchoCommand(FileSystemManager fsm) {
    super(fsm);
    valid = false;
  }

  /**
   * This execute method is used to finish the functionality of the echo
   * command, by using the parameters set up by the setUp methods.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    setPrintStream();
    System.out.println(this.text);
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This setUp method is used to setup the parameter which will be used by
   * execute method later.
   *
   * @param args is all the parameter given by the user for EchoCommand.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    valid = true;
    if (args.size() != 1) {
      valid = false;
      throw new CommandError("echo: invalid number of parameters");
    } else {
      this.text = args.get(0);
      if (text.matches("\".*?\"")) {
        text = text.substring(1, text.length() - 1);
      } else {
        valid = false;
        throw new CommandError(
            "echo: input string should be surrounded by double quote");
      }
    }
  }

  /**
   * This method prints the documentation of the EchoCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: EchoCommand");
    System.out.println("\tCreate a file if file doesn't exists");
    System.out.println("If file exists and using '>', then replace"
        + "\t the file's content with new content");
    System.out.println("If file exists and using '>>',"
        + "\t then add new content to the end of the file's content");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

}
