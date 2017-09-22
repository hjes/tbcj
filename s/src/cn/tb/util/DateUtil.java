package cn.tb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.helper.StringUtil;


/**
 * 日期工具�?获取日期时间，格式化日期时间�?
 * 
 * @author wangxianchao
 * datetime 2015-09-17
 */
public class DateUtil {

	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 把String类型日期格式化成java.util.Date类型 默认�?yyyy-MM-dd HH:mm:ss 格式
	 * 
	 * @param datestr 字符型日�?
	 * @return java.util.Date类型
	 * @throws java.text.ParseException
	 */
	public static Date parseDate(String datestr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATETIME);
		return sdf.parse(datestr);
	}

	 /**
     * <li>功能描述：时间相减得到天�?
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static int getDaySub(String beginDateStr,String endDateStr)
    {
        int day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);    
            day=(int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000));    
            //System.out.println("相隔的天�?"+day);   
        } catch (ParseException e)
        {
            // TODO 自动生成 catch �?
            e.printStackTrace();
        }   
        return day;
    }
	
	
	
	
	/**
	 * 把String类型日期格式化成java.util.Date类型
	 * 
	 * @param datestr 字符型日�?
	 * @param pattern 格式化样式�?如：yyyy-MM-dd HH:mm:ss
	 * @return java.util.Date类型
	 * @throws java.text.ParseException
	 */
	public static Date parseDate(String datestr, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(datestr);
	}

	/**
	 * 把DATE类型按指定格式格式化后返回String 返回类型�?yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param dt 以前的时�?
	 * @return yyyy-MM-dd HH:mm:ss 格式时间
	 */
	public static String formatDate(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATETIME);
		return sdf.format(dt);
	}

	/**
	 * 把DATE类型按指定格式格式化后返回String
	 * 
	 * @param dt 以前的时�?
	 * @param dateFormat �?��的格�?
	 * @return 用户指定格式的时�?
	 */
	public static String formatDate(Date dt, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(dt);
	}

	/**
	 * 将字符串类型时间格式化成指定格式的字符串
	 * 
	 * @param strdate 字符串类型的时间
	 * @param dateFormat �?��返回的格�?String类型的yyyy-MM-dd格式的字符串 转指定格式的Date类型
	 * @return 用户指定格式的时�?
	 */
	public static String formatDate(String strdate, String dateFormat) {
		String toDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		SimpleDateFormat sdf_to = new SimpleDateFormat(dateFormat);
		try {
			Date dt = sdf.parse(strdate);
			toDate = sdf_to.format(dt);
		} catch (ParseException e) {
			System.err.print("字符转换时间错误�?");
		} catch (Exception e) {
			System.err.print("时间格式化错误！");
		}
		return toDate;
	}

	/**
	 * 当前日期
	 * 
	 * @return yyyy-MM-dd 格式时间
	 */
	public static String currentDateString() {

		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
		String mDateTime = dateFormat.format(currentTime());
		return mDateTime;
	}
	
	/**
	 * 当前时间
	 * 
	 * @return HH:mm:ss 格式时间
	 */
	public static String currentTimeString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String mDateTime = dateFormat.format(currentTime());
		return mDateTime;
	}
	/**
	 * 昨天日期
	 * 
	 * @return yyyy-MM-dd 格式时间
	 */
	public static String yesterdayDateString() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
		String mDateTime = dateFormat.format(yesterdayTime());
		return mDateTime;
	}

	/**
	 * 当前日期时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 格式时间
	 */
	public static String currentDateTimeString() {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(PATTERN_DATETIME);
		String mDateTime = dateTimeFormat.format(currentTime());
		return mDateTime;
	}
	/**
	 * 当前日期时间
	 * 
	 * @return yyyyMMddHHmmss 格式时间
	 */
	public static String currentDateTimeStringf() {
		
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String mDateTime = dateTimeFormat.format(currentTime());
		return mDateTime;
	}

	/**
	 * 当前日期时间
	 * 
	 * @return java.util.Date类型的当前时�?
	 */
	public static Date currentTime() {

		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 昨天日期时间
	 * 
	 * @return java.util.Date类型的当前时�?
	 */
	public static Date yesterdayTime() {
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		return cal.getTime();
	}
	/**
	 * 当前日期时间后几�?
	 * 
	 * @return java.util.Date类型的当前时�?
	 */
	public static Date currentTime(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	/**
	 * 由指定日期计算指定月数后的日�?
	 * 
	 * @return yyyy-MM-dd 格式时间
	 * @throws java.text.ParseException
	 */
	public static String getDateByResetMonth(String datestr, int months) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(datestr));
		cal.add(Calendar.MONTH, months);
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
		String redate = dateFormat.format(cal.getTime());
		return redate;
	}

	/**
	 * 由指定日期计算指定月数后的日�?
	 * 
	 * @return java.util.Date类型
	 * @throws java.text.ParseException
	 */
	public static Date getDateByResetMonth(Date date, int months) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 由指定日期计算指定天数后的日�?
	 * 
	 * @return yyyy-MM-dd 格式时间
	 * @throws java.text.ParseException
	 */
	public static String getDateByResetDay(String datestr, int days) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(datestr));
		cal.add(Calendar.DATE, days);
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
		String redate = dateFormat.format(cal.getTime());
		return redate;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getDateByResetDay(currentDateTimeString(),1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    /**
     * 日期加天数得到新日期: 如果2012-12-12  �? �?012-12-15
     * @param date 当前日期
     * @param days   加上相应天数(可以为负�?
     * @return
     */
    public static Date addDayNumsDate(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return  calendar.getTime();
    }
	/**
	 * 给指定日期加上指定天数后得到的日�?
	 * 
	 * @param sDate
	 * @param DayNums
	 * @return yyyy-MM-dd 格式时间
	 */
	public static String addDayNumsDate(String sDate, int DayNums) {
		String mDateTime = "";
		if (sDate == null)
			return null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
			Date StartDate = dateFormat.parse(sDate);
            Date endDate = addDayNumsDate(StartDate, DayNums);
//			long lTime = (StartDate.getTime() / 1000) + 60 * 60 * 24 * DayNums;
//			StartDate.setTime(lTime * 1000);
			mDateTime = dateFormat.format(endDate);
		} catch (Exception ex) {
			System.err.print(ex.getMessage());
		}

		return mDateTime;
	}

	/**
	 * 两个指定日期之间相隔的天�?
	 * 
	 * @param sDate
	 * @param eDate
	 * @return 天数
	 */
	public static long dateDayInteval(String sDate, String eDate) {
		long day = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
			Date StartDate = dateFormat.parse(sDate);
			Date EndDate = dateFormat.parse(eDate);
			day = (EndDate.getTime() - StartDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception ex) {
			System.err.print(ex.getMessage());
		}
		return day;
	}
	/**
	 * 两个指定日期之间相隔的天�?
	 * 
	 * @param sDate
	 * @param eDate
	 * @return 天数
	 */
	public static long dateDayInteval(String sDate, String eDate,String format) {
		long day = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date StartDate = dateFormat.parse(sDate);
			Date EndDate = dateFormat.parse(eDate);
			day = (EndDate.getTime() - StartDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception ex) {
			System.err.print(ex.getMessage());
		}
		return day;
	}
	
	/**
	 * 两个指定日期之间相隔的分�?
	 * 
	 * @param sDate
	 * @param eDate
	 * @param format 日期格式
	 * @return 天数
	 */
	public static long dateDayIntevalMinuteTime(String sDate, String eDate,String format) {
		long day = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date StartDate = dateFormat.parse(sDate);
			Date EndDate = dateFormat.parse(eDate);
			day = (EndDate.getTime() - StartDate.getTime()) / (60 * 1000);
		} catch (Exception ex) {
			System.err.print(ex.getMessage());
		}
		return day;
	}
 
	/**
	 * 根据指定日期确定星期�?
	 *
	 * @param date 日期
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String getWeekDay(String date) throws ParseException {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		Date d = parseDate(date, PATTERN_DATE);
		cal.setTime(d);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	
	public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
        return new SimpleDateFormat( "dd").format(cal.getTime());  
    }   
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
        return new SimpleDateFormat( "dd").format(cal.getTime());  
     }   
    /**
     * 
     * @param 得到几天后的12�?
     * @param day
     * @return
     */
    public static Date getSomeDaySomeTime(Date date,int day){
    	date.setDate(date.getDate()+day);
    	date.setHours(12);
    	date.setMinutes(0);
    	date.setSeconds(0);
    	return date;
    }
    /**
     * 判断是否规定结账点前入住
     * @param DATE1 大的话返回True 
     * @param DATE2 大的话返回false
     * @param format 默认格式 HH:mm:ss 
     * @return
     */
 public static boolean compare_date(String format,String Date1,String Date2) {
		 if(StringUtil.isBlank(format)){
			 format="yyyy-MM-dd HH:mm:ss";
		 }
	     DateFormat df = new SimpleDateFormat(format);//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd  
	     try {  
	         Date dt1 = df.parse(Date1);//将字符串转换为date类型  
	         Date dt2 = df.parse(Date2);  
	         if(dt1.getTime()>dt2.getTime())//比较时间大小,如果dt1大于dt2  
	         {  
	        	 return true;
	         }  
	         else  
	         {  
	        	 return false;
	         }  
	     } catch (ParseException e) {  
	    	  return false;
	     }  
    }
    
}
