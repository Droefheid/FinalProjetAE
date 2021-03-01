package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.User;

public class DataServiceUserCollectionImpl implements DataServiceUserCollection {
  // TODO Need the package be.vinci.pae.utils.Congig.java
  private static final String DB_FILE_PATH = Config.getProperty("DatabaseFilePath");
  private static final String COLLECTION_NAME = "users";

  private List<User> users;

  public DataServiceUserCollectionImpl() {
    // TODO with JSON config
    this.users = Json.loadDataFromFile(DB_FILE_PATH, COLLECTION_NAME, User.class);
    System.out.println("USER DATA SERVICE");
  }

  @Override
  public User getUser(int id) {
    return this.users.stream().filter(u -> u.getID() == id).findAny().orElse(null);
  }

  @Override
  public User getUser(String pseudoOrEmail) {
    return this.users.stream()
        .filter(u -> u.getPseudo().equals(pseudoOrEmail) || u.getEmail().equals(pseudoOrEmail))
        .findAny().orElse(null);
  }

  @Override
  public List<User> getUsers() {
    return users;
  }

  @Override
  public User addUser(User user) {
    user.setID(this.users.size() + 1);
    this.users.add(user);
    Json.saveDataToFile(this.users, DB_FILE_PATH, COLLECTION_NAME); // TODO with JSON config
    return user;
  }

}
