import java.util.*;

/**
 * Created by devon on 27/02/2015.
 */
public class ContactManagerImpl implements ContactManager{

    Calendar currentDate = Calendar.getInstance();
    private Set <Contact> contact;
    private List <Meeting> meetings;
    private Calendar calendar;
    private static  int id;

    public ContactManagerImpl (){
        this.contact = new HashSet<Contact>();
        this.meetings = new ArrayList<Meeting>();

    }

    private int setId (){
        return ++this.id;

    }
    private int getId () {
        return setId();

    }


    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (date.before(currentDate)){
            throw new IllegalArgumentException();
        }

        MeetingImpl meeting = new FutureMeetingImpl(getId(),date,contacts);
        meetings.add(meeting);
        return meeting.getId();

    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        return null;
    }

    @Override
    public Meeting getMeeting(int id) {
        return null;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        return null;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        return null;
    }

    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        return null;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

    }

    @Override
    public void addMeetingNotes(int id, String text) {

    }

    @Override
    public void addNewContact(String name, String notes) {

    }

    @Override
    public Set<Contact> getContacts(int... ids) {
        return null;
    }

    @Override
    public Set<Contact> getContacts(String name) {
        return null;
    }

    @Override
    public void flush() {

    }
}
