package be.vinci.pae.domaine.visit;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import views.Views;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitImpl implements VisitDTO {

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



  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public Timestamp getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Timestamp requestDate) {
    this.requestDate = requestDate;
  }

  public String getTimeSlot() {
    return timeSlot;
  }

  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  }

  public Timestamp getDateAndHoursVisit() {
    return dateAndHoursVisit;
  }

  public void setDateAndHoursVisit(Timestamp dateAndHoursVisit) {
    this.dateAndHoursVisit = dateAndHoursVisit;
  }

  public String getExplanatoryNote() {
    return explanatoryNote;
  }

  public void setExplanatoryNote(String explanatoryNote) {
    this.explanatoryNote = explanatoryNote;
  }

  public String getLabelFurniture() {
    return labelFurniture;
  }

  public void setLabelFurniture(String labelFurniture) {
    this.labelFurniture = labelFurniture;
  }

  public boolean getIsConfirmed() {
    return isConfirmed;
  }

  public void setIsConfirmed(boolean isConfirmed) {
    this.isConfirmed = isConfirmed;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * auto generated hashcode.
   */
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    VisitImpl other = (VisitImpl) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int adresseId) {
    this.addressId = adresseId;
  }

  /**
   * auto generated toString.
   */
  public String toString() {
    return "VisitImpl [id=" + id + ", requestDate=" + requestDate + ", timeSlot=" + timeSlot
        + ", dateAndHoursVisit=" + dateAndHoursVisit + ", explanatoryNote=" + explanatoryNote
        + ", labelFurniture=" + labelFurniture + ", isConfirmed=" + isConfirmed + ", userId="
        + userId + ", addressId=" + addressId + "]";
  }

}
