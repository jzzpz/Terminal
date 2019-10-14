package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the JRemotreDataFetcher class that used for getting data
 *
 * from an URL
 */
public class JRemoteDataFetcher implements RemoteDataFetcher {

  /**
   * This is th constructor of the JRemoteDataFetcher
   */
  public JRemoteDataFetcher() {
  }

  /**
   * This method get content of the file from the URL and return
   *
   * the content
   * @param URL is the source URL to get the the file content
   * @return the content of the file
   */
  @Override
  public String getRemoteDataFromURL(String URL) {
    java.net.URL oracle;
    String inputLine;
    String result = "";

    try {
      // try to download the contents
      oracle = new URL(URL);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(oracle.openStream()));
      // add the contents to the file
      while ((inputLine = in.readLine()) != null) {
        result = result + inputLine;
      }
      in.close();
      return result;
    } catch (MalformedURLException e) {
      System.err.println(e.getMessage());
      return "";
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return "";
    }
  }
}
