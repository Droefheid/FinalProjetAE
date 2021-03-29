package be.vinci.pae.domaine;

import java.sql.Timestamp;

public interface Delivery {

  public int getId();

  public void setId(int id);

  public Timestamp getEstimatedDeliveryDate();

  public void setEstimatedDeliveryDate(Timestamp estimatedDeliveryDate);

  public boolean isDelivered();

  public void setDelivered(boolean isDelivered);

  public int getCustomer();

  public void setCustomer(int customer);
}
