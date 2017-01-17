package first;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;


public class FileLog {
    public static void Write(File file, double value, double stackValue, String operator){
        Calendar calendar = Calendar.getInstance();
        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
            br.write( calendar.getTime().toString()+ "; " + value + operator + stackValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Write(File file, String login, double value){
        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
            br.write("=" + value + "; " + login + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}