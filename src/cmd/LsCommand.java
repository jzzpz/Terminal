package cmd;

import exceptions.NodeNotFoundException;
import fs.Directory;
import fs.File;
import fs.FileSystemManager;

import fs.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents the LsCommand which can show the files and directories
 * inside a specified directory.
 */
public class LsCommand extends FileSystemCommand {

  /**
   * This is the path to the directory
   */
  private List<String> path;
  /**
   * This is to determine if the ls is going to be recursive or not.
   */
  private boolean recursive;

  /**
   * this is the constructor for the LsCommand that instantiate the fsm
   * property.
   *
   * @param fsm is the FileSystemManager used to control the FileSystem.
   */
  public LsCommand(FileSystemManager fsm) {
    super(fsm);
  }

  /**
   * This method finish all the functionality of the LsCommand eg. if the path
   * given is a file, then print the filename if the path given directs to a
   * directory, then print the content of the directory if no parameter is
   * given, then print the content of the current directory.
   *
   * @return a string of the documentation.
   */
  @Override
  public Output execute() {
    setPrintStream();
    // if no parameter is given, print the content of the current directory
    if (path.size() == 0) {
      noParamPrint();
      // if there are parameters
    } else {
      // loop through each path
      for (String name : path) {
        try {
          Node temp = fsm.getNodeByPath(name);
          this.singleList(temp);
          if (recursive && temp instanceof Directory) {
            for (Node child : ((Directory) temp).getChildren()) {
              recursivelyList(child);
            }
          }
        } catch (NodeNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This method print the information for the current directory
   *
   * if there is no parameter
   */
  private void noParamPrint() {
    Directory current = fsm.getCurrent();

    List<Node> children = current.getChildren();
    for (Node child : children) {
      System.out.println(child.getName());
    }

    if (recursive) {
      if (children.size() != 0) {
        System.out.println();
      }
      this.singleList(current);
      for (Node child : current.getChildren()) {
        recursivelyList(child);
      }
    }
  }

  /**
   * Do ls to a single directory, so only for the given directory.
   *
   * @param source is a directory or file to print.
   */
  private void singleList(Node source) {
    if (source instanceof File) {
      System.out.println(source.getName());
    } else if (source instanceof Directory) {
      List<Node> children = ((Directory) source).getChildren();
      System.out.print(source.getName() + ": ");
      for (Node child : children) {
        System.out.print(child.getName() + " ");
      }
      System.out.println();
    }
  }


  /**
   * This method will recursively print out the contents inside the list.
   *
   * It is also a bread first traversal.
   *
   * Only when user has -R flag, then we will recursively print.
   *
   * @param source is the node to start.
   */
  private void recursivelyList(Node source) {
    System.out.print("\t");
    this.singleList(source);

    if (source instanceof Directory) {
      List<Node> children = ((Directory) source).getChildren();
      for (Node child : children) {
        recursivelyList(child);
      }
    }
  }

  /**
   * This is the method used to set up parameters for LsCommand.
   *
   * @param args this is the parameters got from user for LsCommand.
   */
  @Override
  public void setUp(List<String> args) {
    recursive = false;
    this.path = new ArrayList<>();
    if (args.size() != 0) {
      for (String arg : args) {
        if (arg.equals("-R")) {
          recursive = true;
        } else {
          this.path.add(arg);
        }
      }
    } else {
      this.path = args;
    }
  }

  /**
   * This method prints the documentation for the LsCommand.
   *
   * @return a string of the documentation.
   */
  @Override
  public String getUsage() {
    setStdoutStream();
    System.out.println();
    System.out.println("usage: LsCommand");
    System.out.println("\tCall FileSystemManager to get the list of Files");
    System.out.println("\tand Directories inside the specified Directory");
    System.out.println("\tIf it is with -R tag, then recursively list");
    System.out.println("\tits subdirectories and files");
    System.out.println();
    String usage = outStream.toString();
    restorePrintStream();
    return usage;
  }
}
