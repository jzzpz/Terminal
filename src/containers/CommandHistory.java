package containers;

import exceptions.EmptyContainerError;
import java.util.ArrayList;
import java.util.List;

/**
 * CommandHistory has a list contains all history commands, the push and pop
 * works like a queue, but pop may never be called.
 */
public class CommandHistory implements Container<String> {

  /**
   * This is the queue to store all the command history.
   */
  private List<String> queue;

  public CommandHistory() {
    queue = new ArrayList<>();
  }

  /**
   * This method will push one just typed command by user, and store this string
   * into the list.
   *
   * @param obj is the history command to be stored.
   */
  @Override
  public void push(String obj) {
    queue.add(obj);
  }

  /**
   * This method will remove the very first command in the container.
   *
   * @return a string which is the very first command.
   * @throws EmptyContainerError if container is empty.
   */
  @Override
  public String pop() throws EmptyContainerError {
    if (queue.isEmpty()) {
      // needs an exception for empty command history
      throw new EmptyContainerError("The Command history is empty");
      //return null;
    } else {
      return queue.remove(0);
    }
  }

  /**
   * The method will return the getSize of the list.
   *
   * @return a int represents the getSize.
   */
  public int getSize() {
    return queue.size();
  }

  /**
   * The method will look for a specific element by given index.
   *
   * @param index is the index for looking for.
   * @return a string at the specific index.
   */
  public String getByIndex(int index) {
    if (index >= queue.size()) {
      return null;
    }
    return queue.get(index);
  }

  @Override
  public String toString() {
    StringBuilder totalItem = new StringBuilder();
    for (String aItem : queue) {
      totalItem.append(aItem);
    }
    return totalItem.toString();
  }

  /**
   * This method will check if the queue is empty.
   *
   * @return if the queue is empty.
   */
  public boolean isEmpty() {
    return queue.size() == 0;
  }

}
