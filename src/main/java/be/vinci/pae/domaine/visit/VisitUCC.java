package be.vinci.pae.domaine.visit;

import java.util.List;
import be.vinci.pae.domaine.address.AddressDTO;

public interface VisitUCC {


  VisitDTO introduceVisit(VisitDTO visitDTO, AddressDTO addressDTO, int user);

  VisitDTO getVisit(int id);

  Object[] getAllInfosOfVisit(int id);

  List<VisitDTO> getAll();

  List<VisitDTO> getAllMyVisits(int userId);

  void updateConfirmed(VisitDTO visitDTO);

  List<VisitDTO> getAllNotConfirmed();

  List<VisitDTO> getAllConfirmed();

  void delete(int visitId);

}
