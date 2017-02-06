package first;

import java.io.File;
import java.io.IOException;

/**
 * Created by Phoen on 05.02.2017.
 *
 */
public class NewThread implements Runnable {
    private File file;
    private String login;
    private String stackContent;
    private String result;

    NewThread(){

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
                FileLog.Write(file, login, stackContent, result);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
