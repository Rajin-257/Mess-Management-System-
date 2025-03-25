import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Addbazar extends JFrame{
    JTextField blist, bcost;
    JComboBox<String> choose;
    
    Addbazar(){
        setTitle("Add Bazar List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/Bazar.png"));
        Image i2 = i1.getImage().getScaledInstance(240, 230, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(335, -10, 240, 250);
        add(l1);

        JLabel lDate = new JLabel("Date:-");
        lDate.setBounds(30, 40, 35, 30);
        add(lDate);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fdate = sdf.format(date);
        JLabel datee = new JLabel(fdate);
        datee.setBounds(65, 40, 70, 30);
        add(datee);

        JLabel lname = new JLabel("Name:- ");
        lname.setBounds(150, 40, 60, 30);
        add(lname);

        String[] sname = Data.getnames();
        choose = new JComboBox<>(sname);
        choose.setBounds(200, 40, 120, 30);
        add(choose);

        JLabel lbazar = new JLabel("Bazar List:-");
        lbazar.setBounds(30, 90, 110, 30);
        add(lbazar);

        blist = new JTextField();
        blist.setBounds(120, 90, 230, 25);
        add(blist);

        JLabel lbalance = new JLabel("Balance:-");
        lbalance.setBounds(30, 130, 60, 30);
        add(lbalance);

        bcost = new JTextField();
        bcost.setBounds(120, 135, 90, 25);
        add(bcost);

        JButton ibtn = new JButton("Submit");
        ibtn.setBounds(230, 170, 100, 25);
        ibtn.setFocusable(false);
        ibtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sname = choose.getSelectedItem() != null ? choose.getSelectedItem().toString() : "";
                String list = blist.getText();
                if (!list.isEmpty() && !sname.isEmpty()) {
                    try {
                        double cost = Double.parseDouble(bcost.getText());
                        Data bazar = new Data(fdate, sname, list, cost);
                        
                        if (Data.addBazar(bazar)) {
                            blist.setText("");
                            bcost.setText("");
                            JOptionPane.showMessageDialog(null, "Successfully Added", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add bazar entry", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Addbazar.this, "Please enter a valid cost", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Addbazar.this, "Please select a valid name Or BazarList", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(ibtn);

        JButton itbn2 = new JButton("Back");
        itbn2.setBounds(120, 170, 100, 25);
        itbn2.setFocusable(false);
        itbn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Dashboard();
            }
        });
        add(itbn2);

        setVisible(true);
    }
}