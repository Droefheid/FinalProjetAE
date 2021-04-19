package be.vinci.pae.domaine.visit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import be.vinci.pae.domaine.address.Address;

@JsonDeserialize(as = VisitImpl.class)
public interface Visit extends VisitDTO {

  void setAdress(Address address);

}
