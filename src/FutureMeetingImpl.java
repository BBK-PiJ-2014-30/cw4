import java.util.Calendar;
import java.util.Set;

/**
 * Created by devon on 12/03/2015.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

    FutureMeetingImpl(int id, Calendar calendar, Set<Contact> contact) {
        super(id, calendar, contact);

    }
}

