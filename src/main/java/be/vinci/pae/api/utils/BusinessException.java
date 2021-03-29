package be.vinci.pae.api.utils;

import org.glassfish.grizzly.http.util.HttpStatus;
import jakarta.ws.rs.WebApplicationException;

public class BusinessException extends WebApplicationException {

  private static final long serialVersionUID = 3224655338475587487L;
  private HttpStatus status;

  public BusinessException() {
    super();
  }

  public BusinessException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public BusinessException(String message) {
    super(message);
  }

  public int getStatus() {
    return this.status.getStatusCode();
  }
}
