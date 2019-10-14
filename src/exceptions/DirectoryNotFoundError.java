package exceptions;

/**
 * This represents the DirectoryNotFoundError
 */
public class DirectoryNotFoundError extends Exception {
  /**
   * This is a custom exception that will be called when perform functionality
   * to a Directory that can not be found
   * 
   * @param message is the message to show that the Directory can't not
   *        be found
   */
  private static final long serialVersionUID = 1L;

  /**
   * This is the constructor for DirectoryNotFoundError
   * @param message is error message
   */
  public DirectoryNotFoundError(String message) {
    super(message);
  }

}
