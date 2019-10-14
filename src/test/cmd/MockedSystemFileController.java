package test.cmd;

import cmd.SystemFileController;

public class MockedSystemFileController implements SystemFileController {
  public void save(String path, String content) { }

  public String read(String path) {
    return "success";
  }
}
