package box.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static public Date formatDate(String strYear,String strMonth,String strDay,String strTime)
	{
		
		Date dd = new Date();
		try {
			dd = format.parse(strYear+"-"+strMonth+"-"+strDay+" "+strTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dd;
	}
}
