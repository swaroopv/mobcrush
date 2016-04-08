/**
 * Created by swvenu on 07-04-2016.
 */
class Customer {
    private static int idCounter = 0;
    long id;
    boolean isPrime;
    String name;  // use to maintain your own customer data (if needed)

    Customer(String name, boolean isPrime) throws Exception {
        if(idCounter == Long.MAX_VALUE)
            throw new Exception("customer id overflow");
        id = ++idCounter;
        this.isPrime = isPrime;
    }

    public int hashCode()
    {
        return Long.hashCode(id);//java 8 feature
    }

    public boolean equals( Object obj )
    {
        Customer c = ( Customer )obj;
        return c.id == id ;
    }
};

