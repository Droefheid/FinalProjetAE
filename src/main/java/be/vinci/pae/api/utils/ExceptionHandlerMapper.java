package be.vinci.pae.api.utils;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandlerMapper implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(Throwable exception) {
    LogToFile.log((Exception) exception);
    return Response.status(getStatusCode(exception)).entity(getEntity(exception)).build();
  }

  /*
   * Get appropriate HTTP status code for an exception.
   */
  private int getStatusCode(Throwable exception) {
    if (exception instanceof BusinessException) {
      return ((BusinessException) exception).getResponse().getStatus();
    }
    if (exception instanceof PresentationException) {
      return ((PresentationException) exception).getResponse().getStatus();
    }
    return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }

  private String getEntity(Throwable exception) {
    return exception.getMessage();
  }


}
