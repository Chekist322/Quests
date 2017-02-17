package first;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


class LoginPage extends JFrame {

    private File file = Calc.usersDB;
    static String currentUser;
    boolean loginFlag = false;

    LoginPage(JFrame frame, DataOutputStream output, DataInputStream input) throws IOException {


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
            try {
                output.writeUTF(login.getText());

                output.writeUTF(new String(password.getPassword()));

                output.writeBoolean(true);

            //Если проверка пройдена, создаем окно калькулятора.
                if (input.readBoolean()) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        registration.addActionListener(event -> {
            try {

                output.writeUTF(login.getText());

                output.writeUTF(new String(password.getPassword()));

                output.writeBoolean(false);

                check.setText("");
                if(input.readBoolean()){
                    check.setText("success!");
                }else{
                    check.setText("invalid login");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        add(panel, "Center");


    }

}
