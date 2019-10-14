package test.cmd;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.HistoryCommand;
import cmd.Output;
import containers.CommandHistory;
import exceptions.CommandError;
import org.junit.Assert;

public class HistoryCommandTest {
  private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
  private final PrintStream stdout = System.out;
  private final PrintStream stderr = System.err;
  private Command command;
  private CommandHistory commandHistory;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));
    commandHistory = new CommandHistory();
    command = new HistoryCommand(commandHistory);


  }

  @Test
  public void negativeInputTest() {
    List<String> args = new ArrayList<>();
    args.add("-10");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      System.err.println(e.getMessage());
    }
    command.execute();
    String expected = "history: can't accept negative number\n";
    Assert.assertEquals(expected, errStream.toString());

  }

  @Test
  public void invalidArgumentInputTest() {
    List<String> args = new ArrayList<>();
    args.add("2");
    args.add("3");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      System.err.println(e.getMessage());
    }
    command.execute();
    String expected = "history: accept at most one parameter\n";
    Assert.assertEquals(expected, errStream.toString());

  }

  @Test
  public void excuteWithNoParameterTest() {
    List<String> args = new ArrayList<>();
    commandHistory.push("ls");
    commandHistory.push("pwd");
    commandHistory.push("history");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      System.err.println(e.getMessage());
    }
    Output output = command.execute();

    String expected = "0. ls\n" + "1. pwd\n" + "2. history\n";
    Assert.assertEquals(expected, output.stdout);
  }

  @Test
  public void excuteWithValidParameterTest() {
    List<String> args = new ArrayList<>();
    commandHistory.push("ls");
    commandHistory.push("pwd");
    commandHistory.push("mkdir a");
    commandHistory.push("history 2");
    args.add("2");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      System.err.println(e.getMessage());
    }
    Output output = command.execute();

    String expected = "2. mkdir a\n" + "3. history 2\n";
    Assert.assertEquals(expected, output.stdout);
  }
  @Test
  public void getUsuageTest() {
    String actual = command.getUsage();
    String expected = "\nusage: HistoryCommand\n\t"
        + "Get user's input histories\n"+"\n";
    Assert.assertEquals(expected, actual);
  }

  @After
  public void restore() {
    System.setOut(stdout);
    System.setErr(stderr);
  }

}
