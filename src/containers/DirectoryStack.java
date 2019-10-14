package containers;

import fs.Directory;
import java.util.Stack;
import exceptions.EmptyContainerError;

/**
 * The DirectoryStack stores all references of Directories and it is a LIFO.
 */
public class DirectoryStack implements Container<Directory> {

  /**
   * This is the stack to store the directories.
   */
  private Stack<Directory> stack;

  public DirectoryStack() {
    stack = new Stack<>();
  }

  /**
   * The method will push the input directory into the top of the stack.
   *
   * @param obj is the element to be stored.
   */
  @Override
  public void push(Directory obj) {
    stack.push(obj);
  }

  /**
   * The method will pop out the top directory in the stack.
   *
   * @return is a directory which is on the top of the stack.
   * @throws EmptyContainerError if stack is empty.
   */
  @Override
  public Directory pop() throws EmptyContainerError {
    if (stack.empty()) {
      // needs one exception for empty stack
      throw new EmptyContainerError("Directory Stack is empty");
      // return null;
    } else {
      return stack.pop();
    }
  }

  /**
   * This method will check if the stack is empty.
   *
   * @return a boolean if stack is empty.
   */
  public boolean isEmpty() {
    return this.stack.size() == 0;
  }

  /**
   * This method will check how many directories are in the stack right now.
   *
   * @return number of elements in the stack.
   */
  public Integer getLength() {
    return this.stack.size();
  }

  /**
   * This method will show the directory by giving a index.
   *
   * @param index is the index given.
   * @return a directory in stack based on the index.
   */
  public Directory getByIndex(int index) {
    if (index >= stack.size()) {
      return null;
    }
    return stack.get(index);
  }
}
