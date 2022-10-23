package ua.hillelit.lms.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import ua.hillelit.lms.model.exceptions.ProductNotFoundException;

public class ProductList {

  private final List<Product> list = new ArrayList<>();

  public ProductList() {
  }

  public ProductList(Product product) {
    this.add(product);
  }

  private double calculatePrice(Product product) {
    return product.getPrice() * (100 - product.getDiscountPercent()) / 100;
  }

  public void add(Product product) {
    if (product == null) {
      System.out.println("Not correct product");
      return;
    }

    list.add(product);
  }

  public List<Product> filterByTypeAndSize(String type, int price) {

    return list.stream()
        .filter(product -> product.getType().equals(type) & product.getPrice() > price)
        .toList();
  }

  public List<Product> filterByTypeAndDiscount(String type) {
    return list.stream()
        .filter(product -> product.getDiscountPercent() > 0.0 & product.getType().equals(type))
        .map(product -> new Product(product.getType(), calculatePrice(product),
            product.getDiscountPercent(), product.getDateTime()
        ))
        .collect(Collectors.toList());
  }

  public Product cheapest(String type) throws ProductNotFoundException {
    return list.stream()
        .filter(product -> product.getType().equals(type))
        .map(product -> new Product(product.getType(), calculatePrice(product)))
        .min(Comparator.comparing(Product::getPrice)).orElseThrow(
            () -> new ProductNotFoundException("Product " + type + " does not exist in our shop"));
  }

  public List<Product> lastAdded(int amount) {
    return list.stream()
        .sorted(Comparator.comparing(Product::getDateTime))
        .skip(list.size() - amount)
        .toList();
  }

  public Double calculatePrice(String type, double price, int year) {
    return list.stream()
        .map(product -> new Product(product.getType(),
            calculatePrice(product), product.getDiscountPercent(), product.getDateTime()))
        .filter(product -> product.getType().equals(type) & product.getPrice() < price
            & product.getDateTime().getYear() == year)
        .mapToDouble(Product::getPrice).sum();
  }

  public Map<String, ProductList> getProductMap() {
    Map<String, ProductList> productMap = new HashMap<>();

    list.stream()
        .map(Product::getType)
        .distinct()
        .forEach(type -> productMap.put(type, new ProductList()));

    list.stream().forEach(product -> productMap.get(product.getType()).add(product));

    return productMap;
  }

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
