import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by devon on 12/03/2015.
 */
public class MeetingImpl implements Meeting, Serializable {
    private Set <Contact> contact;

    private Calendar calendar;
    private int id;

    MeetingImpl ( ){

    }

    MeetingImpl (int id, Calendar calendar,Set<Contact>contact) {
        this.id = id;
        this.calendar = calendar;
        this.contact = contact;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Calendar getDate() {
        return this.calendar;
    }

    @Override
    public Set<Contact> getContacts() {
        return this.contact;
    }
}