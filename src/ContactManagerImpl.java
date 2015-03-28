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
        if (contacts.equals(null) || date.before(currentDate)){
            throw new IllegalArgumentException();
        }

        FutureMeetingImpl meeting = new FutureMeetingImpl(getId(),date,contacts);
        meetings.add(meeting);
        return meeting.getId();

    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        Meeting meeting;
        int count = 0;
        for ( Meeting meet: meetings){
            count++;
        }
        // counts meetings
        if ( id > count){
            return null;
        }
        //if id is greater than count meeting does not exist; IDs have the same value to the meetings
        //position in the list

        if( id <=count && meetings.get(id).getDate().getTime().before(currentDate.getTime())){

        }


        if (id <=count && meetings.get(id).getDate().getTime().after(currentDate.getTime())){

            throw new IllegalArgumentException();

        }
        meeting = meetings.get(id);


        return (PastMeeting) meeting;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        int count =0;

        for ( Meeting meet: meetings){
            count++;
        }
        // counts the meetings. meets

        if (  id > count ){
            return  null;
        }
        // if count id is greater than the amount in the list id won't exist; ids have the same value to the meetings
        //position in the list


        if ( id <= count && meetings.get(id).getDate().getTime().after(currentDate.getTime())){


        }
        //test if the id is a future meeting



        if ( meetings.get(id).getDate().getTime().before(currentDate.getTime())){
            throw new IllegalArgumentException();
        }
        // throws IllegalArgumentException if there is a meeting with that ID happening in the past
        Meeting a = meetings.get(id);
        return (FutureMeeting) a;
    }

    @Override
    public Meeting getMeeting(int id) {
        int count = 0;

        for ( Meeting meet: meetings){
            count++;
            // counts the number of meetings in the list
        }


        if ( id <= count){
            Meeting a = meetings.get(id);

            return a;
        }
        else {
           return null;
        }

    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {

        boolean futureMeeting = false;
        boolean nam = false;
        boolean pastMeet = false;

        List<Meeting> list = new ArrayList<Meeting>();


        for (Meeting meet : meetings) {

            for (Contact name : meet.getContacts()) {

                if (contact.equals(contact)) {
                    nam = true;

                    // if contact exist in list value get set to true
                }
                if (contact.equals(name) && meet.getDate().getTime().before(currentDate.getTime())) {

                    pastMeet = true;
                    // pastMeet is set to true if there is a contact scheduled with a meet in the past
                }


                if (contact.equals(name) && meet.getDate().getTime().after(currentDate.getTime())) {


                    list.add(meet);
                    futureMeeting = true;
                    // if there is a contact scheduled with a meeting in the future, it gets added to list


                }


            }
        }

        if (nam == true && pastMeet == true && futureMeeting == false) {
            return list;
        }
        // if there are no future meetings but there is a contact with a past meeting, an empty list is returned

        if (nam == false) {
            throw new IllegalArgumentException();
        }

        // if the contact is not in the list nam will equal false


    return list;
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
        // A NullPointerException is thrown if either any of the objects are  null.



        for ( Meeting meeting: meetings) {

            if (date.equals(meeting.getDate()) && meeting.getDate().before(currentDate) && contacts.equals(meeting.getContacts()) ) {
                //tests if the Meeting is the right meeting and is before current date

                PastMeetingImpl pastMeeting = new PastMeetingImpl(meeting.getId(), date, contacts, text);
                // creates PastMeeting

                meetings.set(meeting.getId(), pastMeeting);
                //add PastMeeting to the list
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
        // if no id found the boolean value stays true

        if ( noMeeting == true){
            throw new IllegalArgumentException();
        }
        // if boolean value is true it throw an IllegalArgumentException

        for ( Meeting meet: meetings){
            if ( text.equals(null) ){
                throw new NullPointerException();
            }
                // throw NullPointerException if note is null


            if(id == meet.getId() && meet instanceof FutureMeetingImpl && meet.getDate().before(currentDate)){
               // test if id of meeting exists, is an instance of FutureMeeting and its date not after current date

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

        // adds contact with name and note


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
                    // when contact is found count decremented by one

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
            // adds meetings to the list with the contact
        }
        return names;





    }

    public class MeetingComparator implements Comparator <Meeting>{
    @Override
    public int compare (Meeting first, Meeting second){
        return first.getDate().getTime().compareTo(second.getDate().getTime());
    }
    // puts the items in chronologically in the list in terms of its date
    }


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
        //saves the data in the lists
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
        // deserializes the data in the file and adds the objects back to the list

    }
        }



