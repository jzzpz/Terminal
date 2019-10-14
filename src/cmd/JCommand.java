package cmd;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This is the JCommand abstract class, it implements from Command
 *
 * and have two methods to change print stream
 *
 * setStdoutStream set a new stdout stream
 *
 * restoreStdoutStream restore the stdout stream after some operations
 *
 * setStderrStream set a new stderr stream
 *
 * restoreStderrStream restore the stderr stream after some operations
 *
 * setPrintStream set stderr and stdout streams
 *
 * restorePrintStream restore two streams after some operations
 */
abstract class JCommand implements Command {

  /**
   * This is the standard output print stream.
   */
  private final PrintStream stdout = System.out;
  /**
   * This is the standard error print stream.
   */
  private final PrintStream stderr = System.err;

  /**
   * Redirect the out.print into this print stream.
   */
  ByteArrayOutputStream outStream;
  /**
   * Redirect the err.print into this print stream.
   */
  ByteArrayOutputStream errStream;

  /**
   * Set up the stdout print stream so that out.print goes to the stream we
   * defined.
   */
  void setStdoutStream() {
    outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));
  }

  /**
   * Set up the stderr print stream s othat err.print goes to the stream we
   * defined.
   */
  private void setStderrStream() {
    errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
  }

  /**
   * Reset the stdout print stream.
   */
  void restoreStdoutStream() {
    System.setOut(stdout);
    outStream = null;
  }

  /**
   * Reset the stderr print stream.
   */
  private void restoreStderrStream() {
    System.setErr(stderr);
    errStream = null;
  }

  /**
   * Set up both stdout and stderr stream.
   */
  void setPrintStream() {
    setStdoutStream();
    setStderrStream();
  }

  /**
   * Reset both stdout and stderr stream.
   */
  void restorePrintStream() {
    restoreStdoutStream();
    restoreStderrStream();
  }

}
