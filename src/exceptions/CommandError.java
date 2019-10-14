package exceptions;

/**
 * This represents the CommandError
 */
public class CommandError extends Exception {

  /**
   * This class is a custom exception that will be called when the input
   * parameter is not valid for the commands
   *
   * @param message is the message to show the input parameter is not valid
   * for the commands
   */
  private static final long serialVersionUID = 1L;

  /**
   * This is the constructor for CommandError
   * @param message is error message
   */
  public CommandError(String message) {
    super(message);
  }

}
