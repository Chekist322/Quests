package first;

import first.Cash.Level1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;


public class Calc extends JPanel {
    static File usersDB = new File("UsersDB.txt");
    static File logArch;
    private static File log = new File("Log\\Log.log");
    static File logDir = new File("Log\\");
    private double value = 0;
    private boolean negative = false;
    private String currentUser;
    private OperationStack operationStack = new OperationStack();
    static Level1 level1 = new Level1();
    private static NewThread newThread = new NewThread();
    private static Thread thrd = new Thread(newThread);


    private JTextField display = new JTextField("0");
    private boolean calculating = true;

    Calc() throws IOException {
        //Проверка для того, чтобы отсчет архивных логов начинался с 1 и продолжался инкрементированно
        if (FileLog.fileCounter == 1) {
            logArch = new File("Log\\Log.log" + "." + (FileLog.fileCounter));
        }else
        {
            logArch = new File("Log\\Log.log" + "." + (FileLog.fileCounter-1));
        }
        //Создаем архивный лог, если он не существует
        FileRuler.NewFile(logArch);

        //Считаем строки в последнем архивном логе
        FileReader fileReader = new FileReader(logArch);
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

        //Заполняем панель кпонок значениями
        String buttonLabels = "789/456*123-0.=+";
        for (int i = 0; i < buttonLabels.length(); i++) {
            JButton button = new JButton(buttonLabels.substring(i, i + 1));
            panel.add(button);
            button.setFont(font);

            button.addActionListener(evt -> {
                String cmd = evt.getActionCommand();
                try {
                    CalcEvent(cmd);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });

            button.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent evt) {
                    String cmd = String.valueOf(evt.getKeyChar());
                    try {
                        CalcEvent(cmd);
                    } catch (IOException | InterruptedException e) {
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

    private void CalcEvent(String cmd) throws IOException, InterruptedException {

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
            newThread.Set(log, currentUser, stackContent, result);
            thrd = new Thread(newThread);
            thrd.start();
            operationStack.clear();
            calculating = true;
//            level1.print();

        }
    }


    public static void main(String[] args) {
        //Создаем директорию для логов, если ее нет
        logDir.mkdir();
        FileLog.fileCounter = logDir.listFiles().length;
//        System.out.println(logDir.listFiles().length);
        FileRuler.NewFile(usersDB);
        FileRuler.NewFile(log);

        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setSize(300, 350);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    //При закрытии окна главный поток ожидает окончания работы потока, работающего с логами.
                    thrd.join();
                    System.exit(0);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        LoginPage loginPane = new LoginPage(frame);
        loginPane.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginPane.setVisible(true);
    }


}