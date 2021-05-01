package be.vinci.pae.api.utils;

import java.sql.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.utils.Config;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ResponseMaker {
  // 86400s = 1 jour (86.400.000 ms).
  private static final long EXPIRATION_TIME = 86400 * 1000;
  private static final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private static final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * create a response with a token.
   * 
   * @param user the user to put in the token.
   * @return a response.ok build with a token and the user inside.
   */
  public static Response createResponseWithToken(UserDTO user) {
    ObjectNode node = createToken(user);
    return createResponseWith(node);
  }

  /**
   * create a response with a ObjectNode with 1 putPOJO.
   * 
   * @param <E> the type of the object.
   * @param namePOJO the name of the POJO put.
   * @param object object to put.
   * @return a response.ok build with the ObjectNode inside.
   */
  public static <E> Response createResponseWithObjectNodeWith1PutPOJO(String namePOJO, E object) {
    ObjectNode node = createObjectNodeWithObjectNodeWith1PutPOJO(namePOJO, object);
    return createResponseWith(node);
  }

  /**
   * create a response with a ObjectNode with 2 putPOJO.
   * 
   * @param <E> the type of the object.
   * @param <F> the type of the second object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @return a response.ok build with all the ObjectNode inside.
   */
  public static <E, F> Response createResponseWithObjectNodeWith2PutPOJO(String namePOJO1,
      E object1, String namePOJO2, F object2) {
    ObjectNode node =
        createObjectNodeWithObjectNodeWith2PutPOJO(namePOJO1, object1, namePOJO2, object2);
    return createResponseWith(node);
  }

  /**
   * create a response with a ObjectNode with 3 putPOJO.
   * 
   * @param <E> the type of the object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @return a response.ok build with all the ObjectNode inside.
   */
  public static <E, F, G> Response createResponseWithObjectNodeWith3PutPOJO(String namePOJO1,
      E object1, String namePOJO2, F object2, String namePOJO3, G object3) {
    ObjectNode node = createObjectNodeWithObjectNodeWith3PutPOJO(namePOJO1, object1, namePOJO2,
        object2, namePOJO3, object3);
    return createResponseWith(node);
  }

  /**
   * create a response with a ObjectNode with 4 putPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param <H> the type of the four object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @param namePOJO4 the name of the four POJO put.
   * @param object4 four object to put.
   * @return a response.ok build with all the ObjectNode inside.
   */
  public static <E, F, G, H> Response createResponseWithObjectNodeWith4PutPOJO(String namePOJO1,
      E object1, String namePOJO2, F object2, String namePOJO3, G object3, String namePOJO4,
      H object4) {
    ObjectNode node = createObjectNodeWithObjectNodeWith4PutPOJO(namePOJO1, object1, namePOJO2,
        object2, namePOJO3, object3, namePOJO4, object4);
    return createResponseWith(node);
  }

  /**
   * create a response with a ObjectNode with 5 putPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param <H> the type of the four object.
   * @param <I> the type of the five object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @param namePOJO4 the name of the four POJO put.
   * @param object4 four object to put.
   * @param namePOJO5 the name of the five POJO put.
   * @param object5 five object to put.
   * @return a response.ok build with all the ObjectNode inside.
   */
  public static <E, F, G, H, I> Response createResponseWithObjectNodeWith5PutPOJO(String namePOJO1,
      E object1, String namePOJO2, F object2, String namePOJO3, G object3, String namePOJO4,
      H object4, String namePOJO5, I object5) {
    ObjectNode node = createObjectNodeWithObjectNodeWith5PutPOJO(namePOJO1, object1, namePOJO2,
        object2, namePOJO3, object3, namePOJO4, object4, namePOJO5, object5);
    return createResponseWith(node);
  }

  /**
   * create a response with a ObjectNode with 6 putPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param <H> the type of the four object.
   * @param <I> the type of the five object.
   * @param <J> the type of the six object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @param namePOJO4 the name of the four POJO put.
   * @param object4 four object to put.
   * @param namePOJO5 the name of the five POJO put.
   * @param object5 five object to put.
   * @param namePOJO6 the name of the six POJO put.
   * @param object6 six object to put.
   * @return a response.ok build with all the ObjectNode inside.
   */
  public static <E, F, G, H, I, J> Response createResponseWithObjectNodeWith6PutPOJO(
      String namePOJO1, E object1, String namePOJO2, F object2, String namePOJO3, G object3,
      String namePOJO4, H object4, String namePOJO5, I object5, String namePOJO6, J object6) {
    ObjectNode node = createObjectNodeWithObjectNodeWith6PutPOJO(namePOJO1, object1, namePOJO2,
        object2, namePOJO3, object3, namePOJO4, object4, namePOJO5, object5, namePOJO6, object6);
    return createResponseWith(node);
  }



  // ******************** Private's Methods ********************

  /**
   * Create a token and a ObjectNode with an user.
   * 
   * @param user : the user to put in the token.
   * @return ObjectNode contains the token and the user filter.
   */
  private static ObjectNode createToken(UserDTO user) {
    // Create token
    String token;
    try {
      token = JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
          .withIssuer("auth0").withClaim("user", user.getID()).sign(jwtAlgorithm);
    } catch (Exception e) {
      throw new FatalException("Unable to create token", e);
    }

    // Build response
    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    UserDTO publicUser = Json.filterBossJsonView(user, UserDTO.class);
    return jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
  }

  /**
   * create a ObjectNode with 1 PutPOJO.
   * 
   * @param <E> the type of the object.
   * @param namePOJO the name of the POJO put.
   * @param object object to put.
   * @return a ObjectNode with 1 PutPOJO inside.
   */
  private static <E> ObjectNode createObjectNodeWithObjectNodeWith1PutPOJO(String namePOJO,
      E object) {
    return jsonMapper.createObjectNode().putPOJO(namePOJO, object);
  }

  /**
   * create a ObjectNode with 2 PutPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @return a ObjectNode with 2 PutPOJO inside.
   */
  private static <E, F> ObjectNode createObjectNodeWithObjectNodeWith2PutPOJO(String namePOJO1,
      E object1, String namePOJO2, F object2) {
    return createObjectNodeWithObjectNodeWith1PutPOJO(namePOJO1, object1).putPOJO(namePOJO2,
        object2);
  }

  /**
   * create a ObjectNode with 3 PutPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @return a ObjectNode with 3 PutPOJO inside.
   */
  private static <E, F, G> ObjectNode createObjectNodeWithObjectNodeWith3PutPOJO(String namePOJO1,
      E object1, String namePOJO2, F object2, String namePOJO3, G object3) {
    return createObjectNodeWithObjectNodeWith2PutPOJO(namePOJO1, object1, namePOJO2, object2)
        .putPOJO(namePOJO3, object3);
  }

  /**
   * create a ObjectNode with 4 PutPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param <H> the type of the four object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @param namePOJO4 the name of the four POJO put.
   * @param object4 four object to put.
   * @return a ObjectNode with 4 PutPOJO inside.
   */
  private static <E, F, G, H> ObjectNode createObjectNodeWithObjectNodeWith4PutPOJO(
      String namePOJO1, E object1, String namePOJO2, F object2, String namePOJO3, G object3,
      String namePOJO4, H object4) {
    return createObjectNodeWithObjectNodeWith3PutPOJO(namePOJO1, object1, namePOJO2, object2,
        namePOJO3, object3).putPOJO(namePOJO4, object4);
  }

  /**
   * create a ObjectNode with 5 PutPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param <H> the type of the four object.
   * @param <I> the type of the five object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @param namePOJO4 the name of the four POJO put.
   * @param object4 four object to put.
   * @param namePOJO5 the name of the five POJO put.
   * @param object5 five object to put.
   * @return a ObjectNode with 5 PutPOJO inside.
   */
  private static <E, F, G, H, I> ObjectNode createObjectNodeWithObjectNodeWith5PutPOJO(
      String namePOJO1, E object1, String namePOJO2, F object2, String namePOJO3, G object3,
      String namePOJO4, H object4, String namePOJO5, I object5) {
    return createObjectNodeWithObjectNodeWith4PutPOJO(namePOJO1, object1, namePOJO2, object2,
        namePOJO3, object3, namePOJO4, object4).putPOJO(namePOJO5, object5);
  }

  /**
   * create a ObjectNode with 6 PutPOJO.
   * 
   * @param <E> the type of the first object.
   * @param <F> the type of the second object.
   * @param <G> the type of the third object.
   * @param <H> the type of the four object.
   * @param <I> the type of the five object.
   * @param <J> the type of the six object.
   * @param namePOJO1 the name of the first POJO put.
   * @param object1 first object to put.
   * @param namePOJO2 the name of the second POJO put.
   * @param object2 second object to put.
   * @param namePOJO3 the name of the third POJO put.
   * @param object3 third object to put.
   * @param namePOJO4 the name of the four POJO put.
   * @param object4 four object to put.
   * @param namePOJO5 the name of the five POJO put.
   * @param object5 five object to put.
   * @param namePOJO6 the name of the six POJO put.
   * @param object6 six object to put.
   * @return a ObjectNode with 6 PutPOJO inside.
   */
  private static <E, F, G, H, I, J> ObjectNode createObjectNodeWithObjectNodeWith6PutPOJO(
      String namePOJO1, E object1, String namePOJO2, F object2, String namePOJO3, G object3,
      String namePOJO4, H object4, String namePOJO5, I object5, String namePOJO6, J object6) {
    return createObjectNodeWithObjectNodeWith5PutPOJO(namePOJO1, object1, namePOJO2, object2,
        namePOJO3, object3, namePOJO4, object4, namePOJO5, object5).putPOJO(namePOJO6, object6);
  }

  /**
   * create a response with a ObjectNode.
   * 
   * @param node the ObjectNode use for the Response.
   * @return a response.ok build with the ObjectNode inside.
   */
  private static Response createResponseWith(ObjectNode node) {
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }

}
