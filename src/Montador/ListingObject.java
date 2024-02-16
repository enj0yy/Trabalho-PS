package Montador;

import java.util.ArrayList;

public class ListingObject {
    

    public int startingAddress;
    public int startingInstruction;
    private int length;
    public int endAddress;
    public ArrayList<String> TextRecord;
    
    public void set_length(){
        this.length = startingAddress - endAddress;
    }
    public int get_length(){
        return this.length;
    }

    public void reset(){
        this.startingAddress = 0;
        this.startingInstruction = 0;
        this.length = 0;
        this.endAddress = 0;
    }
}
