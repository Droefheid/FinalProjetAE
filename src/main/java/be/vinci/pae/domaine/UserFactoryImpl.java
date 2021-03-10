package be.vinci.pae.domaine;

public class UserFactoryImpl implements UserFactory {

  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }

}
