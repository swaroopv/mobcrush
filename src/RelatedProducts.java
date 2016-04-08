import java.util.*;
/*
    Question: Implement the "people who bought X item also bought Y item" feature in amazon.com?
    Expectations:
    - We should be able to understand your implementation without needing to reach out to you.
    - Use comments when appropriate to document your design or implementation choices.
    - Implement the interface given in either C++ or Java. Feel free to use C++11 features or Java 1.8 features
    - Submitted code should compile and include unit tests
    - Consider making a very basic implementation that works and is readable before enhancing it.
*/

public interface RelatedProducts {
// Called when a product purchase is made by a customer
    void registerPurchase(long customerID, long productId);

    // Returns the next `numProducts` products related to this product
    ArrayList<Long> getRelatedProducts(long customerID, long productId, int numProducts);

    // Returns the ID of a different customer who has also bought the productID
    long getRelatedCustomer(long customerID, int productID);
}
