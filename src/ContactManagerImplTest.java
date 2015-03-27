import org.junit.Test;

import java.io.*;
import java.util.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

public class ContactManagerImplTest {


    Calendar futureMeeting1 = new GregorianCalendar( 2015,9,9);

    Calendar futureMeeting5 = new GregorianCalendar( 2015,1,1);
    Calendar futureMeeting4 = new GregorianCalendar( 2015,1,1);
    Calendar futureMeeting2 = new GregorianCalendar( 2015,9,15);
    Calendar futureMeeting3 = new GregorianCalendar( 2015,11,11);
    Calendar meetingInPast = new GregorianCalendar( 2015,2,9);
    Calendar pastMeeting1 = new GregorianCalendar(2015,2,1);
    Calendar pastMeeting2 = new GregorianCalendar(2015,2,7);



    private Calendar date;


    Calendar currentDate = Calendar.getInstance();
    private Set <Contact> contacts;
    private List <Meeting> meetings;

    private   int id =0;

    public ContactManagerImplTest (){

        this.meetings = new ArrayList<Meeting>();
        this.contacts = new HashSet<Contact>();




    }

    private int setId (){
        return this.id++;

    }
    private int getId () {
        return setId();

    }


    @Test
    public void testAddFutureMeeting() throws Exception {


        Meeting meeting1 = new FutureMeetingImpl (getId(),futureMeeting1,this.contacts);
        Meeting meeting2 = new FutureMeetingImpl (getId(),futureMeeting2,this.contacts);
        Meeting meeting3 = new FutureMeetingImpl(getId(),meetingInPast,this.contacts);

        // tests if meeting object has the right ID

        meetings.add (meeting1);
        meetings.add (meeting2);
        meetings.add (meeting3);

        assertEquals(0,meeting1.getId());
        assertEquals(2,meeting3.getId());
        assertEquals(1,meeting2.getId());


        // throws IllegalArgumentException if meeting is set for a time in the past.
        Calendar timeInPast = meetings.get(2).getDate();
        Calendar timeInFuture = meetings.get(1).getDate();

        if ( timeInFuture.getTime().before(currentDate.getTime())){


            throw new IllegalArgumentException();
        }



    }



    @Test
    public void testGetPastMeeting() throws Exception {


        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,this.contacts);
        Meeting meeting2 = new MeetingImpl (getId(),pastMeeting2,this.contacts);
        Meeting meeting3 = new FutureMeetingImpl(getId(),futureMeeting2,this.contacts);
        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        int pastId = 2;
        int futureId =3;

        // throws IllegalArgumentException if there is a meeting with the ID happening in the future
        Calendar futureDate = meetings.get(futureId-1).getDate();
        Calendar pastDate = meetings.get(pastId-1).getDate();

        System.out.println( currentDate.getTime());

        System.out.println( pastDate.getTime());


