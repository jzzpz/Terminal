package test.cmd;

import cmd.Command;
import cmd.ExitCommand;
import cmd.ManCommand;
import cmd.Output;
import exceptions.CommandError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ManCommandTest {

  private Map<String, Command> map;
  private Command command;

  @Before
  public void setUp() {
    map = new HashMap<>();
    command = new ManCommand(map);
  }


  @Test
  public void manExitTest() throws CommandError {
    List<String> args = new ArrayList<>();
    map.put("exit", new ExitCommand());
    args.add("exit");
    command.setUp(args);
    Output output = command.execute();
    String res = "\nexit:\n\tQuit the program\n\n";
    Assert.assertEquals(res, output.stdout);
  }

  @Test
  public void manManTest() throws CommandError {
    List<String> args = new ArrayList<>();
    map.put("man", new ManCommand(null));
    args.add("man");
    command.setUp(args);
    Output output = command.execute();
    String res = "\nman CMD\n\tPrint Documentation for CMD\n";
    Assert.assertEquals(res, output.stdout);
  }

  @Test
  public void noParameterInputTest() {
    try {
      command.setUp(new ArrayList<>());
      command.execute();
    } catch (CommandError e) {
      String res = "What manual page do you want?";
      Assert.assertEquals(res, e.getMessage());
    }

  }

  @Test
  public void tooManyParameterInputTest() {
    List<String> args = new ArrayList<>();
    args.add("first");
    args.add("second");
    args.add("third");
    try {
      command.setUp(args);
      command.execute();
    } catch (CommandError e){
      String res = "man: too many arguments";
      Assert.assertEquals(res, e.getMessage());
    }


  }


}
