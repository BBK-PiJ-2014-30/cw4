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
        //If ID is greater than count meeting does not exist; IDs have the same value to the meetings
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
        // Counts the meetings.

        if (  id > count ){
            return  null;
        }
        // If the ID number is greater than the amount count then ID does not exist; IDS have the same value to the meetings
        //position in the list


        if ( id <= count && meetings.get(id).getDate().getTime().after(currentDate.getTime())){


        }




        if ( meetings.get(id).getDate().getTime().before(currentDate.getTime())){
            throw new IllegalArgumentException();
        }
        // Throws IllegalArgumentException if there is a meeting with that ID happening in the past
        Meeting a = meetings.get(id);
        return (FutureMeeting) a;
    }

    @Override
    public Meeting getMeeting(int id) {
        int count = 0;

        for ( Meeting meet: meetings){
            count++;
            // Counts the number of meetings in the list
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

                    // If contact exist in list value get set to true
                }
                if (contact.equals(name) && meet.getDate().getTime().before(currentDate.getTime())) {

                    pastMeet = true;
                    // PastMeet is set to true if there is a contact scheduled with a meet in the past
                }


                if (contact.equals(name) && meet.getDate().getTime().after(currentDate.getTime())) {


                    list.add(meet);
                    futureMeeting = true;
                    // If there is a contact scheduled with a meeting in the future, it gets added to list


                }


            }
        }

        if (nam == true && pastMeet == true && futureMeeting == false) {
            return list;
        }
        // If there are no future meetings but there is a contact with a past meeting, an empty list is returned

        if (nam == false) {
            throw new IllegalArgumentException();
        }

        // If the contact is not in the list nam will equal false


    return list;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {

        List <Meeting> talk = new ArrayList<Meeting>();



        for ( Meeting meet: meetings) {

            if ((meet.getDate().equals(date) && date.after(currentDate) )) {
                talk.add(meet);}
            // Adds meeting to the list if the date is a date in the future



            Collections.sort(talk, new MeetingComparator ());

        }



        return talk;
    }

    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        int count= 0;
        // Use to count how many times the meeting with the contact is found

        List<PastMeeting> list = new ArrayList<PastMeeting>();


        for ( Meeting meet: meetings){

            for( Contact contacts: meet.getContacts())


                if ( contact.equals(contacts) && meet instanceof PastMeeting){

                    list.add((PastMeeting) meet);

                    count++;
                    // If meeting is found count increments
                }

        }
        if( count == 0){
            throw new IllegalArgumentException();
        }
        // If no meetings are found an IllegalArgumentException is thrown

        Collections.sort(list, new MeetingComparator() );
        // List gets sorted in chronological order


        return  list ;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {


        if ( date.equals(null) || contacts.equals(null) || text.equals( null)){

            throw new NullPointerException();
        }
        // A NullPointerException is thrown if either object is null.



        for ( Meeting meeting: meetings) {

            if (date.equals(meeting.getDate()) && meeting.getDate().before(currentDate) && contacts.equals(meeting.getContacts()) ) {
                //Tests if the Meeting is the right meeting and is before current date

                PastMeetingImpl pastMeeting = new PastMeetingImpl(meeting.getId(), date, contacts, text);
                // Creates PastMeeting

                meetings.set(meeting.getId(), pastMeeting);
                //Add PastMeeting to the list
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
        // test if ID number exists

        if ( noMeeting == true){
            throw new IllegalArgumentException();
        }
        // if boolean value is true it throw an IllegalArgumentException

        if ( text.equals(null) ){
            throw new NullPointerException();
        }
        // throw NullPointerException if note is null

        for ( Meeting meet: meetings){



            if(id == meet.getId() && meet instanceof FutureMeetingImpl && meet.getDate().before(currentDate)){
               // test if id of meeting exists, is an instance of FutureMeeting and its date not after current date

                PastMeetingImpl pastMeeting = new PastMeetingImpl(meet.getId(), meet.getDate(),meet.getContacts(),text);
                // FutureMeeting changed into PastMeeting with added notes

                meetings.set ( id, pastMeeting);
                // PastMeeting replaced FutureMeeting in the list



            }


            if ( id == meet.getId() && meet.getDate().after(currentDate)){
                throw new IllegalStateException();
                // if meeting is in the future throws IllegalStateException
            }

        }


    }

    @Override
    public void addNewContact(String name, String notes) {

        if ( name.equals(null) || notes.equals(null)){
            throw new NullPointerException();
        }
        // throws NullPointerException if name or note  is null



        ContactImpl contact = new ContactImpl(name);
        contact.addNotes(notes);
        contacts.add(contact);

        // Adds the contact and name and note


    }

    @Override
    public Set<Contact> getContacts(int... ids) {


        Set<Contact> list = new HashSet<Contact>();


        int count=0;


        for ( int i: ids){
            count++;
            // counts the ids
        }




        for (int id : ids) {

            for (Contact contact : contacts) {

                if (id == contact.getId()) {
                    count--;
                    // When a contact is found count decrements

                    list.add(contact);
                    // Adds contacts with the id to the list


                }


            }
        }

        if (count !=0 ){
            throw new IllegalArgumentException ();
        }
        // if count doesn't equal zeros then all the IDs have not been found and IllegalArgumentException
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
            // Returns a list with the contacts whose names contain that string
        }
        return names;





    }

    public class MeetingComparator implements Comparator <Meeting>{
    @Override
    public int compare (Meeting first, Meeting second){
        return first.getDate().getTime().compareTo(second.getDate().getTime());
    }
    // Puts the items in chronologically order in terms of its date
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
        //Saves the data in the lists
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
        // de-serializes the data in the file and adds the objects back to the list

    }
        }



