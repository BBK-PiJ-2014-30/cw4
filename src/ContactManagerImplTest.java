import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
public class ContactManagerImplTest {


    Calendar futureMeeting1 = new GregorianCalendar( 2015,9,9);

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

        Set< Contact>contactOne = new HashSet<>();
        contactOne.add(contact1);
        contactOne.add(contact2);
        contactOne.add(contact3);
        contactOne.add(contact4);

        Set< Contact>contactFour = new HashSet<>();

        contactFour.add(contact1);
        contactFour.add(contact2);
        contactFour.add(contact3);
        contactFour.add(contact4);


        Set< Contact>contactTwo = new HashSet<>();
        contactTwo.add(contact5);


        Calendar date1 = null;
        Calendar date2 = new GregorianCalendar(2015,2,1);


        MeetingImpl meeting1 = new FutureMeetingImpl(getId(),futureMeeting2,this.contacts);
        MeetingImpl meeting2 = new MeetingImpl      (getId(),pastMeeting1,contactOne);
        MeetingImpl meeting3 = new FutureMeetingImpl(getId(),futureMeeting3,this.contacts);
        MeetingImpl meeting4 = new MeetingImpl      (getId(),pastMeeting2,contactTwo);

        String note1 = "Meeting to be held at 25th street at the Plaza hotel";
        String note2 = null;

        int id = 0;

        assertEquals( date2,pastMeeting1);

        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        System.out.println("TEST   " + meetings.get(0).getDate().getTime());


        if ( date2.equals(null) || contactFour.equals(null) || note1.equals( null)){

            throw new NullPointerException();
        }


        for ( Meeting meeting: meetings){

             if ( !contactFour.contains(meeting.getContacts())){
                    System.out.println( " does not contain");
                }


            if (contactFour.equals(null)  || date2.equals(null) || note1.equals(null)){
                throw new NullPointerException();

            }
            if( date2.equals(meeting.getDate())){
                System.out.println(" date contains within the list");
            }
            if (date2.equals(meeting.getDate()) && meeting.getDate().before(currentDate)&& contactFour.equals(meeting.getContacts())  ){

                System.out.println(" ID "+ id);
                System.out.println (" Instance of Past Meeting");
                PastMeetingImpl pastMeeting = new PastMeetingImpl(meeting.getId(),date2,contactFour,note1);
                TestCase.assertNotNull(meetings.get(id));
                assertEquals(meeting.getDate(),date2);
                assertNotNull(meetings.get(id));
                meetings.set(1, pastMeeting);


                assertEquals(meetings.get(0).getDate().getTime(), futureMeeting2.getTime());
                assertEquals(meetings.get(1).getDate().getTime(),pastMeeting1.getTime());
                assertEquals(meetings.get(2).getDate().getTime(),futureMeeting3.getTime());
                assertEquals(meetings.get(3).getDate().getTime(), pastMeeting2.getTime());


            }
            else if ( contactFour.equals(meeting.getContacts())){
                System.out.println( " contacts in the list");

            }





        }








        // tests if new record of meeting is created



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





        MeetingImpl meeting1 = new FutureMeetingImpl(getId(),futureMeeting4,contactOne);
        MeetingImpl meeting2 = new MeetingImpl      (getId(),pastMeeting1,contactTwo);
        MeetingImpl meeting3 = new FutureMeetingImpl(getId(),futureMeeting3,this.contacts);
        MeetingImpl meeting4 = new MeetingImpl      (getId(),pastMeeting2,contactOne);

        String note1 = "Meeting to be held at 16th street at the Plaza hotel";
        String note2 = "Meeting to be held at 25th street at the Plaza hotel";
        String note3 = null;


        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        int id = 0;
        int id2 =3;
        List <Meeting> futureMeet = new ArrayList<Meeting>();
        List <Meeting> pastMeet = new ArrayList<Meeting>();



