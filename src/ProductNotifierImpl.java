import java.util.*;

/**
 * Created by swvenu on 07-04-2016.
 */
public class ProductNotifierImpl implements ProductNotifier {
    //Each product contains two queue - one for normal users and another for prime
    //Each queue object is a HashMap - key is Customer object and value is date object
    //With this implementation - getCustomersToNotify runs in O(numCustomersToBeNotified) or O(total notifies for a product)
    // whichever is lesser
    private HashMap<Long, ArrayList<LinkedHashMap<Customer, Date>>> productMap = new HashMap<Long, ArrayList<LinkedHashMap<Customer, Date>>>();

    @Override
    public ArrayList<Long> getCustomersToNotify(long productId, int scheme, int numCustomersToBeNotified) throws IllegalArgumentException{
        ArrayList<Long> out = new ArrayList<Long>();
        Customer c = null;
        ArrayList<Map.Entry<Customer,Date>> t1,t2;
        Map.Entry<Customer,Date> entry;
        ArrayList<LinkedHashMap<Customer, Date>> t = productMap.get(productId);
        if(t == null)
            return out; //return empty list if product id is not found

        LinkedHashMap<Customer, Date> normalQueue = t.get(0);
        LinkedHashMap<Customer, Date> primeQueue = t.get(1);
        t1 = new ArrayList<Map.Entry<Customer,Date>>(normalQueue.entrySet());
        t2 = new ArrayList<Map.Entry<Customer,Date>>(primeQueue.entrySet());

        if(scheme == SCHEME_FIFO){
            if(productMap.containsKey(productId)) {
                int i = 0, j = 0, k = 0;
                while(k < numCustomersToBeNotified && i < t1.size() && j < t2.size()) {
                    //compare dates and customer who date is lesser
                    if(t1.get(i).getValue().before(t2.get(j).getValue()))
                        out.add(t1.get(i++).getKey().id);
                    else
                        out.add(t2.get(j++).getKey().id);
                    ++k;
                }
                //add remaining
                while(k < numCustomersToBeNotified && i < t1.size()) {
                    out.add(t1.get(i++).getKey().id);
                    ++k;
                }

                while(k < numCustomersToBeNotified && j < t2.size()) {
                    out.add(t2.get(j++).getKey().id);
                    ++k;
                }
            }
        }else if(scheme == SCHEME_PRIME_FIRST){
            if(productMap.containsKey(productId)) {
                int i = 0, j = 0, k = 0;
                //add all prime first from prime queue

                while(k < numCustomersToBeNotified && i < t2.size()){
                    entry = (Map.Entry<Customer,Date>) t2.get(i++);
                    c = entry.getKey();
                    out.add(c.id);
                    ++k;
                }
                //add remaining from  normal Queue
                while(k < numCustomersToBeNotified && j < t1.size()){
                    entry = (Map.Entry<Customer,Date>) t1.get(j++);
                    c = entry.getKey();
                    out.add(c.id);
                    ++k;
                }
            }
        }else{
            throw new IllegalArgumentException("scheme value is illegal");
        }
        return out;
    }

    @Override
    public void notifyMe(long productId, Customer customer) {
        ArrayList<LinkedHashMap<Customer, Date>> t;
        if(!productMap.containsKey(productId)){
            t = new ArrayList<LinkedHashMap<Customer, Date>>();
            t.add(new LinkedHashMap<Customer, Date>()); //queue for normal consumers
            t.add(new LinkedHashMap<Customer, Date>()); //queue for prime consumers
            productMap.put(productId, t);
        }

        t = productMap.get(productId);
        if(customer.isPrime)
            t.get(1).put(customer, new Date());
        else
            t.get(0).put(customer, new Date());
    }

}
