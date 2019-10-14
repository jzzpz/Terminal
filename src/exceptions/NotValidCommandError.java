package exceptions;

/**
 * This represents the NotValidCommandError
 */
public class NotValidCommandError extends Exception {

  /**
   * This is a custom exception that will be called when user input a command
   * that is not valid
   * 
   * @param message is the message to show that the user the input command is
   *        not valid
   */
  private static final long serialVersionUID = 1L;

  /**
   * This is the constructor for NotValidCommandError
   * @param message is error message
   */
  public NotValidCommandError(String message) {
    super(message);
  }

}
