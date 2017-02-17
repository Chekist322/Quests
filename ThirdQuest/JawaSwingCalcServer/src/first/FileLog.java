package first;

import java.io.*;
import java.util.Calendar;

import static first.Start.logDir;


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

        try (BufferedWriter br = new BufferedWriter(new FileWriter((Start.logArch), true))) {
            br.write(calendar.getTime().toString() + "; " + stackContent + result + "; " + login + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Проверяем число файлов в папке и число строк в последнем архивированном логе, если их > 10 создаем новый архивный лог.
    static void tryLog(File file) throws IOException {
        fileCounter = logDir.listFiles().length;

        if (FileLog.fileCounter == 1) {
            Start.logArch = new File("Log\\Log.log" + "." + (fileCounter));
            FileRuler.newFile(Start.logArch);
        } else {
            Start.logArch = new File("Log\\Log.log" + "." + (fileCounter-1));
            FileRuler.newFile(Start.logArch);
        }

        FileReader fileReader = new FileReader(Start.logArch);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        while (lineNumberReader.readLine() != null) {
            FileLog.lineCounter++;
        }
        if (lineCounter >= 10){
            fileCounter = logDir.listFiles().length;
            Start.logArch = new File(file.getPath() + "." + fileCounter);
        }
        lineCounter = 0;
    }

}