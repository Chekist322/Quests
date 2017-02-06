package first;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


class LoginPage extends JFrame {

    private File file = Calc.usersDB;
    static String currentUser;

    LoginPage(JFrame frame) {

        setLayout(new BorderLayout());
        setSize(300, 350);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,4));


        JTextField login = new JTextField("");
        JPasswordField password =new JPasswordField("");
        JButton logIn = new JButton("log in");
        JButton registration = new JButton("registration");
        JLabel check = new JLabel("");

        panel.add(new JLabel("login"));
        panel.add(login);
        panel.add(new JLabel("password"));
        panel.add(password);
        panel.add(logIn);
        panel.add(registration);
        panel.add(check);
        Font font = new Font("Verdana", Font.BOLD, 16);
        login.setFont(font);
        password.setFont(font);
        logIn.setFont(font);
        registration.setFont(font);
        login.setFont(font);
        panel.getComponent(0).setFont(font);
        panel.getComponent(2).setFont(font);


        logIn.addActionListener(event -> {

            //Если проверка пройдена, создаем окно калькулятора.
            if (FileRuler.LogIn(file, login.getText(), password.getText())) {
                Container calcPane = frame.getContentPane();
                currentUser = login.getText();
                try {
                    calcPane.add(new Calc());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setVisible(true);
                setVisible(false);
            } else {
                check.setText("invalid login or password");
            }
        });

        registration.addActionListener(event -> {
            check.setText("");
            if(FileRuler.RegistrationLoginCheck(file, login.getText())){
                check.setText("success!");
                FileRuler.FileWrite(file, login.getText(), password.getText());
            }else{
                check.setText("invalid login");
            }
        });

        add(panel, "Center");


    }

}
