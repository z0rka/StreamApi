package ua.hillelit.lms;

import java.util.List;
import java.util.Map;
import ua.hillelit.lms.model.Product;
import ua.hillelit.lms.model.ProductList;
import ua.hillelit.lms.model.exceptions.ProductNotFoundException;



/**
 * @author Kostya
 * @version 1.0
 * @since 1.0
* Main class where we test {@link ProductList} methods
* */
public class Main {

/**
 *Main method where we create {@link  ProductList} class and add {@link Product } to it
 * @param args
 * Then call methods one by one
 * @see ProductList#filterByTypeAndPrice(String, int)
 * @see ProductList#filterByTypeAndDiscount(String)
 * @see ProductList#cheapest(String)
 * @see ProductList#lastAdded(int)
 * @see ProductList#calculatePrice(String, double, int)
 * @see ProductList#getProductMap()
 * */
  public static void main(String[] args) {
    ProductList productList = new ProductList();

    productList.add(new Product("Book", 250, 10));
    productList.add(new Product("Book", 260));
    productList.add(new Product("Apple", 210));
    productList.add(new Product("Toy", 270));
    productList.add(new Product("Book", 20));
    productList.add(new Product("Book", 300, 99));

    System.out.println("-".repeat(20));
    List<Product> bookList = productList.filterByTypeAndPrice("Book", 250);
    bookList.forEach(book -> System.out.println(book.getType() + " " + book.getPrice()));

    System.out.println("-".repeat(20));
    List<Product> bookList2 = productList.filterByTypeAndDiscount("Book");
    bookList2.forEach(book -> System.out.println(book.getType() + " " + book.getPrice()));

    System.out.println("-".repeat(20));
    Product book1 = null;
    try {
      book1 = productList.cheapest("Book");
    } catch (ProductNotFoundException e) {
      System.out.println(e.getMessage());
    }

    assert book1 != null;
    System.out.println(book1.getType() + " " + book1.getPrice());

    System.out.println("-".repeat(20));
    List<Product> bookList3 = productList.lastAdded(3);
    bookList3.forEach(book -> System.out.println(book.getType() + " " + book.getPrice()));

    System.out.println("-".repeat(20));
    Double productSum = productList.calculatePrice("Book", 300, 2022);
    System.out.println(productSum);

    System.out.println("-".repeat(20));
    Map<String, ProductList> productListMap = productList.getProductMap();

    productListMap.forEach((key, value) -> {
      System.out.println(key);
      value.getList().forEach(System.out::println);
    });
  }
}