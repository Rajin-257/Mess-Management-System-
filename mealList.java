import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class mealList extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Data> meals;

    mealList() {
        setTitle("Meal List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        String[] cNames = {"ID", "Date", "Name", "Meal Count", "Total Meal by Name"};

        model = new DefaultTableModel(cNames, 0);
        meals = Data.getAllMeals();

        for (Data data : meals) {
            Object[] fData = {
                    data.getId(),
                    data.getDate(),
                    data.getName(),
                    data.getMealCount(),
                    Data.totalmealbyname(data.getName()) 
            };
            model.addRow(fData);
        }
        
        // Add total meal row
        Object[] tmeal = {"", "", "Total Meal", Data.totalMeal(), ""};
        model.addRow(tmeal);

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
                    ArrayList<Data> filteredMeals = Data.getMealsByDate(searchText);
                    updateTableWithFilteredData(filteredMeals);
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
                if (selectedRow != -1 && selectedRow < meals.size()) {
                    int id = meals.get(selectedRow).getId();
                    if (Data.deleteMeal(id)) {
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
                if (selectedRow != -1 && selectedRow < meals.size()) {
                    update(selectedRow);
                }
            }
        });
        add(btn4);

        setVisible(true);
    }

    private void refreshTable() {
        meals = Data.getAllMeals();
        updateTableWithFilteredData(meals);
    }
    
    private void updateTableWithFilteredData(ArrayList<Data> filteredData) {
        model.setRowCount(0);
        
        for (Data data : filteredData) {
            Object[] fData = {
                    data.getId(),
                    data.getDate(),
                    data.getName(),
                    data.getMealCount(),
                    Data.totalmealbyname(data.getName())
            };
            model.addRow(fData);
        }
        
        // Add total row
        double totalMeal = 0;
        if (filteredData.size() > 0) {
            for (Data data : filteredData) {
                totalMeal += data.getMealCount();
            }
        } else {
            totalMeal = Data.totalMeal();
        }
        
        Object[] tmeal = {"", "", "Total Meal", totalMeal, ""};
        model.addRow(tmeal);
    }

    private void update(int selectedRow) {
        Data selectedMeal = meals.get(selectedRow);
        
        JTextField dateField = new JTextField(selectedMeal.getDate());
        
        // Create name dropdown with current name selected
        String[] allNames = Data.getnames();
        JComboBox<String> nameCombo = new JComboBox<>(allNames);
        nameCombo.setSelectedItem(selectedMeal.getName());
        
        JTextField mealCountField = new JTextField(Double.toString(selectedMeal.getMealCount()));

        Object[] message = {
                "Date:", dateField,
                "Name:", nameCombo,
                "Meal Count:", mealCountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Meal Data", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String date = dateField.getText();
                String name = nameCombo.getSelectedItem().toString();
                double mealCount = Double.parseDouble(mealCountField.getText());

                Data updatedMeal = new Data(selectedMeal.getId(), date, name, mealCount);

                if (Data.updateMeal(updatedMeal)) {
                    refreshTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for meal count", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}