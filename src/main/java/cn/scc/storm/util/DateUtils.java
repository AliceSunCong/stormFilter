package cn.scc.storm.util;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 * 
 * @author gongjian 2018年8月3日
 */
public class DateUtils {

	public static final String LOCALDATEPATTERN = "yyyy-MM-dd";
	public static final String LOCALDATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String LOCALDATETIMEPATTERNT = "yyyy-MM-ddTHH:mm:ss";
	public static final String TIMESTAMP = "TIMESTAMP";

	public static final DateTimeFormatter DF = DateTimeFormatter.ofPattern(LOCALDATETIMEPATTERN);

	private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

	public static Map<String, DateTimeFormatter> map = new HashMap<String, DateTimeFormatter>();

	/**
	 * 获取当前日期
	 * 
	 * @author Gongjian 2018年8月3日
	 * @return
	 */
	public static String getCurrentDate() {
		return getCurrentDate("");
	}
	
	/**
	 * 获取指定格式的当前日期
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		LocalDate localDate = LocalDate.now();
		String today = null;
		if (StringUtils.isNotEmpty(pattern)) {
			today = format(localDate, pattern);
		} else {
			today = format(localDate, LOCALDATEPATTERN);
		}
		return today;
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @author Gongjian 2018年8月3日
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime("");
	}

	/**
	 * 获取指定格式的当前日期时间
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateTime(String pattern) {
		LocalDateTime localDateTime = LocalDateTime.now();
		String today = null;
		if (StringUtils.isNotEmpty(pattern)) {
			today = format(localDateTime, pattern);
		} else {
			today = format(localDateTime, LOCALDATETIMEPATTERN);
		}
		return today;
	}

	/**
	 * 字符串转日期
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param date
	 * @return
	 */
	public static String formatDate(String date, String fromPattern, String toPattern) {
		DateTimeFormatter formatter = formatter(fromPattern);
		LocalDate localDate = LocalDate.parse(date, formatter);
		return format(localDate, toPattern);
	}

	/**
	 * 字符串转日期
	 * 
     * @author Gongjian 2018年8月3日
	 * @return
	 */
	public static String formatDateTime(String dateTime, String fromPattern, String toPattern) {
		DateTimeFormatter formatter = formatter(fromPattern);
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
		return format(localDateTime, toPattern);
	}

	/**
	 * 字符串转日期
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static LocalDate patternToLocalDate(String date, String pattern) {
		DateTimeFormatter formatter = formatter(pattern);
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	/**
	 * 日期类型格式化
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param localDate
	 * @param pattern
	 * @return
	 */
	public static String format(LocalDate localDate, String pattern) {

		DateTimeFormatter formatter = formatter(pattern);
		return localDate.format(formatter);
	}

	/**
	 * 字符串转日期时间
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param pattern
	 * @return
	 */
	public static LocalDateTime patternToLocalDateTime(String dateTime, String pattern) {

		DateTimeFormatter formatter = formatter(pattern);
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
		return localDateTime;
	}

	/**
	 * 日期时间类型格式化
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param pattern
	 * @return
	 */
	public static String format(LocalDateTime localDateTime, String pattern) {

		DateTimeFormatter formatter = formatter(pattern);
		return localDateTime.format(formatter);
	}

	/**
	 * 日期时间类型格式化
	 * 
	 * @author Gongjian 2018年9月6日
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 时间戳转日期
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param timeStamp
	 *            秒
	 * @param pattern
	 * @return
	 */
	public static String formatOfEpochSecond(Long timeStamp, String pattern) {

		DateTimeFormatter formatter = formatter(pattern);
		Instant instant = Instant.ofEpochSecond(timeStamp);
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		return localDateTime.format(formatter);
	}

	/**
	 * 时间戳转日期
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param timeStamp
	 *            毫秒
	 * @param pattern
	 * @return
	 */
	public static String formatOfEpochMilli(Long timeStamp, String pattern) {

		DateTimeFormatter formatter = formatter(pattern);
		Instant instant = Instant.ofEpochMilli(timeStamp);
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		return localDateTime.format(formatter);
	}

