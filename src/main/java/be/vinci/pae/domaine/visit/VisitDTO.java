package be.vinci.pae.domaine.visit;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import be.vinci.pae.domaine.address.Address;
import be.vinci.pae.domaine.user.User;


@JsonDeserialize(as = VisitImpl.class)
public interface VisitDTO {

  int getId();

  void setId(int id);

  Timestamp getRequestDate();

  void setRequestDate(Timestamp requestDate);

  String getTimeSlot();

  void setTimeSLot(String timeSlot);

  Timestamp getDateAndHoursVisit();

  void setDateAndHoursVisit(Timestamp dateAndHoursVisit);

  String getExplanatoryNote();

  void setExplanatoryNote(String explanatoryNote);

  String getLabelFurniture();

  void setLabelFurniture(String labelFurniture);

  boolean getIsConfirmed();

  void setIsConfirmed(boolean isConfirmed);

  User getUsers();

  void setUsers(User users);

  Address getAddress();

  void setAdress(Address adresseId);


}
