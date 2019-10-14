// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: huangq41
// UT Student #: 1002915107
// Author: Joey Huang
//
// Student2:
// UTORID user_name: sunkaihu
// UT Student #: 1002910200
// Author: William Sun
//
// Student3:
// UTORID user_name: zhuangpi
// UT Student #: 1003652200
// Author: Jacky Zhuang
//
// Student4:
// UTORID user_name: chenc257
// UT Student #: 1003831017
// Author: Tony Chen
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import cmd.Command;
import cmd.Output;
import containers.CommandHistory;
import exceptions.NotValidCommandError;
import fs.FileSystemManager;

import fs.JFileSystemManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import containers.DirectoryStack;

/**
 * This class is the main class of the project which represents a JShell.
 *
 * This class will assemble all features implemented by other classes together
 * and make the whole project run.
 *
 * Therefore, it contains a main function.
 */
public class JShell {

  /**
   * This is used to parse the user input.
   */
  private Parser parser;
  /**
   * This is used to print out the standard output to user.
   */
  private BufferedReader stdin;
  /**
   * This is used to manage files and directories in the file system.
   */
  private FileSystemManager fsm;
  /**
   * This is the stack used to store directories.
   */
  private DirectoryStack stackd;
  /**
   * This is the queue used to store history.
   */
  private CommandHistory cmdHistory;

  /**
   * This constructor will initialize standard input, file system manager,
   *
   * directory stack, command history queue, and parser.
   */
  public JShell() {
    stdin = new BufferedReader(new InputStreamReader(System.in));
    fsm = new JFileSystemManager();
    stackd = new DirectoryStack();
    cmdHistory = new CommandHistory();
    parser = new Parser(fsm, stackd, cmdHistory);
  }

  /**
   * The method will start to run the JShell by keep reading input from user.
   *
   * @throws IOException when input stream has something wrong.
   */
  private void run() throws IOException {
    String cmd;
    Command command;
    while (true) {
      System.out.print(fsm.getCurrentPath());
      System.out.print(" $ ");
      cmd = readLine();
      cmdHistory.push(cmd);
      try {
        command = parser.parse(cmd);
        if (command == null) {
          continue;
        }
        Output output = command.execute();
        if (output == null) {
          continue;
        }
        if (!output.stderr.isEmpty()) {
          System.err.print(output.stderr);
        }
        if (!output.stdout.isEmpty()) {
          System.out.print(output.stdout);
        }
      } catch (NotValidCommandError e) {
        System.err.println(e.getMessage());
      }
    }
  }

  /**
   * The method will read one line from standard input which is command typed by
   * user.
   *
   * @return a string from stdin.
   * @throws IOException when input stream has something wrong.
   */
  private String readLine() throws IOException {
    String input;
    input = stdin.readLine();
    return input;
  }

  public static void main(String[] args) throws IOException {
    // staring the JShell here
    JShell jShell = new JShell();
    jShell.run();
  }


}