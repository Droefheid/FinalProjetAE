package be.vinci.pae.api.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

public class PresentationException extends WebApplicationException {

  private static final long serialVersionUID = 8990223327289262130L;

  public PresentationException() {
    super();
  }

  public PresentationException(String message, Exception e, Status status) {
    super(message, e, status);
  }

  public PresentationException(String message, Status status) {
    super(message, status);
  }

  public PresentationException(String message) {
    super(message);
  }
}
