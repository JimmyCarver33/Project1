import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataAdapter implements DataAdapter {

    double taxRate = 0.07;

    Connection conn = null;

    public int connect() {
        try {
            // db parameters
            String url = "jdbc:mysql://localhost:3306/store";
            // create a connection to the database
            conn = DriverManager.getConnection(url, "root", "");

            System.out.println("MySQL Database connected");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public Product loadProduct(int productID) {
        Product product = new Product();

        try {
            String sql = "SELECT ProductID, Name, Price, Quantity FROM Products WHERE ProductID = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.productID = rs.getInt("ProductID");
            product.name = rs.getString("Name");
            product.price = rs.getDouble("Price");
            product.quantity = rs.getDouble("Quantity");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public int saveProduct(Product product) {
        try {
            String sql = "INSERT INTO Products(ProductID, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }

        return PRODUCT_SAVED_OK;
    }

    public int updateProductQTY(int qtyPurchased, int productID)
    {
        try {
            String getCurrentQTY = "SELECT Quantity FROM Products WHERE ProductID = " + productID;
            Statement getQTY = conn.createStatement();
            ResultSet qtyQuery = getQTY.executeQuery(getCurrentQTY);
            int currentQTY = qtyQuery.getInt("Quantity");
            int qtyDeduction = currentQTY - qtyPurchased;

            String sql = "UPDATE Products SET Quantity = " + qtyPurchased + " WHERE ProductID = " + productID;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("Update QTY failed"))
                return PRODUCT_UPDATE_ERROR;
        }

        return PRODUCT_UPDATE_OK;
    }

    public int saveCustomer(Customer cust) {
        try {
            String sql = "INSERT INTO Customers(CustomerID, LastName, FirstName, Phone) VALUES " + cust;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;
        }

        return CUSTOMER_SAVED_OK;
    }

    public Customer loadCustomer(int id) {
        Customer cust = new Customer();

        try {
            String sql = "SELECT CustomerID, FirstName, LastName, Phone FROM Customers WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            cust.customerID = rs.getInt("CustomerID");
            cust.lastName = rs.getString("LastName");
            cust.firstName = rs.getString("FirstName");
            cust.phoneNum = rs.getInt("Phone");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cust;
    }

    public int savePurchase(Purchase purchase)
    {
        double subTot = generateReceipt(purchase.productID, purchase.quantity);
        double tax =  subTot * taxRate;
        double tot = subTot + tax;

        purchase.setTotal(tot);

        try {

            String sql = "INSERT INTO Purchases(PurchaseID, CustomerID, ProductID, Quantity, Total) VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            updateProductQTY(purchase.quantity, purchase.productID);

        } catch (Exception e) {

            String msg = e.getMessage();

            System.out.println(msg);

            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;

        }


        Customer cust = loadCustomer(purchase.customerID);

        System.out.println("\nPurchasing Customer: " + cust.firstName + " " + cust.lastName);
        System.out.println("Subtotal: " + subTot + "\n" + "Tax: " + tax + "\n" + "Total: " + tot + "\n");
        return PURCHASE_SAVED_OK;
    }

    //returns subtotal
    private double generateReceipt(int ProductID, int qty)
    {
        Product prod = loadProduct(ProductID);
        return qty * prod.price;
    }

    public int loadPurchase(int id) {
        return 0;
    }
}
