package test.cmd;

import cmd.Command;
import cmd.GetURLCommand;
import cmd.RemoteDataFetcher;
import exceptions.CommandError;
import fs.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetCommandTest {

  private Command command;
  private RemoteDataFetcher rdf;
  private MockedFileSystemManager fsm;

  @Before
  public void setUp() {
    rdf = new MockedRemoteDataFetcher();
    fsm = new MockedFileSystemManager();
    command = new GetURLCommand(fsm, rdf);
  }

  @Test
  public void getContentTest() {
    List<String> args = new ArrayList<>();
    args.add("testURL/test.txt");
    try {
      command.setUp(args);
      command.execute();
      File resultFile = fsm.getCurrent().getFile("test.txt");
      String expected = "test\n";
      Assert.assertEquals(expected, resultFile.getContents());
    } catch (CommandError commandError) {
      commandError.printStackTrace();
    }
  }

  @Test
  public void invalidParamTest() {
    List<String> args = new ArrayList<>();
    args.add("test1");
    args.add("test2");

    try {
      command.setUp(args);
    } catch (CommandError commandError) {
      String result = "get: accept at most one parameter";
      Assert.assertEquals(result, commandError.getMessage());
    }
  }

  @Test
  public void getUsageTest() {
    String actual = command.getUsage();
    String expect = "\nusage: GetURLCommand\n\tRetrieve the file at " +
        "that URL and add it to \n\tthe current working directory\n";
    Assert.assertEquals(expect, actual);
  }

}
