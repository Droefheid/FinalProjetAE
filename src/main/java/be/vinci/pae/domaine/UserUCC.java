package be.vinci.pae.domaine;

public interface UserUCC {

  UserDTO login(String username, String password);

<<<<<<< HEAD
  UserDTO register(UserDTO userDTO, Adress adress);
=======
  UserDTO register(UserDTO userDTO);
  
  UserDTO getUser(int id);
>>>>>>> 91598ecab7c2d309d3a640ce7ba9e6f1d3cd4060
}
