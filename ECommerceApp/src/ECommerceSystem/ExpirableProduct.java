package ECommerceSystem;
import java.util.Date;

public class ExpirableProduct extends Product {
    private Date expiryDate;

    public ExpirableProduct(String name, double price, int quantity, Date expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isAvailable(int requestedQuantity) {
        Date currentDate = new Date();
        return requestedQuantity <= getQuantity() && expiryDate.after(currentDate);
    }
}
