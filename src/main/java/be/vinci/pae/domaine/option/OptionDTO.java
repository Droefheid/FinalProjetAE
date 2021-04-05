package be.vinci.pae.domaine;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = OptionImpl.class)
public interface OptionDTO {

  int getId();

  void setId(int id);

  Timestamp getOptionTerm();

  void setOptionTerm(Timestamp optionTerm);

  Timestamp getBeginningOptionDate();

  void setBeginningOptionDate(Timestamp beginningOptionDate);

  int getCustomer();

  void setCustomer(int customer);

  int getFurniture();

  void setFurniture(int furniture);
}
