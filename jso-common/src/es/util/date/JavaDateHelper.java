package es.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.util.pattern.ESPattern;


public class JavaDateHelper {
	public static final String SIMPLE_DATE_PATTERN="([\\d]{2}|[\\d]{4}){1}[-][\\d]{1,2}[-][\\d]{1,2}";
	public static final String SQLSERVER_SM_PATTERN="yyyy-MM-dd k:m:s";
	public static final String SQLSERVER_SM_PATTERN5="yyyy-MM-dd/k:m:s";
	public static final String SQLSERVER_SM_PATTERN2="yyyy-MM-dd k:m";
	public static final String SQLSERVER_SM_PATTERN3="MM/dd k:m";
	public static final String SQLSERVER_SM_PATTERN4="yyyy-MM-dd";
	public static final String SQLSERVER_SM_PATTERN6="yyyy/MM/dd k:m";
	public static final String SQLSERVER_SM_PATTERN7="MM-dd";
	public static final String PATTERN_YYMM="yyyy-MM";
	public SimpleDateFormat format=new SimpleDateFormat(SQLSERVER_SM_PATTERN);
	
	public Date getFormatDate(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.getTime();
	}
	
//	public static boolean isLegalDate
	public static Date getFormatDate(String dateStr,String pattern){
		SimpleDateFormat format2=new SimpleDateFormat(pattern);
		try {
			format2.parse(dateStr);
			return format2.getCalendar().getTime();
		} catch (ParseException e) {
			// log error here
			System.out.println("wrong date string:"+dateStr);
		}
		return null;
	}
	
	public int getMinute(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}
	
	public int getHour(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}	
	
	public int getDay(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}	
	
	public int getWeekOfYear(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	public int getWeekOfMonth(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	public long getValue(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	
	public int getMonth(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}	
	
	public int getYear(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}	
	
	public static Date getDate(String date){
		try {
			if (date!=null){
				if (ESPattern.matches(SIMPLE_DATE_PATTERN,date))
					date=date+" 00:00:00";
			return new SimpleDateFormat(SQLSERVER_SM_PATTERN).parse(date);
			}
		} catch (ParseException e) {
			// log error here
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getCurrentDate(){
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String getCurrentTime(){
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
	}
	
	public static String getYMDStr(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		
	}
	public static Date getDate(String date,String pattern){
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			// log error here
			e.printStackTrace();
		}
		return null;
	}	
	public int getSecond(Date date){
		Calendar calendar=format.getCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}	
	
	public int compareMonths(Date sDate){
		format.getCalendar().setTime(new Date());
		return getMonth(format.getCalendar().getTime())-getMonth(sDate);
	}	
	public int compareDaysOfMonth(Date sDate){
		format.getCalendar().setTime(new Date());
		return getDay(format.getCalendar().getTime())-getDay(sDate);
	}		
	
	public long compareDaysOfYear(Date sDate){
		Date date=new Date();
		long dd=date.getTime()-sDate.getTime();
		return dd/(1000*60*60*24);
	}
	
	public long compareWeeksOfYear(Date sDate){
		Date date=new Date();
		long dd=date.getTime()-sDate.getTime();
		return dd/(1000*60*60*24*7);
	}	
	
	public long getComparison(Date sDate){
		format.getCalendar().setTime(new Date());
		return format.getCalendar().getTime().getTime()-sDate.getTime();
	}	
	
	public int compareYears(Date sDate){
		format.getCalendar().setTime(new Date());
		return getYear(format.getCalendar().getTime())-getYear(sDate);
	}	
	
	public int compareHoursOfDay(Date sDate){
		format.getCalendar().setTime(new Date());
		return getHour(format.getCalendar().getTime())-getHour(sDate);
	}	
	
	public int compareMinutes(Date sDate){
		int a=getMinute(sDate);
		format.getCalendar().setTime(new Date());
		int b=getMinute(format.getCalendar().getTime());
		return b-a;
	}		
	
	public int compareMonths(Date fDate,Date sDate){
		return getMonth(sDate)-getMonth(fDate);
	}
	
	public int compareDaysOfMonth(Date fDate,Date sDate){
		return getDay(sDate)-getDay(fDate);
	}		
	
	public long compareDaysOfYear(Date fDate,Date sDate){
		Date date=new Date();
		long dd=date.getTime()-fDate.getTime();
		return dd/(1000*60*60*24);
	}
	
	public int compareYears(Date fDate,Date sDate){
		return getYear(sDate)-getYear(fDate);
	}	
	
	public int compareHoursOfDay(Date fDate,Date sDate){
		return getHour(sDate)-getHour(fDate);
	}	
	
	public int compareMinutes(Date fDate,Date sDate){
		return getMinute(sDate)-getMinute(fDate);
	}	
}
