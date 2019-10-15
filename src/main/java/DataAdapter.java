public interface DataAdapter {

    public static final int CONNECTION_OPEN_OK = 100;
    public static final int CONNECTION_OPEN_FAILED = 101;

    public static final int CONNECTION_CLOSE_OK = 200;
    public static final int CONNECTION_CLOSE_FAILED = 201;

    public static final int PRODUCT_SAVED_OK = 0;
    public static final int PRODUCT_DUPLICATE_ERROR = 1;
    public static final int PRODUCT_UPDATE_OK = 0;
    public static final int PRODUCT_UPDATE_ERROR = 1;

    public static final int CUSTOMER_SAVED_OK = 0;
    public static final int CUSTOMER_DUPLICATE_ERROR = 1;

    public static final int PURCHASE_SAVED_OK = 0;
    public static final int PURCHASE_DUPLICATE_ERROR = 1;

    public int connect();
    public int disconnect();

    public Product loadProduct(int id);
    public int saveProduct(Product model);

    public Customer loadCustomer(int id);
    public int saveCustomer(Customer model);

    public int loadPurchase(int id);
    public int savePurchase(Purchase purchase);
}
