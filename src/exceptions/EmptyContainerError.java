package exceptions;

/**
 * This represents the EmptyContainerError
 */
public class EmptyContainerError extends Exception {

  /**
   * This is a custom exception when popping from an empty container
   * 
   * @param message is the message to show that is popping from an empty
   *        container
   */
  private static final long serialVersionUID = 1L;

  /**
   * This is the constructor for EmptyContainerError
   * @param message is error message
   */
  public EmptyContainerError(String message) {
    super(message);
  }

}
