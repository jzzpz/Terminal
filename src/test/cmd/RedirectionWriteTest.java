package test.cmd;


import cmd.CatCommand;
import cmd.RedirectionWrite;
import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.LsCommand;
import cmd.Output;
import cmd.RedirectionAppend;
import cmd.RedirectionDecorator;
import fs.FileSystemManager;


public class RedirectionWriteTest {

  private FileSystemManager fsm;

  @Before
  public void setUp() {
    // change this back to mock
    fsm = new MockedFileSystemManager();
  }

  @Test
  public void notSetupTest() {
    RedirectionDecorator redirection = new RedirectionAppend(fsm,
        new LsCommand(fsm));
    Output actual = redirection.execute();
    String expected = "output with stdout: with stderr: "
        + "Please indicate which file should redirect to\n";
    Assert.assertEquals(expected, actual.toString());
  }

  static void runCatCommandSample(FileSystemManager fsm, String type)
      throws CommandError {
    Command command = new CatCommand(fsm);
    List<String> args = new ArrayList<>();
    args.add("testfile1");
    command.setUp(args);
    RedirectionDecorator redirection =
        type.equals("w") ? new RedirectionWrite(fsm, command)
            : new RedirectionAppend(fsm, command);
    args = new ArrayList<>();
    args.add("testfile");
    redirection.setUp(args);
    redirection.execute();
  }

  @Test
  public void executeTest()
      throws DirectoryNotFoundError, FileNotFoundError, CommandError {
    runCatCommandSample(fsm, "w");
    Assert.assertEquals("mocked-contents-one",
        fsm.getFile("testfile").getContents());
  }

}
