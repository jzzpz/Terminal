package cmd;

/**
 * This is the SystemFileController interface and
 *
 * it can read the session of the JShell and save it into a target
 * file
 *
 * it can also read the session information saved in a file and
 * reinitialize the previous JShell process
 */
public interface SystemFileController {

  /**
   * This is the method to read the session information and saved it
   *
   * into the file in the target path
   * @param path is the path of the target file
   * @param content is the session information to save into the file
   */
  void save(String path, String content);

  /**
   * This is the method to read the session information from the file
   *
   * and reinitialize the previous JShell process
   * @param path is the path of the target file
   * @return the session information of the previous JShell process
   */
  String read(String path);
}
