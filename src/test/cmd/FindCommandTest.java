package test.cmd;

import cmd.Output;
import java.util.ArrayList;
import java.util.List;
import cmd.Command;
import cmd.FindCommand;
import exceptions.CommandError;
import fs.FileSystemManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FindCommandTest {

  private Command command;
  private FileSystemManager fsm;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new FindCommand(fsm);
  }

  @Test
  public void zeroParamsTest() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
    } catch (CommandError e) {
      String expected = "find: Please indicate path, type, and name.";
      Assert.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  public void lessThanTwoParamsTest() {
    List<String> args = new ArrayList<>();
    args.add("childOne");
    args.add("childTwo");
    args.add("childThree");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      String expected = "find: Please indicate path, type, and name.";
      Assert.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  public void getUsageTest() {
    String expected = "\n"
        + "usage: FindCommand\n"
        + "\tsearch on some given path\n"
        + "\tfor directory or file by name\n"
        + "\n";
    String actual = command.getUsage();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void executeFileTest() throws CommandError {
    List<String> args = new ArrayList<>();
    args.add("childFive");
    args.add("-type");
    args.add("f");
    args.add("-name");
    args.add("\"childSeven\"");
    command.setUp(args);
    Output output = command.execute();
    Assert.assertEquals("/childFive/childSeven\n", output.stdout);
  }

  @Test
  public void executeDirTest() throws CommandError {
    List<String> args = new ArrayList<>();
    args.add("childFive");
    args.add("-type");
    args.add("d");
    args.add("-name");
    args.add("\"childSix\"");
    command.setUp(args);
    Output output = command.execute();
    Assert.assertEquals("/childFive/childSix/\n", output.stdout);
  }

}
