package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.visit.VisitDTO;

public interface VisitDAO {



  VisitDTO findById(int id);


  VisitDTO introduceVisit(VisitDTO visit);

  /**
   * insert an address into the DB.
   * 
   * @param addressDTO to be inserted.
   * @return the id of the address or -1 if address already exists.
   */
  int registerAddress(AddressDTO addressDTO);

  /**
   * fetch an address with the corresponding information.
   * 
   * @param street name.
   * @param buildingNumber the number of the building.
   * @param commune the name of the commune.
   * @param country name of the country.
   * @return the address id or -1 if the address doesn't exists.
   */
  int getAddressByInfo(String street, String buildingNumber, String commune, String country);

  List<VisitDTO> getAll();

  void updateConfirmed(VisitDTO visit);

  List<VisitDTO> getAllConfirmed();
}
