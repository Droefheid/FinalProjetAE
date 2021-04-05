package be.vinci.pae.domaine.type;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeImpl implements TypeDTO {

  private int typeId;
  private String name;

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int id) {
    this.typeId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "TypesImpl [id=" + typeId + ", name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + typeId;
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
    TypeImpl other = (TypeImpl) obj;
    if (typeId != other.typeId) {
      return false;
    }
    return true;
  }



}
