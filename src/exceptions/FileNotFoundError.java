package exceptions;

/**
 * This represents the FileNotFoundError
 */
public class FileNotFoundError extends Exception {

  /**
   * This is a custom exception that will be called when perform functionality
   * to a File that can not be found
   * 
   * @param message is the message to show that the File can't not be
   *        found
   */
  private static final long serialVersionUID = 1L;

  /**
   * This is the constructor for FileNotFoundError
   * @param message is error message
   */
  public FileNotFoundError(String message) {
    super(message);
  }


}
