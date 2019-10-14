package cmd;

import exceptions.DirectoryNotFoundError;
import fs.FileSystemManager;

import java.util.List;

/**
 * This represents the MkdirCommand which used to create a new directory in the
 * place specified by user.
 */
public class MkdirCommand extends FileSystemCommand {

  /**
   * This is a list contains all the directories to do ls.
   */
  public List<String> directories;

  /**
   * This is the constructor for MkdirCommand and it instantiate the
   * FileSystemManager that will be used to change the FileSystem.
   *
   * @param fsm is the FileSystemManager which helps add new directory.
   */
  public MkdirCommand(FileSystemManager fsm) {
    super(fsm);
  }

  /**
   * This is the method that will be used to make a new directory.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    setPrintStream();
    if (directories.size() == 0) {
      System.err.println(
          "please enter the directory name you want to create");
      return null;
    }
    for (String dirName : directories) {
      try {
        this.fsm.createDirectory(dirName);
      } catch (DirectoryNotFoundError e) {
        System.err.println(e.getMessage());
      }
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This is the method that will be used to set up the directory list.
   *
   * @param args this is the list of directory names got from user.
   */
  @Override
  public void setUp(List<String> args) {
    this.directories = args;
  }

  /**
   * This is the method that uses to print the documentation of MkdirCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: MkdirCommand");
    System.out.println("\tCall FileSystemManager to create a new "
        + "Directory in place get from User");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }
}
