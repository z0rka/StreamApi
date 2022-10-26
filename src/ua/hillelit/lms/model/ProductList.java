package ua.hillelit.lms.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import ua.hillelit.lms.model.exceptions.ProductNotFoundException;

/**
 * ProductList class that contains {@link List} {@link Product} field as private and final
 * <p>
 * Constructors
 *
 * @see ProductList#ProductList()
 * @see ProductList#ProductList(Product)
 * <p>
 * <p>
 * And class contains methods to navigate through the list Public methods
 * @see ProductList#filterByTypeAndPrice(String, int)
 * @see ProductList#filterByTypeAndDiscount(String)
 * @see ProductList#cheapest(String)
 * @see ProductList#lastAdded(int)
 * @see ProductList#calculatePrice(String, double, int)
 * @see ProductList#getProductMap()
 * <p>
 * Private methods
 * @see ProductList#calculatePrice(Product)
 * <p>
 * Overrided methods
 * @see ProductList#equals(Object)
 * @see ProductList#hashCode()
 **/
public class ProductList {

  private final List<Product> list = new ArrayList<>();

  /**
   * This method calculates product price with discount
   *
   * @param product product to calculate price
   * @return double price with discount
   */
  private double calculatePrice(Product product) {
    return product.getPrice() * (100 - product.getDiscountPercent()) / 100;
  }

  /**
   * Default constructor
   */
  public ProductList() {
  }

  /**
   * Constructor with adding product
   */
  public ProductList(Product product) {
    this.add(product);
  }

  /**
   * Method that checks if product == null and if false it adds product to the list
   *
   * @param product our product of class {@link Product}
   */
  public void add(Product product) {
    if (product == null) {
      System.out.println("Not correct product");
      return;
    }

    list.add(product);
  }

  /**
   * Method that filters by type of product and its price using Stream Api
   *
   * @param type  String type of our product
   * @param price double price of product
   * @return List<Product> return filtered list of products
   */
  public List<Product> filterByTypeAndPrice(String type, int price) {

    return list.stream()
        .filter(product -> product.getType().equals(type) & product.getPrice() > price)
        .toList();
  }

  /**
   * Finds products that have discount and return them with calculated price(price with discount)
   *
   * @param type String type of our product
   * @return List<Product> return filtered list of products
   */
  public List<Product> filterByTypeAndDiscount(String type) {
    return list.stream()
        .filter(product -> product.getDiscountPercent() > 0.0 & product.getType().equals(type))
        .map(product -> new Product(product.getType(), calculatePrice(product),
            product.getDiscountPercent(), product.getDateTime()
        ))
        .collect(Collectors.toList());
  }

  /**
   * Finds the cheapest product and returns it
   *
   * @param type String type of our product
   * @return {@link  Product} object with the lowest price
   *
   * @exception ProductNotFoundException if product wasn`t found
   */
  public Product cheapest(String type) throws ProductNotFoundException {
    return list.stream()
        .filter(product -> product.getType().equals(type))
        .map(product -> new Product(product.getType(), calculatePrice(product)))
        .min(Comparator.comparing(Product::getPrice)).orElseThrow(
            () -> new ProductNotFoundException("Product " + type + " does not exist in our shop"));
  }

  /**
   * Returns List<Product> that contains last added products(amount provided,filtered by time when
   * product was added)
   *
   * @param amount amount of products that should be found
   * @return List<Product> return filtered list of products
   */
  public List<Product> lastAdded(int amount) {
    return list.stream()
        .sorted(Comparator.comparing(Product::getDateTime))
        .skip(list.size() - amount)
        .toList();
  }

  /**
   * Returns sum price of products(considering discount) filtered by type,price(that of
   * less),year(when added)
   *
   * @param type  String type of product
   * @param price double price of product
   * @param year  year when product was added to list
   * @return sum of prices {@link Double}
   */
  public Double calculatePrice(String type, double price, int year) {
    return list.stream()
        .map(product -> new Product(product.getType(),
            calculatePrice(product), product.getDiscountPercent(), product.getDateTime()))
        .filter(product -> product.getType().equals(type) & product.getPrice() <= price
            & product.getDateTime().getYear() == year)
        .mapToDouble(Product::getPrice).sum();
  }

  /**
   * Returns {@link HashMap} key - type of product , value - list of products 1.Create map 2.Add
   * keys to map 3.Add values by keys in map
   *
   * @return {@link HashMap}
   */
  public Map<String, ProductList> getProductMap() {
    Map<String, ProductList> productMap = new HashMap<>();

    list.stream()
        .map(Product::getType)
        .distinct()
        .forEach(type -> productMap.put(type, new ProductList()));

    list.stream().forEach(product -> productMap.get(product.getType()).add(product));

    return productMap;
  }

  /**
   * Getter of List of products
   */
  public List<Product> getList() {
    return list;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductList that = (ProductList) o;
    return Objects.equals(list, that.list);
  }

  @Override
  public int hashCode() {
    return Objects.hash(list);
  }
}
