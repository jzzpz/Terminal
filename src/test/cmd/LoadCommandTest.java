package test.cmd;

import cmd.Command;
import cmd.LoadCommand;
import containers.CommandHistory;
import containers.DirectoryStack;
import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoadCommandTest {
  private Command command;
  private FileSystemManager fsm;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new LoadCommand(fsm, new CommandHistory(),
        new DirectoryStack(),
        new MockedSystemFileController());
  }

  @Test
  public void loadTest() {
    List<String> args = new ArrayList<>();
    args.add("testFileName");
    try {
      command.setUp(args);
      Assert.assertEquals("", command.execute().stdout);
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }
  }

  @Test
  public void invalidNumberParamTest() {
    List<String> args = new ArrayList<>();
    args.add("arg1");
    args.add("arg2");
    try {
      command.setUp(args);
    } catch (CommandError commandError) {
      String expected = "load: only accept one parameter";
      Assert.assertEquals(expected, commandError.getMessage());
    }
  }

  @Test
  public void setUpTest() {
    String expected = "\nusage: LoadCommand\n" +
        "\tload session information from a file\n" +
        "\tand restart the previous JShell process\n\n";
    Assert.assertEquals(expected, command.getUsage());
  }
}
