package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.type.TypeDTO;
import jakarta.inject.Inject;

public class TypeDAOImpl implements TypeDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;

  @Override
  public List<TypeDTO> getAll() {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT type_id , name FROM projet.types" + " ORDER BY type_id");


    List<TypeDTO> list = new ArrayList<TypeDTO>();

    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        TypeDTO type = createFullFillType(rs);
        list.add(type);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillUsers", e);
    }

    return list;
  }

  @Override
  public TypeDTO findById(int id) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT type_id," + " name" + " FROM projet.types WHERE type_id = ?");
    TypeDTO type = null;
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          type = createFullFillType(rs);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error findById", e);
    }
    return type;
  }

  private TypeDTO createFullFillType(ResultSet rs) {
    TypeDTO type = domaineFactory.getTypeDTO();
    try {
      type.setTypeId(rs.getInt(1));
      type.setName(rs.getString(2));

    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillUsers", e);
    }
    return type;
  }

}
