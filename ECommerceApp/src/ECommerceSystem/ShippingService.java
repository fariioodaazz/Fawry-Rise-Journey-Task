package ECommerceSystem;

import java.util.List;

public class ShippingService {
    public void shipItems(List<Shippable> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        
        for (Shippable item : items) {
            System.out.printf("%s    %.0fg%n", item.getName(), item.getWeight() * 1000);
            totalWeight += item.getWeight();
        }
        
        System.out.printf("Total package weight %.2fkg%n%n", totalWeight);
    }
}