        if ( pastDate.getTime().after(currentDate.getTime())){

            throw new IllegalArgumentException ();
        }






    }

    @Test
    public void testGetFutureMeeting() throws Exception {

        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,this.contacts);
        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,this.contacts);
        meetings.add(meeting1);
        meetings.add(meeting2);

        int pastId = 1;
        int futureId =2;

        Calendar futureDate = meetings.get(futureId-1).getDate();
        Calendar pastDate = meetings.get(pastId-1).getDate();

        // throws IllegalArgumentException if there is a meeting with that ID happening in the past
        if ( futureDate.getTime().before(currentDate.getTime())){
            throw new IllegalArgumentException();
        }

        // tests if the saved meeting with the requested id is available
        int requestId = 2;
        Meeting meeting = meetings.get(requestId-1);
        Calendar date = meeting.getDate();
        assertEquals(futureMeeting2.getTime(),date.getTime());


    }

    @Test
    public void testGetMeeting() throws Exception {

        int requestedId =1;
        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,this.contacts);
        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,this.contacts);
        meetings.add(meeting1);
        meetings.add(meeting2);

        // test that the meeting with the requested id is not equal to null
        assertNotNull(meetings.get(requestedId-1));


        assertFalse(" there's no meeting with that ID",requestedId-1 > meetings.size());




    }

    @Test
    public void testGetFutureMeetingList() throws Exception {

        List <Meeting> talk = new ArrayList<Meeting>();

        ContactImpl contact1 = new ContactImpl(" Dave");

        ContactImpl contact2 = new ContactImpl(" jones");
        ContactImpl contact3 = new ContactImpl(" tammy");
        ContactImpl contact4 = new ContactImpl(" Alison");

        Set< Contact>contactOne = new HashSet<>();
        contactOne.add(contact1);

        contactOne.add(contact2);

        contactOne.add(contact3);

        contactOne.add(contact4);

        Set< Contact>contactTwo = new HashSet<>();
        contactTwo.add(contact1);
        Set< Contact>contactThree = new HashSet<>();
        contactThree.add(contact1);

        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,contactThree);


        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,contactOne);
        Meeting meeting3 = new FutureMeetingImpl(getId(),futureMeeting1,contactThree);
        Meeting meeting4 = new FutureMeetingImpl(getId(),futureMeeting3,contactTwo);
        Meeting meeting5 = new MeetingImpl      (getId(),pastMeeting2,contactThree);

        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        meetings.add(meeting5);


        // list created and saved within meeting in terms it's date not chronologically

        List <Meeting> mw = new ArrayList<Meeting>();
        mw.add(meetings.get(0));
        mw.add(meetings.get(1));
        mw.add(meetings.get(2));

        // throws IllegalArgumentException if contact does not exist
        boolean contain = false;


        for ( Meeting meet: meetings){



        if (  meet.getContacts().contains(contact1) &&   meet instanceof Meeting ){


            contain = true;

        }
            // if within the list there is an instance of PastMeeting then meet equals null

        if ( meet.getContacts().contains(contact1) && meet instanceof FutureMeeting){

            talk.add(meet);
            contain = true;



        }
            // if contact is in the list and the meeting an instance of FutureMeeting the meeting get added to the list
            // and the boolean value is set to true.

            if ( contain ==false ){
                throw new IllegalArgumentException ();
            }

            // if the contact is not in the list the boolean value will be false and then will throw an IllegalArgumentException
        }
        assertEquals(3,talk.size() );
           // expected value after the contact has been search for through the list



        Collections.sort(talk, new MeetingComparator ());
        Calendar date1 = talk.get(0).getDate();
        Calendar date2 = talk.get(1).getDate();
        Calendar date3 = talk.get(2).getDate();

        System.out.println( "list " +date1.getTime());
        System.out.println("list" + date2.getTime());
        System.out.println( "list" + date3.getTime());

        assertEquals(date1.getTime(), futureMeeting1.getTime());
        assertEquals (date2.getTime(),futureMeeting2.getTime());
        assertEquals (date3.getTime(),futureMeeting3.getTime());
        // tests if the list is chronologically sorted in terms of it's date and that they are FutureMeeting instances


    }


    @Test
    public void testGetFutureMeetingList1() throws Exception {
        Meeting meeting1 = new MeetingImpl      (getId(),pastMeeting1,this.contacts);
        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,this.contacts);
        Meeting meeting3 = new FutureMeetingImpl(getId(),pastMeeting1,this.contacts);
        Meeting meeting4 = new FutureMeetingImpl(getId(),futureMeeting3,this.contacts);
        Meeting meeting5 = new MeetingImpl      (getId(),pastMeeting2,this.contacts);

        List <Meeting> talk = new ArrayList<Meeting>();
        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        meetings.add(meeting5);
        int size;



        for ( Meeting meet: meetings) {

            if (meet.getDate().equals(pastMeeting1)) {
                talk.add(meet);}


        Collections.sort(talk, new MeetingComparator ());
        size = talk.size();
        }

        talk.size();
        System.out.println(talk.size());



    }

    @Test
    public void testGetPastMeetingList() throws Exception {





    }

    @Test
    public void testAddNewPastMeeting() throws Exception {


        Contact contact1 = new ContactImpl(" Dave");
        Contact contact2 = new ContactImpl(" jones");
        Contact contact3 = new ContactImpl(" tammy");
        Contact contact4 = new ContactImpl(" Alison");
        Contact contact5 = new ContactImpl(" Alison");

        Set<Contact> contactOne = new HashSet<>();
        contactOne.add(contact1);
        contactOne.add(contact2);
        contactOne.add(contact3);
        contactOne.add(contact4);

        Set<Contact> contactFour = new HashSet<>();

        contactFour.add(contact1);
        contactFour.add(contact2);
        contactFour.add(contact3);
        contactFour.add(contact4);


        Set<Contact> contactTwo = new HashSet<>();
        contactTwo.add(contact5);
        //list of contacts created

        MeetingImpl meeting1 = new MeetingImpl(getId(), pastMeeting2, contactTwo);
        MeetingImpl meeting2 = new MeetingImpl(getId(), pastMeeting1, contactOne);
        MeetingImpl meeting3 = new FutureMeetingImpl(getId(), futureMeeting3, this.contacts);
        FutureMeetingImpl meeting4 = new FutureMeetingImpl(getId(), futureMeeting5, contactOne);
        // list of meetings created

        String note1 = "Meeting to be held at 25th street at the Plaza hotel";
        // notes to be added to PastMeeting

        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        //Meetings added to list


        for (Meeting meeting : meetings) {


            if (contactFour.equals(null) || futureMeeting5.equals(null) || note1.equals(null)) {
                throw new NullPointerException();

            }
            // A NullPointerException is thrown if either any of the objects are  null.


            if (futureMeeting5.equals(meeting.getDate()) && meeting.getDate().before(currentDate) && contactFour.equals(meeting.getContacts())) {
                //tests if the Meeting is the right meeting and is before current date


                System.out.println(" Instance of Past Meeting");
                PastMeetingImpl pastMeeting = new PastMeetingImpl(meeting.getId(), futureMeeting5, contactFour, note1);
                // creates PastMeeting

                int position = meeting.getId();
                assertEquals(meeting.getId(), 3);

                // checks that the Meeting will be placed in the right place in the list


                meetings.set(position, pastMeeting);
                //add PastMeeting to the list


            }



    }




    }

    @Test
    public void testAddMeetingNotes() throws Exception {
        Contact contact1 = new ContactImpl(" Dave");
        Contact contact2 = new ContactImpl(" jones");


        Set< Contact>contactOne = new HashSet<>();
        contactOne.add(contact1);
        contactOne.add(contact2);

        Set< Contact>contactTwo = new HashSet<>();
        contactOne.add(contact1);
        //list of contacts





        MeetingImpl meeting1 = new FutureMeetingImpl(getId(),futureMeeting4,contactOne);
        MeetingImpl meeting2 = new MeetingImpl      (getId(),pastMeeting1,contactTwo);
        MeetingImpl meeting3 = new FutureMeetingImpl(getId(),futureMeeting3,this.contacts);
        MeetingImpl meeting4 = new MeetingImpl      (getId(),pastMeeting2,contactOne);
        // created Meetings

        String note1 = "Meeting to be held at 16th street at the Plaza hotel";
        String note2 = "Meeting to be held at 25th street at the Plaza hotel";
        String note3 = null;

        // notes to be added


        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        // Meetings added to the list
        int id = 0;
        int id2 =3;
        // id of searched meeting
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
                if ( note1.equals(null) ){
                    throw new NullPointerException();
                }
                // throw NullPointerException if note is null


                if(id == meet.getId() && meet instanceof FutureMeetingImpl && meet.getDate().before(currentDate)){
                    // test if id of meeting exists, that is an instance of FutureMeeting and is not date before current date

                    assertEquals( id,meet.getId());
                    assertEquals(meet.getDate(),futureMeeting4);
                    assertEquals(meet.getContacts(),contactOne);

                    PastMeetingImpl pastMeeting = new PastMeetingImpl(meet.getId(), meet.getDate(),meet.getContacts(),note1);
                    // FutureMeeting changed into PastMeeting with added notes
                    System.out.println("ID:"+meet.getId());

                    meetings.set( meet.getId(), pastMeeting);
                    // PastMeeting replaced FutureMeeting in the list

                }


                if ( id == meet.getId() && meet.getDate().after(currentDate)){
                    throw new IllegalStateException();
                }

            }

            for (Meeting meeting : meetings) {

                if (futureMeeting5.equals(meeting.getDate()) && meeting.getDate().before(currentDate) && meeting instanceof PastMeetingImpl && contactOne.equals(meeting.getContacts())) {
                    System.out.println(" FutureMeeting converted to PastMeeting");
                    // tests if FutureMeeting has been changed to PastMeeting
                }}





            }

    @Test
    public void testAddNewContact() throws Exception {

        String name = "Michael Jackson";

        ContactImpl contact = new ContactImpl(name);
        contacts.add(contact);
        // contact added without note


        String name1 = "Samuel Jackson";



        // creates a list of contacts

        String notes = "Train driver";
        // note to be added

        if ( name1.equals(null)){
            throw new NullPointerException();
        }
        // throws NullPointerException if note is null

        ContactImpl contact2 = new ContactImpl(name1);
        contact.addNotes(notes);
        contacts.add(contact2);


        // adds contact with added with note




        for ( Contact con: contacts){

            System.out.println(" NAME:   " +con.getName());
            System.out.println(" Notes:  " +con.getNotes());
            System.out.println(" ID:     "+ con.getId());
        }
        // tests that contact with note is added


    }

    @Test
    public void testGetContacts() throws Exception {

        String name1 = " Edward Jackson";
        String notes1 = " business manager";
        ContactImpl contact1 = new ContactImpl(name1);
        contact1.addNotes(notes1);
        System.out.println("contact 1:"+ contact1.getId());

        String name2 = " James Williams";
        String notes2 = " Shop Keeper";
        ContactImpl contact2 = new ContactImpl(name2);
        contact2.addNotes(notes2);
        System.out.println("contact 2: "+contact2.getId());
        String name3 = " Jennifer Anderson";
        String notes3 = " Secretary";
        ContactImpl contact3 = new ContactImpl(name3);
        contact3.addNotes(notes3);
        System.out.println("contact 3 : "+contact3.getId());
        String name4 = " Jennifer Lopez";
        String notes4 = " Doctor";
        ContactImpl contact4 = new ContactImpl(name4);
        contact4.addNotes(notes4);
        System.out.println("contact 4:" +contact4.getId());
        String name5 = " Sue Space";
        String notes5 = " Zoo Keeper";
        ContactImpl contact5 = new ContactImpl(name5);
        contact5.addNotes(notes5);
        System.out.println("contact 5:" +contact5.getId());

        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        contacts.add(contact5);
        // list of contacts





        int []num= new int [2];
        num [0]=1;
        num [1]=3;
        // ids to be looked for saved in an array list


        Set<Contact> list = new HashSet<Contact>();


        int count=0;


        for ( int i: num){
            count++;
            System.out.println( " COUNT: "+ count);
        }
        // counts how many ids there are

        int result = contacts.size()-count;
        System.out.println(" result " + result);


        for (int id : num) {

            for (Contact contact: contacts) {

                System.out.println("contact");

                    if ( id == contact.getId() ) {

                        System.out.println("ID CONTACT  "+ contact.getId());
                        // checks if the correct contact with the id is added to the list

                        list.add(contact);
                        // adds the contacts with the ids to the another list
                        count--;



                    }

                }
            }

        if ( count != 0){
            throw new IllegalArgumentException();
        }
        // if count doesn't equal zeros means that all the ids numbers have not been found and IllegalArgumentException
        // is thrown
       for ( Contact contact: list){

           System.out.println(contact.getName());
           System.out.println(contact.getNotes());
           System.out.println(contact.getId());


       }

        // tests if the two contacts are successfully added


    }

    @Test
    public void testGetContacts1() throws Exception {

        ContactImpl one = new ContactImpl("Mark");
        ContactImpl two = new ContactImpl("Mark");
        ContactImpl three = new ContactImpl("Alice");
        ContactImpl four = new ContactImpl(" Edward");
        ContactImpl five = new ContactImpl(" Tammy");


        contacts.add(one);
        contacts.add(two);
        contacts.add(three);
        contacts.add(four);
        contacts.add(five);
        // created list

        String name = "Mark";



        Set <Contact> names = new HashSet<Contact>();
        for ( Contact contact: contacts){
            if ( name.equals(null)){
                throw new NullPointerException();
            }
            // if string is null NullPointerException thrown

            if ( name.equals(contact.getName())){
                names.add(contact);
                // creates a list
            }
        }
        assertEquals(names.size(),2);


        for ( Contact contact: names){
            if ( contact.getName().equals("Mark")){
                System.out.println(contact.getName());
                // testing if the two objects saved have the right name

        }}

    }
    public class MeetingComparator implements Comparator<Meeting> {
        @Override
        public int compare (Meeting first, Meeting second){
            return first.getDate().getTime().compareTo(second.getDate().getTime());
            // chronologically sorts the list in terms of date
        }}

    @Test
    public void testFlush() throws Exception {

        Contact a = new ContactImpl(" Hello");
        Set<Contact> contact1 = new HashSet<Contact>();
        contact1.add(a);


        Contact b = new ContactImpl(" john Harsh ");
        Set<Contact> contact2 = new HashSet<Contact>();
        contact1.add(b);
        contact2.add(b);


        MeetingImpl meet = new MeetingImpl(getId(), pastMeeting1, contact1);
        MeetingImpl meet2 = new MeetingImpl(getId(), pastMeeting2, contact2);
        meetings.add(meet);
        meetings.add(meet2);
        // add meeting contacts to the list


        String name1 = " James Jackson";
        ContactImpl contactOne = new ContactImpl(name1);
        String note1 = " Shop Keeper";
        contactOne.addNotes(note1);
        contacts.add(contactOne);

        String name2 = "  Sam Smith";
        ContactImpl contactTwo = new ContactImpl(name2);
        String notes2 = " Muscian";
        contactTwo.addNotes(notes2);
        contacts.add(contactTwo);

        // add contacts to the contact list




        try {
            FileOutputStream fileOut = new FileOutputStream(" z.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(meetings);
            out.writeObject(contacts);
            out.close();
            fileOut.close();
        } catch (IOException ex) {
        }
        // serializes the meeting and contact list



        }
    @Test
    public void startUp () throws Exception{
        List <Meeting> meeting = null;
        Set<Contact> contact = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(" z.ser"));
            meeting = (List<Meeting>) in.readObject();
            contact = (Set <Contact>)in.readObject();

        }
        catch(Exception e) {}

        finally {
            in.close();
        }
        // deserialises the saved data


         for ( Meeting meet: meeting){
             meetings.add(meet);
         }
        for ( Contact InContact: contact){
            contacts.add(InContact);
        }
        // adds the saved data to the list


        for ( Contact b: meetings.get(0).getContacts()){
            System.out.println(b.getName());
        }
        System.out.println(meetings.get(0).getDate().getTime());
        System.out.println(meetings.get(0).getId());



        for ( Contact b: meetings.get(1).getContacts()){
            System.out.println(b.getName());

        }
        System.out.println(meetings.get(1).getDate().getTime());
        System.out.println(meetings.get(1).getId());

        // tests if the deserialised data has the right values



        for ( Contact b: contacts){
            System.out.println(b.getName());
            System.out.println(b.getNotes());
            System.out.println(b.getId());

        }

        for ( Contact b: contacts){
            System.out.println(b.getName());
            System.out.println(b.getNotes());
            System.out.println(b.getId());

        }

        // tests if that the deserialized data of the contacts has the right value

        assertEquals( meetings.size(),2);
        assertEquals( contacts.size(),2);
        // check that the list is the right size















    }
    }










































