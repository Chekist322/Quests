package first;


import java.io.*;

class FileRuler {

    static void fileWrite(File file, String login, String password) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
            br.write("#" + login + "\n");
            br.write(Enigma.getHash(password) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    static boolean registrationLoginCheck(File file, String login) {
        if (!(login.isEmpty()) && (login.matches("[a-zA-Z[1-9]]+")) && registrationLoginComparator(file, login)) {
            return true;
        }
        return false;
    }

    private static boolean registrationLoginComparator(File file, String login){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("#")){
                    if(login.equals(line.substring(1))){
                        return false;
                    }
                }
                line = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    private static boolean logInPasswordCheck(String password) {
        if (!(password.isEmpty()) && (password.matches("[a-zA-Z[1-9]]+"))) {
            return true;
        }
        return false;
    }


    static boolean logIn(File file, String login, String password) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("#")){
                    if(login.equals(line.substring(1))){
                        if (logInPasswordCheck(password)){
                            line = reader.readLine();
                            if (Enigma.getHash(password).equals(line)){
                                return true;
                            }
                        }
                    }
                }
                line = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

}