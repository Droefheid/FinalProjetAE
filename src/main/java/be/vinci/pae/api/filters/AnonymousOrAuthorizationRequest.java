package be.vinci.pae.api.filters;

import java.io.IOException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;


/**
 * This filter allows anonymous requests.
 * 
 * @author e-Baron.
 *
 */

@Singleton
@Provider
@AnonymousOrAuthorize
public class AnonymousOrAuthorizationRequest implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier =
      JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();

  @Inject
  private UserUCC userUCC;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String token = requestContext.getHeaderString("Authorization");
    if (token != null) {
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
      } catch (Exception e) {
        throw new PresentationException("Malformed token.", e, Status.UNAUTHORIZED);
      }
      requestContext.setProperty("user", userUCC.getUser(decodedToken.getClaim("user").asInt()));
    }
  }

}
