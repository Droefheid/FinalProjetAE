package be.vinci.pae.domaine;

public class AdressFactoryImpl implements AdressFactory {

  @Override
  public Adress getAdress() {
    return new AdressImpl();
  }

}
