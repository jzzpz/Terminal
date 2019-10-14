package cmd;

import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.NodeNotFoundException;
import fs.Directory;
import fs.File;
import fs.FileSystemManager;
import fs.Node;
import java.util.List;

/**
 * This represents the CpCommand which can copy a existed file into a new file
 * or under some directory.
 */
public class CpCommand extends FileSystemCommand {

  /**
   * This is the file name to copy from
   */
  String oldPath;

  /**
   * This is the destination could be a file name or directory name
   */
  String newPath;

  /**
   * This is to determine if the command should execute
   */
  boolean valid;

  /**
   * This constructor will call its parent constructor to setup.
   *
   * @param fsm is a file system manager.
   */
  public CpCommand(FileSystemManager fsm) {
    super(fsm);
  }

  /**
   * The method will copy the contents of the old file path indicated by user to
   * a new file that is also indicated by user.
   *
   * @return a string of the documentation.
   */
  @Override
  public Output execute() {
    if (!valid) {
      return new Output("", "");
    }
    setPrintStream();
    try {
      // try to get nodes from old path and new path
      Node oldNode = fsm.getNodeByPath(oldPath);
      Node newNode = fsm.getNodeByPath(newPath);
      copyExistNodes(oldNode, newNode);
    } catch (NodeNotFoundException e) {
      try {
        // if the second path doesn't exist, then we create new for that
        Node oldNode = fsm.getNodeByPath(oldPath);
        int index = newPath.lastIndexOf("/");
        Directory dir = index == -1 ? fsm.getCurrent() : fsm.getDirectoryByPath(
            newPath.substring(0, newPath.lastIndexOf("/")));
        createNewNodes(oldNode, dir,
            index == -1 ? newPath : newPath.substring(index + 1));
      } catch (NodeNotFoundException | DirectoryNotFoundError e1) {
        System.err.println(e1.getMessage());
      }
    }
    Output output = new Output(outStream.toString(), errStream.toString());
    restorePrintStream();
    return output;
  }

  /**
   * This method will create new node, if the new path doesn't exist.
   *
   * @param oldNode is the old node
   * @param dir is the directory copy to
   * @param name is the name of the new node
   */
  private void createNewNodes(Node oldNode, Directory dir, String name) {
    if (oldNode instanceof File) {
      File newFile = new File(name, dir, ((File) oldNode).getContents());
      dir.addChild(newFile);
    } else if (oldNode instanceof Directory) {
      Node newDir = copyNodeRecursively(oldNode);
      if (newDir == null) {
        return;
      }
      newDir.setParent(dir);
      newDir.setName(name);
      dir.addChild(newDir);
    }
  }

  /**
   * This method is for recursively copying all the nodes inside the
   *
   * old node children.
   *
   * @param node is the old node
   * @return a new node with exactly the same with old node
   */
  private Node copyNodeRecursively(Node node) {
    if (node instanceof File) {
      return new File(node.getName(), node.getParent(),
          ((File) node).getContents());
    } else if (node instanceof Directory) {
      Directory directory = new Directory(node.getName(), node.getParent());
      for (Node subNode : ((Directory) node).getChildren()) {
        directory.addChild(copyNodeRecursively(subNode));
      }
      return directory;
    }
    // for now this line will never be called,
    // cause we define node only be file or directory,
    // but for future, it may change
    return null;
  }

  /**
   * This method will copy oldNode to newNode if they both exist,
   *
   * based on if they are File or Directory, there are four cases,
   *
   * The last case should be else, but for future use, maybe we would
   *
   * have more Nodes rather than file and directory.
   *
   * @param oldNode is the old node
   * @param newNode is the new node
   */
  private void copyExistNodes(Node oldNode, Node newNode) {
    // if is D -> F, then give error message
    if (oldNode instanceof Directory && newNode instanceof File) {
      System.err.println("cp: " + oldPath + " is a directory but " + newPath
          + " is a file.");
    } else if (oldNode instanceof Directory && newNode instanceof Directory) {
      // if is D -> D, then copy same old directory into new directory
      Node newDir = copyNodeRecursively(oldNode);
      if (newDir == null) {
        return;
      }
      newDir.setParent((Directory) newNode);
      ((Directory) newNode).addChild(newDir);
    } else if (oldNode instanceof File && newNode instanceof Directory) {
      // if is F -> D, then copy old file into new file
      File newFile = new File(oldNode.getName(), (Directory) newNode,
          ((File) oldNode).getContents());
      ((Directory) newNode).addChild(newFile);
    } else if (oldNode instanceof File && newNode instanceof File) {
      // if is F1 -> F2, then override F2
      ((File) newNode).setContents(((File) oldNode).getContents());
    }
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
      throw new CommandError("cp: accept only two parameters");
    }
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
    System.out.println("getUsage: cp CMD");
    System.out.println("\tcopy an item from OLDPATH to NEWPath");
    System.out.println();
    String usage = outStream.toString();
    restoreStdoutStream();
    return usage;
  }
}
