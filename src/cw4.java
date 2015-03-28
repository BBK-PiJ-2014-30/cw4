import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by devon on 14/03/2015.
 */
public class cw4 {
    public static void main(String[] args) {



        Calendar futureDate = new GregorianCalendar( 2015,9,9);
        Calendar pastDate = new GregorianCalendar( 2015,1,1);

        ContactManagerImpl contactManager = new ContactManagerImpl();

        Set <Contact>  contact1 = new HashSet<Contact>();
        ContactImpl name1 = new ContactImpl("devon");
        ContactImpl name2 = new ContactImpl("Sue");
        contact1.add (name1);
        contact1.add (name2);

        Set <Contact>  contact2 = new HashSet<Contact>();
        ContactImpl name3 = new ContactImpl("Carlos");
        ContactImpl name4 = new ContactImpl(" Sally");
        contact2.add (name3);
        contact2.add (name4);

        System.out.println(contactManager.addFutureMeeting(contact1, futureDate));
        System.out.println(contactManager.addFutureMeeting(contact2, futureDate));
        System.out.println(contactManager.getFutureMeeting(1).getDate().getTime());
        contactManager.addNewContact(" Mark Harrison"," at the department store");








    }
}
