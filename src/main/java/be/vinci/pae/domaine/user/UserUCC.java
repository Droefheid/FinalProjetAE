package be.vinci.pae.domaine.user;

import java.util.List;
import be.vinci.pae.domaine.address.AddressDTO;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO userDTO, AddressDTO adress);

  UserDTO getUser(int id);

  List<UserDTO> getAll();

  void updateConfirmed(boolean confirmed, boolean antiqueDealer, int userId);



}

