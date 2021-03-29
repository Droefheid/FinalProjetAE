package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            + "            special_sale_price, delivery, state, deposit_date, date_of_sale, sale_withdrawal_date, seller \r\n"
            + "            FROM projet.furnitures WHERE id = id_furniture");
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          furniture.setId_furniture(rs.getInt(1));
          furniture.setType(rs.getInt(2));
          furniture.setBuyer(rs.getInt(3));
          furniture.setFurniture_title(rs.getString(4));
          furniture.setPurchase_price(rs.getDouble(5));
          furniture.setPick_up_date(rs.getTimestamp(6)); // get timestamp ?
          furniture.setSelling_price(rs.getDouble(7));
          furniture.setSpecial_sale_price(rs.getDouble(8));
          furniture.setDelivery(rs.getInt(9));
          furniture.setState(rs.getString(10));
          furniture.setDeposit_date(rs.getTimestamp(11));
          furniture.setDate_of_sale(rs.getTimestamp(12));
          furniture.setSale_withdrawal_date(rs.getTimestamp(13));
          furniture.setSeller(rs.getInt(14));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return furniture;

  }

  @Override
  public FurnitureDTO getAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "INSERT INTO projet.furnitures " + "VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    try {
      ps.setInt(1, furniture.getType());
      ps.setInt(2, furniture.getBuyer());
      ps.setString(3, furniture.getFurniture_title());
      ps.setDouble(4, furniture.getPurchase_price());
      ps.setTimestamp(5, furniture.getPick_up_date());
      ps.setDouble(6, furniture.getSelling_price());
      ps.setDouble(7, furniture.getSpecial_sale_price());
      ps.setInt(8, furniture.getDelivery());
      ps.setString(9, furniture.getState());
      ps.setTimestamp(10, furniture.getDeposit_date());
      ps.setTimestamp(11, furniture.getDate_of_sale());
      ps.setTimestamp(12, furniture.getSale_withdrawal_date());
      ps.setInt(13, furniture.getSeller());

      ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return findById(furniture.getId_furniture());
  }


}
