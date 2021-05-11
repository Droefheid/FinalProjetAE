package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.furniture.FurnitureDTO;

public interface FurnitureDAO {

  FurnitureDTO findById(int id);

  List<FurnitureDTO> getAll();

  FurnitureDTO add(FurnitureDTO furniture);

  FurnitureDTO update(FurnitureDTO furniture);

  List<FurnitureDTO> getMyFurniture(int userID);

  FurnitureDTO findByFurnitureInfo(FurnitureDTO furnitureDTO);

  List<FurnitureDTO> getBoughtFurniture(int id);

  List<FurnitureDTO> searchFurniture(String searchBar, int typeID, int minPrice, int maxPrice);

  List<FurnitureDTO> searchFurnitureWithoutType(String search, int minPrice, int maxPrice);

  List<FurnitureDTO> searchFurnitureWithSeller(String searchBar, String sellerName, int typeID,
      int minPrice, int maxPrice);

  List<FurnitureDTO> searchFurnitureWithSellerWithoutType(String searchBar, String sellerName,
      int minPrice, int maxPrice);

}
