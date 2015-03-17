import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by devon on 14/03/2015.
 */
public class cw4 {
    public static void main(String[] args) {

        String date = " 13/3/2015 10:30";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar calendar1 = Calendar.getInstance();
        String date2 = " 1/1/2016 19:45";
        Calendar calendar2 = Calendar.getInstance();
        String date3 = " 1/1/2016 19:45";




        try {
            calendar.setTime(stf.parse(date));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }



        try {

            calendar1.setTime(stf.parse(date2));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        try{
            calendar2.setTime(stf.parse(date3));
        }catch (ParseException e1               ){
            e1.printStackTrace();
        }
       System.out.println(calendar.getTime());
        Contact a = new ContactImpl(" David");





    }
}
