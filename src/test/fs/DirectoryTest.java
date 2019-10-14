package test.fs;

import exceptions.NodeNotFoundException;
import fs.Directory;
import fs.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectoryTest {

  private Directory root;

  @Before
  public void setRoot() {
    root = new Directory("root", null);
    root.setParent(root);
  }

  /**
   * Test if constructor allows null as parent for root;
   */
  @Test
  public void constructorTest() {
    Assert.assertNotNull(root);
  }

  /**
   * Test if put parent with constructor, if the relationship is correct.
   */
  @Test
  public void constructorWithParentTest() {
    Directory subDir = new Directory("subDir", root);
    Assert.assertEquals(subDir, root.getDirectory("subDir"));
    Assert.assertEquals(subDir.getParent(), root);
  }

  @Test
  public void AddDirectoryTest() {
    Directory subDir = new Directory("subDir", null);
    root.addChild(subDir);
    Assert.assertEquals(subDir, root.getDirectory("subDir"));
    Assert.assertEquals(subDir.getParent(), root);
  }

  @Test
  public void AddFileTest() {
    File file = new File("test_file", root);
    root.addChild(file);
    Assert.assertEquals(file, root.getFile("test_file"));
  }

  @Test
  public void GetDirectoryTest() {
    Directory subDir = new Directory("subDir", root);
    Assert.assertEquals(root.getDirectory("subDir"), subDir);
  }

  @Test
  public void GetWFileTest() {
    File file = new File("someTextFile", root);
    root.addChild(file);
    Assert.assertEquals(root.getFile("someTextFile"), file);
  }

  @Test
  public void GetDirectoryWrongInputTest() {
    Assert.assertNull(root.getDirectory("someNotExistDirectory"));
  }

  @Test
  public void GetWFileWrongInputTest() {
    Assert.assertNull(root.getFile("someNotExistFile"));
  }

  @Test
  public void DeleteDirectoryTest() throws NodeNotFoundException {
    Directory subDir = new Directory("subDir", null);
    root.addChild(subDir);
    root.deleteChild("subDir");
    Assert.assertNull(root.getDirectory("subDir"));
  }

  @Test
  public void DeleteFileTest() throws NodeNotFoundException {
    File file = new File("test_file", root);
    root.addChild(file);
    root.deleteChild("test_file");
    Assert.assertNull(root.getFile("test_file"));
  }

  @Test
  public void AbsolutePathTest() {
    Directory etc = new Directory("etc", root);
    Directory security = new Directory("security", etc);
    Assert.assertEquals(security.getAbsolutePath(), "/etc/security/");
  }

  @Test
  public void GetNameTest() {
    Assert.assertEquals(root.getName(),
        "root");
  }

}
