import java.util.*;

/**
 * Created by swvenu on 08-04-2016.
 */
public class RelatedProductsImpl implements RelatedProducts {
    //Each customer's purchased products is stored in  HashSet
    HashMap<Long, HashSet<Long>> customerProductMap = new HashMap<Long, HashSet<Long>>();

    @Override
    public void registerPurchase(long customerID, long productId) {
        if(!customerProductMap.containsKey(customerID)){
            customerProductMap.put(customerID, new HashSet<Long>());
        }
        customerProductMap.get(customerID).add(productId);
    }

    @Override
    public ArrayList<Long> getRelatedProducts(long customerID, long productId, int numProducts) {
        //builds  a HashMap of productId -> total count.
        // when customer has bought productId, all other products he bought are added to HashMap
        // return top products according to the count
        ArrayList<Long> out = new ArrayList<Long>();
        HashMap<Long, Long> productCount = new HashMap<Long, Long>();

        HashSet<Long> t;
        for(long cid: customerProductMap.keySet()){
            t = customerProductMap.get(cid);
            if(cid != customerID && t.contains(productId)){
                addAll(productCount, t, productId);
            }
        }
        int k = 0;
        for(Map.Entry<Long, Long> entry  : entriesSortedByValues(productCount)){
            if(k++ < numProducts){
                out.add(entry.getKey());
            }
        }
        return out;
    }

    private void addAll(HashMap<Long, Long> productCount, HashSet<Long> set, long productId){
        long l;
        for(long t: set ){
            if(t == productId)
                continue;
            if(!productCount.containsKey(t)){
                productCount.put(t,(long)0);
            }
            l = (long)productCount.get(t);
            productCount.put(t, ++l);
        }
    }

    @Override
    public long getRelatedCustomer(long customerID, int productID) {
        //returns a customer who has highest similarity score
        //  Similarity score is defined as
        // score = (1.0 + len(intersection))/(1.0 + max(len(set1), len(set2))
        //customer returned should also have bought productID
        //if none matches it returns -1

        HashSet<Long> cp = customerProductMap.get(customerID);
        HashSet<Long> t;
        HashSet<Long> intersection;
        long relatedCustomer = -1;
        double max = 0.0;
        double similarityScore = 0.0;

        if(cp == null)
            return relatedCustomer;

        for(long cid: customerProductMap.keySet()) {
            if(cid == customerID)
                continue;

            t = customerProductMap.get(cid);
            if(t.contains((long)productID)){
                intersection = new HashSet<Long>(cp);
                intersection.retainAll(t);
                similarityScore = (1.0 + intersection.size())/(1.0 + Math.max(cp.size(),t.size()));
                if(similarityScore > max){
                    max = similarityScore;
                    relatedCustomer = cid;
                }
            }
        }
        return relatedCustomer;
    }

    private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1; // Special fix to preserve items with equal values
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
