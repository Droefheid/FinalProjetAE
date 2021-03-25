package be.vinci.pae.domaine;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = OptionImpl.class)
public interface Option {

  public int getId();

  public void setId(int id);

  public Date getOptionTerm();

  public void setOptionTerm(Date optionTerm);

  public Date getBeginningOptionDate();

  public void setBeginningOptionDate(Date beginningOptionDate);

  public User getCustomer();

  public void setCustomer(User customer);
}
