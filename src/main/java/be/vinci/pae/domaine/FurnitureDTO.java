package be.vinci.pae.domaine;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {


	int getId_furniture();
	void setId_furniture(int id_furniture);
	int getType();
	void setType(int type);
	int getBuyer();
	void setBuyer(int buyer);
	String getFurniture_title();
	void setFurniture_title(String furniture_title);
	double getPurchase_price();
	void setPurchase_price(double purchase_price);
	Timestamp getPick_up_date();
	void setPick_up_date(Timestamp pick_up_date);
	double getSelling_price();
	void setSelling_price(double selling_price);
	double getSpecial_sale_price();
	void setSpecial_sale_price(double special_sale_price);
	int getDelivery();
	void setDelivery(int delivery);
	String getState();
	void setState(String state);
	Timestamp getDeposit_date();
	void setDeposit_date(Timestamp deposit_date);
	Timestamp getDate_of_sale();
	void setDate_of_sale(Timestamp date_of_sale);
	Timestamp getSale_withdrawal_date();
	void setSale_withdrawal_date(Timestamp sale_withdrawal_date);
	int getSeller();
	void setSeller(int seller);
	
}
