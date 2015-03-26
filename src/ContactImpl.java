import java.io.Serializable;

/**
 * Created by devon on 07/03/2015.
 */
public class ContactImpl implements Contact,Serializable {

    private String name;
    private String notes;
    private int id;
    private static int nextUniqueID =0;

    ContactImpl (){


    }

    ContactImpl (String name){
        this.name = name;
        this.nextUniqueID++;
        this.id = nextUniqueID;



    }

    ContactImpl (int id, String name){
        this.name = name;
        this.id = id;


    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    @Override
    public void addNotes(String note) {
        this.notes = note;

    }
}
