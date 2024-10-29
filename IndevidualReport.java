import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class IndevidualReport extends JFrame {
    IndevidualReport() {
        setTitle("Total Cost List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        Object[] columnNames = {"Name", "Rent", "Utility", "Total Meal", "P/M Cost", "Meal Cost", "Total Cost"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 10, 560, 210);
        add(scrollPane);

        JButton btn = new JButton("Back");
        btn.setBounds(470, 225, 100, 30);
        btn.setFocusable(false);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
                new Dashboard();
            }
        });
        add(btn);
        
        Data.CTotalCost();
        for (int i = Data.tcost.size()-Data.pData.size(); i < Data.tcost.size(); i++) {
            Data.TotalCost totalCost = Data.tcost.get(i);
        
            Object[] rowData = {
                    totalCost.name,
                    totalCost.rentCost,
                    totalCost.utilityCostPerPerson,
                    totalCost.totalmeal,
                    totalCost.permeal,
                    totalCost.totalMealCost,
                    totalCost.totalCost
            };
        
            tableModel.addRow(rowData);
        }

        setVisible(true);
    }

}
