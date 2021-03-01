package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.User;

public interface DataServiceUserCollection {

  User getUser(int id);

  User getUser(String pseudoOrEmail);

  List<User> getUsers();

  User addUser(User user);
}
