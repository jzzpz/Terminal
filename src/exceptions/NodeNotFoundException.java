package exceptions;

public class NodeNotFoundException extends Exception{
  /**
   * This is a custom exception that will be called when perform functionality
   * to a Node that can not be found
   * 
   * @param message is the message to show that the Node can't not
   *        be found
   */
  private static final long serialVersionUID = 1L;

  /**
   * This is the constructor for NodeNotFoundException
   * @param message is error message
   */
  public NodeNotFoundException(String message) {
    super(message);
  }

}
