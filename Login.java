import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    JTextField user;
    JPasswordField pass;
    Login()
    {
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

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/login.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(320, 30, 250, 200);
        add(l1);



        
        JLabel userN = new JLabel("User Name");
        userN.setBounds(20, 70, 80, 25);
        add(userN);
        JLabel passs = new JLabel("Password");
        passs.setBounds(20, 107, 60, 35);
        add(passs);

        user = new JTextField();
        user.setBounds(105, 70, 200, 30);
        add(user);

        pass = new JPasswordField();
        pass.setBounds(105, 110, 200, 30);
        add(pass);

        JButton btn1 = new JButton("Close");
        btn1.setBounds(105, 160, 90, 30);
        btn1.setFocusable(false);
        btn1.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        add(btn1);

        JButton btn2 = new JButton("Enter");
        btn2.setFocusable(false);
        btn2.setBounds(213, 160, 90, 30);
        btn2.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String userame = user.getText();
                String password = new String(pass.getPassword());

                if (userame.equals("admin") && password.equals("1234")) {
                    setVisible(false);
                    new Dashboard();
                } else if (userame.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Enter userame & Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "userame or password is wrong", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btn2);
        
        setVisible(true);
    }
   

    public static void main(String [] args)
    {
        new Login();
    }
}
