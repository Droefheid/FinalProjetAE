package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.FurnitureDTO;
import jakarta.inject.Inject;

public class FurnitureDAOImpl implements FurnitureDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalServices;


  @Override
  public FurnitureDTO findById(int id) {

    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT id_furniture," + " type, buyer, furniture_title, purchase_price,"
            + " pick_up_date, selling_price,\r\n" + " special_sale_price, delivery, state, "
            + "deposit_date, date_of_sale, sale_withdrawal_date, seller \r\n"
            + "            FROM projet.furnitures WHERE id_furniture = ?");
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          fullFillFurnitures(rs, furniture);
        }
      }
    } catch (SQLException e) {
      throw new FatalException("error findById", e);
    }

    return furniture;
  }

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "INSERT INTO projet.furnitures " + "VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    try {
      ps.setInt(1, furniture.getType());
      ps.setInt(2, furniture.getBuyer());
      ps.setString(3, furniture.getFurnitureTitle());
      ps.setDouble(4, furniture.getPurchasePrice());
      ps.setTimestamp(5, furniture.getFurnitureDateCollection());
      ps.setDouble(6, furniture.getSellingPrice());
      ps.setDouble(7, furniture.getSpecialSalePrice());
      ps.setInt(8, furniture.getDelivery());
      ps.setString(9, furniture.getState());
      ps.setTimestamp(10, furniture.getDepositDate());
      ps.setTimestamp(11, furniture.getDateOfSale());
      ps.setTimestamp(12, furniture.getSaleWithdrawalDate());
      ps.setInt(13, furniture.getSeller());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException("error add", e);
    }
    return findById(furniture.getFurnitureId());
  }

  @Override
  public List<FurnitureDTO> getAll() {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT furniture_id," + " type,state_furniture, buyer, furniture_title,"
            + " purchase_price, furniture_date_collection ,"
            + " selling_price,special_sale_price,deposit_date,"
            + " date_of_sale, sale_withdrawal_date, seller" + " FROM projet.furnitures");

    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();


    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        fullFillFurnitures(rs, furniture);
        list.add(furniture);
      }
    } catch (SQLException e) {
      throw new FatalException("error fullFillFurnitures", e);
    }


    return list;
  }

  private void fullFillFurnitures(ResultSet rs, FurnitureDTO furniture) {
    try {
      furniture.setFurnitureId(rs.getInt(1));
      furniture.setType(rs.getInt(2));
      furniture.setBuyer(rs.getInt(3));
      furniture.setFurnitureTitle(rs.getString(4));
      furniture.setPurchasePrice(rs.getFloat(5));
      furniture.setFurnitureDateCollection(rs.getTimestamp(6));
      furniture.setSellingPrice(rs.getFloat(7));
      furniture.setSpecialSalePrice(rs.getFloat(8));
      furniture.setDelivery(rs.getInt(9));
      furniture.setState(rs.getString(10));
      furniture.setDepositDate(rs.getTimestamp(11));
      furniture.setDateOfSale(rs.getTimestamp(12));
      furniture.setSaleWithdrawalDate(rs.getTimestamp(13));
      furniture.setSeller(rs.getInt(14));

    } catch (SQLException e) {
      throw new FatalException("error fullFillFurnitures", e);
    }



  }

}
