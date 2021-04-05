package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.type.TypeDTO;

public interface TypeDAO {

  List<TypeDTO> getAll();

}
