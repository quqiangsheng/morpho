package com.max256.morpho.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * 日期时间工具类 基于joda类库封装
 * 
 * 使用joda替代jdk自带的日期格式化类，因为 DateFormat 和 SimpleDateFormat 类不都是线程安全的
 * 确保不会在多线程状态下使用同一个 DateFormat 或者 SimpleDateFormat 实例
 * 如果多线程情况下需要访问同一个实例，那么请用同步方法
 * 可以使用 joda-time 日期时间处理库来避免这些问题，如果使用java 8, 请切换到 java.time包
 * 你也可以使用 commons-lang 包中的 FastDateFormat 工具类
 * 另外你也可以使用 ThreadLocal 来处理这个问题	 
 * 
 * @author fbf
 * 
 */
public class DateUtils {

	/**
	 * 中国默认东八区 国际化的应用时请注意修改
	 */
	public final static String DEFAULT_TIMEZONE = "GMT+8";

	public final static String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	public final static String ISO_SHORT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public final static String SHORT_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	public final static String FULL_SEQ_FORMAT = "yyyyMMddHHmmssSSS";

	public final static String[] MULTI_FORMAT = { DEFAULT_DATE_FORMAT,
			DEFAULT_TIME_FORMAT, ISO_DATE_TIME_FORMAT,
			ISO_SHORT_DATE_TIME_FORMAT, SHORT_TIME_FORMAT, "yyyy-MM" };

	public final static String FORMAT_YYYY = "yyyy";

	public final static String FORMAT_YYYYMM = "yyyyMM";

	public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

	public final static String FORMAT_YYYYMMDDHH = "yyyyMMddHH";

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static Integer formatDateToInt(Date date, String format) {
		if (date == null) {
			return null;
		}
		return Integer.valueOf(new SimpleDateFormat(format).format(date));
	}

	public static Long formatDateToLong(Date date, String format) {
		if (date == null) {
			return null;
		}
		return Long.valueOf(new SimpleDateFormat(format).format(date));
	}

	public static String formatShortTime(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(SHORT_TIME_FORMAT).format(date);
	}

	public static String formatTime(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(date);
	}

	/**
	 * @Title:getDiffDay
	 * @Description:获取日期相差天数
	 * @param:@param beginDate Date类型开始日期
	 * @param:@param endDate Date类型结束日期
	 * @param:@return
	 * @return:Long 相差天数
	 * @author: 谢
	 * @thorws:
	 */
	public static Long getDiffDay(Date beginDate, Date endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strBeginDate = format.format(beginDate);

		String strEndDate = format.format(endDate);
		return getDiffDay(strBeginDate, strEndDate);
	}

	/**
	 * @Title:getDiffDay
	 * @Description:获取日期相差天数
	 * @param:@param beginDate 字符串类型开始日期
	 * @param:@param endDate 字符串类型结束日期
	 * @param:@return
	 * @return:Long 日期相差天数
	 * @author:谢
	 * @thorws:
	 */
	public static Long getDiffDay(String beginDate, String endDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Long checkday = 0l;
		// 开始结束相差天数
		try {
			checkday = (formatter.parse(endDate).getTime() - formatter.parse(
					beginDate).getTime())
					/ (1000 * 24 * 60 * 60);
		} catch (ParseException e) {

			e.printStackTrace();
			checkday = null;
		}
		return checkday;
	}

	public static String getHumanDisplayForTimediff(Long diffMillis) {
		if (diffMillis == null) {
			return "";
		}
		long day = diffMillis / (24 * 60 * 60 * 1000);
		long hour = (diffMillis / (60 * 60 * 1000) - day * 24);
		long min = ((diffMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long se = (diffMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		StringBuilder sb = new StringBuilder();
		if (day > 0) {
			sb.append(day + "D");
		}
		DecimalFormat df = new DecimalFormat("00");
		sb.append(df.format(hour) + ":");
		sb.append(df.format(min) + ":");
		sb.append(df.format(se));
		return sb.toString();
	}

	public static Integer getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String yearString = formatDate(date, FORMAT_YYYY);
		int weekNum = c.get(Calendar.WEEK_OF_YEAR);
		if (weekNum < 10) {
			yearString = StringUtils.rightPad(yearString, 5, "0");
		}
		return Integer.valueOf(yearString + weekNum);
	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(DateUtils.formatDate(c.getTime(),
				DateUtils.FORMAT_YYYYMMDD));

		DateUtils.formatDate(new Date(), DEFAULT_DATE_FORMAT);

		Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})");
		Matcher m = p.matcher(DateUtils.formatDate(new Date(),
				DEFAULT_DATE_FORMAT));

		if (m.find()) {
			System.out.println("日期:" + m.group());
			System.out.println("年:" + m.group(1));
			System.out.println("月:" + m.group(2));
			System.out.println("日:" + m.group(3));
		}

		String time = "2015/11/02 ";

		System.out.println(parseDate(time, "yyyy/MM/dd"));
	}

	/**
	 * N天之后
	 * 
	 * @param n
	 * @param date
	 * @return
	 */
	public static Date nDaysAfter(Integer n, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + n);
		return cal.getTime();
	}

	/**
	 * N天之前
	 * 
	 * @param n
	 * @param date
	 * @return
	 */
	public static Date nDaysAgo(Integer n, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - n);
		return cal.getTime();
	}

	/**
	 * 把类似2014-01-01 ~ 2014-01-30格式的单一字符串转换为两个元素数组
	 */
	public static Date[] parseBetweenDates(String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		date = date.replace("～", "~");
		Date[] dates = new Date[2];
		String[] values = date.split("~");
		dates[0] = parseMultiFormatDate(values[0].trim());
		dates[1] = parseMultiFormatDate(values[1].trim());
		return dates;
	}

	public static Date parseDate(String date) {
		return parseDate(date, DEFAULT_DATE_FORMAT);
	}

	public static Date parseDate(String date, String format) {
		if (date == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseMultiFormatDate(String date) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(date,
					MULTI_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parseTime(String date) {
		return parseTime(date, DEFAULT_TIME_FORMAT);
	}

	public static Date parseTime(String date, String format) {
		if (date == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String plusOneDay(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		return formatDate(dateTime.plusDays(1).toDate());
	}

	public static String plusOneDay(String date) {
		DateTime dateTime = new DateTime(parseDate(date).getTime());
		return formatDate(dateTime.plusDays(1).toDate());
	}

}
