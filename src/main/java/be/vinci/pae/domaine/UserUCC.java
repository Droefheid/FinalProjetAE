package be.vinci.pae.domaine;

import java.util.List;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO userDTO, AddressDTO adress);

  UserDTO getUser(int id);

  List<UserDTO> getAll() ;
  
  void updateConfirmed(boolean confirmed , boolean antique_dealer, int user_id) ; 
  
 

	
}

