package be.vinci.pae.domaine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface User extends UserDTO {
  // TODO Retient les methodes chiffrerMDP(), checkCanBeAdmin, changetoadmin

  boolean checkPassword(String password);

  String hashPassword(String password);

  String toString();

}
