package be.vinci.pae.domaine.visit;

import java.util.List;
import be.vinci.pae.domaine.address.AddressDTO;

public interface VisitUCC {


  VisitDTO introduceVisit(VisitDTO visitDTO, AddressDTO addressDTO);

  VisitDTO getVisit(int id);

  List<VisitDTO> getAll();

  void updateConfirmed(VisitDTO visitDTO);

  List<VisitDTO> getAllNotConfirmed();

}
