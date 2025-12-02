package entity;

public class IDCreator {
    private int id;
    private static IDCreator instance;

    private IDCreator(){
        id = 1;
    }
    public static IDCreator getInstance(){
        if (instance == null){
            instance = new IDCreator();
        }
        return instance;
    }

    public int getNextId(){
        return id++;
    }
}
