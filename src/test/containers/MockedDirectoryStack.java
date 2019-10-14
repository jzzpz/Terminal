package test.containers;

import fs.Directory;
import java.util.Stack;

public class MockedDirectoryStack {
  private Stack<Directory> stack = new Stack<>();

  public MockedDirectoryStack() {
    Directory root = new Directory("/", null);
    Directory child1 = new Directory("child1", root);
    Directory child2 = new Directory("child2", root);
    this.stack.push(child2);
    this.stack.push(child1);
  }

}
