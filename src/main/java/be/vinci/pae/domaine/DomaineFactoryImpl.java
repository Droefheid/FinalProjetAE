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
  public AddressDTO getAdressDTO() {
    return new AddressImpl();
  }

  @Override
  public OptionDTO getOptionDTO() {
    return new OptionImpl();
  }

  @Override
  public TypeDTO getTypeDTO() {
    return new TypeImpl();
  }

}
