import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Addmeal extends JFrame {
    String fdate;
    JComboBox<String> choose;
    JCheckBox cb1, cb2, cb3;
    JTextField txtra;
    double meal = 0;
    
    Addmeal() {
        setTitle("Add Meal List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/Meal.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(300, 10, 300, 250);
        add(l1);

        JLabel lDate = new JLabel("Date:- ");
        lDate.setBounds(30, 30, 60, 25);
        add(lDate);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fdate = sdf.format(date);
        JLabel datee = new JLabel(fdate);
        datee.setBounds(85, 28, 110, 30);
        add(datee);

        JLabel lname = new JLabel("Name:-");
        lname.setBounds(30, 55, 60, 30);
        add(lname);

        String names[] = Data.getnames();
        choose = new JComboBox<>(names);
        choose.setBounds(85, 60, 125, 25);
        add(choose);

        JLabel Meal = new JLabel("Meal:-");
        Meal.setBounds(30, 85, 40, 30);
        add(Meal);
        
        cb1 = new JCheckBox("BreakFast");
        cb1.setBounds(80, 85, 90, 30);
        cb1.setFocusable(false);
        add(cb1);

        cb2 = new JCheckBox("Lunch");
        cb2.setBounds(170, 85, 70, 30);
        cb2.setFocusable(false);
        add(cb2);

        cb3 = new JCheckBox("Diner");
        cb3.setBounds(240, 85, 110, 30);
        cb3.setFocusable(false);
        add(cb3);

        JLabel extra = new JLabel("Extra Meal");
        extra.setBounds(30, 115, 60, 30);
        add(extra);

        txtra = new JTextField("0");
        txtra.setBounds(110, 120, 80, 25);
        add(txtra);

        JButton btn = new JButton("Submit");
        btn.setBounds(160, 150, 120, 25);
        btn.setFocusable(false);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = choose.getSelectedItem() != null ? choose.getSelectedItem().toString() : "";
                boolean bf = cb1.isSelected();
                boolean lunch = cb2.isSelected();
                boolean dinner = cb3.isSelected();

                try {
                    double extraMeals = Double.parseDouble(txtra.getText());
                    int one = bf ? 1 : 0;
                    int two = lunch ? 1 : 0;
                    int three = dinner ? 1 : 0;
        
                    meal = one + two + three + extraMeals;
        
                    if (!name.isEmpty()) { 
                        Data mealData = new Data(fdate, name, meal);
                        
                        if (Data.addMeal(mealData)) {
                            JOptionPane.showMessageDialog(null, "Successfully Added", "Information", JOptionPane.INFORMATION_MESSAGE);
                            cb1.setSelected(false);
                            cb2.setSelected(false);
                            cb3.setSelected(false);
                            txtra.setText("0");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add meal entry", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(Addmeal.this, "Please select a valid name", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Addmeal.this, "Please enter a valid number for extra meals", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btn);

        JButton btn2 = new JButton("Back");
        btn2.setBounds(30, 150, 120, 25);
        btn2.setFocusable(false);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Dashboard();
            }
        });
        add(btn2);

        setVisible(true);
    }
}