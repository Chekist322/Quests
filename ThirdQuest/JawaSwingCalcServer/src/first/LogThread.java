package first;

import java.io.File;
import java.io.IOException;

/**
 * Created by Phoen on 17.02.2017.
 */
public class LogThread implements  Runnable{
    private File file;
    private String login;
    private String stackContent;
    private String result;

    LogThread(){

    }

    void Set(File file,  String login, String stackContent, String result){
        this.file = file;
        this.login = login;
        this.stackContent = stackContent;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            FileLog.tryLog(file);
            FileLog.Write(file, login, stackContent, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
