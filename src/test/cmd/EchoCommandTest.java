package test.cmd;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.EchoCommand;
import cmd.Output;
import exceptions.CommandError;
import fs.FileSystemManager;

public class EchoCommandTest {
  private FileSystemManager fsm;
  private Command command;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new EchoCommand(fsm);
  }

  @Test
  public void getUsageTest() {
    String actual = command.getUsage();
    String expected = "\nusage: EchoCommand\n\tCreate a file if file doesn't "
        + "exists" + "\nIf file exists and using '>', then replace"
        + "\t the file's content with new content"
        + "\nIf file exists and using '>>',"
        + "\t then add new content to the end of the file's content\n\n";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void inputIsNotDoubleQuote() {
    List<String> args = new ArrayList<>();
    args.add("'WriteThis'");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      String expected =
          "echo: input string should be surrounded by " + "double quote";
      Assert.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  public void wrongNumberOfParamaters() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      String expected = "echo: invalid number of parameters";
      Assert.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  public void validExcueteTest() throws CommandError {
    List<String> args = new ArrayList<>();
    args.add("\"READ THIS LINE\"");
    // error won't occur
    command.setUp(args);
    Output actual = command.execute();
    Assert.assertEquals("output with stdout:READ THIS LINE\n with stderr: ",
        actual.toString());
  }
}
