package test.cmd;

import cmd.Output;
import org.junit.Assert;
import org.junit.Test;

public class OutputTest {

  @Test
  public void stdoutTest() {
    Output output = new Output("test stdout", "test stderr");
    Assert.assertEquals("test stdout", output.stdout);
  }

  @Test
  public void stderrTest() {
    Output output = new Output("test stdout", "test stderr");
    Assert.assertEquals("test stderr", output.stderr);
  }

  @Test
  public void toStringTest() {
    Output output = new Output("test stdout", "test stderr");
    String expected
        = "output with stdout:test stdout with stderr: test stderr";
    Assert.assertEquals(expected, output.toString());
  }

}
