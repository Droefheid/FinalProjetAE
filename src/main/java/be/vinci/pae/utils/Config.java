package be.vinci.pae.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

  private static Properties props;

  /**
   * Reads a property list (key and element pairs) from the inputbyte file.
   * 
   * @param file : the input file.
   */
  public static void load(String file) {
    props = new Properties();
    try (InputStream input = new FileInputStream(file)) {
      props.load(input);
    } catch (IOException e) {
      throw new FatalException(e.getMessage(), e);
    }
  }

  /**
   * Searches for the property with the specified key in this property list.
   * The method returns null if the property is not found.
   * 
   * @param key the value in this property list with the specified key value.
   * @return the value in this property list with the specified key value.
   */
  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  public static Integer getIntProperty(String key) {
    return Integer.parseInt(props.getProperty(key));
  }

  public static boolean getBoolProperty(String key) {
    return Boolean.parseBoolean(props.getProperty(key));
  }

}
