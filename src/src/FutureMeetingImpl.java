import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by devon on 12/03/2015.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting,Serializable {

FutureMeetingImpl (int id, Calendar date, Set<Contact> contact){


        super ( id,date,contact );



}


}
