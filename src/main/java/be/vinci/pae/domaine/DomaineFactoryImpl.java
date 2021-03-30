package be.vinci.pae.domaine;

public class DomaineFactoryImpl implements DomaineFactory {


  @Override
  public FurnitureDTO getFurnitureDTO() {
    return new FurnitureImpl();
  }

  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }

  @Override
  public AddressDTO getAdress() {
    return new AddressImpl();
  }

}