        for ( Meeting meet: meetings){
            if ( note1.equals(null) ){
                throw new NullPointerException();
            }

            if(id == meet.getId() && meet instanceof FutureMeetingImpl && meet.getDate().before(currentDate)){

                assertEquals( id,meet.getId());
                assertEquals(meet.getDate(),futureMeeting4);
                assertEquals(meet.getContacts(),contactOne);

                PastMeetingImpl pastMeeting = new PastMeetingImpl(meet.getId(), meet.getDate(),meet.getContacts(),note1);

                futureMeet.add(pastMeeting);
            }

            if ( id2 == meet.getId() && meet.getDate().before(currentDate) &&!(meet instanceof FutureMeeting)){
                PastMeeting pastMeetingTwo = new PastMeetingImpl(meet.getId(),meet.getDate(),meet.getContacts(),note1);

                pastMeet.add(pastMeetingTwo);


            }
            if ( id == meet.getId() && meet.getDate().after(currentDate)){
                throw new IllegalStateException();
            }

        }
        assertEquals(futureMeet.size(),1);
        assertEquals(futureMeet.get(0).getDate(),futureMeeting4);

        assertEquals(pastMeet.size(), 1);
        assertEquals(pastMeet.get(0).getDate(), pastMeeting2);



    }

    @Test
    public void testAddNewContact() throws Exception {

        String name = " Edward Jackson";
        String notes = " business manager";
        ContactImpl contact = new ContactImpl(name);
        contact.addNotes(notes);
        System.out.println(" ID ADD NEW CONTACT "+ contact.getId());
        contacts.add(contact);
        String name1 = "  Jackson";
        String notes1 = " bu manager";
        ContactImpl contact2 = new ContactImpl(name1);
        contact.addNotes(notes1);
        contacts.add(contact2);
        System.out.println(" SSSIZZZE"+ contacts.size());
        System.out.println(" ID ADD NEW CONTACT "+ contact2.getId());

        for ( Contact con: contacts){
            System.out.println(" NAME   " +con.getName());
            System.out.println(" ID     "+ con.getId());
        }






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




        int []num= new int [3];
        num [0]=24;
        num [1]=21;
        num [2]=23;

        Set<Contact> list = new HashSet<Contact>();


        int count=0;


        for ( int i: num){
            count++;
            System.out.println( " COUNT: "+ count);
        }

        int size = contacts.size();
        System.out.println(" SIZE "+ size);


        int result = contacts.size()-count;
        System.out.println(" result " + result);





        for (int id : num) {

            for (Contact contact: contacts) {

                System.out.println("contact");





                    if ( id == contact.getId() ) {

                        System.out.println("ID CONTACT  "+ contact.getId());
                        list.add(contact);
                        size--;

                        System.out.println( " COUNT decrement: "+ size);

                    }

                    else if (id != contact.getId()){
                        boolean s = false;
                    }






                }
            }
        System.out.println( " LIST SIZE"+ list.size());
        System.out.println( " size " + size);
        System.out.println("result  "+ result);








    }

    @Test
    public void testGetContacts1() throws Exception {

        ContactImpl one = new ContactImpl("Mark");
        System.out.println("start " +one.getId());
        ContactImpl two = new ContactImpl("Mark");
        System.out.println(two.getId());
        ContactImpl three = new ContactImpl("Alice");
        System.out.println(three.getId());
        ContactImpl four = new ContactImpl(" Sam");
        System.out.println(four.getId());
        ContactImpl five = new ContactImpl(" Sam");
        System.out.println("end "+ five.getId());
        contacts.add(one);
        contacts.add(two);
        contacts.add(three);
        contacts.add(four);
        contacts.add(five);

        String name = "Mark";



        Set <Contact> names = new HashSet<Contact>();
        for ( Contact contact: contacts){
            if ( name.equals(null)){
                throw new NullPointerException();
            }
            else if ( name.equals(contact.getName())){
                names.add(contact);
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

        MeetingImpl meet = new MeetingImpl(20, pastMeeting1, contact1);
        MeetingImpl meet2 = new MeetingImpl(20, pastMeeting2, contact2);
        meetings.add(meet);
        meetings.add(meet2);

        List <Meeting> list = new ArrayList<Meeting>();

        for(Meeting me: meetings){
            list.add(me);
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(" z.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(meetings);
            out.close();
            fileOut.close();
        } catch (IOException ex) {
        }



        }
    @Test
    public void startUp () throws Exception{
        List <Meeting> meeting = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(" z.ser"));
            meeting = (List<Meeting>) in.readObject();

        }
        catch(Exception e) {}
        
        finally {
            in.close();
        }


         for ( Meeting meet: meeting){
             meetings.add(meet);
         }
        System.out.println(meetings.get(0).getDate().getTime());



    }
    }










































