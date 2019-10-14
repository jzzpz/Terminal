package fs;

import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import exceptions.NodeNotFoundException;

/**
 * This represents the FileSystemManager interface
 */
public interface FileSystemManager {

  /**
   * This create a file inside the current directory
   *
   * @param name is the name of the file
   */
  void createFile(String name) throws DirectoryNotFoundError;

  /**
   * This method create a directory in the current directory with specified
   * name
   *
   * @param name is the name of the new directory
   */
  void createDirectory(String name) throws DirectoryNotFoundError;

  /**
   * This method delete a child from the ChildList of the current working
   * directory
   *
   * @param name is the name of the node to delete
   */
  void delete(String name) throws DirectoryNotFoundError, NodeNotFoundException;

  /**
   * This method add extra contents to a file with given name
   *
   * @param filename is the name of file to add content
   * @param extraContent is the extra content to add
   */
  void addFileContents(String filename, String extraContent)
      throws DirectoryNotFoundError, FileNotFoundError;

  /**
   * This method reset the content of a file with given name
   *
   * @param filename is the name of the file to reset content
   * @param content is the new content to reset
   */
  void setFileContents(String filename, String content)
      throws DirectoryNotFoundError, FileNotFoundError;

  /**
   * This method change the current directory to the given path
   *
   * @param pathToChange is the path to change to
   */
  void changeCurrent(String pathToChange) throws DirectoryNotFoundError;

  /**
   * this method return the absolute path of the current directory
   *
   * @return the absolute path for the current directory
   */
  String getCurrentPath();

  /**
   * This method return the current directory
   *
   * @return the current directory
   */
  Directory getCurrent();

  /**
   * check whether the name given is a name of a file inside the current
   * directory
   *
   * @param name is the name to check whether it is a file name
   * @return whether it is a file name
   */
  boolean fileCheck(String name);

  /**
   * check whether the name given is a name of a directory inside the current
   * directory
   *
   * @param name is the name to check whether it is a directory name
   * @return whether it is a directory name
   */
  boolean directoryCheck(String name);

  /**
   * This method return a directory of the location specified by path
   *
   * @param path is the path to look for this directory
   * @return the directory of the location specified by path
   */
  Directory getDirectoryByPath(String path)
      throws DirectoryNotFoundError;

  /**
   * This method returns the file on the location specified by path
   *
   * @param path is the path to look for the file
   * @return the a file on the location specified by path
   */
  File getFile(String path)
      throws DirectoryNotFoundError, FileNotFoundError;

  Directory getRoot();

  String getTreeRepresentation(Node dir);

  String getTreeRepresentationHelper(Node dir, Integer depth);

  Node getNodeByPath(String path)
      throws NodeNotFoundException;
}
