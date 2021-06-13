package streams;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private List<Order> orders = new ArrayList<>();


    public void saveOrder(Order order) {
        orders.add(order);
    }

    public long countOrderByStatus(String status) {
        return orders
                .stream()
                .filter(order -> order.getStatus().equals(status))
                .count();
    }

    public List<Order> collectOrdersWithProductCategory(String category) {
        return orders
                .stream()
                .filter(order -> isItContains(order, category))
                .collect(Collectors.toList());
    }

    private boolean isItContains(Order order, String category) {
        return order.getProducts()
                .stream()
                .anyMatch(product -> product.getCategory().equals(category));
    }

    public List<Product> productsOverAmountPrice(double price) {
        return orders
                .stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getPrice() > price)
                .distinct()
                .collect(Collectors.toList());
    }

    public double incomeBetweenDates(LocalDate startdate, LocalDate endDate) {
        return orders
                .stream()
                .filter(order -> !order.getOrderDate().isBefore(startdate))
                .filter(order -> !order.getOrderDate().isAfter(endDate))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(product -> product.getPrice())
                .sum();
    }

    public Optional<Product> findProductByName(String name) {
        return orders
                .stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getName().equals(name))
                .distinct()
                .findAny();
    }

    public Optional<Order> findMostExpensiveOrder() {
        return orders
                .stream()
                .max((Order o1, Order o2) ->
                        (int) (o1.getProducts().stream().mapToDouble(Product::getPrice).sum()
                                - o2.getProducts().stream().mapToDouble(Product::getPrice).sum())
                );
    }
}
