package first;

import first.Cash.Level1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;


public class Calc extends JPanel {
    static File usersDB = new File("UsersDB.txt");
    private static File log = new File("Log.log");
    private double value = 0;
    private boolean negative = false;
    private String currentUser;
    private OperationStack operationStack = new OperationStack();
    static Level1 level1 = new Level1();


    private JTextField display = new JTextField("0");
    private boolean calculating = true;

    Calc() throws IOException {
        FileReader fileReader = new FileReader(log);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        while (lineNumberReader.readLine() != null){
            FileLog.lineCounter++;
        }
        fileReader.close();

        currentUser = LoginPage.currentUser;
        setLayout(new BorderLayout());
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        Font font = new Font("Verdana", Font.PLAIN, 32);
        display.setFont(font);

        add(display, "North");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String buttonLabels = "789/456*123-0.=+";
        for (int i = 0; i < buttonLabels.length(); i++) {
            JButton button = new JButton(buttonLabels.substring(i, i + 1));
            panel.add(button);
            button.setFont(font);
            button.addActionListener(evt -> {
                String cmd = evt.getActionCommand();
                try {
                    CalcEvent(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent evt) {
                    String cmd = String.valueOf(evt.getKeyChar());
                    try {
                        CalcEvent(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    private void CalcEvent(String cmd) throws IOException {

        if (!cmd.equals("=")) {
            if ('0' <= cmd.charAt(0) && cmd.charAt(0) <= '9' || cmd.equals(".")) {
                if (calculating) {
                    operationStack.push();
                    if (negative) {
                        value = -Double.valueOf(cmd);
                    }else {
                        value = Double.valueOf(cmd);
                    }
                } else {
                    if (negative) {
                        value = value * 10 - Double.valueOf(cmd);
                    }else{
                        value = value * 10 + Double.valueOf(cmd);
                    }
                }
                operationStack.pushValue(String.valueOf(value));
                display.setText(operationStack.getStack());
                calculating = false;
            } else {
                String operator;
                if (calculating) {
                    if (cmd.equals("-")) {
                        negative = true;
                        calculating = true;
                    } else {
                        negative = false;
                        if (!operationStack.isEmpty()) {
                            operator = cmd;
                            operationStack.pushOperator(String.valueOf(operator));
                            display.setText(operationStack.getStack());
                        }
                    }
                } else {
                        negative = false;
                        operator = cmd;
                        operationStack.pushOperator(String.valueOf(operator));
                        display.setText(operationStack.getStack());
                        calculating = true;
                }
            }
        } else {
            negative = false;
            operationStack.pushOperator("=");
            String result;
            String stackContent = operationStack.getStack();
            result = operationStack.getResult();
            display.setText(result);
            FileLog.Write(log, currentUser, stackContent, result);
            operationStack.clear();
            calculating = true;
            level1.print();

        }
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
        loginPane.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginPane.setVisible(true);
    }


}