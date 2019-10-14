package cmd;

import exceptions.DirectoryNotFoundError;
import fs.FileSystemManager;
import java.util.List;

/**
 * The class is the decorator for the Command class.
 *
 * Therefore, this class will achieve the redirection functionality.
 *
 * It has two subclass which is '>' and '>>' for redirection.
 *
 * Deu to the decorator, it supports redirects the output of redirection
 *
 * which would be empty string.
 */
public abstract class RedirectionDecorator implements Command {

  /**
   * The command is the collaborator which is part of the decorator design.
   */
  protected Command command;

  /**
   * The fsm is used to help communicate with file system.
   */
  protected FileSystemManager fsm;

  /**
   * The file path is for recording redirection file.
   */
  String filePath;

  /**
   * The setup flag is to check if filePath is setup.
   */
  protected boolean setup;

  /**
   * The valid flag is to check if multiple filePath is given, which is a wrong
   * input.
   */
  protected boolean valid;

  /**
   * Constructor of the RedirectionDecorator, set up fsm and command.
   *
   * @param fsm is a file system manager used to make changes in file system.
   * @param command is a command to get the result from.
   */
  public RedirectionDecorator(FileSystemManager fsm, Command command) {
    this.fsm = fsm;
    this.command = command;
    this.setup = false;
    this.valid = true;
  }

  /**
   * The method will first execute the command method, and then if there is no
   * stderr, then redirect the contents to a indicated file.
   *
   * @return a Output contains stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("",
          "Please enter a single file path\n");
    }
    if (!setup) {
      return new Output("",
          "Please indicate which file should redirect to\n");
    }
    // create file if not exist
    try {
      fsm.createFile(filePath);
    } catch (DirectoryNotFoundError e) {
      return new Output("",
          "No such file or directory: " + filePath + "\n");
    }
    // execute command first and get the output
    return command.execute();
  }

  /**
   * The method will not be called, extends only because it is a decorator.
   *
   * @return null.
   */
  @Override
  public String getUsage() {
    return null;
  }

  /**
   * The method will setup the indicated file path, and change the flag.
   *
   * @param args this is the parameters got from user for command
   */
  @Override
  public void setUp(List<String> args) {
    if (setup) {
      valid = false;
    } else {
      filePath = args.get(0);
      setup = true;
    }
  }
}
