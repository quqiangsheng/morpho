package com.max256.morpho.common.security.encrypt;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 格式转换工具类
 * 仅供加密模块使用
 * @author fbf
 *
 */
public abstract class ConvertUtils {

    /**
     * DecimalFormat 格式化器
     */
    private static final DecimalFormat simpleFormat = new DecimalFormat("####");

    /**
     * 对象是boolean是的话返回boolean类型 如果为null返回false
     * @param o
     * @return
     */
    public static final boolean objectToBoolean(Object o){
        return o != null ? Boolean.valueOf(o.toString()).booleanValue() : false;
    }

    /**对象转int
     * @param o
     * @return 是数字的话转换int返回
     * 			是null返回-1
     * 			是其他不能转换成int类型的话返回-1
     */
    public static final int objectToInt(Object o){
        if(o instanceof Number)
            return ((Number)o).intValue();
        try{
            if(o == null)
                return -1;
            else
                return Integer.parseInt(o.toString());
        }catch(NumberFormatException e){
            return -1;
        }
    }
    /**对象转short
     * @param o
     * @return 是数字的话转换short返回
     * 			是null返回-1
     * 			是其他不能转换成short类型的话返回-1
     */
    public static final short objectToShort(Object o){
        if(o instanceof Number)
            return ((Number)o).shortValue();
        try{
            if(o == null)
                return -1;
            else
                return Short.parseShort(o.toString());
        }catch(NumberFormatException e){
            return -1;
        }
    }
    
    /**对象转double
     * @param o
     * @return 是数字的话转换double返回
     * 			是null返回-1D
     * 			是其他不能转换成double类型的话返回-1D
     */
    public static final double objectToDouble(Object o){
        if(o instanceof Number)
            return ((Number)o).doubleValue();
        try{
            if(o == null)
                return -1D;
            else
                return Double.parseDouble(o.toString());
        }catch(NumberFormatException e){
            return -1D;
        }
    }
    /**对象转long
     * @param o
     * @return 是数字的话转换long返回
     * 			是null返回-1L
     * 			是其他不能转换成long类型的话返回-1L
     */
    public static final long objectToLong(Object o)
    {
        if(o instanceof Number)
            return ((Number)o).longValue();
        try{
            if(o == null)
                return -1L;
            else
                return Long.parseLong(o.toString());
        }catch(NumberFormatException e){
            return -1L;
        }
    }
    /**对象转String
     * @param o
     * @return 是数字的话转换对应的数字的toString返回
     * 			是其他类型返回其toString方法返回的结果
     */
    public static final String objectToString(Object obj, DecimalFormat fmt)
    {	/*Allows you to set the behavior of the decimal separator with integers. 
    	(The decimal separator will always appear with decimals.) 
    	Example: Decimal ON: 12345 → 12345.; OFF: 12345 → 12345*/
        fmt.setDecimalSeparatorAlwaysShown(false);
        if(obj instanceof Double)
            return fmt.format(((Double)obj).doubleValue());
        if(obj instanceof Long)
            return fmt.format(((Long)obj).longValue());
        else
            return obj.toString();
    }

    public static final Object getObjectValue(String value)
    {
        try{
            return Long.valueOf(value);
        }catch(NumberFormatException e) 
        {
        	//转换失败的下 尝试下方的转换
        }

        try{
            return Double.valueOf(value);
        }catch(NumberFormatException e){
        	//如果double也无法转换 则返回原String
            return value;
        }
    }

    public static String longToSimpleString(long value){
        return simpleFormat.format(value);
    }

    public static String asHex(byte hash[]){
        return toHex(hash);
    }

