import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class personlist extends JFrame {
    JTextField searchField;
    JTable table;
    DefaultTableModel model;
    ArrayList<Data> personData;

    personlist() {
        setTitle("Person List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        JLabel searchLabel = new JLabel("Search by Name:");
        searchLabel.setBounds(10, 10, 120, 25);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(140, 10, 150, 25);
        add(searchField);

        JButton btn1 = new JButton("Search");
        btn1.setBounds(300, 10, 80, 25);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAndDisplayData();
            }
        });
        add(btn1);

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 40, 570, 180);
        add(scrollPane);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Phone");
        model.addColumn("Room Rent");

        personData = Data.getAllPersons();
        refreshTable();

        JButton btn2 = new JButton("Edit");
        btn2.setBounds(100, 230, 80, 25);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editData();
            }
        });
        add(btn2);

        JButton btn3 = new JButton("Delete");
        btn3.setBounds(200, 230, 80, 25);
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });
        add(btn3);

        JButton btn4 = new JButton("Back");
        btn4.setBounds(300, 230, 80, 25);
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Dashboard();
            }
        });
        add(btn4);

        setVisible(true);
    }

    private void searchAndDisplayData() {
        String searchName = searchField.getText().trim();
        model.setRowCount(0);

        for (Data person : personData) {
            if (person.getName().toLowerCase().contains(searchName.toLowerCase())) {
                model.addRow(new Object[]{person.getId(), person.getName(), person.getPhn(), person.getRent()});
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No matching data found for the given name.");
        }
    }

    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table.getValueAt(selectedRow, 0);
            
            for (Data person : personData) {
                if (person.getId() == id) {
                    editDataDialog(person);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        }
    }

    private void editDataDialog(Data person) {
        JTextField newNameField = new JTextField(person.getName());
        JTextField newPhoneField = new JTextField(person.getPhn());
        JTextField newRentField = new JTextField(String.valueOf(person.getRent()));

        Object[] message = {
                "Name:", newNameField,
                "Phone:", newPhoneField,
                "Room Rent:", newRentField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Data", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                person.setName(newNameField.getText());
                person.setPhn(newPhoneField.getText());
                person.setRent(Integer.parseInt(newRentField.getText()));
                
                if (Data.updatePerson(person)) {
                    personData = Data.getAllPersons();
                    refreshTable();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for Room Rent. Please enter a valid integer.");
            }
        }
    }

    private void deleteData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this person? This will also delete all associated meal and bazar records.",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                if (Data.deletePerson(id)) {
                    personData = Data.getAllPersons();
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Person deleted successfully.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Data person : personData) {
            model.addRow(new Object[]{person.getId(), person.getName(), person.getPhn(), person.getRent()});
        }
    }
}