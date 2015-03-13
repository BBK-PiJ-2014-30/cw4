import org.junit.Test;

import java.util.*;
import static org.junit.Assert.*;
public class ContactManagerImplTest {


    Calendar futureMeeting = new GregorianCalendar( 2015,9,9);
    Calendar meetingInPast = new GregorianCalendar( 2015,2,9);
    Calendar currentDate = Calendar.getInstance();
     private Set <Contact> contact;
     private List <Meeting> meetings;
     private Calendar calendar;
     private static  int id;
     int pastMeetingId =2;

    public ContactManagerImplTest (){
        this.meetings = new ArrayList<Meeting>();


    }

    private int setId (){
       return ++this.id;

    }
    private int getId () {
        return setId();

    }




    @Test
    public void testAddFutureMeeting() throws Exception {

        // throws IllegalArgumentException if meeting is set for a time in the past.
        if ( futureMeeting.before(currentDate)){


            throw new IllegalArgumentException();}
        // test if the new meeting object return correct given id number

        MeetingImpl meeting = new FutureMeetingImpl (getId(),futureMeeting,this.contact);
        assertEquals(1,meeting.getId());
       // test if date saved successful saved in the object

        assertEquals( futureMeeting,meeting.getDate());
        MeetingImpl meetingTwo = new FutureMeetingImpl (getId(),meetingInPast,this.contact);
        // test if the next save meeting returns correct ID number

        assertEquals(2,meetingTwo.getId());
        // tests that if the contact object is pasted successfully within it

        assertEquals( this.contact, meetingTwo.getContacts());

        // test if the added meetings equal the amount
        meetings.add(meeting);
        meetings.add(meetingTwo);


        assertEquals( 2,meetings.size());



    }

    @Test
    public void testGetPastMeeting() throws Exception {
        // if meeting is a future meeting throws IllegalArgumentException
        if ( meetings.get(pastMeetingId).getDate().after(currentDate)){
            throw new IllegalArgumentException();
        }

        // tests if requested ID is the requested ID
        Meeting pastMeeting;
        pastMeeting = meetings.get(pastMeetingId);


        assertEquals(meetingInPast, pastMeeting.getDate());
        assertEquals( this.contact,pastMeeting.getContacts());







    }

    @Test
    public void testGetFutureMeeting() throws Exception {

    }

    @Test
    public void testGetMeeting() throws Exception {

    }

    @Test
    public void testGetFutureMeetingList() throws Exception {

    }

    @Test
    public void testGetFutureMeetingList1() throws Exception {

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

    @Test
    public void testFlush() throws Exception {

    }
}