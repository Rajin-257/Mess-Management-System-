import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mealList extends JFrame {

    private DefaultTableModel model;
    private JTable table;

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

        String[] cNames = {"Date", "Name", "Meal Count", "Total Meal by Name"};

        model = new DefaultTableModel(cNames, 0);

        for (Data data : Data.mData) {
            Object[] fData = {
                    data.getDate(),
                    data.getName(),
                    data.getMealCount(),
                    Data.totalmealbyname(data.getName()) 
            };
            model.addRow(fData);
        }
        Object[] tmeal={" ","Total Meal",Data.totalMeal()};
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
                DefaultTableModel fModel = new DefaultTableModel(new String[]{"Date", "Name", "Meal Count"}, 0);

                for (Data data : Data.mData) {
                    if (data.getDate().equals(searchText)) {
                        Object[] fData = {
                                data.getDate(),
                                data.getName(),
                                data.getMealCount()
                        };
                        fModel.addRow(fData);
                    }
                }
                table.setModel(fModel);
                Object[] tmeal={" ","Total Meal",Data.totalMeal()};
                model.addRow(tmeal);
                
            }
        });
        add(btn1);

        JButton btn2 = new JButton("Back");
        btn2.setBounds(10, 10, 80, 25);
        add(btn2);

        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Dashboard();
            }
        });

        JButton btn3 = new JButton("Delete");
        btn3.setBounds(100, 10, 80, 25);
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int SelectRow = table.getSelectedRow();
                if (SelectRow != -1) {
                    Data.mData.remove(SelectRow);
                    refreshTable();
                }
            }
        });
        add(btn3);

        JButton btn4 = new JButton("Edit");
        btn4.setBounds(190, 10, 80, 25);
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int SelectRow = table.getSelectedRow();
                if (SelectRow != -1) {
                    update(SelectRow);
                    refreshTable();
                }
            }
        });
        add(btn4);

        setVisible(true);
    }

    private void update(int SelectRow) {
        JTextField dateField = new JTextField(Data.mData.get(SelectRow).getDate());
        JTextField nameField = new JTextField(Data.mData.get(SelectRow).getName());
        JTextField mealCountField = new JTextField(Double.toString(Data.mData.get(SelectRow).getMealCount()));

        Object[] message = {
                "Date:", dateField,
                "Name:", nameField,
                "Meal Count:", mealCountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Meal Data", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String name = nameField.getText();
            double mealCount = Double.parseDouble(mealCountField.getText());

            Data obj = new Data(date, name, mealCount);
            Data.mData.set(SelectRow, obj);
        } else {
            JOptionPane.showMessageDialog(null, "Update canceled by user.", "Update Canceled", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void refreshTable() {
        model.setRowCount(0);
    
        for (Data data : Data.mData) {
            Object[] fData = {
                    data.getDate(),
                    data.getName(),
                    data.getMealCount()
            };
            model.addRow(fData);
        }
        
        table.setModel(model);
        Object[] tmeal={" ","Total Meal",Data.totalMeal()};
        model.addRow(tmeal);
        
    }

    public static void main(String[] args) {
        new mealList();
    }
}
