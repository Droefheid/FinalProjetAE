package be.vinci.pae.services;

import java.sql.Timestamp;
import java.util.List;
import be.vinci.pae.domaine.visit.VisitDTO;

public interface VisitDAO {



  VisitDTO findById(int id);

  VisitDTO introduceVisit(VisitDTO visit);

  List<VisitDTO> getAll();

  List<VisitDTO> getAllMyVisits(int userId);

  void updateConfirmed(VisitDTO visit);

  List<VisitDTO> getAllNotConfirmed();

  VisitDTO findByDate(Timestamp date);

  List<VisitDTO> getAllConfirmed();

  void delete(int visitId);

}
