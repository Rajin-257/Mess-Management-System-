import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class Dashboard extends JFrame {
    Dashboard()
    {
        setTitle("DashBoard");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("img/Dashboard.jpg"));
        Image i2=i1.getImage().getScaledInstance(600,300,Image.SCALE_SMOOTH);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(0,0,600,300);
        add(l1);

        JLabel j1=new JLabel("Hostel Management System");
        j1.setBounds(20,220,400,40);
        j1.setFont(new Font("Serif",Font.PLAIN,30));
        j1.setForeground(Color.white);
        l1.add(j1);

        JMenuBar mb=new JMenuBar();
        mb.setBounds(0,0,600,30);
        mb.setBackground(new Color(143, 188, 143));
        l1.add(mb);

        JMenu m1=new JMenu("Meal");
        mb.add(m1);

        JMenuItem mi1=new JMenuItem("Add Meal");
        mi1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new Addmeal();
            }
        });
        m1.add(mi1);

        JMenuItem mi2=new JMenuItem("Update Meal");
        mi2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                new mealList();
            }
        });
        m1.add(mi2);

        JMenu m2=new JMenu("Bazer");
        mb.add(m2);

        JMenuItem mi3=new JMenuItem("Add Bazer");
        mi3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                new Addbazar();
            }
        });
        m2.add(mi3);

        JMenuItem mi4=new JMenuItem("Update Bazer");
        mi4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Bazarlist();
            }
        });
        m2.add(mi4);

        JMenu m3=new JMenu("Person");
        mb.add(m3);

        JMenuItem mi5=new JMenuItem("Add Person");
        mi5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Addperson();
            }
        });
        m3.add(mi5);

        JMenuItem mi6=new JMenuItem("Update Person");
        mi6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new personlist();
            }
        });
        m3.add(mi6);

        JMenu m5=new JMenu("Utility");
        mb.add(m5);

        JMenuItem mi11=new JMenuItem("Add Utility");
        mi11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Utilitis();
            }
        });
        m5.add(mi11);

        JMenuItem mi12 = new JMenuItem("Update Utility");
        mi12.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                new Utilitylist();
            }
        });
        m5.add(mi12);

        JMenu m4=new JMenu("Report");
        mb.add(m4);

        JMenuItem mi7=new JMenuItem("Person Report");
        mi7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new personlist();
            }
        });
        m4.add(mi7);

        JMenuItem mi8=new JMenuItem("Bazar Report");
        mi8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Bazarlist();
            }
        });
        m4.add(mi8);

        JMenuItem mi9=new JMenuItem("Meal Report");
        mi9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new mealList();
            }
        });
        m4.add(mi9);

        JMenuItem mi10=new JMenuItem("Utility Report");
        mi10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
               new  Utilitylist();
            }
        });
        m4.add(mi10);

        JMenuItem mi14=new JMenuItem("Indevidual Report");
        mi14.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                new IndevidualReport();
            }
        });
        m4.add(mi14);

        setVisible(true);
    }
}
