package fs;

import exceptions.NodeNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This represent Directory
 */
public class Directory extends Node implements Iterable<Node> {

  /**
   * This is a list of children for this directory
   */
  private List<Node> children;

  /**
   * This is the constructor of the Directory class which instantiate its name
   * and parent directory
   *
   * @param name is the name of this newly created directory
   * @param parent is the parent directory of this newly created directory
   */
  public Directory(String name, Directory parent) {
    super(name, parent);
    this.children = new ArrayList<>();
    if (this.parent != null) {
      this.parent.addChild(this);
    }
  }

  public List<Node> getChildren() {
    return children;
  }


  /**
   * This method add a new Node to the children of this directory
   *
   * @param node is the node to add as a child
   */
  public void addChild(Node node) {
    if (node != null) {
      for (Node nodeInChildren : this.children) {
        if (nodeInChildren.equals(node)) {
          return;
        }
      }
      this.children.add(node);
      if (node instanceof Directory) {
        node.setParent(this);
      }
    }
  }

  /**
   * This method delete a child specified by name
   *
   * @param name is the name of the child
   */
  public void deleteChild(String name) throws NodeNotFoundException {
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).getName().equals(name)) {
        children.remove(i);
        return;
      }
    }
    throw new NodeNotFoundException("No such File or Directory");
  }

  /**
   * This method find and return a child specified by name
   *
   * @param name is the name of the child
   * @return a Node child with the given name
   */
  public Node findChild(String name) {
    for (Node node : children) {
      if (node.getName().equals(name)) {
        return node;
      }
    }
    return null;
  }

  /**
   * This method find and return a directory specified by name
   *
   * @param dirName is the name of the directory
   * @return one directory with the same name given
   */
  public Directory getDirectory(String dirName) {
    Node node = findChild(dirName);
    if (node instanceof File) {
      return null;
    }
    return (Directory) node;
  }

  /**
   * This method find and return a file specified by name
   *
   * @param fileName is the name of the file
   * @return one file with the same given name
   */
  public File getFile(String fileName) {
    Node node = findChild(fileName);
    if (node instanceof Directory) {
      return null;
    }
    return (File) node;
  }

  /**
   * This method get the absolute path for the current directory
   *
   * @return the absolute path for the current directory
   */
  public String getAbsolutePath() {
    String path;

    if (this.getParent() == this) {
      path = "/";
    } else {
      path = this.parent.getAbsolutePath() + this.name + "/";
    }

    return path;
  }


  /**
   * This method returns all the children that are directories
   *
   * @return a list of all the directories children for the current Directory
   */
  public List<Directory> getDirectoryList() {
    List<Directory> directories = new ArrayList<>();
    for (Node node : children) {
      if (node instanceof Directory) {
        directories.add((Directory) node);
      }
    }
    return directories;
  }

  /**
   * This method returns the all the children that are files
   *
   * @return a list of all the file type children for the current Directory
   */
  public List<File> getFiles() {
    List<File> files = new ArrayList<>();
    for (Node node : children) {
      if (node instanceof File) {
        files.add((File) node);
      }
    }
    return files;
  }

  /**
   * This method will enable the iterator functionality.
   *
   * @return the Iterator we defined.
   */
  @Override
  public Iterator<Node> iterator() {
    return new DirectoryIterator(this);
  }

  /**
   * This inner class is the actual Iterator we implemented.
   */
  private class DirectoryIterator implements Iterator<Node> {

    /**
     * This is used to store all the data in the iterator.
     */
    List<Node> nodeList;

    /**
     * This is to trace which index we are.
     */
    Integer nextIndex = 0;

    /**
     * This constructor will setup the nodeLsit.
     */
    DirectoryIterator(Directory dir) {
      this.nodeList = dir.getChildren();
    }

    /**
     * This method will check if we still have next in the iterator.
     *
     * @return true if we didn't reach the end of the list yet.
     */
    @Override
    public boolean hasNext() {
      return nextIndex < nodeList.size();
    }

    /**
     * This method will give out the next data for user.
     *
     * @return the next Node.
     */
    @Override
    public Node next() {
      Node tempNode = nodeList.get(nextIndex);
      nextIndex++;
      return tempNode;
    }
  }

  /**
   * Override the toString method to visualize our data.
   *
   * @return a string representation of the Directory.
   */
  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder(
        "Node " + this.getName() + " with parent ");
    if (this.getParent() != null) {
      rep.append(this.getParent().getName()).append("\n");
    } else {
      rep.append("null\n");
    }
    rep.append("with child:");
    for (Node node : children) {
      rep.append(" ").append(node.getName());
    }
    return rep.toString();
  }

}
