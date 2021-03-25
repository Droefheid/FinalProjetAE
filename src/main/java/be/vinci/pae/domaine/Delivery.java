package be.vinci.pae.domaine;

import java.sql.Date;

public interface Delivery {

  public int getId();

  public void setId(int id);

  public Date getEstimatedDeliveryDate();

  public void setEstimatedDeliveryDate(Date estimatedDeliveryDate);

  public boolean isDelivered();

  public void setDelivered(boolean isDelivered);

  public User getCustomer();

  public void setCustomer(User customer);
}
