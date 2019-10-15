import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StoreManager {

    DataAdapter adapter = null;
    private static StoreManager instance = null;

    public static StoreManager getInstance() {
        if (instance == null)
            instance = new StoreManager("SQLite");
        return instance;
    }

    private StoreManager(String DBname) {
        if (DBname.equals("MySQL"))
            adapter = new MySQLDataAdapter();
        if (DBname.equals("SQLite"))
            adapter = new SQLiteDataAdapter();

        adapter.connect();
    }

    public DataAdapter getDataAdapter() {
        return adapter;
    }

    public void setAdapter(DataAdapter a) {
        adapter = a;
    }

    public static void main(String[] args) {

        getInstance();

        AddProductUI apView = new AddProductUI();
        apView.run();

		AddCustomerUI custView = new AddCustomerUI();
        custView.run();

        AddPurchaseUI purView = new AddPurchaseUI();
        purView.run();

    }
}
