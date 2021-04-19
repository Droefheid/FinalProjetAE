package be.vinci.pae.domaine;

import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.address.AddressImpl;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureImpl;
import be.vinci.pae.domaine.option.OptionDTO;
import be.vinci.pae.domaine.option.OptionImpl;
import be.vinci.pae.domaine.type.TypeDTO;
import be.vinci.pae.domaine.type.TypeImpl;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.user.UserImpl;
import be.vinci.pae.domaine.visit.VisitDTO;
import be.vinci.pae.domaine.visit.VisitImpl;

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

  @Override
  public VisitDTO getVisitDTO() {
    return new VisitImpl();
  }

}
