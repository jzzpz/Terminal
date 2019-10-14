package test.cmd;

import cmd.CdCommand;
import cmd.Command;
import exceptions.CommandError;
import exceptions.DirectoryNotFoundError;
import fs.Directory;
import fs.FileSystemManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CdCommandTest {

  private Command command;
  private FileSystemManager fsm;

  @Before
  public void setUp() {
    fsm = new MockedFileSystemManager();
    command = new CdCommand(fsm);
  }


  @Test
  public void zeroParaSetupTest() {
    List<String> args = new ArrayList<>();
    Directory root = fsm.getCurrent();
    try {
      fsm.changeCurrent("childOne");
      command.setUp(args);
      command.execute();
      Assert.assertEquals(fsm.getCurrent(), root);
    } catch (DirectoryNotFoundError directoryNotFoundError) {
      directoryNotFoundError.printStackTrace();
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }

  }

  @Test
  public void oneParaSetupTest() {
    List<String> args = new ArrayList<>();
    args.add("childOne");
    try {
      command.setUp(args);
      command.execute();
      Assert.assertEquals(
          ((MockedFileSystemManager) fsm).getChildOne(),
          fsm.getCurrent());
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }

  }

  @Test
  public void moreThanOneParaSetupTest() {
    List<String> args = new ArrayList<>();
    args.add("childOne");
    args.add("childTwo");

    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError commandError) {
      String res = "cd: too many arguments";
      Assert.assertEquals(res, commandError.getMessage());
    }
  }

  @Test
  public void getUsageTest() {
    String res ="\ngetUsage: cd CMD\n\tChange directory to DIR," +
        " which may be relative\nto the current directory or " +
        "may be a full path.\n" +
        "\n\tAs with Unix, .. means a parent directory and a .means\n" +
        "\tthe current directory. The directory must be /,\n" +
        "\tthe forward slash.\n" +
        "\tThe foot of the file system is a single slash: /.\n\n";
    Assert.assertEquals(res, command.getUsage());
  }

}