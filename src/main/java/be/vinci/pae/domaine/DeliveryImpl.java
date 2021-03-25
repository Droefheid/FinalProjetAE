package be.vinci.pae.domaine;

import java.sql.Date;

public class DeliveryImpl implements Delivery {

  private int id;
  private Date estimatedDeliveryDate;
  private boolean isDelivered;
  private User customer;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getEstimatedDeliveryDate() {
    return estimatedDeliveryDate;
  }

  public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
    this.estimatedDeliveryDate = estimatedDeliveryDate;
  }

  public boolean isDelivered() {
    return isDelivered;
  }

  public void setDelivered(boolean isDelivered) {
    this.isDelivered = isDelivered;
  }

  public User getCustomer() {
    return customer;
  }

  public void setCustomer(User customer) {
    this.customer = customer;
  }

  @Override
  public String toString() {
    return "DeliveryImpl [id=" + id + ", estimatedDeliveryDate=" + estimatedDeliveryDate
        + ", isDelivered=" + isDelivered + ", customer=" + customer + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DeliveryImpl other = (DeliveryImpl) obj;
    if (id != other.id)
      return false;
    return true;
  }



}
