package fs;

/**
 * This is the FileSystem. It stores a root
 * Directory that can links to every file and directory
 */
public class FileSystem {

  /**
   * This is the root directory
   */
  private static Directory root;
  private static FileSystem fs=null;

  /**
   * This is the private constructor for the FileSystem
   */
  private FileSystem() {
    root = new Directory("/", null);
    root.setParent(root);
  }


  /**
   * This method return a instance of FileSystem
   * @return a instance a FileSystem
   */
  public static FileSystem getInstance() {
    if (fs == null) {
      fs = new FileSystem();
    }
    return fs;
  }


  /**
   * This method return the root directory
   * @return the root directory
   */
  public Directory getRoot() {
    return root;
  }


}
