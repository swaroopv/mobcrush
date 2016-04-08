import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by swvenu on 08-04-2016.
 */
public class RelatedProductsTest {
    RelatedProducts rp;
    Customer c1, c2, c3, c4, c5, c6;

    @Before
    public void setUp() throws Exception {
        rp = new RelatedProductsImpl();
        c1 = new Customer("c1",false);
        c2 = new Customer("c2",false);
        c3 = new Customer("c3",false);
        c4 = new Customer("c4",true);
        c5 = new Customer("c5",true);
        c6 = new Customer("c6",true);

        rp.registerPurchase(c1.id,1);
        rp.registerPurchase(c1.id,2);
        rp.registerPurchase(c1.id,3);
        rp.registerPurchase(c2.id,1);
        rp.registerPurchase(c2.id,2);
        rp.registerPurchase(c2.id,2);
        rp.registerPurchase(c2.id,4);
        rp.registerPurchase(c3.id,3);
        rp.registerPurchase(c3.id,4);
        rp.registerPurchase(c4.id,1);
        rp.registerPurchase(c4.id,4);
        rp.registerPurchase(c5.id,1);
        rp.registerPurchase(c6.id,1);
        rp.registerPurchase(c6.id,4);
    }


    @Test
    public void noProductIdFound(){
        //returns empty list
        assertEquals(rp.getRelatedProducts(c1.id, 5, 3).size(), 0);
    }

    @Test
    public void checkReturnListSizeOfRelatedProducts(){
        //check number of customers to be notified returned
        assertEquals(rp.getRelatedProducts(c4.id, 1, 1).size(), 1);
        assertEquals(rp.getRelatedProducts(c4.id, 1, 5).size(), 3);
        assertEquals(rp.getRelatedProducts(c5.id, 1, 3).size(), 3);
        assertEquals(rp.getRelatedProducts(c5.id, 1, 4).size(), 3);

    }

    @Test
    public void checkRelatedProducts(){
        ArrayList<Long> t = rp.getRelatedProducts(c5.id, 1, 3);
        assertEquals((long)t.get(0), 4);
        assertEquals((long)t.get(1), 2);
        assertEquals((long)t.get(2), 3);
    }

    @Test
    public void checkRelatedCustomer(){
        assertEquals(rp.getRelatedCustomer(c1.id,2), c2.id);
        assertEquals(rp.getRelatedCustomer(c4.id,4), c6.id);
        assertEquals(rp.getRelatedCustomer(c3.id,3), c1.id);
        assertEquals(rp.getRelatedCustomer(c3.id,4), c4.id);
        //customer id not found
        assertEquals(rp.getRelatedCustomer(7,1), -1);
    }
}
