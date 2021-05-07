package be.vinci.pae.domaine.type;

import java.util.List;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.TypeDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

public class TypeUCCImpl implements TypeUCC {

  @Inject
  private TypeDAO typeDAO;

  @Inject
  private DalServices dalservices;

  @Override
  public TypeDTO findById(int id) {
    dalservices.startTransaction();
    TypeDTO typeDTO = typeDAO.findById(id);
    if (typeDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Type doesn't exist.", Status.BAD_REQUEST);
    }
    dalservices.commitTransaction();
    return typeDTO;
  }

  @Override
  public List<TypeDTO> getAll() {
    dalservices.startTransaction();
    List<TypeDTO> list = typeDAO.getAll();
    dalservices.commitTransaction();
    return list;
  }

}
