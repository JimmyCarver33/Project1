import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPurchaseUI {
    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtPurchaseID = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtProductID = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public AddPurchaseUI()   {

        this.view = new JFrame();

        view.setTitle("Add Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("CustomerID "));
        line2.add(txtCustomerID);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("ProductID "));
        line3.add(txtProductID);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());
        btnCancel.addActionListener(new CancelButtonListener()  {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Cancelled");
            }
        });

    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Purchase purchase = new Purchase();

            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null purchase ID");
                return;
            }

            try {
                purchase.purchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid purchase ID");
                return;
            }

            String customerID = txtCustomerID.getText();

            if (customerID.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null customer ID");
                return;
            }

            try {
                purchase.customerID = Integer.parseInt(customerID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid customer ID");
                return;
            }

            String productID = txtProductID.getText();

            if (productID.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null product ID");
                return;
            }

            try {
                purchase.productID = Integer.parseInt(productID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid product ID");
                return;
            }

            String qty = txtQuantity.getText();

            if (qty.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null quantity");
                return;
            }

            try {
                purchase.quantity = Integer.parseInt(qty);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid quantity");
                return;
            }

            switch (StoreManager.getInstance().getDataAdapter().savePurchase(purchase)) {
                case DataAdapter.PRODUCT_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Purchase NOT added, purchaseID already exists");
                default:
                    JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
            }
        }
    }

    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }
}
