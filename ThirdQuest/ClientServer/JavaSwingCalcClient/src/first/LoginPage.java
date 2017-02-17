package first;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;


class LoginPage extends JFrame {
    static String currentUser;
    boolean loginFlag = false;

    LoginPage(JFrame frame, DataOutputStream output, DataInputStream input) throws IOException {


        setLayout(new BorderLayout());
        setSize(300, 150);

        JPanel panel = new JPanel();

        JPanel bigPanel = new JPanel();
        panel.setLayout(new GridBagLayout());
        bigPanel.setLayout(new GridBagLayout());

        GridBagConstraints constr = new GridBagConstraints();


        JTextField login = new JTextField("");
        JPasswordField password =new JPasswordField("");
        JButton logIn = new JButton("log in");
        JButton registration = new JButton("registration");
        JLabel check = new JLabel("Enter the data");

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.weightx = 20;
        constr.weighty = 20;
        panel.add(new JLabel("login"), constr);

        constr.gridx = 1;
        constr.gridy = 0;
        panel.add(login, constr);

        constr.gridx = 0;
        constr.gridy = 1;
        panel.add(new JLabel("password"), constr);

        constr.gridx = 1;
        constr.gridy = 1;
        panel.add(password, constr);

        constr.gridx = 0;
        constr.gridy = 2;
        panel.add(logIn, constr);

        constr.gridx = 1;
        constr.gridy = 2;
        panel.add(registration, constr);

        constr.gridx = 0;
        constr.gridy = 3;
        constr.gridwidth = 60;
        panel.add(check, constr);

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
