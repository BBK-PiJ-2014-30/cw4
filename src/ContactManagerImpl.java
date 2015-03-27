import java.io.*;
import java.util.*;

/**
 * Created by devon on 27/02/2015.
 */
public class ContactManagerImpl implements ContactManager, Serializable{

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

            if (meet.getDate().equals(date) && meet instanceof FutureMeetingImpl || (meet.getDate().equals(date) && date.after(currentDate) )) {
                talk.add(meet);}
            // adds meeting to the list if its an instance of FutureMeeting or the date is a date in the future



            Collections.sort(talk, new MeetingComparator ());

        }
        // chronologically sorts the list in terms of date


        return talk;
    }

    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        int count= 0;
        // use to count how many times the meeting with the contact is found
        List<PastMeeting> list = new ArrayList<PastMeeting>();


        for ( Meeting meet: meetings){

            for( Contact contacts: meet.getContacts())
                // searches through contacts

                if ( contact.equals(contacts) && meet instanceof PastMeeting){

                    list.add((PastMeeting) meet);
                    count++;
                    // if meeting is found count increments
                }

        }
        if( count == 0){
            throw new IllegalArgumentException();
        }
        // if no meetings found IllegalArgumentException is thrown

        Collections.sort(list, new MeetingComparator() );
        // sorts the list chronologically


        return  list ;
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

            if (date.equals(meeting.getDate()) && meeting.getDate().before(currentDate) && contacts.equals(meeting.getContacts()) ) {
                PastMeetingImpl pastMeeting = new PastMeetingImpl(meeting.getId(), date, contacts, text);

                meetings.set(meeting.getId(), pastMeeting);

            }

        }


        }

    @Override
    public void addMeetingNotes(int id, String text) {
        boolean noMeeting = true;

        for ( Meeting meet: meetings){
            if ( meet.getId()== id){
                noMeeting = false;
            }
        }
        if ( noMeeting == true){
            throw new IllegalArgumentException();
        }
        // tests if the meeting exist otherwise it throw an IllegalArgumentException

        for ( Meeting meet: meetings){
            if ( text.equals(null) ){
                throw new NullPointerException();
            }
                // throw NullPointerException if note is null


            if(id == meet.getId() && meet instanceof FutureMeetingImpl && meet.getDate().before(currentDate)){
               // test if id of meeting exists, that is an instance of FutureMeeting and is not date before current date

                PastMeetingImpl pastMeeting = new PastMeetingImpl(meet.getId(), meet.getDate(),meet.getContacts(),text);
                // FutureMeeting changed into PastMeeting with added notes

                meetings.set ( id, pastMeeting);
                // PastMeeting replaced FutureMeeting in the list



            }

            if ( id == meet.getId() && meet.getDate().before(currentDate) &&!(meet instanceof FutureMeeting)){

                PastMeeting pastMeeting = new PastMeetingImpl(meet.getId(),meet.getDate(),meet.getContacts(),text);
                meetings.set(id, pastMeeting);
                // needs modifying confirm with Keith



            }
            if ( id == meet.getId() && meet.getDate().after(currentDate)){
                throw new IllegalStateException();
                // if meeting is in the future throws IllegalStateException
            }

        }


    }

    @Override
    public void addNewContact(String name, String notes) {

        if ( name.equals(null)){
            throw new NullPointerException();
        }
        // throws NullPointerException if note is null



        ContactImpl contact = new ContactImpl(name);
        contact.addNotes(notes);
        contacts.add(contact);

        // adds contact with added with note


    }

    @Override
    public Set<Contact> getContacts(int... ids) {


        Set<Contact> list = new HashSet<Contact>();


        int count=0;


        for ( int i: ids){
            count++;
            // counts the amount of ids

        }




        for (int id : ids) {

            for (Contact contact : contacts) {


                if (id == contact.getId()) {
                    count--;
                    // when contact is found value gets decremented by one


                    list.add(contact);
                    // add contacts with the ids to the list


                }


            }
        }

        if (count !=0 ){
            throw new IllegalArgumentException ();
        }
        // if count doesn't equal zeros means that all the ids numbers have not been found and IllegalArgumentException
        // is thrown

        return list;




    }

    @Override
    public Set<Contact> getContacts(String name) {


        Set <Contact> names = new HashSet<Contact>();
        for ( Contact contact: contacts){
            if ( name.equals(null)){
                throw new NullPointerException();
            }
            else if ( name.equals(contact.getName())){
                names.add(contact);
            }
        }
        return names;




    }

    public class MeetingComparator implements Comparator <Meeting>{
    @Override
    public int compare (Meeting first, Meeting second){
        return first.getDate().getTime().compareTo(second.getDate().getTime());
    }}


    @Override
    public void flush() {

        try {
            FileOutputStream fileOut = new FileOutputStream(" z.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(meetings);
            out.writeObject(contacts);
            out.close();
            fileOut.close();
        } catch (IOException ex) {
        }
    }
    public void startUp() throws IOException {
        List<Meeting> meeting = null;
        Set<Contact> contact = null;

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(" z.ser"));
            meeting = (List<Meeting>) in.readObject();
            contact = (Set<Contact>) in.readObject();

        } catch (Exception e) {
        } finally {
            in.close();
        }


        for ( Meeting meet: meeting){
            meetings.add(meet);
        }
        for ( Contact InContact: contact){
            contacts.add(InContact);
        }

    }
        }



