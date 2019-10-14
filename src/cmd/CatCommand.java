package cmd;

import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import fs.FileSystemManager;
import fs.File;

import java.util.List;

/**
 * This represents the CatCommand, this command will show the content of the
 * given file(s)
 */
public class CatCommand extends FileSystemCommand {

  /**
   * This is the list of Files which needs to show the content
   */
  private List<String> listOfFiles;
  /**
   * This is the valid property to check whether the given parameter is valid
   * for CatCommand
   */
  private boolean valid;

  /**
   * This is the constructor of the CatCommand,
   * it instantiate the fsm parameter
   * inside its parent.
   *
   * @param fsm This is parameter used to instantiate the FileSystemManager
   * inside its parent class
   */
  public CatCommand(FileSystemManager fsm) {
    super(fsm);
  }

  /**
   * This method is called when user type cat.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    setPrintStream();
    if (valid) {
      File file;
      for (String fileName : this.listOfFiles) {
        try {
          file = this.fsm.getFile(fileName);
          System.out.print(file.getContents());
        } catch (FileNotFoundError | DirectoryNotFoundError e) {
          System.err.println(e.getMessage());
        }
      }
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This method is used to set up the parameters
   * for cat command store the list
   * of filenames inside the listOfFiles parameter
   *
   * it will also check whether the parameter is more than one, because cat
   * command takes at least one parameter
   *
   * @param args this is the parameters for the CatCommand typed by user
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args == null || args.size() == 0) {
      valid = false;
      throw new CommandError("cat: at least has one parameter");
    } else {
      valid = true;
      this.listOfFiles = args;
    }
  }

  /**
   * This method is used to print the documentation of the CatCommand.
   *
   * @return a string of the documentation.
   */
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("getUsage: cat CMD");
    System.out.println("\tPrint the Documentation for File(s)");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }
}
