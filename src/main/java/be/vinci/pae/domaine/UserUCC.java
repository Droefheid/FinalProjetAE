package be.vinci.pae.domaine;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO userDTO, AddressDTO adress);

  UserDTO getUser(int id);
}
