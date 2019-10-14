package test.containers;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import containers.DirectoryStack;
import exceptions.EmptyContainerError;
import fs.Directory;

public class TestDirectoryStack {

  private Directory parent;
  private DirectoryStack mainStack;

  @Before
  public void setUp() {
    parent = new Directory("t", null);
    mainStack = new DirectoryStack();
    mainStack.push(parent);
  }

  @Test
  public void TestPopOneItem() throws EmptyContainerError {

    Directory actual = mainStack.pop();

    Directory expected = new Directory("t", null);
    Assert.assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void TestPopMultiItem() throws EmptyContainerError {
    Directory aDirOne = new Directory("a1", parent);
    Directory aDirTwo = new Directory("b1", parent);
    mainStack.push(aDirOne);
    mainStack.push(aDirTwo);
    List<String> actual = new ArrayList<>();
    while (!mainStack.isEmpty()) {
      actual.add(mainStack.pop().toString());
    }
    List<String> expected = new ArrayList<>();
    expected.add(aDirTwo.toString());
    expected.add(aDirOne.toString());
    expected.add(parent.toString());
    Assert.assertEquals(expected, actual);


  }

  @Test
  public void TestGetLength() {
    int actual = mainStack.getLength();
    Assert.assertEquals(1, actual);
  }

  @Test
  public void TestIsEmpty() {
    boolean actual = !mainStack.isEmpty();
    Assert.assertTrue(actual);
  }

  @Test
  public void TestGetByIndex() {
    Directory aDirOne = new Directory("a1", parent);
    mainStack.push(aDirOne);
    Directory actual = mainStack.getByIndex(1);

    Assert.assertEquals(aDirOne, actual);

  }

}
