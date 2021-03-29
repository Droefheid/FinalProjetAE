package be.vinci.pae.api.utils;

import jakarta.ws.rs.WebApplicationException;

public class FatalException extends WebApplicationException {

  private static final long serialVersionUID = -5926196101906096391L;

  public FatalException() {
    super();
  }

  public FatalException(String message, Throwable cause) {
    super(message, cause);
  }

  public FatalException(String message) {
    super(message);
  }

}
