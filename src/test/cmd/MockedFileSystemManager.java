package test.cmd;

import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import exceptions.NodeNotFoundException;
import fs.Directory;
import fs.File;
import fs.FileSystemManager;
import fs.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents the MockedFileSystemManager
 */
public class MockedFileSystemManager implements FileSystemManager {

  /**
   * This is the path property, first it is set as root
   */
  private String path = "/";

  private Directory root = new Directory("/", null);
  private Directory childOne = new Directory("childOne", root);
  private Directory childTwo = new Directory("childTwo", root);
  private Directory current = root;
  private File childThree = new File("childThree", childTwo,
      "childThree content");
  private File childFour = new File("childFour", childOne,
      "childFour content");
  private Directory childFive = new Directory("childFive", root);

  private List<File> files = new ArrayList<>();


  public MockedFileSystemManager() {
    this.root.setParent(root);
    Directory childSix = new Directory("childSix", childFive);
    childFive.addChild(childSix);
    File childSeven = new File("childSeven", childFive);
    childFive.addChild(childSeven);
  }

  /**
   * This method create a file in current directory
   *
   * @param name is the name of the file
   */
  @Override
  public void createFile(String name) throws DirectoryNotFoundError {
    for (File file : files) {
      if (file.getName().equals(name)) {
        return;
      }
    }
    File file = new File(name, null);
    file.setContents("");
    files.add(file);
  }

  /**
   * This method create a directory in the current directory
   *
   * @param name is the name of the new directory
   */
  @Override
  public void createDirectory(String name) throws DirectoryNotFoundError {

  }

  /**
   * This method delete a file with specified name in the current directory
   *
   * @param name is the name of the file to be deleted
   */
  @Override
  public void delete(String name) throws DirectoryNotFoundError {

  }

  /**
   * The method change the current directory by the path given
   *
   * @param pathToChange is the path to change to
   */
  @Override
  public void changeCurrent(String pathToChange) throws DirectoryNotFoundError {
    if (pathToChange.equals("childOne") || pathToChange.equals("/childOne")) {
      this.current = this.childOne;
    } else if (pathToChange.equals("childTwo")
        || pathToChange.equals("/childTwo")) {
      this.current = this.childTwo;
    } else if (pathToChange.equals("/")) {
      this.current = root;
    }

    if (pathToChange.startsWith("/")) {
      path = pathToChange;
    } else {
      path = path + pathToChange;
    }
  }

  /**
   * This method add new content to the file with given name
   *
   * @param filename is the name of file to add content
   * @param extraContent is the extra content to add
   */
  @Override
  public void addFileContents(String filename, String extraContent)
      throws DirectoryNotFoundError, FileNotFoundError {
    for (File file : files) {
      if (file.getName().equals(filename)) {
        file.setContents(file.getContents() + extraContent);
      }
    }
  }

  /**
   * This method reset the content of the file with given name
   *
   * @param filename is the name of the file to reset content
   * @param content is the new content to reset
   */
  @Override
  public void setFileContents(String filename, String content)
      throws DirectoryNotFoundError, FileNotFoundError {
    for (File file : files) {
      if (file.getName().equals(filename)) {
        file.setContents(content);
      }
    }
  }

  /**
   * This method get the absolute path of the current directory
   *
   * @return the path of the current directory
   */
  @Override
  public String getCurrentPath() {
    return path;
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
   * This method return a directory of the location specified by path
   *
   * @param path is the path to look for this directory
   * @return the directory of the location specified by path
   */
  @Override
  public Directory getDirectoryByPath(String path)
      throws DirectoryNotFoundError {
    if (path.equals("childOne") || path.equals("/childOne")) {
      return childOne;
    } else if (path.equals("childTwo") || path.equals("/childTwo")) {
      return childTwo;
    } else if (path.equals("/")) {
      throw new DirectoryNotFoundError("directory " + path + " is not found");
    } else if (path.equals("childFive")) {
      return childFive;
    }
    return null;

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
    for (File file : files) {
      if (file.getName().equals(path)) {
        return file;
      }
    }
    Directory a = new Directory("test", null);
    switch (path) {
      case "testfile1":
        return new File("test-file-one", a,
            "mocked-contents-one");
      case "testfile2":
        return new File("test-file-two", a,
            "mocked-contents-two");
      case "testfile3":
        return new File("test-file-three", a,
            "mocked-contents-three");
      case "NotExistFile":
        throw new FileNotFoundError("NotExistFile");
      default:
        throw new DirectoryNotFoundError("NotExistDirectory");
    }
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
    return false;
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
    return false;
  }

  @Override
  public Directory getRoot() {
    return root;
  }

  @Override
  public String getTreeRepresentation(Node dir) {
    return "\\";
  }

  @Override
  public String getTreeRepresentationHelper(Node dir, Integer depth) {
    return "\\";
  }

  @Override
  public Node getNodeByPath(String path)
      throws NodeNotFoundException {
    if (path.equals("/childOne") || path.equals("childOne")) {
      return this.childOne;
    } else if (path.equals("/childTwo") || path.equals("childTwo")) {
      return this.childTwo;
    } else if (path.equals("/childThree") || path.equals("childThree")) {
      return this.childThree;
    } else if (path.equals("/childFour") || path.equals("childFour")) {
      return this.childFour;
    }
    return this.root;
  }

  Directory getChildOne() {
    return childOne;
  }

  Directory getChildTwo() {
    return childTwo;
  }

  File getChildThree() {
    return childThree;
  }

}
