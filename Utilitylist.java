import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Utilitylist extends JFrame {
    DefaultTableModel model;
    ArrayList<Data> utilities;
    
    Utilitylist() {
        setTitle("Utility List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        String[] cnames = {"Utility Name", "Cost"};
        model = new DefaultTableModel(cnames, 0);
        
        utilities = Data.getAllUtilities();
        
        if (!utilities.isEmpty()) {
            Data utility = utilities.get(0);
            model.addRow(new Object[]{"House Utility Fee", utility.getHu()});
            model.addRow(new Object[]{"Maid Bill", utility.getMb()});
            model.addRow(new Object[]{"Electricity Bill", utility.getEb()});
            model.addRow(new Object[]{"Clean Bill", utility.getCb()});
            model.addRow(new Object[]{"Wifi Bill", utility.getWb()});
            
            // Add total row
            int total = utility.getHu() + utility.getMb() + utility.getEb() + utility.getCb() + utility.getWb();
            model.addRow(new Object[]{"Total", total});
        } else {
            JOptionPane.showMessageDialog(this, "No utility data found. Please add utility costs first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Dashboard();
            return;
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 20, 560, 180);
        add(scrollPane);

        JButton editBtn = new JButton("Edit");
        editBtn.setBounds(330, 225, 100, 30);
        editBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!utilities.isEmpty()) {
                    editUtility(utilities.get(0));
                }
            }
        });
        add(editBtn);

        JButton btn1 = new JButton("Back");
        btn1.setBounds(470, 225, 100, 30);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Dashboard();
            }
        });
        add(btn1);

        setVisible(true);
    }
    
    private void editUtility(Data utility) {
        JTextField huField = new JTextField(String.valueOf(utility.getHu()));
        JTextField mbField = new JTextField(String.valueOf(utility.getMb()));
        JTextField ebField = new JTextField(String.valueOf(utility.getEb()));
        JTextField cbField = new JTextField(String.valueOf(utility.getCb()));
        JTextField wbField = new JTextField(String.valueOf(utility.getWb()));
        
        Object[] message = {
            "House Utility:", huField,
            "Maid Bill:", mbField,
            "Electricity Bill:", ebField,
            "Clean Bill:", cbField,
            "Wifi Bill:", wbField
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Utility Data", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                int huValue = Integer.parseInt(huField.getText());
                int mbValue = Integer.parseInt(mbField.getText());
                int ebValue = Integer.parseInt(ebField.getText());
                int cbValue = Integer.parseInt(cbField.getText());
                int wbValue = Integer.parseInt(wbField.getText());
                
                utility.setHu(huValue);
                utility.setMb(mbValue);
                utility.setEb(ebValue);
                utility.setCb(cbValue);
                utility.setWb(wbValue);
                
                if (Data.updateUtility(utility)) {
                    // Refresh the data
                    utilities = Data.getAllUtilities();
                    refreshTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        
        if (!utilities.isEmpty()) {
            Data utility = utilities.get(0);
            model.addRow(new Object[]{"House Utility Fee", utility.getHu()});
            model.addRow(new Object[]{"Maid Bill", utility.getMb()});
            model.addRow(new Object[]{"Electricity Bill", utility.getEb()});
            model.addRow(new Object[]{"Clean Bill", utility.getCb()});
            model.addRow(new Object[]{"Wifi Bill", utility.getWb()});
            
            // Add total row
            int total = utility.getHu() + utility.getMb() + utility.getEb() + utility.getCb() + utility.getWb();
            model.addRow(new Object[]{"Total", total});
        }
    }
}