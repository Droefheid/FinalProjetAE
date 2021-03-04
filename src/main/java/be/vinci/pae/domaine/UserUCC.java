package be.vinci.pae.domaine;

public interface UserUCC {

  public UserDTO login(String username, String password);

  public UserDTO register(UserDTO userDTO);
}
