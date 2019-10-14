package test.cmd;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.Output;
import cmd.PopdCommand;
import containers.DirectoryStack;
import exceptions.CommandError;

public class PopdCommandTest {
  private MockedFileSystemManager fsm;
  private DirectoryStack aDirectoryStack;
  private Command command;
  
  @Before
  public void setUp() {
    aDirectoryStack = new DirectoryStack();
    fsm = new MockedFileSystemManager();
    command = new PopdCommand(aDirectoryStack, fsm);
  }
  
  @Test
  public void getUsageTest() {
    String actual = command.getUsage();
    String expected = "\nusage: PopdCommand\n\t"
        + "Pop the last directory that pushed into the directory stack\n\n";
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void invalidSetUp() {
    
    List<String> args = new ArrayList<>();
    args.add("/test");
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError e) {
      String res = "popd: can't recognize [/test]";
      Assert.assertEquals(res, e.getMessage());
    }
    
  }
  
  
  @Test
  public void emptyContainerExcute() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Output actual = command.execute();
    String expected = "output with stdout: with stderr: Directory Stack "
        + "is empty\n";
    Assert.assertEquals(expected, actual.toString());
  }
  
  
}
  
 
