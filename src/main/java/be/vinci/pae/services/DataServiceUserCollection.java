package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.UserDTO;

public interface DataServiceUserCollection {

  UserDTO getUser(int id);

  UserDTO getUser(String pseudoOrEmail);

  List<UserDTO> getUsers();

  UserDTO addUser(UserDTO user);
}
