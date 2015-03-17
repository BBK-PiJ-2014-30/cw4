/**
 * Created by devon on 07/03/2015.
 */
public class ContactImpl implements Contact {

    private String name;

    ContactImpl (String name){
        this.name = name;

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNotes() {
        return null;
    }

    @Override
    public void addNotes(String note) {

    }
}
