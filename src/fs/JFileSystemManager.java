package fs;

import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import exceptions.NodeNotFoundException;

/**
 * This class represents the implementation of the FileSystemManager interface
 */
public class JFileSystemManager implements FileSystemManager {

  /**
   * This is the current directory
   */
  private Directory current;
  /**
   * This is the fileSystem
   */
  private FileSystem fs;

  /**
   * This is the constructor of the JFileSystemManager and it sets the
   * FileSystem and current directory
   */
  public JFileSystemManager() {
    fs = FileSystem.getInstance();
    current = fs.getRoot();
  }

  // methods about files

  /**
   * This create a file by a given path
   *
   * @param path is the path to the newly created file
   */
  @Override
  public void createFile(String path) throws DirectoryNotFoundError {
    int i = path.lastIndexOf("/");
    if (i == -1) {
      current.addChild(new File(path, current));
    } else {
      Directory directory = getDirectoryByPath(path.substring(0, i));
      directory.addChild(new File(path.substring(i + 1), directory));
    }
  }

  /**
   * This method add extra contents to a file with given name
   *
   * @param filename is the name of file to add content
   * @param extraContent is the extra content to add
   */
  @Override
  public void addFileContents(String filename, String extraContent)
      throws DirectoryNotFoundError, FileNotFoundError {
    File file = getFile(filename);
    file.addContents(extraContent);
  }

  /**
   * This method reset the content of a file with given name
   *
   * @param filename is the name of the file to reset content
   * @param content is the new content to reset
   */
  @Override
  public void setFileContents(String filename, String content)
      throws DirectoryNotFoundError, FileNotFoundError {
    File file = getFile(filename);
    file.setContents(content);
  }

  /**
   * check whether the name given is a name of a file inside the current
   * directory
   *
   * @param name is the name to check whether it is a file name
   * @return whether it is a file name
   */
  @Override
  public boolean fileCheck(String name) {
    Node testChild = current.findChild(name);

    return testChild instanceof File;
  }

  /**
   * This method returns the file on the location specified by path
   *
   * @param path is the path to look for the file
   * @return the a file on the location specified by path
   */
  @Override
  public File getFile(String path)
      throws DirectoryNotFoundError, FileNotFoundError {
    File file;
    int i = path.lastIndexOf("/");
    if (i == -1) {
      file = current.getFile(path);
    } else {
      Directory fileDirectory = getDirectoryByPath(path.substring(0, i));
      file = fileDirectory.getFile(path.substring(i + 1));
    }
    if (file == null) {
      throw new FileNotFoundError("File: " + path + " is not found");
    }
    return file;
  }

  // methods about directories

  /**
   * This method create a directory in the current directory with specified
   * name
   *
   * @param name is the name of the new directory
   */
  @Override
  public void createDirectory(String name) throws DirectoryNotFoundError {
    int i = name.lastIndexOf("/");
    if (i == -1) {
      new Directory(name, current);
    } else {
      Directory directory = getDirectoryByPath(name.substring(0, i));
      if (name.substring(i + 1).isEmpty()) {
        throw new DirectoryNotFoundError("invalid directory name");
      }
      new Directory(name.substring(i + 1), directory);
    }
  }


  /**
   * This method delete a child with given name from the current working
   * directory
   *
   * @param name is the name of the node to delete
   */
  @Override
  public void delete(String name)
      throws DirectoryNotFoundError, NodeNotFoundException {
    int index = name.lastIndexOf("/");
    if (index == -1) {
      current.deleteChild(name);
    } else {
      Directory dir = getDirectoryByPath(name.substring(0, index));
      dir.deleteChild(name.substring(index + 1));
    }
  }

