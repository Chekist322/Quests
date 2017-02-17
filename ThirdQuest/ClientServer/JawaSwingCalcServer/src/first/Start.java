package first;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Phoen on 15.02.2017.
 */
public class Start {



    static File usersDB = new File("UsersDB.txt");
    static File logArch;
    public static File log = new File("Log\\Log.log");
    static File logDir = new File("Log\\");



    static public void main(String[] args) throws IOException, ClassNotFoundException {

        JFrame frame = new JFrame();
        frame.setTitle("Server");
        frame.setSize(300, 100);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //При закрытии окна главный поток ожидает окончания работы потока, работающего с логами.
                System.exit(0);
            }
        });

        JButton stop = new JButton("STOP");

        stop.setFocusable(false);

        stop.setFont(new Font("Verdana", Font.BOLD, 32));
        stop.setBackground(Color.RED);
        stop.setForeground(Color.ORANGE);

        stop.addActionListener(e -> {
            System.exit(0);
        });

        frame.add(stop);

        frame.setVisible(true);

        logDir.mkdir();
        FileLog.fileCounter = logDir.listFiles().length;
        FileRuler.newFile(usersDB);
        FileRuler.newFile(log);
        ServerSocket server = new ServerSocket(1488);


        while (true) {

            Socket socket = server.accept();
            NewThread newThread = new NewThread();
            Thread thrd;
            newThread.set(socket);
            System.out.println("User accepted");
            thrd = new Thread(newThread);
            thrd.start();


        }
    }
}
