package cmd;

import containers.CommandHistory;
import containers.DirectoryStack;
import exceptions.CommandError;
import fs.Directory;
import fs.FileSystemManager;
import fs.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for save command. It will save the current status into
 *
 * the real file system, so that next time, when user start over the JShell,
 *
 * they could load the file, so that they would have the same files and
 *
 * directories.
 */
public class SaveCommand extends FileSystemCommand {

  /**
   * This is the file name that we are going to store in the real file system.
   */
  private String fileName;
  /**
   * This is the command history queue.
   */
  private CommandHistory cmdHistory;
  /**
   * This is the stack for the directories.
   */
  private DirectoryStack stackd;
  /**
   * This is the Utility class for saving the JShell session information
   */
  private SystemFileController sfc;

  /**
   * This constructor will set up the file system manager, command history
   *
   * queue, and directories stack.
   *
   * @param fsm is the file system manager.
   * @param cmdHistory is the command history queue.
   * @param stackd is the directories stack.
   */
  public SaveCommand(
      FileSystemManager fsm,
      CommandHistory cmdHistory,
      DirectoryStack stackd,
      SystemFileController sfc
  ) {
    super(fsm);
    this.cmdHistory = cmdHistory;
    this.stackd = stackd;
    this.sfc = sfc;
  }

  /**
   * The method will get all files and directories, and also command history,
   *
   * and directory stack. Then write all of those contents into a file which
   *
   * is indicated by the user.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    List<String> result;
    Directory root = fsm.getRoot();
    // get all the information of the FileSystem here
    result = getAllPaths(root);
    String stringToWrite = "";

    for (int i = 0; i < this.cmdHistory.getSize(); i++) {
      String cmd = this.cmdHistory.getByIndex(i);
      stringToWrite = stringToWrite + cmd + '\n';
    }

    stringToWrite = stringToWrite + "\n";
    for (int j = 0; j < this.stackd.getLength(); j++) {
      Directory a = this.stackd.getByIndex(j);
      stringToWrite = stringToWrite + a.getAbsolutePath() + '\n';
    }

    stringToWrite = stringToWrite + "\n";
    for (String item : result) {
      item = '/' + item + '\n';
      stringToWrite = stringToWrite + item;
    }

    this.sfc.save(this.fileName, stringToWrite);
    return new Output("", "");
  }

  /**
   * This method will get all the paths by given a input node.
   *
   * The method will recursively find all the nodes including files and
   *
   * directories. and then return in a list.
   *
   * @param file is a input source node.
   * @return a list of all path under the given node.
   */
  private List<String> getAllPaths(Node file) {
    List<String> result = new ArrayList<>();
    Integer startIndex = 0;

    // file the input is a directory, then get all recursive path
    // including files and directories.
    if (file instanceof Directory) {
      if (file.getName() != "/") {
        result.add(file.getName());
        startIndex = 1;
      }

      for (Node a : ((Directory) file).getChildren()) {
        List<String> tempList = getAllPaths(a);
        result.addAll(tempList);
      }

      for (int i = startIndex; i < result.size(); i++) {
        String item = result.get(i);
        if (file.getName() != "/") {
          item = file.getName() + '/' + item;
        }
        result.set(i, item);
      }
    } else {
      // if input is a file, then get the file directly
      result.add(file.getName() + " -f " + ((fs.File) file).getContents());
    }

    return result;
  }

  /**
   * This method prints the documentation of a command that deal with the
   * FileSystem.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("getUsage: get CMD");
    System.out.println("\tget file information from a certain URL");
    System.out.println("\tand download it as a file with same name");
    System.out.println("\tand save it into the current working directory");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * This method will set up the file to store in the real file system.
   *
   * @param args this is the parameters got from user for
   * @throws CommandError when input is invalid.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() != 1) {
      throw new CommandError("save: only accept one parameter");
    } else {
      this.fileName = args.get(0);
    }
  }

}
