import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Addperson extends JFrame{
    JTextField tname, tphn, trent;
    
    Addperson(){
        setTitle("Add Person List");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/Person.png"));
        Image i2 = i1.getImage().getScaledInstance(280, 250, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(300, -10, 300, 250);
        add(l1);

        JLabel lname = new JLabel("Name");
        lname.setBounds(30, 60, 60, 30);
        add(lname);

        tname = new JTextField();
        tname.setBounds(110, 60, 180, 25);
        add(tname);

        JLabel lphn = new JLabel("Phone No.");
        lphn.setBounds(30, 90, 60, 30);
        add(lphn);

        tphn = new JTextField();
        tphn.setBounds(110, 90, 180, 25);
        add(tphn);

        JLabel lrent = new JLabel("Room rent");
        lrent.setBounds(30, 120, 60, 30);
        add(lrent);

        trent = new JTextField();
        trent.setBounds(110, 120, 180, 25);
        add(trent);

        JButton btn1 = new JButton("Submit");
        btn1.setBounds(210, 160, 80, 25);
        btn1.setFocusable(false);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sname = tname.getText();
                String sphn = tphn.getText();
        
                if (!sname.isEmpty() && !sphn.isEmpty()) {
                    try {
                        int rent = Integer.parseInt(trent.getText());
                        Data person = new Data(sname, sphn, rent);
                        
                        if (Data.addPerson(person)) {
                            tname.setText("");
                            tphn.setText("");
                            trent.setText("");
                            JOptionPane.showMessageDialog(null, "Successfully Added", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add person", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Addperson.this, "Please enter a valid integer for Room rent", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Addperson.this, "Please enter all information", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btn1);

        JButton btn3 = new JButton("Back");
        btn3.setBounds(120, 160, 80, 25);
        btn3.setFocusable(false);
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Dashboard(); 
            }
        });
        add(btn3);

        setVisible(true);
    }
}