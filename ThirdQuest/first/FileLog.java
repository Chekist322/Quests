package first;

import java.io.*;
import java.util.Calendar;


class FileLog {

    static int lineCounter;
    private static int fileCounter;

    static void Write(File file, String login, String stackContent, String result) throws IOException {
        if (lineCounter >= 10){
            File archiveFile = new File(file.getPath()+ "."+FileLog.fileCounter);
            if(file.renameTo(archiveFile)){
                System.out.println("Файл переименован успешно");
                lineCounter = 0;
                FileLog.fileCounter++;
            }else{
                System.out.println("Файл не был переименован");
                if(archiveFile.delete()){
                    System.out.println("Файл удален");
                    if(file.renameTo(archiveFile)){
                        System.out.println("Файл переименован успешно");
                        FileLog.fileCounter++;
                    }else{
                        System.out.println("Файл не был переименован");
                    }
                }else System.out.println("Файл не обнаружен");

            }
        }

            Calendar calendar = Calendar.getInstance();
            try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
                br.write(calendar.getTime().toString() + "; " + stackContent + result + "; " + login + "\n");
                lineCounter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

//    public static void Write(File file, String login, double value){
//        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
//            br.write("=" + value + "; " + login + "\n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}