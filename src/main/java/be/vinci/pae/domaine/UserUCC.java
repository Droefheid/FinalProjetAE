package be.vinci.pae.domaine;

import java.util.List;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO userDTO, AddressDTO adress);

  UserDTO getUser(int id);

  List<UserDTO> getAll();

  void updateConfirmed(UserDTO userDTO);

  List<UserDTO> getAllNotConfirmed();



}

