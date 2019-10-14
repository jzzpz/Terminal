package cmd;

import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.NodeNotFoundException;
import fs.FileSystemManager;
import java.util.List;

/**
 * This represents the MvCommand which can move a existed file into a new file
 * or under some directory.
 */
public class MvCommand extends CpCommand {

  /**
   * The constructor will setup the file system manager.
   *
   * @param fsm is a file system maanger.
   */
  public MvCommand(FileSystemManager fsm) {
    super(fsm);
  }

  // since the setUp for mv is the same as cp so don't need to override setUp


  /**
   * The method will mv one file to another directory.
   *
   * Therefore, we are going to use cp first and then delete the original file.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    // call super class cp to copy the file first
    Output output = super.execute();
    if (!output.stderr.isEmpty()) {
      output.stderr = output.stderr.replace("cp", "mv");
      return output;
    }
    // then try to delete the original one
    try {
      if (!newPath.startsWith(oldPath)) {
        fsm.delete(oldPath);
      }
    } catch (DirectoryNotFoundError | NodeNotFoundException e) {
      return new Output("", e.getMessage());
    }
    return output;
  }

  /**
   * The method will get the usage of the cp command.
   *
   * @return a string of the usage.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("getUsage: mv CMD");
    System.out.println("\tmove an item from OLDPATH to NEWPath");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * The method will set up two path.
   *
   * @param args this is the parameters got from user for.
   * @throws CommandError when input is invalid.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() == 2) {
      oldPath = args.get(0);
      newPath = args.get(1);
      valid = true;
    } else {
      valid = false;
      throw new CommandError("mv: accept only two parameters");
    }
  }


}
