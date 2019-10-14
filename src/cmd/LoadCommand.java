package cmd;

import containers.CommandHistory;
import containers.DirectoryStack;
import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import fs.Directory;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is the load command which will load the JShell file from the
 *
 * real file system into our mocked file system in JShell, so that our JShell
 *
 * could continue from the last time it terminated.
 */
public class LoadCommand extends FileSystemCommand {

  /**
   * This is the file name indicated by user.
   */
  private String fileName;
  /**
   * This is a list to store the contents in file.
   */
  private List<String> fileInfo;
  /**
   * This is a list to store the history information.
   */
  private List<String> historyInfo;
  /**
   * This is a list to store the stack information.
   */
  private List<String> stackInfo;
  /**
   * This is a queue stored all command history
   */
  private CommandHistory cmdHistory;
  /**
   * This is a directory stack to store all directory that pushed into.
   */
  private DirectoryStack stackd;

  /**
   * This is the SystemFileController used for getting session information
   */
  private SystemFileController sfc;

  /**
   * The constructor will setup the file system manager, history command, and
   * directory stack.
   *
   * @param fsm is a file system manager.
   * @param cmdHistory is a history command queue.
   * @param stackd is a directory stack.
   */
  public LoadCommand(
      FileSystemManager fsm,
      CommandHistory cmdHistory,
      DirectoryStack stackd,
      SystemFileController sfc) {
    super(fsm);
    this.cmdHistory = cmdHistory;
    this.stackd = stackd;
    this.sfc = sfc;
    fileInfo = new ArrayList<>();
    historyInfo = new ArrayList<>();
    stackInfo = new ArrayList<>();
  }

  /**
   * The method will load the file and then setup the files and directories,
   * command history, and directory stack.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    setPrintStream();
    if (cmdHistory.getSize() > 1) {
      System.err.println("load: load must be the first command you typed in");
    } else {
      String contents = sfc.read(fileName);
      this.fileUp(contents);
      // reset all files and directories.
      for (String name : fileInfo) {
        createNode(name);
      }
      // reset all directories used to be in the stack.
      for (String path : stackInfo) {
        try {
          Directory temp = fsm.getDirectoryByPath(path);
          stackd.push(temp);
        } catch (DirectoryNotFoundError directoryNotFoundError) {
          directoryNotFoundError.printStackTrace();
        }
      }
      // reset all the command history.
      for (String cmd : historyInfo) {
        cmdHistory.push(cmd);
      }
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
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
    System.out.println("usage: LoadCommand");
    System.out.println("\tload session information from a file");
    System.out.println("\tand restart the previous JShell process");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }

  /**
   * The method will setup the arguments from user.
   *
   * @param args this is the parameters got from user for
   * @throws CommandError when is input is invalid.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() != 1) {
      throw new CommandError("load: only accept one parameter");
    } else {
      this.fileName = args.get(0);
    }
  }

  /**
   * The method will read the file content and split the information into
   * historyInfo, stackInfo, fileInfo
   *
   * @param contents is athe content read from the file
   */
  private void fileUp(String contents) {
    String[] contentArray = contents.split("\n");
    List<String> temp = historyInfo;
    for (String s : contentArray) {
      if (s.equals("")) {
        if (temp == historyInfo) {
          temp = stackInfo;
        } else if (temp == stackInfo) {
          temp = fileInfo;
        }
      } else {
        temp.add(s);
      }
    }

  }

  /**
   * The method will create file or directory based on the file we read from the
   * real file system.
   *
   * @param fileName is a file indicated by user.
   */
  private void createNode(String fileName) {
    String[] infoList = fileName.split(" ");

    if (infoList.length == 1) {
      try {
        // create directory
        fsm.createDirectory(infoList[0]);
      } catch (DirectoryNotFoundError e) {
        System.err.println(e.getMessage());
      }
    } else if (infoList.length == 3) {
      if (infoList[1].equals("-f")) {
        try {
          // create file and set contents
          fsm.createFile(infoList[0]);
          fs.File tempFile = fsm.getFile(infoList[0]);
          tempFile.setContents(infoList[2]);
        } catch (DirectoryNotFoundError | FileNotFoundError e) {
          System.err.println(e.getMessage());
        }
      } else {
        System.err.println("no file flag founded here");
      }
    } else {
      System.err.println("error Node type");
    }
  }
}
