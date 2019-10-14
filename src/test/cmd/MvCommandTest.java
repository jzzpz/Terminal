package test.cmd;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.MvCommand;
import exceptions.CommandError;
import fs.Directory;
import fs.File;
import fs.FileSystemManager;
import fs.Node;

public class MvCommandTest {
  private Command command;
  private FileSystemManager fsm;
  
  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new MvCommand(fsm);
  }
  @Test
  public void getUsageTest() {
    String expected =
        "\ngetUsage: mv CMD\n" +
        "\tmove an item from OLDPATH to NEWPath\n\n";
    String actual = command.getUsage();
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void zeroParamTest() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // fix this bug
      String expected = "mv: accept only two parameters";
      Assert.assertEquals(expected, e.getMessage());
    }
  }
  
  @Test
  public void validParamDirToDirTest() throws CommandError {
    List<String> args = new ArrayList<>();
    Directory child1 = ((MockedFileSystemManager) fsm).getChildOne();
    args.add("childOne");
    args.add("childTwo");
    command.setUp(args);
    // move childOne into childTwo
    command.execute();
    Directory child2 = ((MockedFileSystemManager) fsm).getChildTwo();
    Directory child = (Directory) child2.getChildren().get(0);
    List<Node> listOfDir = new ArrayList<>();
    listOfDir.add(child2);
    // change mock for getDirectoryList
    boolean result = (child1.getName() == child.getName() ||
        fsm.getRoot().getChildren() == listOfDir);
    Assert.assertTrue(result);
    
  }
  @Test
  public void validParamFileToDir() throws CommandError {
    List<String> args = new ArrayList<>();
    File child3 = ((MockedFileSystemManager) fsm).getChildThree();
    args.add("childThree");
    args.add("childOne");
    command.setUp(args);
    // move childThree into childOne
    command.execute();
    Directory child1 = ((MockedFileSystemManager) fsm).getChildOne();
    File child = (File) child1.getChildren().get(0);
    List<Node> listOfNodes = new ArrayList<>();
    
    boolean result = (child3.getName() == child.getName() ||
        ((MockedFileSystemManager) fsm).getChildTwo().getChildren()
        == listOfNodes);
    Assert.assertTrue(result);
  }
  
  
 
}
