package be.vinci.pae.api.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogToFile {

  protected static final Logger logger = Logger.getLogger("MyLOG");

  /**
   * log Method enable to log all exceptions to a file and display user message on demand.
   * 
   * @param exception to log
   */
  public static void log(Exception exception) {

    FileHandler fh = null;
    try {
      fh = new FileHandler("logger.log");
      logger.addHandler(fh);
      fh.setFormatter(new LogFormatter());
      logger.setLevel(Level.ALL);
      logger.log(Level.ALL, exception + "\n");
    } catch (IOException | SecurityException ex1) {
      logger.log(Level.ALL, null, ex1);
    } finally {
      if (fh != null) {
        fh.close();
      }
    }
  }

}
