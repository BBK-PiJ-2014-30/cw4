import junit.framework.TestCase;
import org.junit.Test;

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
    private Set <Contact> contact;
    private List <Meeting> meetings;

    private   int id =0;

    public ContactManagerImplTest (){

        this.meetings = new ArrayList<Meeting>();




    }

    private int setId (){
        return this.id++;

    }
    private int getId () {
        return setId();

    }


    @Test
    public void testAddFutureMeeting() throws Exception {


        Meeting meeting1 = new FutureMeetingImpl (getId(),futureMeeting1,this.contact);
        Meeting meeting2 = new FutureMeetingImpl (getId(),futureMeeting2,this.contact);
        Meeting meeting3 = new FutureMeetingImpl(getId(),meetingInPast,this.contact);

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


        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,this.contact);
        Meeting meeting2 = new MeetingImpl (getId(),pastMeeting2,this.contact);
        Meeting meeting3 = new FutureMeetingImpl(getId(),futureMeeting2,this.contact);
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

        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,this.contact);
        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,this.contact);
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
        Meeting meeting1 = new MeetingImpl (getId(),pastMeeting1,this.contact);
        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,this.contact);
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
        ContactImpl contact2 = new ContactImpl(" James");
        ContactImpl contact3 = new ContactImpl(" tom");
        ContactImpl contact4 = new ContactImpl(" jones");
        ContactImpl contact5 = new ContactImpl(" tammy");
        ContactImpl contact6 = new ContactImpl(" Alison");

        Set< Contact>contactOne = new HashSet<>();
        contactOne.add(contact1);
        contactOne.add(contact4);
        contactOne.add(contact5);
        contactOne.add(contact6);

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
        Meeting meeting1 = new MeetingImpl      (getId(),pastMeeting1,this.contact);
        Meeting meeting2 = new FutureMeetingImpl(getId(),futureMeeting2,this.contact);
        Meeting meeting3 = new FutureMeetingImpl(getId(),pastMeeting1,this.contact);
        Meeting meeting4 = new FutureMeetingImpl(getId(),futureMeeting3,this.contact);
        Meeting meeting5 = new MeetingImpl      (getId(),pastMeeting2,this.contact);

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


        MeetingImpl meeting1 = new FutureMeetingImpl(getId(),futureMeeting2,this.contact);
        MeetingImpl meeting2 = new MeetingImpl      (getId(),pastMeeting1,contactOne);
        MeetingImpl meeting3 = new FutureMeetingImpl(getId(),futureMeeting3,this.contact);
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
        MeetingImpl meeting3 = new FutureMeetingImpl(getId(),futureMeeting3,this.contact);
        MeetingImpl meeting4 = new MeetingImpl      (getId(),pastMeeting2,contactOne);

        String note1 = "Meeting to be held at 16th street at the Plaza hotel";
        String note2 = "Meeting to be held at 25th street at the Plaza hotel";
        String note3 = null;


        meetings.add(meeting1);
        meetings.add(meeting2);
        meetings.add(meeting3);
        meetings.add(meeting4);
        int id = 0;
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
                meetings.set(0,pastMeeting);
                futureMeet.add(pastMeeting );
            }

            if ( id == meet.getId() && meet.getDate().before(currentDate) &&!(meet instanceof FutureMeeting)){
                PastMeeting pastMeetingTwo = new PastMeetingImpl(meet.getId(),meet.getDate(),meet.getContacts(),note1);
                meetings.set(meet.getId(),pastMeetingTwo);
                pastMeet.add(pastMeetingTwo);


            }
            if ( id == meet.getId() && meet.getDate().after(currentDate)){
                throw new IllegalStateException();
            }

        }



    }

    @Test
    public void testAddNewContact() throws Exception {

    }

    @Test
    public void testGetContacts() throws Exception {

    }

    @Test
    public void testGetContacts1() throws Exception {

    }
    public class MeetingComparator implements Comparator<Meeting> {
        @Override
        public int compare (Meeting first, Meeting second){
            return first.getDate().getTime().compareTo(second.getDate().getTime());
        }}

    @Test
    public void testFlush() throws Exception {

    }
}