package first;

import java.io.*;
import java.net.Socket;

import static first.Start.logDir;

/**
 * Created by Phoen on 17.02.2017.
 */
public class NewThread implements Runnable{
    Socket socket;
    boolean flag = true;
    private File file = Start.usersDB;
    private static LogThread logThrd = new LogThread();
    private static Thread thrd = new Thread(logThrd);
    private String currentUser;

    public void set(Socket socket){
        this.socket = socket;
    }


    public void doing() throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();


        DataOutputStream output = new DataOutputStream(outputStream);
        DataInputStream input = new DataInputStream(inputStream);




        while (flag){
            String login = input.readUTF();
            String password = input.readUTF();
            boolean loginFlag = input.readBoolean();
            if (loginFlag) {
                if (FileRuler.logIn(file, login, password)) {
                    currentUser = login;
                    output.writeBoolean(true);
                    flag = false;
                } else {
                    output.writeBoolean(false);
                }
            } else {
                if (FileRuler.registrationLoginCheck(file, login)) {
                    FileRuler.fileWrite(file, login, password);
                    output.writeBoolean(true);
                } else {
                    output.writeBoolean(false);
                }
            }
        }

        String operator;
        double value;
        double stackValue;
        String result;

        while(true) {

            if (input.readBoolean()) {
                operator = input.readUTF();
                value = input.readDouble();
                stackValue = input.readDouble();
                result = Arithmetic.Calculate(operator, value, stackValue);
                output.writeUTF(result);
            }else {
                String stackContent = input.readUTF();
                result = input.readUTF();
                logThrd.Set(Start.log, currentUser, stackContent, result);
                thrd = new Thread(logThrd);
                thrd.start();
            }

        }
    }

    @Override
    public void run() {
        try {
            doing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
