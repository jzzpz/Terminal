package cmd;

import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import fs.FileSystemManager;

/**
 * The class will append the redirection string into an indicated file.
 */
public class RedirectionAppend extends RedirectionDecorator {

  /**
   * The constructor will call the parent to setup.
   *
   * @param fsm is a file system manager.
   * @param command is a command to execute.
   */
  public RedirectionAppend(FileSystemManager fsm, Command command) {
    super(fsm, command);
  }

  /**
   * The method will call the command to execute and then add into another
   * file.
   *
   * @return a Output contains stdout and stderr as string.
   */
  @Override
  public Output execute() {
    Output output = super.execute();
    if (!output.stderr.isEmpty()) {
      return output;
    }
    // append into the file
    try {
      fsm.addFileContents(filePath, output.stdout);
    } catch (FileNotFoundError | DirectoryNotFoundError e) {
      return new Output("", "No such file or directory: " + filePath + "\n");
    }
    return new Output("", "");
  }
}
