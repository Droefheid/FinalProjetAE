package be.vinci.pae.api.filters;

import java.io.IOException;
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
