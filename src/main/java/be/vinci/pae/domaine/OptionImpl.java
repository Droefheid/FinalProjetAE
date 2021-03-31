package be.vinci.pae.domaine;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionImpl implements OptionDTO {

  private int id;
  private Timestamp optionTerm;
  private Timestamp beginningOptionDate;
  private int customer;
  private int furniture;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getOptionTerm() {
    return optionTerm;
  }

  public void setOptionTerm(Timestamp optionTerm) {
    this.optionTerm = optionTerm;
  }

  public Timestamp getBeginningOptionDate() {
    return beginningOptionDate;
  }

  public void setBeginningOptionDate(Timestamp beginningOptionDate) {
    this.beginningOptionDate = beginningOptionDate;
  }

  public int getCustomer() {
    return customer;
  }

  public void setCustomer(int customer) {
    this.customer = customer;
  }

  public int getFurniture() {
    return furniture;
  }

  public void setFurniture(int furniture) {
    this.furniture = furniture;
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
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    OptionImpl other = (OptionImpl) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }



}
