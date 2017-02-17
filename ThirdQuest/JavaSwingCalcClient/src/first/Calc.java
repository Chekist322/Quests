package first;

import first.cache.Level1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


import javax.swing.*;


public class Calc extends JPanel {
    static File usersDB = new File("UsersDB.txt");
    static File logArch;
    private static File log = new File("Log\\Log.log");
    static File logDir = new File("Log\\");
    private String newValue;
    private double value = 0;
    private boolean negative = false;
    private boolean dotFlag = false;
    private String currentUser;
    private OperationStack operationStack = new OperationStack();
    static Level1 level1 = new Level1();
    private static NewThread newThread = new NewThread();
    private static Thread thrd = new Thread(newThread);
    private final String[] buttons = {"7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "+",
            ".", "0", "=", "-"};



    private JTextField display = new JTextField("0");
    private JTextArea display2 = new JTextArea("0");
    private boolean calculating = true;
    private double dot = 10;

    static Socket socket;

    static InputStream inputStream;
    static OutputStream outputStream;

    static DataOutputStream output;
    static DataInputStream input;


    Calc() throws IOException {
        //Проверка для того, чтобы отсчет архивных логов начинался с 1 и продолжался инкрементированно
        if (FileLog.fileCounter == 1) {
            logArch = new File("Log\\Log.log" + "." + (FileLog.fileCounter));
        } else {
            logArch = new File("Log\\Log.log" + "." + (FileLog.fileCounter - 1));
        }
        //Создаем архивный лог, если он не существует
        FileRuler.newFile(logArch);

        //Считаем строки в последнем архивном логе
        FileReader fileReader = new FileReader(logArch);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        while (lineNumberReader.readLine() != null) {
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
        display2.setEditable(false);
//        display2.setHorizontalAlignment(SwingConstants.RIGHT);
        Font font2 = new Font("Verdana", Font.PLAIN, 12);
        display2.setFont(font2);
        display2.setColumns(20);
        display2.setLineWrap(true);
        add(display2, "Center");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        //Заполняем панель кпонок значениями
        buttonsInit();
    }

    private void buttonsInit(){
        JButton[] keys = new JButton[buttons.length];
        JPanel buttonPanel = new JPanel(new GridLayout(5, 3));
        for (int i = 0; i < buttons.length; i++) {
            keys[i] = new JButton(buttons[i]);
        }
        add(buttonPanel, "South");
        Font buttonsFont = new Font("Verdana", Font.BOLD, 18);
        for (int i = 0; i < keys.length; i++) {
            keys[i].setFocusPainted(false);
            keys[i].setContentAreaFilled(false);
            keys[i].setFont(buttonsFont);
            keys[i].addActionListener(evt -> {
                String cmd = evt.getActionCommand();
                try {
                    calcEvent(cmd);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            keys[i].addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    try {
                        calcEvent(Character.toString(e.getKeyChar()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            buttonPanel.add(keys[i]);
        }
    }

    private void calcEvent(String cmd) throws IOException, InterruptedException, ClassNotFoundException {

        if (!cmd.equals("=")) {
            if ('0' <= cmd.charAt(0) && cmd.charAt(0) <= '9') {
                if (calculating) {
                    operationStack.push();
                    if (negative) {
                        if (!dotFlag) {
                            value = -Double.valueOf(cmd);
                        }else {
                            value = value - Double.valueOf(cmd)/dot;
                            dot*=10;
                        }
                    }else {
                        if (dotFlag){

                            value = value + Double.valueOf(cmd)/dot;
                            dot*=10;
                        }else {
                            value = Double.valueOf(cmd);
                        }
                    }
                } else {
                    if (negative) {
                        if (dotFlag){
                            value = value - Double.valueOf(cmd)/dot;
                            dot*=10;
                        }else {
                            value = value * 10 - Double.valueOf(cmd);
                        }
                    }else{
                        if (dotFlag){
                            value = value + Double.valueOf(cmd)/dot;
                            dot*=10;
                        }else {
                            value = value * 10 + Double.valueOf(cmd);
                        }
                    }
                }
                operationStack.pushValue(String.valueOf(value));
                display2.setText(operationStack.getStack());
                display.setText(cmd);
                calculating = false;
            }
            else if (cmd.equals(".")){
                dotFlag = true;
            }else {
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
                            display2.setText(operationStack.getStack());
                            display.setText(cmd);
                        }
                    }
                } else {
                        negative = false;
                        operator = cmd;
                        operationStack.pushOperator(String.valueOf(operator));
                        display2.setText(operationStack.getStack());
                        display.setText(cmd);
                        calculating = true;
                }
                value = 0;
                dot = 10;
                dotFlag = false;
            }
        } else {
            if (operationStack.isEmpty()){

            }else {
                negative = false;
                operationStack.pushOperator("=");
                String result;
                String stackContent = operationStack.getStack();
                //Отправляем на сервер запрос на расчет выражения
                result = operationStack.getResult();
                display2.setText(display2.getText() + "=" + result);
                //Отправляем на сервер всё выражение в строке для логирования
                output.writeBoolean(false);
                output.writeUTF(stackContent);
                output.writeUTF(result);
                newThread.Set(log, currentUser, stackContent, result);
                thrd = new Thread(newThread);
                thrd.start();
                operationStack.clear();
                value = 0;
                dot = 10;
                dotFlag = false;
                calculating = true;
                level1.print();
            }

        }
    }


    public static void main(String[] args) throws IOException {
        //Создаем директорию для логов, если ее нет
        InetAddress ipAddress = InetAddress.getByName("127.0.0.1");
        socket = new Socket(ipAddress,1488);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        output = new DataOutputStream(outputStream);
        input = new DataInputStream(inputStream);
        logDir.mkdir();
        FileLog.fileCounter = logDir.listFiles().length;
//        System.out.println(logDir.listFiles().length);
        FileRuler.newFile(usersDB);
        FileRuler.newFile(log);

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

        LoginPage loginPane = new LoginPage(frame, output, input);
        loginPane.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginPane.setVisible(true);
    }


}