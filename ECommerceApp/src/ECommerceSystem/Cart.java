package ECommerceSystem;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (product.isAvailable(quantity)) {
            items.add(new CartItem(product, quantity));
            product.setQuantity(product.getQuantity() - quantity);
        } else {
            throw new IllegalArgumentException("Product " + product.getName() + 
                " is not available in the requested quantity or has expired");
        }
    }

    public List<CartItem> getItems() { return items; }
    public double getSubtotal() { return items.stream().mapToDouble(CartItem::getTotalPrice).sum(); }
    public boolean isEmpty() { return items.isEmpty(); }

    public List<Shippable> getShippableItems() {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : items) {
            if (item.getProduct() instanceof Shippable) {
                shippableItems.add((Shippable) item.getProduct());
            }
        }
        return shippableItems;
    }
}
