package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import jakarta.inject.Inject;

public class FurnitureDAOImpl implements FurnitureDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;


  @Override
  public FurnitureDTO findById(int id) {

    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT furniture_id," + " type, buyer, furniture_title,"
            + " purchase_price, furniture_date_collection ,selling_price,"
            + " special_sale_price,delivery,state_furniture,deposit_date,"
            + " date_of_sale, sale_withdrawal_date, seller, pick_up_date"
            + " FROM projet.furnitures WHERE furniture_id = ?");
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          furniture = fullFillFurnitures(rs, furniture);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error findById", e);
    }
    return furniture;
  }

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "INSERT INTO projet.furnitures " + "VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    try {
      setAllPsAttributNotNull(ps, furniture);

      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error add", e);
    }
    return findById(furniture.getFurnitureId());
  }

  @Override
  public List<FurnitureDTO> getAll() {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT furniture_id," + " type, buyer, furniture_title,"
            + " purchase_price, furniture_date_collection ,selling_price,"
            + " special_sale_price,delivery,state_furniture,deposit_date,"
            + " date_of_sale, sale_withdrawal_date, seller, pick_up_date"
            + " FROM projet.furnitures ORDER BY furniture_id");

    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();

    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        FurnitureDTO furniture = domaineFactory.getFurnitureDTO();
        furniture = fullFillFurnitures(rs, furniture);
        list.add(furniture);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error getAll", e);
    }
    return list;
  }

  @Override
  public FurnitureDTO update(FurnitureDTO furniture) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement("UPDATE projet.furnitures "
        + "SET type = ?" + ", buyer = ?, furniture_title = ?, purchase_price = ?,"
        + " furniture_date_collection = ?, selling_price = ?, special_sale_price = ?,"
        + " delivery = ?, state_furniture = ?, deposit_date = ?, date_of_sale = ?,"
        + " sale_withdrawal_date = ?, seller = ?, pick_up_date = ?" + " WHERE furniture_id = ?");
    try {
      ps = setAllPsAttributNotNull(ps, furniture);
      ps.setInt(15, furniture.getFurnitureId());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("ERROR update furniture.", e);
    }
    return findById(furniture.getFurnitureId());
  }

  private FurnitureDTO fullFillFurnitures(ResultSet rs, FurnitureDTO furniture) {
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
      furniture.setPickUpDate(rs.getTimestamp(15));

    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillFurnitures", e);
    }

    return furniture;
  }

  private PreparedStatement setAllPsAttributNotNull(PreparedStatement ps, FurnitureDTO furniture)
      throws SQLException {
    // Il faut verifier ce qui est nessecaire ou non.
    ps.setInt(1, furniture.getType());
    if (furniture.getBuyer() != 0) {
      ps.setInt(2, furniture.getBuyer());
    } else {
      ps.setNull(2, furniture.getBuyer());
    }
    ps.setString(3, furniture.getFurnitureTitle());
    ps.setDouble(4, furniture.getPurchasePrice());
    ps.setTimestamp(5, furniture.getFurnitureDateCollection());
    ps.setDouble(6, furniture.getSellingPrice());
    ps.setDouble(7, furniture.getSpecialSalePrice());
    if (furniture.getDelivery() != 0) {
      ps.setInt(8, furniture.getDelivery());
    } else {
      ps.setNull(8, furniture.getDelivery());
    }
    ps.setString(9, furniture.getState());
    ps.setTimestamp(10, furniture.getDepositDate());
    ps.setTimestamp(11, furniture.getDateOfSale());
    ps.setTimestamp(12, furniture.getSaleWithdrawalDate());
    ps.setInt(13, furniture.getSeller());
    ps.setTimestamp(14, furniture.getPickUpDate());
    return ps;
  }
}
