package be.vinci.pae.domaine.visit;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import views.Views;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitImpl implements Visit {


  @JsonView(Views.Public.class)
  private int id;
  @JsonView(Views.Boss.class)
  private Timestamp requestDate;

  @JsonView(Views.Boss.class)
  private String timeSlot;
  @JsonView(Views.Boss.class)
  private Timestamp dateAndHoursVisit;
  @JsonView(Views.Boss.class)
  private String explanatoryNote;
  @JsonView(Views.Boss.class)
  private String labelFurniture;
  @JsonView(Views.Boss.class)
  private boolean isConfirmed;
  @JsonView(Views.Boss.class)
  private int userId;
  @JsonView(Views.Boss.class)
  private int addressId;



  @Override
  public int getId() {

    return id;
  }

  @Override
  public void setId(int id) {

    this.id = id;

  }

  @Override
  public Timestamp getRequestDate() {

    return requestDate;
  }

  @Override
  public void setRequestDate(Timestamp requestDate) {

    this.requestDate = requestDate;

  }

  @Override
  public String getTimeSlot() {

    return timeSlot;
  }

  @Override
  public void setTimeSlot(String timeSlot) {

    this.timeSlot = timeSlot;

  }

  @Override
  public Timestamp getDateAndHoursVisit() {

    return dateAndHoursVisit;
  }

  @Override
  public void setDateAndHoursVisit(Timestamp dateAndHoursVisit) {

    this.dateAndHoursVisit = dateAndHoursVisit;
  }

  @Override
  public String getExplanatoryNote() {

    return explanatoryNote;
  }

  @Override
  public void setExplanatoryNote(String explanatoryNote) {

    this.explanatoryNote = explanatoryNote;

  }

  @Override
  public String getLabelFurniture() {

    return labelFurniture;
  }

  @Override
  public void setLabelFurniture(String labelFurniture) {

    this.labelFurniture = labelFurniture;

  }

  @Override
  public boolean getIsConfirmed() {

    return isConfirmed;
  }

  @Override
  public void setIsConfirmed(boolean isConfirmed) {

    this.isConfirmed = isConfirmed;

  }


  @Override
  public int getUserId() {

    return userId;
  }

  @Override
  public void setUserId(int userId) {

    this.userId = userId;
  }



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VisitImpl other = (VisitImpl) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public int getAddressId() {
    return addressId;
  }

  @Override
  public void setAddressId(int adresseId) {
    this.addressId = adresseId;
  }

  @Override
  public String toString() {
    return "VisitImpl [id=" + id + ", requestDate=" + requestDate + ", timeSlot=" + timeSlot
        + ", dateAndHoursVisit=" + dateAndHoursVisit + ", explanatoryNote=" + explanatoryNote
        + ", labelFurniture=" + labelFurniture + ", isConfirmed=" + isConfirmed + ", userId="
        + userId + ", addressId=" + addressId + "]";
  }



}
