package containers;

import exceptions.EmptyContainerError;

/**
 * This is a interface for all containers used by JShell, which would
 * contains two methods push and pop.
 * @param <T> is the type of data that container stored.
 */
public interface Container<T> {

  /**
   * THe method will push the input element into the container.
   * @param obj is the element to be stored.
   */
  void push(T obj);

  /**
   * The method will pop one element from the container if there is.
   * @return one element from the container.
   * @throws EmptyContainerError is the container is empty.
   */
  T pop() throws EmptyContainerError;

}
