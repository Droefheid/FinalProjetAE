package be.vinci.pae.domaine;

import org.mindrot.jbcrypt.BCrypt;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import views.Views;

@JsonInclude(JsonInclude.Include.NON_NULL) // ignore all null fields in order to avoid sending props not linked to a JSON view
public class UserImpl implements User {
  // TODO Est ce que le client ne voit pas que son id et pseudo?

  @JsonView(Views.Public.class)
  private int id;
  @JsonView(Views.Public.class)
  private String lastName;
  @JsonView(Views.Public.class)
  private String firstName;
  @JsonView(Views.Public.class)
  private String username;
  @JsonView(Views.Public.class)
  private int adressID;
  @JsonView(Views.Public.class)
  private String email;

  @JsonView(Views.Boss.class)
  private boolean isBoss;
  @JsonView(Views.Boss.class)
  private boolean isAntiqueDealer;
  @JsonView(Views.Boss.class)
  private boolean isConfirmed;
  @JsonView(Views.Boss.class)
  private String registrationDate;

  @JsonView(Views.Internal.class)
  private String password;

  @Override
  public int getID() {
    return id;
  }

  @Override
  public void setID(int id) {
    this.id = id;
  }

  @Override
  public boolean isBoss() {
    return isBoss;
  }

  @Override
  public void setBoss(boolean isBoss) {
    this.isBoss = isBoss;
  }

  @Override
  public boolean isAntiqueDealer() {
    return isAntiqueDealer;
  }

  @Override
  public void setAntiqueDealer(boolean isAntiqueDealer) {
    this.isAntiqueDealer = isAntiqueDealer;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public int getAdressID() {
    return adressID;
  }

  @Override
  public void setAdressID(int adressID) {
    this.adressID = adressID;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean isConfirmed() {
    return isConfirmed;
  }

  @Override
  public void setConfirmed(boolean isConfirmed) {
    this.isConfirmed = isConfirmed;
  }

  @Override
  public String getRegistrationDate() {
    return registrationDate;
  }

  @Override
  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String hashPassword) {
    this.password = hashPassword;
  }

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public String toString() {
    // TODO Auto-generated method stub
    return null;
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
    UserImpl other = (UserImpl) obj;
    if (id != other.id)
      return false;
    return true;
  }

}
