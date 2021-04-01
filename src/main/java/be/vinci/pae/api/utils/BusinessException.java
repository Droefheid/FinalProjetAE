package be.vinci.pae.api.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class BusinessException extends WebApplicationException {

  private static final long serialVersionUID = 3224655338475587487L;

  public BusinessException() {
    super();
  }

  public BusinessException(String message, Status status) {
    super(message, status);
  }

  public BusinessException(String message) {
    super(message);
  }

}
