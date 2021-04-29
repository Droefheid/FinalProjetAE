package be.vinci.pae.domaine.visit;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = VisitImpl.class)
public interface VisitDTO {

  int getId();

  void setId(int id);

  Timestamp getRequestDate();

  void setRequestDate(Timestamp requestDate);

  String getTimeSlot();

  void setTimeSlot(String timeSlot);

  Timestamp getDateAndHoursVisit();

  void setDateAndHoursVisit(Timestamp dateAndHoursVisit);

  String getExplanatoryNote();

  void setExplanatoryNote(String explanatoryNote);

  String getLabelFurniture();

  void setLabelFurniture(String labelFurniture);

  boolean getIsConfirmed();

  void setIsConfirmed(boolean isConfirmed);

  int getUserId();

  void setUserId(int users);

  int getAddressId();

  void setAddressId(int adresseId);


}
