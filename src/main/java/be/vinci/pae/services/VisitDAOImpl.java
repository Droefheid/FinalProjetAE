package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.visit.VisitDTO;

public class VisitDAOImpl implements VisitDAO {

  @Override
  public VisitDTO findById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public VisitDTO introduceVisit(VisitDTO visit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int registerAddress(AddressDTO addressDTO) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getAddressByInfo(String street, String buildingNumber, String commune,
      String country) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<VisitDTO> getAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateConfirmed(VisitDTO visit) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<VisitDTO> getAllConfirmed() {
    // TODO Auto-generated method stub
    return null;
  }

}
