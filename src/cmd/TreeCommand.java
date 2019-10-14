package cmd;

import exceptions.CommandError;
import fs.Directory;
import fs.FileSystemManager;
import java.util.List;

/**
 * The class the the tree command.
 *
 * The command will be execute when user want to see the structure of
 *
 * the current file system.
 *
 * The user will visualize the data inside by a tree.
 */
public class TreeCommand extends FileSystemCommand {

  /**
   * This is to determine if the command is executable.
   */
  private boolean valid;

  /**
   * This method will use parent's constructor to setup.
   *
   * @param fsm is the file system manager.
   */
  public TreeCommand(FileSystemManager fsm) {
    super(fsm);
  }

  /**
   * The method will setup the variables for the tree command,
   *
   * which will check if there is no input.
   *
   * @param args this is the parameters got from user for FileSystemCommand.
   * @throws CommandError when input is invalid.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() != 0) {
      valid = false;
      throw new CommandError("tree: this command takes no parameter");
    } else {
      valid = true;
    }
  }

  /**
   * Print out the tree of all the data inside the file system.
   *
   * @return the output with stdout stderr.
   */
  @Override
  public Output execute() {
    setPrintStream();
    if (valid) {
      Directory root = fsm.getRoot();
      System.out.print(fsm.getTreeRepresentation(root));
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * Print out the usage message to user to check.
   *
   * @return a string of the usage.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: TreeCommand");
    System.out.println("Outputs all the Directory"
        + "\nand Files in tree structure");
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }
}
