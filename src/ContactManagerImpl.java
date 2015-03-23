import java.util.*;

/**
 * Created by devon on 27/02/2015.
 */
public class ContactManagerImpl implements ContactManager{

    Calendar currentDate = Calendar.getInstance();
    private Set <Contact> contacts;
    private List <Meeting> meetings;
    private Calendar calendar;
    private static  int id;

    public ContactManagerImpl (){
        this.contacts = new HashSet<Contact>();
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
        Meeting meeting;


        if( meetings.get(id).getDate().after(currentDate)){
            throw new IllegalArgumentException();

        }
        if ( id > meetings.size()){
            return null;
        }
        else  meeting = meetings.get(id);

        return (PastMeeting) meeting;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Meeting futureMeeting;
        if ( id > meetings.size()){
            return null;
        }
        if ( meetings.get(id).getDate().before(currentDate)){
            throw new IllegalArgumentException();
        }
        else
            futureMeeting = meetings.get(id);

        return (FutureMeeting) futureMeeting;
    }

    @Override
    public Meeting getMeeting(int id) {
        int idMeeting = id -1;
        if ( idMeeting > meetings.size()){
            return null;
        }
        else
        return meetings.get(idMeeting);
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        boolean contain = false;
        List <Meeting> meeting = new ArrayList<Meeting>();

        for ( Meeting meet: meetings){


            if (  meet.getContacts().contains(contact) &&   meet instanceof Meeting ){


                contain = true;

            }


            if ( meet.getContacts().contains(contact) && meet instanceof FutureMeeting){

                meeting.add(meet);
                contain = true;



            }


            if ( contain ){
                throw new IllegalArgumentException ();
            }

            Collections.sort(meeting, new MeetingComparator ());
            return  meeting;
        }




        meeting = null;
        return meeting;





    }

    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {

        List <Meeting> talk = new ArrayList<Meeting>();


        for ( Meeting meet: meetings) {

            if (meet.getDate().equals(date)) {
                talk.add(meet);}


            Collections.sort(talk, new MeetingComparator ());

        }



        return talk;
    }

    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        return null;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {


        if ( date.equals(null) || contacts.equals(null) || text.equals( null)){

            throw new NullPointerException();
        }


        for ( Meeting meeting: meetings) {


            if (contacts.equals(null) || date.equals(null) || text.equals(null)) {
                throw new NullPointerException();
            }

            if (date.equals(meeting.getDate()) && meeting.getDate().before(currentDate) && contacts.equals(meeting.getContacts())) {
                PastMeetingImpl pastMeeting = new PastMeetingImpl(meeting.getId(), date, contacts, text);

                meetings.set(meeting.getId(), pastMeeting);

            }

        }


        }

    @Override
    public void addMeetingNotes(int id, String text) {

        for ( Meeting meet: meetings){
            if ( text.equals(null) ){
                throw new NullPointerException();
            }

            if(id == meet.getId() && meet instanceof FutureMeetingImpl && meet.getDate().before(currentDate)){



                PastMeetingImpl pastMeeting = new PastMeetingImpl(meet.getId(), meet.getDate(),meet.getContacts(),text);
                meetings.set ( id, pastMeeting);


            }

            if ( id == meet.getId() && meet.getDate().before(currentDate) &&!(meet instanceof FutureMeeting)){
                PastMeeting pastMeeting = new PastMeetingImpl(meet.getId(),meet.getDate(),meet.getContacts(),text);
                meetings.set(id, pastMeeting);



            }
            if ( id == meet.getId() && meet.getDate().after(currentDate)){
                throw new IllegalStateException();
            }

        }


    }

    @Override
    public void addNewContact(String name, String notes) {




        ContactImpl contact = new ContactImpl(name);
        contact.addNotes(notes);



    }

    @Override
    public Set<Contact> getContacts(int... ids) {


        Set<Contact> list = new HashSet<Contact>();


        int count=0;


        for ( int i: ids){
            count++;

        }

        int size = contacts.size();



        int result = contacts.size()-count;



        for (int id : ids) {

            for (Contact contact : contacts) {


                if (id == contact.getId()) {
                    size--;


                    list.add(contact);


                } else if (id != contact.getId()) {
                    size++;
                }


            }
        }

        if (result !=size ){
            throw new IllegalArgumentException ();
        }

        return list;




    }

    @Override
    public Set<Contact> getContacts(String name) {

        return null;
    }

    public class MeetingComparator implements Comparator <Meeting>{
    @Override
    public int compare (Meeting first, Meeting second){
        return first.getDate().getTime().compareTo(second.getDate().getTime());
    }}


    @Override
    public void flush() {

    }
}
