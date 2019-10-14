package test.cmd;

import cmd.Command;
import cmd.CpCommand;
import cmd.Output;
import exceptions.CommandError;
import fs.Directory;
import fs.File;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CpCommandTest {

  private Command command;
  private FileSystemManager fsm;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new CpCommand(fsm);
  }

  @Test
  public void moreThanTwoParamsTest() {
    List<String> args = new ArrayList<>();
    args.add("abc");
    args.add("dasd");
    args.add("qefn");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      String expected = "cp: accept only two parameters";
      Assert.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  public void zeroParamTest() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
    } catch (CommandError e) {
      String expected = "cp: accept only two parameters";
      Assert.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  public void validParamDirToDirTest() {
    List<String> args = new ArrayList<>();
    Directory child1 = ((MockedFileSystemManager) fsm).getChildOne();
    args.add("childOne");
    args.add("childTwo");
    try {
      command.setUp(args);
      command.execute();
      Directory child2 = ((MockedFileSystemManager) fsm).getChildTwo();
      Directory child = (Directory) child2.getChildren().get(0);
      Assert.assertEquals(child1.getName(), child.getName());
    } catch (CommandError commandError) {
      System.out.println(commandError.getMessage());
    }
  }

  @Test
  public void validParamFileToDir() {
    List<String> args = new ArrayList<>();
    File child3 = ((MockedFileSystemManager) fsm).getChildThree();
    args.add("childThree");
    args.add("childOne");

    try {
      command.setUp(args);
      command.execute();
      Directory child1 = ((MockedFileSystemManager) fsm).getChildOne();
      File child = (File) child1.getChildren().get(0);
      Assert.assertEquals(child3.getName(), child.getName());
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }
  }

  @Test
  public void validParamDirToFile() {
    List<String> args = new ArrayList<>();
    args.add("childOne");
    args.add("childThree");

    try {
      command.setUp(args);
      Output output = command.execute();
      String res = "cp: childOne is a directory but childThree is a file.\n";
      Assert.assertEquals(res, output.stderr);
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }
  }

  @Test
  public void validParamFileToFile() {
    List<String> args = new ArrayList<>();
    args.add("childFour");
    args.add("childThree");

    try {
      command.setUp(args);
      command.execute();
      Assert.assertEquals("childFour content",
          ((MockedFileSystemManager) fsm).getChildThree().getContents());
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }
  }

  @Test
  public void getUsageTest() {
    String expected =
        "\ngetUsage: cp CMD\n" +
        "\tcopy an item from OLDPATH to NEWPath\n\n";
    String actual = command.getUsage();
    Assert.assertEquals(expected, actual);
  }

}
