package streams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    OrderService ordersService = new OrderService();


    @BeforeEach
    public void init() {


        Product p1 = new Product("Tv", "IT", 2000);
        Product p2 = new Product("Laptop", "IT", 2400);
        Product p3 = new Product("Phone", "IT", 400);
        Product p4 = new Product("Lord of The Rings", "Book", 20);
        Product p5 = new Product("Harry Potter Collection", "Book", 120);

        Order o1 = new Order("pending", LocalDate.of(2021, 06, 07));
        o1.addProduct(p1);
        o1.addProduct(p2);
        o1.addProduct(p5);

        Order o2 = new Order("on delivery", LocalDate.of(2021, 06, 01));
        o2.addProduct(p3);
        o2.addProduct(p1);
        o2.addProduct(p2);

        ordersService.saveOrder(o1);
        ordersService.saveOrder(o2);

    }


    @Test
    void countOrderByStatusOnePiece() {
        assertEquals(1, ordersService.countOrderByStatus("pending"));
    }

    @Test
    void countOrderByStatusNothing() {
        assertEquals(0, ordersService.countOrderByStatus("delivered"));
    }

    @Test
    void countOrderByStatusEmptyList() {
        assertEquals(0, new OrderService().countOrderByStatus("pending"));
    }

    @Test
    void collectOrdersWithProductCategory() {
        assertEquals(2, ordersService.collectOrdersWithProductCategory("IT").size());
    }

    @Test
    void collectOrdersWithProductCategoryone() {
        assertEquals(1, ordersService.collectOrdersWithProductCategory("Book").size());
    }

    @Test
    void productsOverAmountPrice() {
        assertEquals(2, ordersService.productsOverAmountPrice(1999).size());
    }

    @Test
    void incomeBetweenDatesAll() {
        assertEquals(9320, ordersService.incomeBetweenDates(
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 6, 7)));
    }

    @Test
    void incomeBetweenDatesOne() {
        assertEquals(4800, ordersService.incomeBetweenDates(
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 6, 1)));
    }

    @Test
    void incomeBetweenDatesNone() {
        assertEquals(0, ordersService.incomeBetweenDates(
                LocalDate.of(2000, 6, 1),
                LocalDate.of(2021, 5, 31)));
    }

    @Test
    void findProductByName() {
        assertEquals("Tv", ordersService.findProductByName("Tv").get().getName());
    }

    @Test
    void findProductByNameNotFind() {
        assertEquals(Optional.empty(), ordersService.findProductByName("Not existing product name."));
    }

    @Test
    void findMostExpensiveOrder() {
        assertEquals(4800, ordersService.findMostExpensiveOrder()
                .get().getProducts().stream().mapToDouble(Product::getPrice).sum());
    }

    @Test
    void findMostExpensiveOrderEmptyList() {
        assertEquals(Optional.empty(), new OrderService().findMostExpensiveOrder());
    }
}