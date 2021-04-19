package be.vinci.pae.domaine.visit;

import java.util.List;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.user.UserDTO;

public interface VisitUCC {


  VisitDTO introduceVisit(VisitDTO visitDTO, AddressDTO addressDTO, UserDTO userDTO);

  VisitDTO getVisit(int id);

  List<VisitDTO> getAll();

  void updateConfirmed(VisitDTO visitDTO);

  List<VisitDTO> getAllNotConfirmed();

}
