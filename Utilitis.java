import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Utilitis extends JFrame{
    static JTextField wb,cb,hu,mb,eb;
    Utilitis(){
        setTitle("Login");
        setSize(600, 300);
        setResizable(false);
        setLayout(null);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon favIcon = new ImageIcon(ClassLoader.getSystemResource("img/fav.png"));
        setIconImage(favIcon.getImage());

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("img/Utility.png"));
        Image i2=i1.getImage().getScaledInstance(300,250,Image.SCALE_SMOOTH);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(320,10,300,250);
        add(l1);

        JLabel lhouse=new JLabel("House Utility");
        lhouse.setBounds(30,40,80,25);
        add(lhouse);

        hu=new JTextField();
        hu.setBounds(110,40,170,25);
        add(hu);

        JLabel lmaid=new JLabel("Maid Bill");
        lmaid.setBounds(30,70,80,25);
        add(lmaid);

        mb=new JTextField();
        mb.setBounds(110,70,170,25);
        add(mb);

        JLabel lelectricity=new JLabel("Electricity Bill");
        lelectricity.setBounds(30,100,80,25);
        add(lelectricity);

        eb=new JTextField();
        eb.setBounds(110,100,170,25);
        add(eb);

        JLabel lclean=new JLabel("Clean Bill");
        lclean.setBounds(30,130,80,25);
        add(lclean);

        cb=new JTextField();
        cb.setBounds(110,130,80,25);
        add(cb);

        JLabel lwifi=new JLabel("Wifi Bill");
        lwifi.setBounds(30,160,80,25);
        add(lwifi);

        wb=new JTextField();
        wb.setBounds(110,160,80,25);
        add(wb);

        JButton ubtn1=new JButton("Submit");
        ubtn1.setBounds(200,130,80,25);
        ubtn1.setFocusable(false);
        ubtn1.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (!hu.getText().isEmpty() && !mb.getText().isEmpty() && !eb.getText().isEmpty() && !cb.getText().isEmpty() && !wb.getText().isEmpty()) {
                    try {
                        int huValue = Integer.parseInt(hu.getText());
                        int mbValue  = Integer.parseInt(mb.getText());
                        int ebValue = Integer.parseInt(eb.getText());
                        int cbValue = Integer.parseInt(cb.getText());
                        int wbValue = Integer.parseInt(wb.getText());
                        
                        Data data=new Data(huValue,mbValue,ebValue,cbValue,wbValue);
                        Data.utility.add(data);
                    
                        hu.setText("");
                        mb.setText("");
                        eb.setText("");
                        cb.setText("");
                        wb.setText("");
                        JOptionPane.showMessageDialog(null,"Successfully Added","Information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Utilitis.this, "Please enter valid numbers for Utilities", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Utilitis.this, "Please enter the amount of every Utility", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });        
        add(ubtn1);

        JButton ubtn2=new JButton("Back");
        ubtn2.setBounds(200,160,80,25);
        ubtn2.setFocusable(false);
        add(ubtn2);
        ubtn2.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Dashboard();
            }
        });    

        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Utilitis();
    }
}
