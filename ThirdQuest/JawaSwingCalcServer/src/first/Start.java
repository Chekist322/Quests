package first;


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
