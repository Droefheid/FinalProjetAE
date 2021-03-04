package be.vinci.pae.domaine;

public class UserFactoryImpl implements UserFactory {

  @Override
  public User getUser() {
    return new UserImpl();
  }

}
