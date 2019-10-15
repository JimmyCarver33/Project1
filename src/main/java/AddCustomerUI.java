
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {

    public JFrame view;
    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtLastName = new JTextField(20);
    public JTextField txtFirstName = new JTextField(20);
    public JTextField txtPhoneNum = new JTextField(20);

    public AddCustomerUI()   {

        this.view = new JFrame();

        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID "));
        line1.add(txtCustomerID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Last Name "));
        line2.add(txtLastName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("First Name "));
        line3.add(txtFirstName);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone Number "));
        line4.add(txtPhoneNum);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());

        btnCancel.addActionListener(new ActionListener() {
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
            Customer customer = new Customer();

            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null customer ID");
                return;
            }

            try {
                customer.customerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid customer ID");
                return;
            }

            String lastName = txtLastName.getText();
            if (lastName.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null customer last name");
                return;
            }

            customer.lastName = lastName;

            String firstName = txtFirstName.getText();
            if (firstName.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error. Null customer first name");
                return;
            }

            customer.firstName = firstName;

            String phoneNum = txtPhoneNum.getText();

            if (phoneNum.length() != 10) {
                JOptionPane.showMessageDialog(null, "Error. Invalid Phone Number");
                return;
            }

            try {
                customer.phoneNum = Integer.parseInt(phoneNum);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid phone number");
                return;
            }

            switch (StoreManager.getInstance().getDataAdapter().saveCustomer(customer)) {
                case DataAdapter.CUSTOMER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Customer NOT added. Duplicate exists");
                default:
                    JOptionPane.showMessageDialog(null, "Customer added" + customer);
            }
        }
    }
}

