package be.vinci.pae.domaine;

import java.sql.Timestamp;
import java.util.Arrays;

public class FurnitureImpl implements FurnitureDTO {

  private int id_furniture;
  private int type;
  private int buyer;
  private String furniture_title;
  private double purchase_price;
  private Timestamp pick_up_date;
  private double selling_price;
  private double special_sale_price;
  private int delivery;
  private static final String[] STATES = {"R", "V", "A", "O", "RE", "VE"};
  private String state;
  private Timestamp deposit_date;
  private Timestamp date_of_sale;
  private Timestamp sale_withdrawal_date;
  private int seller;


  public int getId_furniture() {
    return id_furniture;
  }

  public void setId_furniture(int id_furniture) {
    this.id_furniture = id_furniture;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getBuyer() {
    return buyer;
  }

  public void setBuyer(int buyer) {
    this.buyer = buyer;
  }

  public String getFurniture_title() {
    return furniture_title;
  }

  public void setFurniture_title(String furniture_title) {
    this.furniture_title = furniture_title;
  }

  public double getPurchase_price() {
    return purchase_price;
  }

  public void setPurchase_price(double purchase_price) {
    this.purchase_price = purchase_price;
  }

  public Timestamp getPick_up_date() {
    return pick_up_date;
  }

  public void setPick_up_date(Timestamp pick_up_date) {
    this.pick_up_date = pick_up_date;
  }

  public double getSelling_price() {
    return selling_price;
  }

  public void setSelling_price(double selling_price) {
    this.selling_price = selling_price;
  }

  public double getSpecial_sale_price() {
    return special_sale_price;
  }

  public void setSpecial_sale_price(double special_sale_price) {
    this.special_sale_price = special_sale_price;
  }

  public int getDelivery() {
    return delivery;
  }

  public void setDelivery(int delivery) {
    this.delivery = delivery;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    if (Arrays.asList(STATES).contains(state)) {
      this.state = state;
    }
  }

  public Timestamp getDeposit_date() {
    return deposit_date;
  }

  public void setDeposit_date(Timestamp deposit_date) {
    this.deposit_date = deposit_date;
  }

  public Timestamp getDate_of_sale() {
    return date_of_sale;
  }

  public void setDate_of_sale(Timestamp date_of_sale) {
    this.date_of_sale = date_of_sale;
  }

  public Timestamp getSale_withdrawal_date() {
    return sale_withdrawal_date;
  }

  public void setSale_withdrawal_date(Timestamp sale_withdrawal_date) {
    this.sale_withdrawal_date = sale_withdrawal_date;
  }

  public int getSeller() {
    return seller;
  }

  public void setSeller(int seller) {
    this.seller = seller;
  }

}
