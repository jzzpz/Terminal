package test.fs;

import fs.Directory;
import fs.File;
import org.junit.Assert;
import org.junit.Test;

public class FileTest {

  @Test
  public void getNameTest() {
    Directory a = new Directory("a", null);
    File file = new File("someName", a);
    Assert.assertEquals(file.getName(), "someName");
  }

  @Test
  public void getContentsTest() {
    Directory a = new Directory("a", null);
    File file = new File("someName", a, "someContents");
    Assert.assertEquals(file.getContents(), "someContents");
  }

  @Test
  public void setContentsTest() {
    Directory a = new Directory("a", null);
    File file = new File("someName", a, "someContents");
    file.setContents("hello, world!");
    Assert.assertEquals(file.getContents(), "hello, world!");
  }

  @Test
  public void equalsTrueTest() {
    Directory a = new Directory("a", null);
    File firstFile = new File("myNameIsHere", a);
    File secondFile = new File("myNameIsHere", a);
    Assert.assertTrue(firstFile.equals(secondFile));
  }

  @Test
  public void equalsFalseTest() {
    Directory a = new Directory("a", null);
    File firstFile = new File("myNameIsHere", a);
    File secondFile = new File("myNameIsTHere", a);
    Assert.assertFalse(firstFile.equals(secondFile));
  }

  @Test
  public void addContentsTest() {
    Directory a = new Directory("a", null);
    File file = new File("someName", a,"someContents");
    file.addContents("hello, world!");
    Assert.assertEquals(file.getContents(), "someContentshello, world!");
  }
}