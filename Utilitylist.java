import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;


public class Utilitylist extends JFrame {
    DefaultTableModel model;
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
        if (!Data.utility.isEmpty()) {
            model.addRow(new Object[]{"House Utility Fee", Data.utility.get(0).getHu()});
            model.addRow(new Object[]{"Maid Bill", Data.utility.get(0).getMb()});
            model.addRow(new Object[]{"Electricity Bill", Data.utility.get(0).getEb()});
            model.addRow(new Object[]{"Clean Bill", Data.utility.get(0).getCb()});
            model.addRow(new Object[]{"Wifi Bill", Data.utility.get(0).getWb()});
        } else {
            JOptionPane.showMessageDialog(this,"Input Utility Cost First","Information", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,20,560,180);
        add(scrollPane);

        JButton btn1=new JButton("Back");
        btn1.setBounds(470, 225, 100, 30);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                new Dashboard();
            }
        }
        );
        add(btn1);


        setVisible(true);
    }
}
