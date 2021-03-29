package be.vinci.pae.domaine;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = OptionImpl.class)
public interface Option {

  public int getId();

  public void setId(int id);

  public Timestamp getOptionTerm();

  public void setOptionTerm(Timestamp optionTerm);

  public Timestamp getBeginningOptionDate();

  public void setBeginningOptionDate(Timestamp beginningOptionDate);

  public int getCustomer();

  public void setCustomer(int customer);

  public int getFurniture();

  public void setFurniture(int furniture);
}
