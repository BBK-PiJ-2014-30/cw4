import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

public class ContactManagerImplTest {


    Calendar futureMeeting1 = new GregorianCalendar( 2015,9,9);

    Calendar futureMeeting2 = new GregorianCalendar( 2015,9,15);
    Calendar futureMeeting3 = new GregorianCalendar( 2015,11,11);
    Calendar meetingInPast = new GregorianCalendar( 2015,2,9);
    Calendar pastMeeting1 = new GregorianCalendar(2015,2,1);
    Calendar pastMeeting2 = new GregorianCalendar(2015,2,7);
    Calendar currentDate = Calendar.getInstance();
    private Set <Contact> contact;
    private List <Meeting> meetings;
    private Calendar calendar;
    private int id;
    int requestedId = 0;
    int requestedId2 =1;
    int requestedId3 = 2;






    public ContactManagerImplTest (){
        this.meetings = new ArrayList<Meeting>();
        this.id =0;



    }

    public int getId() {

        return ++this.id;
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

        assertEquals(1,meeting1.getId());
        assertEquals(3,meeting3.getId());
        assertEquals(2,meeting2.getId());


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

            if ( contain ){
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

            if ( contain ){
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
    public void testGetPastMeetingList() throws Exception {

    }

    @Test
    public void testAddNewPastMeeting() throws Exception {

    }

    @Test
    public void testAddMeetingNotes() throws Exception {

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