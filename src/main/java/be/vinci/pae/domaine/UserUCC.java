package be.vinci.pae.domaine;

public interface UserUCC {
  // Retient register(UserDTO);
  // login(login, password);
  public UserDTO login(String username, String password);

  public UserDTO register(UserDTO userDTO);
}
