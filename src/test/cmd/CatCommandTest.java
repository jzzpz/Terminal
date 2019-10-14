package test.cmd;

import cmd.CatCommand;
import cmd.Command;
import cmd.Output;
import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CatCommandTest {

  private Command command;

  @Before
  public void setUp() {

    FileSystemManager fsm = new MockedFileSystemManager();
    command = new CatCommand(fsm);
  }


  @Test
  public void notExistFileTest() {
    List<String> args = new ArrayList<>();
    args.add("NotExistFile");
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError commandError) {
      Assert.assertEquals("NotExistFile\n", commandError.getMessage());
    }

  }

  @Test
  public void notExistDirectoryTest() {
    List<String> args = new ArrayList<>();
    args.add("somethignelse");
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError commandError) {
      Assert.assertEquals("NotExistDirectory\n", commandError.getMessage());
    }
  }

  @Test
  public void catSingleFileTest() {
    List<String> args = new ArrayList<>();
    args.add("testfile1");
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError commandError) {
      Assert.assertEquals("mocked-contents-one\n", commandError.getMessage());
    }
  }

  @Test
  public void catMultipleFilesTest() {
    List<String> args = new ArrayList<>();
    args.add("testfile1");
    args.add("testfile2");
    args.add("testfile3");
    String res = "mocked-contents-onemocked-contents-twomocked-contents-three";
    try {
      command.setUp(args);
      Output output = command.execute();
      Assert.assertEquals(res, output.stdout);
    } catch (CommandError commandError) {
      System.err.println(commandError.getMessage());
    }
  }

  @Test
  public void getUsuageTest() {
    String actual = command.getUsage();
    String expected =
        "\ngetUsage: cat CMD\n\tPrint the Documentation " + "for File(s)\n\n";
    Assert.assertEquals(expected, actual);
  }
}
