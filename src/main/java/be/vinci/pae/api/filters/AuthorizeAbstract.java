package be.vinci.pae.api.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public abstract class AuthorizeAbstract {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier =
      JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();

  @Inject
  private UserUCC userUCC;

  /**
   * get a user from the token.
   * 
   * @throws PresentationException if Expired or Malformed token.
   * @param requestContext contains in header the token as "Authorization".
   * @return a user or null if the token isn't there.
   */
  public UserDTO decodedToken(ContainerRequestContext requestContext) {
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
          .entity("A token is needed to access this resource").build());
      return null;
    } else {
      if (token.startsWith("\"") && token.endsWith("\"")) {
        token = token.substring(1, token.length() - 1);
      }
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
      } catch (TokenExpiredException e) {
        throw new PresentationException("Expired token", e, Status.UNAUTHORIZED);
      } catch (Exception e) {
        throw new PresentationException("Malformed token", e, Status.UNAUTHORIZED);
      }
      return this.userUCC.getUser(decodedToken.getClaim("user").asInt());
    }
  }

}
