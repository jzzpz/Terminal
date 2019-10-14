package test.containers;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import containers.CommandHistory;
import exceptions.EmptyContainerError;


public class TestCommandHistory {
  CommandHistory aHistory;

  @Before
  public void setUp() {
    aHistory = new CommandHistory();
  }

  @Test
  public void ContainerPopEmptyStringTest() {

    aHistory.push("");

    String actual = "";
    try {
      actual = aHistory.pop();
    } catch (EmptyContainerError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Assert.assertEquals("", actual);
  }

  @Test
  public void EmptyContainerGetSizeTest() {
    Assert.assertEquals(0, aHistory.getSize());
  }

  @Test
  public void ContainerPopOneItemTest() throws EmptyContainerError {

    aHistory.push("mkdir test1");

    String actual = null;

    actual = aHistory.pop();


    Assert.assertEquals(actual, "mkdir test1");

  }

  @Test
  public void ContainerPopMultiItemTest() throws EmptyContainerError {
    List<String> expected = new ArrayList<>();
    List<String> actual = new ArrayList<>();

    expected.add("mkdir test1");
    expected.add("pwd");
    expected.add("cd test1");

    for (String aCommand : expected) {
      aHistory.push(aCommand);
    }

    while (aHistory.getSize() != 0) {
      actual.add(aHistory.pop());

    }
    assertEquals(expected, actual);

  }
}
