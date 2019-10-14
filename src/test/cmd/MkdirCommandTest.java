package test.cmd;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.MkdirCommand;
import cmd.Output;
import exceptions.CommandError;
import fs.FileSystemManager;


public class MkdirCommandTest {
  private FileSystemManager fsm;
  private Command command;
  
  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new MkdirCommand(fsm);
  }

  @Test
  public void getUsageTest() {
    String actual = command.getUsage();
    String expected = "\nusage: MkdirCommand\n\tCall FileSystemManager "
        + "to create a new Directory in place get from User\n\n";
    Assert.assertEquals(expected, actual);
    
  }

  @Test
  public void invalidParameterTest() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError e) {
      String expected = "please enter the directory name you want to create";
      Assert.assertEquals(expected, e.getMessage());
    }

  }

  @Test
  public void validParameterTest() throws CommandError {
    List<String> args = new ArrayList<>();
    args.add("a1");
    command.setUp(args);
    Output actual = command.execute();
    String expected = "";
    Assert.assertEquals(expected, actual.stdout);
    
  }
  

}
