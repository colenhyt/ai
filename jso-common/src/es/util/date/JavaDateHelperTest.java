package es.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class JavaDateHelperTest extends TestCase {
	public void test(){
		Calendar.getInstance().setTime(new Date());
		System.out.println(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
	}
	
	public void test1(){
		Date d=JavaDateHelper.getDate("2003-00-01", "yy-mm-dd");
		System.out.println(new java.sql.Date(d.getTime()));
	}
	public void testCompareMonth() {
		Date d=new JavaDateHelper().getDate("2006-01-14 09:44:00");
		Date d2=new JavaDateHelper().getDate("2007-12-15 13:54:00");
		assertTrue(new JavaDateHelper().getYear(d2)==2007);
		assertTrue(new JavaDateHelper().getMonth(d2)==12);
		assertTrue(new JavaDateHelper().getDay(d2)==15);
		assertTrue(new JavaDateHelper().getHour(d2)==13);
		assertTrue(new JavaDateHelper().getMinute(d2)==54);
		assertTrue(new JavaDateHelper().compareMonths(d, d2)==11);
		assertTrue(new JavaDateHelper().compareDaysOfMonth(d, d2)==1);
		assertTrue(new JavaDateHelper().compareHoursOfDay(d, d2)==4);
		assertTrue(new JavaDateHelper().compareMinutes(d, d2)==10);
		assertTrue(new JavaDateHelper().compareYears(d, d2)==1);
		
		Date date=new Date();
		assertTrue(new JavaDateHelper().getHour(date)==16);
		
		Calendar calendar=new SimpleDateFormat().getCalendar();
		calendar.setTime(date);
		Calendar c2=Calendar.getInstance();
		c2.setTime(date);
		System.out.println(c2.get(Calendar.HOUR_OF_DAY));
		
	}
	
	public void testCompareDayOfYear(){
		Date d=new JavaDateHelper().getDate("2007-03-13 09:44:00");
		System.out.println(new JavaDateHelper().compareDaysOfYear(d));
		
	}

}
