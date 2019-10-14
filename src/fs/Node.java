package fs;

/**
 * This class represent a Node, a Node can both be a File or a Directory
 */
public class Node {

  /**
   * This is the name of the Node
   */
  String name;

  /**
   * This is the parent directory, which means who contains this file.
   */
  Directory parent;


  /**
   * This is the constructor of the node and it takes a name parameter and a
   * type parameter
   *
   * @param name is the name of the Node
   */
  public Node(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
  }

  /**
   * This method returns the name of the node
   *
   * @return the name of the Node
   */
  public String getName() {
    return name;
  }

  /**
   * This method will set the name for the file.
   *
   * @param newName is the file name.
   */
  public void setName(String newName) {
    name = newName;
  }

  /**
   * This method return whether two nodes are equal
   *
   * @param node is the node to compare with this node
   * @return a boolean to represent whether they are equal
   */
  public boolean equals(Node node) {
    return node != null && name.equals(node.getName());
  }

  /**
   * This method will get the parent of the file, which means the directory that
   * has this file.
   *
   * @return a directory that contains this file.
   */
  public Directory getParent() {
    return this.parent;
  }

  /**
   * This method will set the parent of the file.
   *
   * @param newParent the parent directory of the file.
   */
  public void setParent(Directory newParent) {
    this.parent = newParent;
  }
}
