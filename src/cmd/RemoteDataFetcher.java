package cmd;

/**
 * This is the RemoteDataFetcher class interface
 *
 * it has a method to get file content from an certain URL
 */
public interface RemoteDataFetcher {

  /**
   * This method get file content from the given URL
   * @param URL is the source URL to get the file content
   * @return the content of the file saved on the URL
   */
  String getRemoteDataFromURL(String URL);
}
