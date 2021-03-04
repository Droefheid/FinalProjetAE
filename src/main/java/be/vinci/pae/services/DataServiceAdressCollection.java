package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.Adress;

public interface DataServiceAdressCollection {

  Adress getAdress(int id);

  List<Adress> getAdresses();

  Adress addAdress(Adress adress);

}
