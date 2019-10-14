package cmd;

/**
 * This class contains data about each executed command.
 *
 * In the JShell, if the output is null, it means that the command is not setup
 * properly.
 *
 * If stdout or stderr is empty string, it means that there is no stdout or
 * stderr during this command.
 */
public class Output {

  /**
   * This contains all the data from stdout.
   */
  public String stdout;
  /**
   * This contains all the data from stderr.
   */
  public String stderr;

  /**
   * The constructor will setup the standard output and error as a output for
   * any execute method.
   *
   * @param stdout is a string of the output result.
   * @param stderr is a string of the error result.
   */
  public Output(String stdout, String stderr) {
    this.stdout = stdout;
    this.stderr = stderr;
  }

  /**
   * This defines the toString of our dto object.
   *
   * @return a string of toString result.
   */
  @Override
  public String toString() {
    return "output with stdout:" + stdout + " with stderr: " + stderr;
  }
}
