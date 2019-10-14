package test.cmd;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.Output;
import cmd.PushdCommand;
import containers.DirectoryStack;
import exceptions.CommandError;
import exceptions.EmptyContainerError;
import fs.FileSystemManager;


public class PushdCommandTest {
  private FileSystemManager fsm;
  private Command command;
  private DirectoryStack aStackDirectory;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    aStackDirectory = new DirectoryStack();
    command = new PushdCommand(aStackDirectory, fsm);
  }
  @Test
  public void getUsageTest() {
    String expected = "\nusage: PushdCommand\n\tPush a Directory "
        + "into the stack\n\n";
    String actual = command.getUsage();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void invalidSetUpTest() {
    List<String> args = new ArrayList<>();
    args.add("dir1");
    args.add("dir2");
    try {
      command.setUp(args);
    } catch (CommandError commandError) {
      String expected = "pushd: accept only one filename";
      Assert.assertEquals(expected, commandError.getMessage());
    }
  }

  @Test
  // this is incomplete
  public void aDirectoryExecuteTest() throws CommandError,
  EmptyContainerError {
    List<String> args = new ArrayList<>();
    // assume childOne is a directory in mock
    args.add("childOne");
    command.setUp(args);
    // change mock's changeCurrent method and make sure is added into the stack
    command.execute();
    boolean result = (fsm.getCurrentPath().equals("/childOne/") &&
        aStackDirectory.pop() == fsm.getRoot());
    Assert.assertTrue(result);
  }

  @Test
  public void invalidDirectoryExcuteTest() throws CommandError {
    List<String> args = new ArrayList<>();
    // assume childer123 directory does not exist in mock
    args.add("child123");
    command.setUp(args);
    Output actual = command.execute();
    String res = "Directory: child123 is not found\n";
    Assert.assertEquals(res, actual.stderr);
  }

}
