package test.cmd;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.LsCommand;
import cmd.Output;
import exceptions.CommandError;


public class LsCommandTest {

  private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
  private final PrintStream stdout = System.out;
  private final PrintStream stderr = System.err;
  private Command command;
  private MockedFileSystemManager fsm;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));
    fsm = new MockedFileSystemManager();
    command = new LsCommand(fsm);


  }

  @Test
  public void getUsageTest() {
    String expected = "\n"
        + "usage: LsCommand\n"
        + "\tCall FileSystemManager to get the list of Files\n"
        + "\tand Directories inside the specified Directory\n"
        + "\tIf it is with -R tag, then recursively list\n"
        + "\tits subdirectories and files\n"
        + "\n";

    String actual = command.getUsage();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void noParameterExecuteTest() throws CommandError {
    List<String> args = new ArrayList<>();
    command.setUp(args);
    String expected = "childOne\n"
        + "childTwo\n"
        + "childFive\n";
    Output actual = command.execute();
    Assert.assertEquals(expected, actual.stdout);
  }

  @Test
  public void executeParameterRTest() throws CommandError {
    List<String> args = new ArrayList<>();
    args.add("-R");
    command.setUp(args);
    String expected = "childOne\n"
        + "childTwo\n"
        + "childFive\n"
        + "\n"
        + "/: childOne childTwo childFive \n"
        + "\tchildOne: \n"
        + "\tchildTwo: \n"
        + "\tchildFive: childSix childSeven \n"
        + "\tchildSix: \n"
        + "\tchildSeven\n";
    Output actual = command.execute();
    System.out.println(actual.stdout);
    System.out.println(actual.stderr);
    Assert.assertEquals(expected, actual.stdout);
  }

  @After
  public void restore() {
    System.setOut(stdout);
    System.setErr(stderr);
  }

}
