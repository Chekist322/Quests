package first;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;


public class Calc extends JPanel {
    static File usersDB = new File("UsersDB.txt");
    static File log = new File("Log.txt");
    double value = 0;
    double stackValue;
    String currentUser;

    private JTextField display = new JTextField("0");
    private String operator = "=";
    private boolean calculating = true;
    Font font = new Font("Verdana", Font.PLAIN, 32);

    public Calc() {
        currentUser = LoginPage.currentUser;
        setLayout(new BorderLayout());
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(font);
        display.scrollRectToVisible(new Rectangle(100, 200));

        add(display, "North");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String buttonLabels = "789/456*123-0.=+";
        for (int i = 0; i < buttonLabels.length(); i++) {
            JButton button = new JButton(buttonLabels.substring(i, i + 1));
            panel.add(button);
            button.setFont(font);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    String cmd = evt.getActionCommand();
                    CalcEvent(cmd);
                }
            });
            button.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent evt) {
                    String cmd = String.valueOf(evt.getKeyChar());
                    CalcEvent(cmd);
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }
        add(panel, "Center");
    }

    public void CalcEvent(String cmd) {

        if ('0' <= cmd.charAt(0) && cmd.charAt(0) <= '9' || cmd.equals(".")) {
            if (calculating) {
                display.setText(cmd);
            }
            else {
                display.setText(display.getText() + cmd);
            }
            calculating = false;
        } else {
            if (calculating) {
                if (cmd.equals("-")) {
                    display.setText(cmd);
                    calculating = false;
                } else {
                    operator = cmd;
                }
            } else {
                stackValue = Double.parseDouble(display.getText());
                Calculate();
                operator = cmd;
                calculating = true;
            }
        }
    }

    private void Calculate() {

        if (operator.equals("+")) {
            FileLog.Write(log, value, stackValue, "+");
            value += stackValue;
            FileLog.Write(log, currentUser, value);
        }
        else if (operator.equals("-")) {
            FileLog.Write(log, value, stackValue, "-");
            value -= stackValue;
            FileLog.Write(log, currentUser, value);
        }
        else if (operator.equals("*")){
            FileLog.Write(log, value, stackValue, "*");
            value *= stackValue;
            FileLog.Write(log, currentUser, value);
        }
        else if (operator.equals("/")) {
            FileLog.Write(log, value, stackValue, "/");
            value /= stackValue;
            FileLog.Write(log, currentUser, value);
        }
        else if (operator.equals("=")) {
            value = stackValue;
        }
        display.setText("" + value);
    }

    public static void main(String[] args) {
        FileRuler.NewFile(usersDB);
        FileRuler.NewFile(log);
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(300, 350);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        LoginPage loginPane = new LoginPage(frame);
        loginPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPane.setVisible(true);
    }


}