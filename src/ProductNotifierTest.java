import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;
/**
 * Created by swvenu on 08-04-2016.
 */
public class ProductNotifierTest {
    ProductNotifier pn;
    Customer c1, c2, c3, c4;

    @Before
    public void setUp() throws Exception {
        pn = new ProductNotifierImpl();
        c1 = new Customer("c1",false);
        c2 = new Customer("c2",false);
        c3 = new Customer("c3",false);
        c4 = new Customer("c4",true);

        pn.notifyMe(1,c1);
        pn.notifyMe(1,c1);
        pn.notifyMe(1,c2);
        pn.notifyMe(1,c4);
        pn.notifyMe(2,c1);
        pn.notifyMe(2,c2);
        pn.notifyMe(2,c3);
        pn.notifyMe(2,c4);
        pn.notifyMe(3,c1);
        pn.notifyMe(3,c2);
        pn.notifyMe(3,c2);
        pn.notifyMe(3,c4);
        pn.notifyMe(4,c1);
        pn.notifyMe(4,c2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void illegalSchemeValueTest()
    {
        pn.getCustomersToNotify(2, 3, 10);
    }

    @Test
    public void noProductIdFound(){
        //returns empty list
        assertEquals(pn.getCustomersToNotify(10000, 1, 3).size(), 0);
    }

    @Test
    public void checkReturnListSize(){
        //check number of customers to be notified returned
        assertEquals(pn.getCustomersToNotify(1, 0, 3).size(), 3);
        assertEquals(pn.getCustomersToNotify(1, 0, 5).size(), 3);
        assertEquals(pn.getCustomersToNotify(1, 1, 3).size(), 3);
        assertEquals(pn.getCustomersToNotify(1, 1, 5).size(), 3);
        assertEquals(pn.getCustomersToNotify(2, 1, 3).size(), 3);
        assertEquals(pn.getCustomersToNotify(2, 1, 4).size(), 4);

    }

    public void checkFIFO(){
        ArrayList<Long> t = pn.getCustomersToNotify(2, 0, 4);
        assertEquals((long)t.get(0), c1.id);
        assertEquals((long)t.get(1), c2.id);
        assertEquals((long)t.get(2), c3.id);
        assertEquals((long)t.get(3), c4.id);
    }

    public void checkPrimeFirst(){
        ArrayList<Long> t = pn.getCustomersToNotify(2, 1, 4);
        assertEquals((long)t.get(0), c4.id);
        assertEquals((long)t.get(1), c1.id);
        assertEquals((long)t.get(2), c2.id);
        assertEquals((long)t.get(3), c3.id);
    }
}
