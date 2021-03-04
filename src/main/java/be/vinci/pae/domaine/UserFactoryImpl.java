package be.vinci.pae.domaine;

public class UserFactoryImpl implements UserFactory {

  @Override
  public UserDTO getPublicUser() {
    return new UserImpl();
  }

}
