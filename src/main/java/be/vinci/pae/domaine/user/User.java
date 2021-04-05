package be.vinci.pae.domaine.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface User extends UserDTO {

  boolean checkPassword(String password);

  String hashPassword(String password);

}
