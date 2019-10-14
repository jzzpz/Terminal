package test.cmd;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import fs.FileSystemManager;
import cmd.Command;
import cmd.TreeCommand;
import exceptions.CommandError;
import cmd.Output;

public class TreeCommandTest {
  private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
  private final PrintStream stdout = System.out;
  private final PrintStream stderr = System.err;
  private Command command;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));
    FileSystemManager fsm = new MockedFileSystemManager();
    command = new TreeCommand(fsm);
  }

  @Test
  public void invalidParameterTest() {
    List<String> args = new ArrayList<>();
    args.add("abc");
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      System.err.println(e.getMessage());
    }
    String expected = "tree: this command takes no parameter\n";
    Assert.assertEquals(expected, errStream.toString());


  }

  @Test
  public void rootDirectoryTest() {
    List<String> args = new ArrayList<>();
    try {
      command.setUp(args);
    } catch (CommandError e) {
      // TODO Auto-generated catch block
      System.err.println(e.getMessage());
    }
    Output output = command.execute();
    Assert.assertEquals("\\", output.stdout);

  }

  @Test
  public void getUsuageTest() {
    String actual = command.getUsage();
    String expected = "\nusage: TreeCommand\nOutputs all the Directory"
        + "\nand Files in tree structure\n";
    Assert.assertEquals(expected, actual);
  }


  @After
  public void restore() {
    System.setOut(stdout);
    System.setErr(stderr);
  }

}
