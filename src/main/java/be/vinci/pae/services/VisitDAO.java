package be.vinci.pae.services;

import java.sql.Timestamp;
import java.util.List;
import be.vinci.pae.domaine.visit.VisitDTO;

public interface VisitDAO {



  VisitDTO findById(int id);


  VisitDTO introduceVisit(VisitDTO visit);

  List<VisitDTO> getAll();

  void updateConfirmed(VisitDTO visit);

  List<VisitDTO> getAllConfirmed();


  VisitDTO findByDate(Timestamp date);
}
