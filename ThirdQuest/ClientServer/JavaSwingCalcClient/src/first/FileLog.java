package first;

import java.io.*;
import java.util.Calendar;


class FileLog {

    //*Счетчики строк в последнем архивном логе и числа архивных логов
    static int lineCounter;
    static int fileCounter;

    static synchronized void Write(File file,  String login, String stackContent, String result) throws IOException {

        Calendar calendar = Calendar.getInstance();
        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
            br.write(calendar.getTime().toString() + "; " + stackContent + result + "; " + login + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter br = new BufferedWriter(new FileWriter((Calc.logArch), true))) {
            br.write(calendar.getTime().toString() + "; " + stackContent + result + "; " + login + "\n");
            lineCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Проверяем число строк в последнем архивированном логе, если их >10 создаем новый архивный лог.
    static void tryLog(File file){
        if (lineCounter >= 10){
            FileLog.fileCounter = Calc.logDir.listFiles().length;
            Calc.logArch = new File(file.getPath() + "." + fileCounter);
            lineCounter = 0;
        }
    }

}