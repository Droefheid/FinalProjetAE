package be.vinci.pae.domaine.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import be.vinci.pae.domaine.user.UserImpl;

@JsonDeserialize(as = UserImpl.class)
public interface Address extends AddressDTO {



}
