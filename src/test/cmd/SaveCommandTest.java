package test.cmd;

import cmd.Command;
import cmd.Output;
import cmd.SaveCommand;
import containers.CommandHistory;
import containers.DirectoryStack;
import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SaveCommandTest {
  private Command command;
  private FileSystemManager fsm;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new SaveCommand(fsm, new CommandHistory(), new DirectoryStack(),
        new MockedSystemFileController());
  }

  @Test
  public void saveTest() {
    List<String> args = new ArrayList<>();
    args.add("testFileName");
    try {
      command.setUp(args);
      Output output = command.execute();
      Assert.assertEquals("", output.stdout);
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
      String expected = "save: only accept one parameter";
      Assert.assertEquals(expected, commandError.getMessage());
    }
  }

  @Test
  public void getUsage() {
    String expected =
        "\ngetUsage: get CMD\n" + "\tget file information from a certain URL\n"
            + "\tand download it as a file with same name\n"
            + "\tand save it into the current working directory\n\n";
    Assert.assertEquals(expected, command.getUsage());
  }
}
