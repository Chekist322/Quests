package first;


import java.io.*;

class FileRuler {


    static void newFile(File file){
        try
        {
            boolean created = file.createNewFile();
            if(created)
                System.out.println("Файл создан");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

}