package be.vinci.pae.domaine.visit;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import be.vinci.pae.domaine.address.Address;
import be.vinci.pae.domaine.user.User;
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
  private User users;
  @JsonView(Views.Boss.class)
  private Address address;



  @Override
  public int getId() {
    // TODO Auto-generated method stub
    return id;
  }

  @Override
  public void setId(int id) {
    // TODO Auto-generated method stub
    this.id = id;

  }

  @Override
  public Timestamp getRequestDate() {
    // TODO Auto-generated method stub
    return requestDate;
  }

  @Override
  public void setRequestDate(Timestamp requestDate) {
    // TODO Auto-generated method stub
    this.requestDate = requestDate;

  }

  @Override
  public String getTimeSlot() {
    // TODO Auto-generated method stub
    return timeSlot;
  }

  @Override
  public void setTimeSLot(String timeSlot) {
    // TODO Auto-generated method stub
    this.timeSlot = timeSlot;

  }

  @Override
  public Timestamp getDateAndHoursVisit() {
    // TODO Auto-generated method stub
    return dateAndHoursVisit;
  }

  @Override
  public void setDateAndHoursVisit(Timestamp dateAndHoursVisit) {
    // TODO Auto-generated method stub
    this.dateAndHoursVisit = dateAndHoursVisit;
  }

  @Override
  public String getExplanatoryNote() {
    // TODO Auto-generated method stub
    return explanatoryNote;
  }

  @Override
  public void setExplanatoryNote(String explanatoryNote) {
    // TODO Auto-generated method stub
    this.explanatoryNote = explanatoryNote;

  }

  @Override
  public String getLabelFurniture() {
    // TODO Auto-generated method stub
    return labelFurniture;
  }

  @Override
  public void setLabelFurniture(String labelFurniture) {
    // TODO Auto-generated method stub
    this.labelFurniture = labelFurniture;

  }

  @Override
  public boolean getIsConfirmed() {
    // TODO Auto-generated method stub
    return isConfirmed;
  }

  @Override
  public void setIsConfirmed(boolean isConfirmed) {
    // TODO Auto-generated method stub
    this.isConfirmed = isConfirmed;

  }

  @Override
  public User getUsers() {
    // TODO Auto-generated method stub
    return users;
  }

  @Override
  public void setUsers(User users) {
    // TODO Auto-generated method stub
    this.users = users;
  }

  @Override
  public Address getAddress() {
    // TODO Auto-generated method stub
    return address;
  }

  @Override
  public void setAdress(Address address) {
    // TODO Auto-generated method stub
    this.address = address;

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



}
