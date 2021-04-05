package be.vinci.pae.domaine.address;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressImpl implements AddressDTO {

  private int id;
  private String street;
  private String buildingNumber;
  private String unitNumber;
  private String postCode;
  private String commune;
  private String country;

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getBuildingNumber() {
    return buildingNumber;
  }

  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  public String getUnitNumber() {
    return this.unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getCommune() {
    return commune;
  }

  public void setCommune(String commune) {
    this.commune = commune;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    // TODO generate with source
    return "AddressImpl [id=" + id + ", Street=" + street + ", buildingNumber=" + buildingNumber
        + ", unitNumber=" + unitNumber + ", postCode=" + postCode + ", commune=" + commune
        + ", country=" + country + "]";
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
    AddressImpl other = (AddressImpl) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

}