	/**
	 * 日期时间转时间戳 （秒数）
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static Long toInstantOfEpochSecond(String dateTime, String pattern) {
		LocalDateTime localDateTime = patternToLocalDateTime(dateTime, pattern);
		return toInstantOfEpochSecond(localDateTime);
	}

	public static Long toInstantOfEpochSecond(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zoneId).toInstant();
		return instant.getEpochSecond();
	}

	/**
	 * 日期时间转时间戳 （毫秒数）
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static Long toInstantOfEpochMilli(String dateTime, String pattern) {
		LocalDateTime localDateTime = patternToLocalDateTime(dateTime, pattern);
		return toInstantOfEpochMilli(localDateTime);
	}

	public static Long toInstantOfEpochMilli(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zoneId).toInstant();
		return instant.toEpochMilli();
	}

	/**
	 * date 转 LocalDateTime
	 * 
	 * @author Gongjian 2018年8月3日
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {


		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		return localDateTime;
	}

	/**
	 * date 转 LocalDate
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = instant.atZone(zoneId).toLocalDate();
		return localDate;
	}

	/**
	 * LocalDateTime 转 Date
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {

		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		return date;
	}

	/**
	 * LocalDate 转 Date
	 * 
	 * @author Gongjian 2018年8月3日
	 * @param localDate
	 * @return
	 */
	public static Date toDate(LocalDate localDate) {

		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
		Date date = Date.from(zdt.toInstant());
		return date;
	}

	public static Date toDate(String dateTime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取两个时间之间的分钟数
	 * 
	 * @author Gongjian 2018年9月4日
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long betweenMinute(String start, String end) {
		LocalDateTime s = patternToLocalDateTime(start, LOCALDATETIMEPATTERN);
		LocalDateTime e = patternToLocalDateTime(end, LOCALDATETIMEPATTERN);
		return betweenMinute(s, e);
	}

	public static Long betweenMinute(Date start, Date end) {

		LocalDateTime s = toLocalDateTime(start);
		LocalDateTime e = toLocalDateTime(end);
		return betweenMinute(s, e);
	}

	public static Long betweenMinute(LocalDateTime start, LocalDateTime end) {
		return ChronoUnit.MINUTES.between(start, end);
	}

	/**
	 * @author Gongjian 2018年8月3日
	 * @param pattern
	 * @return
	 */
	public static DateTimeFormatter formatter(String pattern) {

		DateTimeFormatter formatter = null;

		if (LOCALDATEPATTERN.equalsIgnoreCase(pattern)) {
			formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		} else if (LOCALDATETIMEPATTERN.equalsIgnoreCase(pattern)) {
			formatter = DF;
		} else if (LOCALDATETIMEPATTERNT.equalsIgnoreCase(pattern)) {
			formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		} else {
			String lower = pattern.toLowerCase();
			formatter = map.get(lower);

			if (formatter == null) {
				map.put(lower, DateTimeFormatter.ofPattern(pattern));
				formatter = map.get(lower);
			}
		}
		return formatter;
	}


	
	public static String getDate(LocalDateTime  date){
		int minute = date.getMinute();
		if(minute>=0&&minute<15){
			//00
			date=date.withSecond(0);
			date=date.withMinute(0);
		}
		
		if(minute>=15&&minute<30){
			//15
			date=date.withSecond(0);
			date=date.withMinute(15);
		}
		if(minute>=30&&minute<45){
			//30
			date=date.withSecond(0);
			date=date.withMinute(30);
		}
		if(minute>=45&&minute<60){
			//45
			date=date.withSecond(0);
			date=date.withMinute(45);
		}
		return DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/***
	 *
	 * @param date
	 *            时间
	 * @return cron类型的日期
	 */
	public static String getCron(final Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
		String formatTimeStr = "";
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}
	
	

}
