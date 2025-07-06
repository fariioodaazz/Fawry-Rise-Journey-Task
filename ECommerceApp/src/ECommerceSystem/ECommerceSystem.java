package ECommerceSystem;
import java.util.Date;
import java.util.List;

public class ECommerceSystem {
    private static final double SHIPPING_FEE_PER_KG = 30.0;

    public static void checkout(Customer customer, Cart cart, ShippingService shippingService) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cannot checkout with an empty cart");
        }

        double shippingFee = cart.getShippableItems().stream()
            .mapToDouble(Shippable::getWeight)
            .sum() * SHIPPING_FEE_PER_KG;

        double subtotal = cart.getSubtotal();
        double totalAmount = subtotal + shippingFee;

        if (customer.getBalance() < totalAmount) {
            throw new IllegalArgumentException("Insufficient balance for checkout");
        }

        customer.deductBalance(totalAmount);

        List<Shippable> shippableItems = cart.getShippableItems();
        if (!shippableItems.isEmpty()) {
            shippingService.shipItems(shippableItems);
        }

        printReceipt(cart, subtotal, shippingFee, totalAmount, customer);
    }

    private static void printReceipt(Cart cart, double subtotal, double shippingFee, 
                                   double totalAmount, Customer customer) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s    %.0f%n", 
                item.getQuantity(), 
                item.getProduct().getName(), 
                item.getTotalPrice());
        }
        System.out.println("---");
        System.out.printf("Subtotal    %.0f%n", subtotal);
        System.out.printf("Shipping    %.0f%n", shippingFee);
        System.out.printf("Amount    %.0f%n", totalAmount);
        System.out.printf("Remaining balance: %.0f%n", customer.getBalance());
        System.out.println("\nEND.");
    }

    public static void main(String[] args) {
        System.out.println("\n=== Test Case 8: Mixed Expired/Valid ===");
    
        // Expired product
        ExpirableProduct yogurt = new ExpirableProduct("Yogurt", 40, 5, 
            new Date(System.currentTimeMillis() - 86400000));

        // Valid product
        ShippableProduct coffee = new ShippableProduct("Coffee", 80, 10, 0.3);

        Customer customer = new Customer("Henry", 500);
        Cart cart = new Cart();

        try {
            cart.add(coffee, 2);
            cart.add(yogurt, 1); // This should fail
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error when adding yogurt: " + e.getMessage());
        }

        // Should still be able to checkout with just coffee
        checkout(customer, cart, new ShippingService());
    }
}
