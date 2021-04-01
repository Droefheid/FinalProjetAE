package be.vinci.pae.api.filters;

import java.io.IOException;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.domaine.UserDTO;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;


@Singleton
@Provider
@AuthorizeBoss
public class AuthorizationBossRequestFilter extends AuthorizeAbstract
    implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {

    UserDTO user = decodedToken(requestContext);
    if (user != null) {
      if (user.isBoss()) {
        requestContext.setProperty("user", user);
      } else {
        throw new PresentationException("You are not a boss.", Status.UNAUTHORIZED);
      }
    }
  }
}
