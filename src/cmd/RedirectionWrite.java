package cmd;

import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import fs.FileSystemManager;

/**
 * The class will write the redirection string into an indicated file.
 */
public class RedirectionWrite extends RedirectionDecorator {

  /**
   * The constructor will call the parent to setup.
   *
   * @param fsm is a file system manager.
   * @param command is a command to execute.
   */
  public RedirectionWrite(FileSystemManager fsm, Command command) {
    super(fsm, command);
  }

  /**
   * The method will call the command to execute and then write the stdout
   * string into an indicated file.
   *
   * @return a Output contains stdout and stderr as string.
   */
  @Override
  public Output execute() {
    Output output = super.execute();
    if (!output.stderr.isEmpty()) {
      return output;
    }
    // write into the file
    try {
      fsm.setFileContents(filePath, output.stdout);
    } catch (FileNotFoundError | DirectoryNotFoundError e) {
      return new Output("", "No such file or directory: " + filePath + "\n");
    }
    return new Output("", "");
  }
}
