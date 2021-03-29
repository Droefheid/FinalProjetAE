package be.vinci.pae.api.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class BusinessException extends WebApplicationException {

  private static final long serialVersionUID = 4L;

  public BusinessException() {
    super(Response.status(Status.BAD_REQUEST).build());
  }

  public BusinessException(String message, Throwable cause) {
    super(cause, Response.status(Status.BAD_REQUEST)
        .entity(message).type("text/plain").build());
  }

  public BusinessException(String message) {
    super(Response.status(Status.BAD_REQUEST)
        .entity(message).type("text/plain").build());
  }

}