    public static String toHex(byte input[]){
        if(input == null)
            return null;
        StringBuilder output = new StringBuilder(input.length * 2);
        for(int i = 0; i < input.length; i++){
            int current = input[i] & 0xff;
            if(current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }

        return output.toString();
    }

    public static byte[] fromHex(String input){
        if(input == null)
            return null;
        byte output[] = new byte[input.length() / 2];
        for(int i = 0; i < output.length; i++)
            output[i] = (byte)Integer.parseInt(input.substring(i * 2, (i + 1) * 2), 16);

        return output;
    }

    public static String stringToHexString(String input, String encoding)
        throws UnsupportedEncodingException{
        return input != null ? toHex(input.getBytes(encoding)) : null;
    }

    public static String stringToHexString(String input){
        try{
            return stringToHexString(input, "UTF-8");
        }catch(UnsupportedEncodingException e){
            throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
        }
    }

    public static String hexStringToString(String input, String encoding)
        throws UnsupportedEncodingException{
        return input != null ? new String(fromHex(input), encoding) : null;
    }

    public static String hexStringToString(String input){
        try{
            return hexStringToString(input, "UTF-8");
        }catch(UnsupportedEncodingException e){
            throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
        }
    }

    public static String timeZoneToCode(TimeZone tz){

        return timeZoneToString(tz);
    }

    public static TimeZone codeToTimeZone(String tzString){

        return stringToTimeZone(tzString);
    }

    public static String timeZoneToString(TimeZone tz){

        return tz != null ? tz.getID() : "";
    }

    public static TimeZone stringToTimeZone(String tzString){

        return TimeZone.getTimeZone(tzString != null ? tzString : "");
    }

    public static String localeToCode(Locale aLocale){

        return localeToString(aLocale);
    }

    public static Locale codeToLocale(String locString){

        return stringToLocale(locString);
    }

    public static String localeToString(Locale loc){

        return loc != null ? loc.toString() : "";
    }

    public static Locale stringToLocale(String locString){

        locString = locString != null ? locString.trim() : "";
        if(locString.equals(""))
            return new Locale("", "", "");
        int pos = locString.indexOf(95);
        if(pos == -1)
            return new Locale(locString, "", "");
        String language = locString.substring(0, pos);
        locString = locString.substring(pos + 1);
        pos = locString.indexOf(95);
        if(pos == -1){
            return new Locale(language, locString, "");
        }else{
            String country = locString.substring(0, pos);
            locString = locString.substring(pos + 1);
            return new Locale(language, country, locString);
        }
    }

    public static Date dateToSQLDate(java.util.Date d){

        return d != null ? new Date(d.getTime()) : null;
    }

    public static Time dateToSQLTime(java.util.Date d){

        return d != null ? new Time(d.getTime()) : null;
    }

    public static Timestamp dateToSQLTimestamp(java.util.Date d){

        return d != null ? new Timestamp(d.getTime()) : null;
    }

    public static java.util.Date sqlTimestampToDate(Timestamp t){

        return t != null ? new java.util.Date(Math.round((double)t.getTime() + (double)t.getNanos() / 1000000D)) : null;
    }

    public static Timestamp getCurrentDate(){

        Calendar c = Calendar.getInstance();
        c.set(c.get(1), c.get(2), c.get(5), 0, 0, 0);
        Timestamp t = new Timestamp(c.getTime().getTime());
        t.setNanos(0);
        return t;
    }

    public static java.util.Date getDate(int y, int m, int d, boolean inclusive)
    {
        java.util.Date dt = null;
        Calendar c = Calendar.getInstance();
        c.clear();
        if(c.getActualMinimum(1) <= y && y <= c.getActualMaximum(1))
        {
            c.set(1, y);
            if(c.getActualMinimum(2) <= m && m <= c.getActualMaximum(2))
            {
                c.set(2, m);
                if(c.getActualMinimum(5) <= d && d <= c.getActualMaximum(5))
                    c.set(5, d);
            }
            if(inclusive)
            {
                c.add(5, 1);
                c.add(14, -1);
            }
            dt = c.getTime();
        }
        return dt;
    }

    public static java.util.Date getDateStart(java.util.Date d)
    {

         Calendar c = new GregorianCalendar();
         c.clear();
         Calendar co = new GregorianCalendar();
         co.setTime(d);
         c.set(Calendar.DAY_OF_MONTH,co.get(Calendar.DAY_OF_MONTH));
         c.set(Calendar.MONTH,co.get(Calendar.MONTH));
         c.set(Calendar.YEAR,co.get(Calendar.YEAR));
         //c.add(Calendar.DAY_OF_MONTH,1);
         //c.add(Calendar.MILLISECOND,-1);
         return c.getTime();
    }

    public static java.util.Date getDateEnd(java.util.Date d)
    {
         Calendar c = Calendar.getInstance();
         c.clear();
         Calendar co = Calendar.getInstance();
         co.setTime(d);
         c.set(Calendar.DAY_OF_MONTH,co.get(Calendar.DAY_OF_MONTH));
         c.set(Calendar.MONTH,co.get(Calendar.MONTH));
         c.set(Calendar.YEAR,co.get(Calendar.YEAR));
         c.add(Calendar.DAY_OF_MONTH,1);
         c.add(Calendar.MILLISECOND,-1);
         return c.getTime();
    }

    public static double roundNumber(double rowNumber, int roundingPoint)
    {
        double base = Math.pow(10D, roundingPoint);
        return (double)Math.round(rowNumber * base) / base;
    }
    public static Object getObject(String type,String value) throws Exception{

    	type=type.toLowerCase();
    	if("boolean".equals(type))
            return Boolean.valueOf(value);
        if("byte".equals(type))
            return Byte.valueOf(value);
        if("short".equals(type))
            return Short.valueOf(value);
        if("char".equals(type))
            if(value.length() != 1)
                throw new NumberFormatException("Argument is not a character!");
            else
                return Character.valueOf(value.toCharArray()[0]);
        if("int".equals(type))
            return Integer.valueOf(value);
        if("long".equals(type))
            return Long.valueOf(value);
        if("float".equals(type))
            return Float.valueOf(value);
        if("double".equals(type))
            return Double.valueOf(value);
        if("string".equals(type))
            return value;
		else{
	        Object objs[]=new String[]{value};
			return Class.forName(type).getConstructor(new Class[] {
				    java.lang.String.class
				}).newInstance(objs);
		}
    }
    //构造函数私有化 不允许实例化调用
    private ConvertUtils(){}

//    public static void main(String[] args)
//    {
//    	System.out.println(getDateStart(new java.util.Date()));
//    }
}
