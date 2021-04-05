package be.vinci.pae.domaine;

import java.util.List;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.TypeDAO;
import jakarta.inject.Inject;

public class TypeUCCImpl implements TypeUCC {

  @Inject
  private TypeDAO typeDao;

  @Inject
  private DalServices dalservices;

  @Override
  public List<TypeDTO> getAll() {
    // TODO Auto-generated method stub
    return null;
  }

}
