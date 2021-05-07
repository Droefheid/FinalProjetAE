package be.vinci.pae.domaine.type;

import java.util.List;

public interface TypeUCC {

  TypeDTO findById(int id);

  List<TypeDTO> getAll();

}
