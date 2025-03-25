import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Bazarlist extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Data> bazars;

    Bazarlist() {
        setTitle("Bazar List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        String[] cNames = {"ID", "Date", "Name", "Bazar List", "Cost"};

        model = new DefaultTableModel(cNames, 0);
        bazars = Data.getAllBazar();

        for (Data data : bazars) {
            Object[] fData = {
                    data.getId(),
                    data.getDate(),
                    data.getName(),
                    data.getList(),
                    data.getCost()
            };
            model.addRow(fData);
        }
        
        // Add total row
        Object[] tbcost = {"", "", "", "Total Cost", Data.totalBazarcost()};
        model.addRow(tbcost);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 40, 570, 180);
        add(scrollPane);

        JLabel search = new JLabel("Search Date:");
        search.setBounds(280, 10, 80, 25);
        add(search);

        JTextField searchField = new JTextField();
        searchField.setBounds(360, 10, 100, 25);
        add(searchField);

        JButton btn1 = new JButton("Search");
        btn1.setBounds(470, 10, 80, 25);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    ArrayList<Data> filteredBazars = Data.getBazarByDate(searchText);
                    updateTableWithFilteredData(filteredBazars);
                } else {
                    refreshTable();
                }
            }
        });
        add(btn1);

        JButton btn2 = new JButton("Back");
        btn2.setBounds(10, 10, 80, 25);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Dashboard();
            }
        });
        add(btn2);

        JButton btn3 = new JButton("Delete");
        btn3.setBounds(100, 10, 80, 25);
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1 && selectedRow < bazars.size()) {
                    int id = bazars.get(selectedRow).getId();
                    if (Data.deleteBazar(id)) {
                        refreshTable();
                    }
                }
            }
        });
        add(btn3);

        JButton btn4 = new JButton("Edit");
        btn4.setBounds(190, 10, 80, 25);
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1 && selectedRow < bazars.size()) {
                    update(selectedRow);
                }
            }
        });
        add(btn4);

        setVisible(true);
    }

    private void refreshTable() {
        bazars = Data.getAllBazar();
        updateTableWithFilteredData(bazars);
    }
    
    private void updateTableWithFilteredData(ArrayList<Data> filteredData) {
        model.setRowCount(0);
        
        for (Data data : filteredData) {
            Object[] fData = {
                    data.getId(),
                    data.getDate(),
                    data.getName(),
                    data.getList(),
                    data.getCost()
            };
            model.addRow(fData);
        }
        
        // Add total row - if filtering by date, show total for that date, otherwise show all
        double totalCost = 0;
        if (filteredData.size() > 0) {
            for (Data data : filteredData) {
                totalCost += data.getCost();
            }
        } else {
            totalCost = Data.totalBazarcost();
        }
        
        Object[] tbcost = {"", "", "", "Total Cost", totalCost};
        model.addRow(tbcost);
    }

    private void update(int selectedRow) {
        Data selectedBazar = bazars.get(selectedRow);
        
        JTextField dateField = new JTextField(selectedBazar.getDate());
        
        // Create name dropdown with current name selected
        String[] allNames = Data.getnames();
        JComboBox<String> nameCombo = new JComboBox<>(allNames);
        nameCombo.setSelectedItem(selectedBazar.getName());
        
        JTextField listField = new JTextField(selectedBazar.getList());
        JTextField costField = new JTextField(Double.toString(selectedBazar.getCost()));

        Object[] message = {
                "Date:", dateField,
                "Name:", nameCombo,
                "Bazar List:", listField,
                "Cost:", costField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Bazar Data", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String date = dateField.getText();
                String name = nameCombo.getSelectedItem().toString();
                String bazarList = listField.getText();
                double cost = Double.parseDouble(costField.getText());

                Data updatedBazar = new Data(selectedBazar.getId(), date, name, bazarList, cost);

                if (Data.updateBazar(updatedBazar)) {
                    refreshTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for cost", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}