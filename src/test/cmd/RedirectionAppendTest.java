package test.cmd;



import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmd.Command;
import cmd.LsCommand;
import cmd.Output;
import cmd.RedirectionAppend;
import cmd.RedirectionDecorator;
import fs.FileSystemManager;


public class RedirectionAppendTest {

  private FileSystemManager fsm;

  @Before
  public void setUp() {
    // change this back to mock
    fsm = new MockedFileSystemManager();
  }

  @Test
  public void notSetupTest() {
    Command command = new LsCommand(fsm);
    RedirectionDecorator redirection = new RedirectionAppend(fsm, command);
    Output actual = redirection.execute();
    String expected = "output with stdout: with stderr: "
        + "Please indicate which file should redirect to\n";
    Assert.assertEquals(expected, actual.toString());
  }

  @Test
  public void executeTest()
      throws CommandError, DirectoryNotFoundError, FileNotFoundError {
    RedirectionWriteTest.runCatCommandSample(fsm, "a");
    RedirectionWriteTest.runCatCommandSample(fsm, "a");
    String expected = "mocked-contents-onemocked-contents-one";
    Assert.assertEquals(expected, fsm.getFile("testfile").getContents());
  }

}
