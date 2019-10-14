package cmd;

import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.List;
import fs.File;

/**
 * The class represents the get Command, which would look at the url input from
 *
 * the user, and then download the contents from there to our file system.
 */
public class GetURLCommand extends FileSystemCommand {

  /**
   * The url input from the user.
   */
  private String givenURL;

  /**
   * This parameter is used for getting file content on a certain URL
   */
  private RemoteDataFetcher rdf;

  /**
   * The constructor will call the parent constructor to setup.
   *
   * @param fsm is a file system manager.
   */
  public GetURLCommand(FileSystemManager fsm, RemoteDataFetcher rdf) {
    super(fsm);
    this.rdf = rdf;
  }

  /**
   * The method will setup the url for the command to execute from user.
   *
   * @param args this is the parameters got from user for
   * @throws CommandError when the input is invalid.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    if (args.size() != 1) {
      throw new CommandError("get: accept at most one parameter");
    } else {
      this.givenURL = args.get(0);
    }
  }

  /**
   * The method will parse the url so that we could get the file name.
   *
   * @return is a string of the file name.
   */
  private String getFileNameFromURL() {
    String fileName;
    String[] tempArray = givenURL.split("/");
    fileName = tempArray[tempArray.length - 1];
    return fileName;
  }

  /**
   * The method will connect to the url and the then download the contents from
   * the url provided by user.
   *
   * @@return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    // create file for adding contents downloaded from the net.
    String fileName = this.getFileNameFromURL();
    File aFile = new File(fileName, fsm.getCurrent());
    fsm.getCurrent().addChild(aFile);
    String content = rdf.getRemoteDataFromURL(this.givenURL);
    aFile.setContents(content);
    return new Output("", "");
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
    System.out.println("usage: GetURLCommand");
    System.out.println("\tRetrieve the file at that URL and add it to ");
    System.out.println("\tthe current working directory");
    String usage = outStream.toString();
    restorePrintStream();
    return usage;
  }
}
