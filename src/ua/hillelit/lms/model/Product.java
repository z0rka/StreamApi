package ua.hillelit.lms.model;

import java.time.LocalDateTime;

/**
 * Class that contains info about product Constructors
 *
 * @see Product#Product(String, double)
 * @see Product#Product(String, double, double)
 * @see Product#Product(String, double, double, LocalDateTime)
 * @see Product#Product(String, double, double, LocalDateTime, int)
 * <p>
 * Overrided methons
 * @see Product#toString()
 */
public class Product {

  private int id;
  private final String type;
  private final double price;
  private double discountPercent = 0.0;

  private LocalDateTime dateTime = LocalDateTime.now();

  public Product(String type, double price) {
    this.type = type;
    this.price = price;
    this.id = type.hashCode() * (int) price;
  }

  public Product(String type, double price, double discountPercent) {
    this.type = type;
    this.price = price;

    if (discountPercent < 0.0) {
      return;
    }

    this.discountPercent = discountPercent;
    this.id = type.hashCode() * (int) price;
  }

  public Product(String type, double price, double discountPercent, LocalDateTime time) {
    this.type = type;
    this.price = price;

    if (discountPercent < 0.0) {
      return;
    }

    this.discountPercent = discountPercent;
    this.dateTime = time;
    this.id = type.hashCode() * (int) price;
  }

  public Product(String type, double price, double discountPercent, LocalDateTime time, int id) {
    this.type = type;
    this.price = price;

    if (discountPercent < 0.0) {
      return;
    }

    this.discountPercent = discountPercent;
    this.dateTime = time;
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public double getPrice() {
    return price;
  }

  public double getDiscountPercent() {
    return discountPercent;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", type='" + type + '\'' +
        ", price=" + price +
        ", discountPercent=" + discountPercent +
        ", dateTime=" + dateTime +
        '}';
  }
}
