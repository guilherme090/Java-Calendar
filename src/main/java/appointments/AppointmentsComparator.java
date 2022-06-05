package appointments;

import java.util.Comparator;

public class AppointmentsComparator implements Comparator<Appointment>{
	
    public int compare (Appointment a, Appointment b) {
    	int dateTimeA = a.getYear() * 10000 +
    					a.getMonth() * 100 +
    					a.getDay();
    	int dateTimeB = b.getYear() * 10000 +
				b.getMonth() * 100 +
				b.getDay();
    	return dateTimeA-dateTimeB;
    }
}
