package cmd;

import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import fs.Directory;
import fs.File;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The class the a find command class. It will find all occurrences of file or
 * directory which is specified by user.
 */
public class FindCommand extends FileSystemCommand {

  /**
   * This is to determine if the command now is valid.
   */
  private boolean valid;

  /**
   * This is used to store all dir names from user.
   */
  private List<String> pathNames;

  /**
   * This is the name we are looking for.
   */
  private String name;

  /**
   * This is the type we are looking for.
   */
  private String type;

  /**
   * The constructor will call parent constructor to setup.
   */
  public FindCommand(FileSystemManager fsm) {
    this.fsm = fsm;
    cleanUp();
  }

  /**
   * The method will find all the occurrences of the file or directory if the
   * input path and arguments are valid.
   *
   * @return a Output which has stdout and stderr as string.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    setPrintStream();
    for (String path : pathNames) {
      try {
        LinkedList<Directory> queue = new LinkedList<>();
        queue.add(fsm.getDirectoryByPath(path));

        bfs(queue);
      } catch (DirectoryNotFoundError e) {
        System.err.println(e.getMessage());
      }
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    cleanUp();
    return output;
  }

  /**
   * The method will do a bfs for given queue to check if there exist file or
   * directory that we are looking for. And then print out the result.
   *
   * @param queue is the queue to use during the bfs.
   */
  private void bfs(LinkedList<Directory> queue) {
    while (!queue.isEmpty()) {
      Directory directory = queue.poll();

      // loop through each file if we are looking for files
      if (type.equals("f")) {
        for (File file : directory.getFiles()) {
          if (file.getName().equals(name)) {
            System.out.println(directory.getAbsolutePath() + name);
          }
        }
      }

      // loop through each directory,
      // add into queue and also if we are looking for directory, then check
      for (Directory subDirectory : directory.getDirectoryList()) {
        queue.add(subDirectory);
        if (type.equals("d") && subDirectory.getName().equals(name)) {
          System.out.println(subDirectory.getAbsolutePath());
        }
      }

    }
  }

  /**
   * The method will set up all the arguments to the command. Only if it is
   * valid, the command will execute.
   *
   * @param args this is the parameters got from user for
   * @throws CommandError when input is wrong.
   */
  @Override
  public void setUp(List<String> args) throws CommandError {
    valid = true;
    if (args.size() < 5) {
      setUpError("find: Please indicate path, type, and name.");
    }
    pathNames = new ArrayList<>();
    pathNames.addAll(args.subList(0, args.size() - 4));
    for (int i = 0; i < 2; i++) {
      switch (args.get(args.size() - 2 * i - 2)) {
        case "-name":
          name = args.get(args.size() - 1 - 2 * i);
          if (!name.matches("\".*\"")) {
            setUpError("find: file name must be surrounded by quotes.");
          } else {
            name = name.substring(1, name.length() - 1);
          }
          break;
        case "-type":
          type = args.get(args.size() - 1 - 2 * i);
          if (!type.equals("f") && !type.equals("d")) {
            setUpError("find: type must be f or d.");
          }
          break;
        default:
          setUpError(
              "find: can't recognize " + args.get(args.size() - 2 * i - 2));
      }
    }
    if (name == null || type == null) {
      setUpError("find: missing type or name.");
    }
  }

  /**
   * The method will throw the CommandError if any input is invalid.
   *
   * @param msg is the msg to show.
   * @throws CommandError when invalid inpu.
   */
  private void setUpError(String msg) throws CommandError {
    cleanUp();
    throw new CommandError(msg);
  }

  /**
   * The method will reset valid, name, and type.
   *
   * The method will be call if one command is done (both executed and invlid
   * input error exception count).
   */
  private void cleanUp() {
    valid = false;
    name = null;
    type = null;
  }

  /**
   * The method will return the usage of the command to the user.
   *
   * @return a string of the usage.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: FindCommand");
    System.out.println("\tsearch on some given path");
    System.out.println("\tfor directory or file by name");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }
}
