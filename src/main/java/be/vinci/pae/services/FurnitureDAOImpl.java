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
  private DalServices dalServices;


  @Override
  public FurnitureDTO findById(int id) {

    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT id_furniture, type, buyer, furniture_title, purchase_price, pick_up_date, selling_price,\r\n"
            + "            special_sale_price, delivery, state, "
            + "deposit_date, date_of_sale, sale_withdrawal_date, seller \r\n"
            + "            FROM projet.furnitures WHERE id_furniture = ?");
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          furniture.setIdFurniture(rs.getInt(1));
          furniture.setType(rs.getInt(2));
          furniture.setBuyer(rs.getInt(3));
          furniture.setFurnitureTitle(rs.getString(4));
          furniture.setPurchasePrice(rs.getDouble(5));
          furniture.setPickUpDate(rs.getTimestamp(6));
          furniture.setSellingPrice(rs.getDouble(7));
          furniture.setSpecialSalePrice(rs.getDouble(8));
          furniture.setDelivery(rs.getInt(9));
          furniture.setState(rs.getString(10));
          furniture.setDepositDate(rs.getTimestamp(11));
          furniture.setDateOfSale(rs.getTimestamp(12));
          furniture.setSaleWithdrawalDate(rs.getTimestamp(13));
          furniture.setSeller(rs.getInt(14));
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
      ps.setTimestamp(5, furniture.getPickUpDate());
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
    return findById(furniture.getIdFurniture());
  }

  @Override
  public List<FurnitureDTO> getAll() {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT id_furniture, type, buyer, furniture_title, purchase_price, pick_up_date, selling_price,\r\n"
            + "            special_sale_price, delivery, state,"
            + " deposit_date, date_of_sale, sale_withdrawal_date, seller \r\n"
            + "            FROM projet.furnitures");
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        furniture.setIdFurniture(rs.getInt(1));
        furniture.setType(rs.getInt(2));
        furniture.setBuyer(rs.getInt(3));
        furniture.setFurnitureTitle(rs.getString(4));
        furniture.setPurchasePrice(rs.getDouble(5));
        furniture.setPickUpDate(rs.getTimestamp(6));
        furniture.setSellingPrice(rs.getDouble(7));
        furniture.setSpecialSalePrice(rs.getDouble(8));
        furniture.setDelivery(rs.getInt(9));
        furniture.setState(rs.getString(10));
        furniture.setDepositDate(rs.getTimestamp(11));
        furniture.setDateOfSale(rs.getTimestamp(12));
        furniture.setSaleWithdrawalDate(rs.getTimestamp(13));
        furniture.setSeller(rs.getInt(14));
        list.add(furniture);
      }
    } catch (SQLException e) {
      throw new FatalException("error get all", e);
    }

    return list;
  }


}
