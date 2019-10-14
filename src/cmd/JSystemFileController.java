package cmd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is the JSystemFileController class used for controlling saving file
 * and reading file content
 */
public class JSystemFileController implements SystemFileController {

  /**
   * This method has the single responsibility to write everything into
   *
   * a real file in the real file system.
   *
   * @param filePath is a path indicated by user.
   * @param content is the contents to write.
   */
  public void save(String filePath, String content) {
    try {
      File tmpFile = new File(filePath);
      // if file exist, then delete
      if (!tmpFile.exists()) {
        tmpFile.delete();
      }
      // create a new file
      try {
        tmpFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      // write contents into the file
      FileOutputStream fos = new FileOutputStream(filePath,
          false);
      PrintWriter pw = new PrintWriter(fos);
      pw.println(content);
      pw.close();
    } catch (FileNotFoundException fnfe) {
      System.err.println("Not Found");
    }
  }

  /**
   * This method has the responsibility to read the content of
   *
   * a file in the real FileSystem
   *
   * @param filePath is the path of the file to read
   * @return the content of the file
   */
  public String read(String filePath) {
    try {
      Scanner file = new Scanner(new File(filePath));
      String result = "";

      // read through each line and then add the contents to the list.
      while (file.hasNextLine()) {
        String line = file.nextLine();
        line = line + "\n";
        result = result + line;
      }
      file.close();
      return result;
    } catch (FileNotFoundException e) {
      System.err.println("File not found");
      return "";
    }
  }
}
