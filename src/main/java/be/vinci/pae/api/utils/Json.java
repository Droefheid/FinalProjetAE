package be.vinci.pae.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import views.Views;

public class Json {
  private static final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Transform a java object into Json object with public view.
   * 
   * @param <T> : generic type of object.
   * @param item : Java object to transformed into JSON.
   * @param targetClass type of class.
   * @return item becomes Json object.
   */
  public static <T> T filterPublicJsonView(T item, Class<T> targetClass) {

    try {
      // serialize using JSON Views : public view (all fields not required in the
      // views are set to null)
      String publicItemAsString =
          jsonMapper.writerWithView(Views.Public.class).writeValueAsString(item);
      // deserialize using JSON Views : Public View
      return jsonMapper.readerWithView(Views.Public.class).forType(targetClass)
          .readValue(publicItemAsString);

    } catch (JsonProcessingException e) {

      e.printStackTrace();
      return null;
    }

  }


  /**
   * Transform a java object into Json object with Boss view.
   * 
   * @param <T> : generic type of object.
   * @param item : Java object to transformed into JSON.
   * @param targetClass type of class.
   * @return item becomes Json object.
   */
  public static <T> T filterBossJsonView(T item, Class<T> targetClass) {

    try {
      // serialize using JSON Views : public view (all fields not required in the
      // views are set to null)
      String publicItemAsString =
          jsonMapper.writerWithView(Views.Boss.class).writeValueAsString(item);
      // deserialize using JSON Views : Public View
      return jsonMapper.readerWithView(Views.Boss.class).forType(targetClass)
          .readValue(publicItemAsString);

    } catch (JsonProcessingException e) {

      e.printStackTrace();
      return null;
    }

  }

}