  /**
   * This method return a directory of the location specified by path
   *
   * @param path is the path to look for this directory
   * @return the directory of the location specified by path
   * @throws DirectoryNotFoundError if directory doesn't exist.
   */
  @Override
  public Directory getDirectoryByPath(String path)
      throws DirectoryNotFoundError {
    if (path.equals("/")) {
      return fs.getRoot();
    }
    String[] pathArray = path.split("/");
    Directory curr = current;
    for (int i = 0; i < pathArray.length; i++) {
      if (i == 0 && pathArray[i].isEmpty()) {
        curr = fs.getRoot();
      } else if (pathArray[i].equals("..")) {
        curr = curr.getParent();
      } else if (!pathArray[i].isEmpty() && !pathArray[i].equals(".")) {
        curr = curr.getDirectory(pathArray[i]);
      }
      // if not in the directory, throw the error
      if (curr == null) {
        throw new DirectoryNotFoundError("directory " + path + " is not found");
      }
    }

    return curr;
  }

  /**
   * This method return a node of the location specified by path.
   *
   * @param path is the path to look for this node.
   * @return the node of the location specified by path.
   * @throws NodeNotFoundException if node doesn't exist.
   */
  @Override
  public Node getNodeByPath(String path)
      throws NodeNotFoundException {
    if (path.equals("/")) {
      return fs.getRoot();
    }
    String[] pathArray = path.split("/");
    Node temp = current;
    for (int i = 0; i < pathArray.length; i++) {
      if (i == 0 && pathArray[i].isEmpty()) {
        temp = fs.getRoot();
      } else if (pathArray[i].equals("..")) {
        temp = temp.getParent();
      } else if (!pathArray[i].isEmpty() && !pathArray[i].equals(".")) {
        if (!(temp instanceof Directory)) {
          throw new NodeNotFoundException(path + " is not found");
        }
        temp = ((Directory) temp).findChild(pathArray[i]);
      }
      // if not in the directory, throw the error
      if (temp == null) {
        throw new NodeNotFoundException(path + " is not found");
      }
    }

    return temp;
  }

  /**
   * check whether the name given is a name of a directory inside the current
   * directory
   *
   * @param name is the name to check whether it is a directory name
   * @return whether it is a directory name
   */
  @Override
  public boolean directoryCheck(String name) {
    Node testChild = current.findChild(name);

    return testChild instanceof Directory;

  }

  // methods about the current

  /**
   * This method change the current directory to the given path
   *
   * @param pathToChange is the path to change to
   */
  @Override
  public void changeCurrent(String pathToChange)
      throws DirectoryNotFoundError {
    current = getDirectoryByPath(pathToChange);
  }

  /**
   * this method return the absolute path of the current directory
   *
   * @return the absolute path for the current directory
   */
  @Override
  public String getCurrentPath() {
    return current.getAbsolutePath();
  }

  /**
   * This method return the current directory
   *
   * @return the current directory
   */
  @Override
  public Directory getCurrent() {
    return current;
  }

  /**
   * The method will get the root Directory.
   *
   * @return the root directory.
   */
  @Override
  public Directory getRoot() {
    return fs.getRoot();
  }

  /**
   * The method will call the helper method to get the tree
   *
   * representation.
   *
   * @param dir is a node to get tree string.
   * @return the string of all the data related on the input node.
   */
  @Override
  public String getTreeRepresentation(Node dir) {
    return getTreeRepresentationHelper(dir, 1);
  }

  /**
   * Recursively get the tree representation of the data.
   *
   * @param dir is a node to get tree string.
   * @param depth is the depth we are in, related on the first input node.
   * @return the string of all the data related on the input node.
   */
  @Override
  public String getTreeRepresentationHelper(Node dir, Integer depth) {
    StringBuilder result = new StringBuilder(dir.getName() + "\n");

    if (dir instanceof Directory) {

      for (Object o : ((Directory) dir)) {
        for (int i = 0; i < depth; i++) {
          result.append("\t");
        }
        result.append(getTreeRepresentationHelper(
            (Node) o, depth + 1));
      }
    }

    return result.toString();
  }


}
