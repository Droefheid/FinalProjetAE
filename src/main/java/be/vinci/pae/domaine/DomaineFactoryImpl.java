package be.vinci.pae.domaine;

public class DomaineFactoryImpl implements DomaineFactory {

  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }

  @Override
  public Address getAdress() {
    return new AddressImpl();
  }

  @Override
  public FurnitureDTO getFurnitureDTO() {
    return new FurnitureImpl();
  }

}
