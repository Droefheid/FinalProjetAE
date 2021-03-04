package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.Adress;
import be.vinci.pae.utils.Config;

public class DataServiceAdressCollectionImpl implements DataServiceAdressCollection {

  private static final String DB_FILE_PATH = Config.getProperty("DatabaseFilePath");
  private static final String COLLECTION_NAME = "adress";

  private List<Adress> adresses;

  public DataServiceAdressCollectionImpl() {
    this.adresses = Json.loadDataFromFile(DB_FILE_PATH, COLLECTION_NAME, Adress.class);
    System.out.println("ADRESS DATA SERVICE");
  }

  @Override
  public Adress getAdress(int id) {
    return this.adresses.stream().filter(u -> u.getID() == id).findAny().orElse(null);
  }

  @Override
  public List<Adress> getAdresses() {
    // TODO Auto-generated method stub
    return adresses;
  }

  @Override
  public Adress addAdress(Adress adress) {
    adress.setID(this.adresses.size() + 1);
    this.adresses.add(adress);
    Json.saveDataToFile(this.adresses, DB_FILE_PATH, COLLECTION_NAME); // TODO with JSON config
    return adress;
  }

}
