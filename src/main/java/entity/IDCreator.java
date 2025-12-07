package entity;

import java.io.*;

public class IDCreator {
    private int id;
    private static IDCreator instance;
    private File file = new File("ID-number.txt") ;
    private BufferedReader reader;
    private BufferedWriter writer;

    private IDCreator() throws IOException{
        if (!file.exists()){
            file.createNewFile();
        }
        id = getLatestId();
        //append = true för att inte skriva över existerande fil
        writer = new BufferedWriter(new FileWriter(file,true));
    }
    public static IDCreator getInstance() throws IOException{
        if (instance == null){
            instance = new IDCreator();
        }
        return instance;
    }

    public int getNextId() throws IOException {
        writer.newLine();
        writer.write(Integer.toString(id));
        writer.flush(); //För att skriva ut direkt
        return id++;
    }

    private int getLatestId()throws IOException{
        reader = new BufferedReader(new FileReader(file));
        String line;
        String lastLine = null;
        while ((line = reader.readLine()) !=null){
            lastLine = line;
        }
        if(lastLine == null){
            return 1;
        }

        return Integer.parseInt(lastLine) + 1;
    }
}
