import java.util.*;
/*
    Problem: Implement the "notify me when the item is in stock" feature in amazon.com
    Expectations:
    - We should be able to understand your implementation without needing to reach out to you.
    - Use comments when appropriate to document your design or implementation choices.
    - Implement the interface given in either C++ or Java. Feel free to use C++11 features or Java 1.8 features
    - Submitted code should compile and include unit tests
    - Consider making a very basic implementation that works and is readable before enhancing it.
*/

public interface ProductNotifier {
    final int SCHEME_FIFO = 0;         // First in First out
    final int SCHEME_PRIME_FIRST = 1;  // Prime members in FIFO order followed by nonPrime in FIFO

    // Returns the list of customer IDs to be notified depending on the scheme
    ArrayList<Long> getCustomersToNotify(long productId, int scheme, int numCustomersToBeNotified);

    // Record a customer's request to be notified when the product becomes available
    void notifyMe(long productId, Customer customer);
}
