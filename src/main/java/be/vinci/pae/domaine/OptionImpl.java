package be.vinci.pae.domaine;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionImpl {

  private int id;
  private Date optionTerm;
  private Date beginningOptionDate;
  private User customer;
  // private Furniture furniture;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getOptionTerm() {
    return optionTerm;
  }

  public void setOptionTerm(Date optionTerm) {
    this.optionTerm = optionTerm;
  }

  public Date getBeginningOptionDate() {
    return beginningOptionDate;
  }

  public void setBeginningOptionDate(Date beginningOptionDate) {
    this.beginningOptionDate = beginningOptionDate;
  }

  public User getCustomer() {
    return customer;
  }

  public void setCustomer(User customer) {
    this.customer = customer;
  }



}
