package test.fs;

import fs.FileSystem;
import org.junit.Assert;
import org.junit.Test;

public class FileSystemTest {

  @Test
  public void SingletonTest() {
    FileSystem fsOne = FileSystem.getInstance();
    FileSystem fsTwo = FileSystem.getInstance();
    Assert.assertEquals(fsOne, fsTwo);
  }

}
