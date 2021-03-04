package be.vinci.pae.domaine;

import jakarta.ws.rs.core.Response;

public interface UserUCC {

  public Response login(String username, String password);

  public Response register(UserDTO userDTO);
}
