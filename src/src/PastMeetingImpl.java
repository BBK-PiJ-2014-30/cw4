import java.util.Calendar;
import java.util.Set;

/**
 * Created by devon on 13/03/2015.
 */
public class PastMeetingImpl implements PastMeeting {
    int id;
    Calendar date;
    Set <Contact> contact;
    String notes;

    PastMeetingImpl ( int id,Calendar date,Set<Contact> contact, String notes){

        this.id = id;
        this.date = date;
        this.contact = contact;
        this.notes = notes;
    }




    @Override
    public String getNotes() {
        return this.notes;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Calendar getDate() {
        return null;
    }

    @Override
    public Set<Contact> getContacts() {
        return null;
    }
}
