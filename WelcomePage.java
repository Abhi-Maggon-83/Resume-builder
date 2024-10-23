import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {
    private JButton registerButton;
    private JButton loginButton;

    public WelcomePage() {
        super("Resume Builder");
        setLayout(new FlowLayout());

        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        add(registerButton);
        add(loginButton);

        registerButton.addActionListener(new RegisterListener());
        loginButton.addActionListener(new LoginListener());

        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new RegisterPage();
            dispose();
        }
    }

    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new LoginPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new WelcomePage();
    }
}