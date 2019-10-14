package test.cmd;

import cmd.Output;
import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.PwdCommand;

public class PwdCommandTest {

  private FileSystemManager fsm;
  private Command command;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new PwdCommand(fsm);
  }


  @Test
  public void rootPathTest() {
    try {
      command.setUp(new ArrayList<>());
      Output output = command.execute();
      Assert.assertEquals("/\n", output.stdout);
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }

  }

  @Test
  public void wrongArgumentInputTest() {
    List<String> args = new ArrayList<>();
    args.add("/test");
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError e) {
      String res = "pwd: no arguments accepted";
      Assert.assertEquals(res, e.getMessage());
    }
  }

  @Test
  public void notRootPathTest() throws DirectoryNotFoundError {
    String path = "/test/this/path/but/not/exist";
    fsm.changeCurrent(path);
    try {
      command.setUp(new ArrayList<>());
      command.execute();
    } catch (CommandError commandError) {
      Assert.assertEquals(path + "\n", commandError.getMessage());
    }

  }


}
