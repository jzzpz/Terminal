package driver;

import cmd.CdCommand;
import cmd.CpCommand;
import cmd.ExitCommand;
import cmd.FindCommand;
import cmd.GetURLCommand;
import cmd.JRemoteDataFetcher;
import cmd.JSystemFileController;
import cmd.LoadCommand;
import cmd.ManCommand;
import cmd.MkdirCommand;
import cmd.MvCommand;
import cmd.RedirectionAppend;
import cmd.RedirectionWrite;
import cmd.SaveCommand;
import cmd.TreeCommand;
import containers.CommandHistory;
import containers.DirectoryStack;
import exceptions.CommandError;
import fs.FileSystemManager;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import cmd.Command;
import cmd.EchoCommand;
import cmd.HistoryCommand;
import cmd.LsCommand;
import cmd.PopdCommand;
import cmd.PwdCommand;
import exceptions.NotValidCommandError;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cmd.CatCommand;
import cmd.PushdCommand;

/**
 * This class is used by JShell to parse the input string, so that we could
 * execute the exact command typed by user.
 *
 * The class will have a HashMap mapping all strings into commands.
 */
public class Parser {

  /**
   * This is the FileSystemManager for control FileSystemManager
   */
  private FileSystemManager fsm;
  /**
   * This is the stack for storing directories
   */
  private DirectoryStack stackd;
  /**
   * This is the container for storing commands
   */
  private CommandHistory cmdHistory;
  /**
   * This is the HashMap to get actual Command class by using class name
   */
  private Map<String, Command> commandMap;

  public Parser(FileSystemManager fsm, DirectoryStack stackd,
      CommandHistory cmdHistory) {
    this.fsm = fsm;
    this.stackd = stackd;
    this.cmdHistory = cmdHistory;
    this.commandMap = initialCommandMap();
  }

  /**
   * The parse method will take a string as input, and then return a command
   * calling getACommand, and then set up the ipnuts are the command so that we
   * execute the command.
   *
   * @param cmd is a string representing a command.
   * @return a command object so that we can execute it.
   * @throws NotValidCommandError when command input is not acceptable.
   */
  Command parse(String cmd) throws NotValidCommandError {
    /*
     *  command is a list of input s.t the first element is a string command
     *  while all other elements are parameters for command class
     */
    Matcher matcher = Pattern.compile("([^\"]\\S*|\".*?\") *").matcher(cmd);
    List<String> commandArray = new ArrayList<>();
    List<String> redirectionArray = new ArrayList<>();
    String part;
    boolean redirectionFlag = false;
    while (matcher.find()) {
      part = matcher.group(1).trim();
      if (redirectionFlag) {
        redirectionArray.add(part);
      } else if (">".equals(part) || ">>".equals(part)) {
        redirectionArray.add(part);
        redirectionFlag = true;
      } else {
        commandArray.add(part);
      }
    }
    if (commandArray.isEmpty()) {
      return null;
    }

    // get the actual command here
    Command command = getACommand(commandArray.get(0),
        commandArray.subList(1, commandArray.size()));

    // add the redirection parameter and wrap the command to
    // redirection class
    for (String param : redirectionArray) {
      if (">".equals(param)) {
        command = new RedirectionWrite(fsm, command);
      } else if (">>".equals(param)) {
        command = new RedirectionAppend(fsm, command);
      } else {
        List<String> args = new ArrayList<>();
        args.add(param);
        try {
          command.setUp(args);
        } catch (CommandError e) {
          System.err.println(e.getMessage());
        }
      }
    }
    return command;
  }

  /**
   * The method will give out a command object based on its inputs which are
   * command name and command parameters.
   *
   * @param aCommand is a string of a command.
   * @param args is the arguments or parameters for the command.
   * @return a command object to execute.
   * @throws NotValidCommandError when command is not acceptable.
   */
  private Command getACommand(String aCommand,
      List<String> args) throws NotValidCommandError {
    if (!commandMap.containsKey(aCommand)) {
      throw new NotValidCommandError(
          "The Command " + aCommand + " is not valid");
    }

    Command command = this.commandMap.get(aCommand);
    try {
      command.setUp(args);
    } catch (CommandError e) {
      System.err.println(e.getMessage());
    }

    return command;
  }

  /**
   * The method will initialize the HashMap, if more commands added in the
   * future, then, we need to update HashMap here.
   *
   * @return a HashMap that is already set up.
   */
  private Map<String, Command> initialCommandMap() {
    // set a hash map for all the commands
    Map<String, Command> map = new HashMap<>();

    map.put("cat", new CatCommand(fsm));
    map.put("ls", new LsCommand(fsm));
    map.put("pwd", new PwdCommand(fsm));
    map.put("pushd", new PushdCommand(stackd, fsm));
    map.put("popd", new PopdCommand(stackd, fsm));
    map.put("history", new HistoryCommand(cmdHistory));
    map.put("echo", new EchoCommand(fsm));
    map.put("mkdir", new MkdirCommand(fsm));
    map.put("cd", new CdCommand(fsm));
    map.put("man", new ManCommand(map));
    map.put("exit", new ExitCommand());
    map.put("tree", new TreeCommand(fsm));
    map.put("cp", new CpCommand(fsm));
    map.put("mv", new MvCommand(fsm));
    map.put("save", new SaveCommand(fsm, cmdHistory, stackd,
        new JSystemFileController()));
    map.put("load", new LoadCommand(fsm, cmdHistory, stackd,
        new JSystemFileController()));
    map.put("get", new GetURLCommand(fsm, new JRemoteDataFetcher()));
    map.put("find", new FindCommand(fsm));
    return map;
  }
}

