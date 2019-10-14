package test.cmd;

import cmd.RemoteDataFetcher;

public class MockedRemoteDataFetcher implements RemoteDataFetcher {
  @Override
  public String getRemoteDataFromURL(String URL) {
    return "test\n";
  }
}
