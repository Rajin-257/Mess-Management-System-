import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bazarlist extends JFrame {

    private DefaultTableModel model;
    private JTable table;

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

        String[] cNames = {"Date","Name", "Bazar List", "Cost"};

        model = new DefaultTableModel(cNames, 0);

        for (Data data : Data.bdata) {
            Object[] fData = {
                    data.getDate(),
                    data.getName(),
                    data.getList(),
                    data.getCost()
            };
            model.addRow(fData);
        }
        Object[] tbcost={" "," ","Total Cost",Data.totalBazarcost()};
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
                DefaultTableModel fModel = new DefaultTableModel(new String[]{"Date", "Name", "Bazar List", "Cost"}, 0);

                for (Data data : Data.bdata) {
                    if (data.getDate().equals(searchText)) {
                        Object[] fData = {
                                data.getDate(),
                                data.getName(),
                                data.getList(),
                                data.getCost()
                        };
                        fModel.addRow(fData);
                    }
                }
                table.setModel(fModel);
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
                    Data.bdata.remove(SelectRow);
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

    private void refreshTable() {
        model.setRowCount(0);
        for (Data data : Data.bdata) {
            Object[] fData = {
                    data.getDate(),
                    data.getName(),
                    data.getList(),
                    data.getCost()
            };
            model.addRow(fData);
        }
        table.setModel(model);
    }

    private void update(int SelectRow) {
        JTextField dateField = new JTextField(Data.bdata.get(SelectRow).getDate());
        JTextField nameField = new JTextField(Data.bdata.get(SelectRow).getName());
        JTextField listField = new JTextField(Data.bdata.get(SelectRow).getList());
        JTextField costField = new JTextField(Double.toString(Data.bdata.get(SelectRow).getCost()));

        Object[] message = {
                "Date:", dateField,
                "Name:", nameField,
                "Bazar List:", listField,
                "Cost:", costField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Bazar Data", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String name = nameField.getText();
            String bazarList = listField.getText();
            double cost = Double.parseDouble(costField.getText());

            Data obj = new Data(date,name, bazarList, cost);

          
            Data.bdata.set(SelectRow, obj);
        } else {
            JOptionPane.showMessageDialog(null, "Update canceled by user.", "Update Canceled", JOptionPane.WARNING_MESSAGE);
        }
    }
}
