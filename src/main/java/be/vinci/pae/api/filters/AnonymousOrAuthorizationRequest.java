package be.vinci.pae.api.filters;

import java.io.IOException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.user.UserUCC;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
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
public class AnonymousOrAuthorizationRequest extends AuthorizeAbstract
    implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier =
      JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();

  @Inject
  private UserUCC userUCC;

  @Inject
  private DomaineFactory domaineFactory;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String token = requestContext.getHeaderString("Authorization");
    if (token != null) {
      requestContext.setProperty("user", decodeIfToken(token));
    } else {
      requestContext.setProperty("user", null);
    }
  }

}
