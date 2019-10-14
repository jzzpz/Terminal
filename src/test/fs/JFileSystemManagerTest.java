package test.fs;

import exceptions.DirectoryNotFoundError;
import exceptions.FileNotFoundError;
import fs.Directory;
import fs.File;
import fs.FileSystem;
import fs.FileSystemManager;
import fs.JFileSystemManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JFileSystemManagerTest {

  private FileSystem fs = FileSystem.getInstance();
  private Directory root = fs.getRoot();
  private FileSystemManager fsm;

  @Before
  public void setUp() {
    fsm = new JFileSystemManager();
  }

  @Test
  public void getCurrentPathTest() {
    Assert.assertEquals("/", fsm.getCurrentPath());
  }

  @Test
  public void getCurrentPathComplexPathTest() throws DirectoryNotFoundError {
    Directory pathOne = new Directory("pathOne", null);
    Directory pathTwo = new Directory("pathTwo", null);
    pathOne.addChild(pathTwo);
    root.addChild(pathOne);
    fsm.changeCurrent("pathOne/pathTwo");
    Assert.assertEquals("/pathOne/pathTwo/", fsm.getCurrentPath());
  }

  @Test
  public void createFileInCurrentPathTest() throws DirectoryNotFoundError {
    fsm.createFile("testFile");
    Assert.assertNotNull(root.findChild("testFile"));
  }

  @Test
  public void createDirectoryInCurrentPathTest() throws DirectoryNotFoundError {
    fsm.createDirectory("someDir");
    Assert.assertNotNull(root.findChild("someDir"));
  }

  @Test
  public void getCurrentTest() {
    Assert.assertEquals(root, fsm.getCurrent());
  }

  @Test
  public void getFileTest() throws DirectoryNotFoundError, FileNotFoundError {
    Directory a = new Directory("a", null);
    File file = new File("testingFile", a);
    root.addChild(file);
    Assert.assertEquals(file, fsm.getFile("testingFile"));
  }

  @Test
  public void fileCheckTest() {
    Directory a = new Directory("a", null);
    root.addChild(new File("testFile", a));
    Assert.assertTrue(fsm.fileCheck("testFile"));
  }

  @Test
  public void directoryCheckTest() {
    root.addChild(new Directory("testDirectory", null));
    Assert.assertTrue(fsm.directoryCheck("testDirectory"));
  }

}