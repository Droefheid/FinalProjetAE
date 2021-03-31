package be.vinci.pae.domaine;

import java.util.List;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO userDTO, Address adress);

  UserDTO getUser(int id);

  List<UserDTO> getAll() ;
  
 
 // UserDTO updateBoss(UserDTO user, boolean is_boss);
	
}
