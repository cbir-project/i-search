import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;


public class Util 
{
	static Properties properties;
	static Log log = Log.getJavaWriteLogInstance();
	FileInputStream fis;
	
	public static BigInteger getRandomClientID()
	{
		int n = 16;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

        Random r = new Random();
        byte[] b = new byte[n];
        r.nextBytes(b);
        BigInteger i = new BigInteger(b);
        
        return i;
	}
	
	
	public static BigInteger getRandomBigInteger (BigInteger digits) 
	{
	    String sNumber = "";
		if(log==null)
			log = Log.getJavaWriteLogInstance();

	    for (BigInteger index = BigInteger.ZERO; index.compareTo(digits) < 0; index = index.add(BigInteger.ONE) ) 
	    {
	        char c = Double.toString(Math.random()*10).charAt(0);
	        sNumber += c;
	    }
	    return new BigInteger(sNumber);
	}

	
	/**
	 * This method will return the random number between from & to input arguments.
	 * 
	 * @param : from is the int, to pass from number.
	 * @param : to is the int, to pass to number.
	 * @return : random number will return as int.
	 */
	
	public static int getRandomNo_FromTo(int from, int to )
	{
	   int Random_No = 0;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

	   try {
		   
		  int fromNo = 0;
		  int toNo = 0;
		  
		  if(from < to){
			  fromNo = from;
			  toNo = to;
		  } else {
			  fromNo = to;
			  toNo = from;
		  }
	     
		  Random_No = (new Random().nextInt(toNo-fromNo) + fromNo);
		  
		  if(Random_No == 0) {
			  if(new Random().nextInt() > 0)
				  Random_No = 1;
		  }//if 
		  
	   } catch(Exception exp){
		   
	   }
	   
	 return Random_No;
	 
	}
	
	public String formatNumber(String number)
	{
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try
		{
	    	DecimalFormat df = new DecimalFormat();
	    	DecimalFormatSymbols dfs = new DecimalFormatSymbols();    
	    	dfs.setGroupingSeparator(',');
	    	df.setDecimalFormatSymbols(dfs);
	    	number = df.format(Integer.parseInt(number));
		}
		catch(Exception e)
		{
			log.printErrorLog("exception while formating numbers\n"+ e.getMessage());
		}
		return number;
	}
	
	

	public boolean searchInString(String totString, String searchStr) 
	{

		boolean strFound = false;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try {

			if ((totString != null && !("").equals(totString))
					&& (searchStr != null && !("").equals(searchStr))) {

				if (totString.indexOf(searchStr) != -1) {
					strFound = true;
				}
			}
		} catch (Exception ex) {
			log.printErrorLog("exception while seraching string in string\n"+ ex.getMessage());
		} finally {
			totString = null;
			searchStr = null;
		}

		return strFound;
	}// EOM

	
	public boolean isEmpty(String inputString) 
	{
		if (null == inputString || ("").equals(inputString)) {
			return true;
		} else {
			return false;
		}// if
	}// EOM

	
	
	public static boolean isNotEmpty(String inputString) 
	{
		if (null != inputString && !("").equals(inputString)) 
		{
			return true;
		} else {
			return false;
		}// if
	}// EOM
	

	
	public String getOnlyString(String inputString) 
	{
		StringBuilder stringBuild = new StringBuilder();
		for (int i = 0; i < inputString.length(); ++i) 
		{
			char c = inputString.charAt(i);
			int j = (int) c;
			if ((j >= 65 && j <= 90) || (j >= 97 && j <= 122)) 
			{
				stringBuild.append(c);
			}// if
		}// for

		return stringBuild.toString();
	}// EOM
	

	public String CreateTimeStamp() 
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd_HH-mm-ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		return sdf.format(cal.getTime());
	}//EOM


	
	public String getCurrentTimeStamp( String dateFormat ) 
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );

		return sdf.format(cal.getTime());
	}//EOM

	public boolean isTrue(String s) 
	{
		return Boolean.valueOf(s);
	}
	public static boolean isGoodString(String input) 
	{
		return ((input != null) && (input.trim().length() > 0));
	}

	public static String getStackTrace(Throwable t) 
	{
		return getStackTrace(null, t, -1);
	}
	public static String getStackTrace(String title, Throwable t, int depth) 
	{
	        StringBuilder stackTrace = new StringBuilder();
	        if (isGoodString(title))
	        {
	            stackTrace.append("******** ").append(title).append('\n');
	        }

	        while(t != null) 
	        {
	            if(stackTrace.length() > 0) {
	                stackTrace.append("\tCaused by: ");
	            }

	            stackTrace.append(t);
	            stackTrace.append('\n');

	            StackTraceElement[] st = t.getStackTrace();

	            if (depth < 1 || depth > st.length) {
	                depth = st.length;
	            }

	            for(int i=0; i<depth; i++) 
	            {
	                stackTrace.append('\t');
	                stackTrace.append(st[i].toString());
	                stackTrace.append('\n');
	            }
	            t = t.getCause();
	        }
	        return stackTrace.toString();
	}
	
	public static void sleep(int time)
	{
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try
		{
			Thread.sleep(1000*time);
		}
		catch (Exception e)
		{
			log.printErrorLog("error while trying to sleep\n"+getStackTrace(e));
		}
		
	}
	public boolean compareDates(String Date1) 
	{
		long mSec = 0;
		long TodaySec = 0;
		boolean GreaterFlag = false;

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		// String Date1 = "15-FEB-2010";

		try 
		{
			Date AppDate = sdf.parse(Date1);
			log.printInfoLog("AppDate = " + sdf.format(AppDate));
			// Display number of milliseconds since midnight, January 1, 1970
			// GMT
			mSec = AppDate.getTime();
			log.printInfoLog(""+mSec);

		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}

		Date currDate = new Date();
		log.printInfoLog("Today:" + sdf.format(currDate));
		// Display number of milliseconds since midnight, January 1, 1970 GMT
		TodaySec = currDate.getTime();
		log.printInfoLog(""+TodaySec);

		if (mSec < TodaySec) 
		{
			GreaterFlag = true;

		}
		else 
		{
			GreaterFlag = false;
		}

		return GreaterFlag;

	}//EOM

	public static String convertDateFormat(String fromFormat, String toFormat, String strDate)
	{
		if(log==null)
			log = Log.getJavaWriteLogInstance();
		Date dt=null;	
		
		try
		{
			dt = new SimpleDateFormat(fromFormat).parse(strDate);
			//dt = prevDate(dt);
			SimpleDateFormat format = new SimpleDateFormat(toFormat);
			strDate = format.format(dt);			
		}
		catch(Exception e)
		{
			log.printErrorLog("exception while converting the date format\n"+ e.getMessage());
		}
		return strDate;
	}	
	
	
	public String convertDateTimeToTimeZone(String toConvertDate) 
	{  
		String convertedDate = "";  
		DateFormat istFormat = new SimpleDateFormat("dd-MMM-yy hh:mm");  
		DateFormat gmtFormat = new SimpleDateFormat("dd-MMM-yy HH:mm");  
		TimeZone gmtTime = TimeZone.getTimeZone("CST");  
		TimeZone istTime = TimeZone.getTimeZone("IST");  
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		istFormat.setTimeZone(gmtTime);  
		gmtFormat.setTimeZone(istTime);  

		Date date = null;  
		try 
		{  
			  date = (Date) istFormat.parseObject(toConvertDate);  
		  } 
		catch (ParseException ex) 
		{  
			log.printInfoLog("Error occured at dateFormat:" + ex);  
		}  
		log.printInfoLog("CST Time: " + istFormat.format(date));  
		log.printInfoLog("IST Time: " + gmtFormat.format(date));  
		return convertedDate;  
		  
	}	
	
	public static long getDays(String start, String end, String format)
	{
		final long MILLIS_PER_DAY = 24 * 3600 * 1000;
		SimpleDateFormat sdf = new SimpleDateFormat(format); //"MM/dd/yyyy"
		long msDiff=0,daysDiff=0;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try
		{
			Date sDate = sdf.parse(start);
			Date eDate = sdf.parse(end);		
			msDiff= eDate.getTime() - sDate.getTime();
			daysDiff = Math.round(msDiff / ((double)MILLIS_PER_DAY));		
		}
		catch (ParseException ex) 
		{  
			log.printErrorLog("Error while finding number of days between 2 dates" + ex); 
			return -1;
		}  
		return daysDiff+1;
	}

	public static int getDayOfWeek(String date, String format)
	{
		int dayOfWeek = -1;
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try
		{
			Date sDate = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		}
		catch (ParseException ex) 
		{  
			log.printErrorLog("Error while finding number of days between 2 dates" + ex); 
			return -1;
		}  
		return dayOfWeek-1;
	}
	
	public static long getDays(Date sDate, Date eDate)
	{
		final long MILLIS_PER_DAY = 24 * 3600 * 1000;
		long msDiff=0,daysDiff=0;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		msDiff= eDate.getTime() - sDate.getTime();
		daysDiff = Math.round(msDiff / ((double)MILLIS_PER_DAY));  
		return daysDiff+1;
	}
	
	public static int getDayOfWeek(Date sDate)
	{
		int dayOfWeek = -1;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		Calendar c = Calendar.getInstance();
		c.setTime(sDate);
		dayOfWeek = c.get(Calendar.DAY_OF_WEEK);  
		return dayOfWeek-1;
	}
	
	static Date getPrevDateFrom(String date, int minusDays, String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date mDate = null;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try
		{
			Date sDate = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			c.add(Calendar.DAY_OF_MONTH, minusDays*-1);
			mDate = c.getTime();
		}
		catch (ParseException ex) 
		{  
			log.printErrorLog("Error while finding number of days between 2 dates" + ex); 
		}  
		return mDate;
	}

	public static String getTodayDate(String format)
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	public static Date nextDate(Date date)
	{
		Date mDate = null;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		mDate = new java.sql.Date(c.getTime().getTime());  
		return mDate;
	}
	
	public static Date prevDate(Date date)
	{
		Date mDate = null;
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -1);
		mDate = new java.sql.Date(c.getTime().getTime());  
		return mDate;
	}
	
	public static java.sql.Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return new java.sql.Date(cal.getTime().getTime());
    }

	public static double roundToDecimals(double d, int c)  
	{   
	   int temp = (int)(d * Math.pow(10 , c));  
	   return ((double)temp)/Math.pow(10 , c);  
	}
	public static double diff(double a, double b)
	{
		return Math.abs(a-b);
	}

	public static double diff(long a, long b)
	{
		return Math.abs(a-b);
	}
	
	public static boolean contains(String key,String strArray[])
	{
		for (int i=0;i<strArray.length;++i) {
			if(strArray[i].contains("pref")) {
				if(key.contains("pref"))
					return true;
			}
			else if(key.equalsIgnoreCase(strArray[i]))
				return true;
		}
		return false;
	}
	
	public static String addOtherAttributesToQuery(String [] attr, String query)
	{
		
		//for attr not in the query, add null check
		for(int i=0; i < attr.length; i++) {
			if(!query.contains(attr[i])) {
				query = query + " AND " + attr[i] + " is null";
			}
		}
		return query;
	}
	

	
	public static boolean isStrNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	
	static String getIds(String id, String key, String[] orginalAttr)
	{
		String [] strId,strKey;	
		String str ="";
		int l,i,j;
		strId= id.split(",");
		strKey= key.split(",");
		
		for(i=0,j=0; i<orginalAttr.length; i++) {
			if(orginalAttr[i].equalsIgnoreCase(strKey[j])) {
				str = str + strId[j] + ",";
				if(j < strKey.length - 1) 
					j++;
			}
			else 
				str = str + ",";
		}
		str = str.substring(0,str.length()-1);
		return str;
	}
	
	
	static String getKey(String[] attributeList)
	{
		String key = "";
		for (int k=0;k<attributeList.length;++k)
		{   										
			
				if(key.equals(""))
					key = attributeList[k];
				else
					key = key + "," + attributeList[k];
		}
		
		return key;
	}
	
	public static Properties loadPropertyFile(String filePath) throws IOException
	{
		Properties pro = new Properties() {
		    @Override public Set<Object> keySet() 
		    {
		        return new TreeSet<Object>(super.keySet());
		    }
		};
		File f = new File(filePath);
		if (f.exists()) 
		{				
			FileInputStream in = new FileInputStream(f);
			pro.load(in);
		} 
		else 
		{
			log.printErrorLog("Property File not found!");
			//we can not proceed with test..we should exit
		}
		return pro;
	}//EOM
	
	public static void setKeyValue(String key, String value, Properties properties)
	{		
		if (key!=null && !key.equals(""))
			properties.setProperty(key,value);
	}

	public static String getKeyValue(String key, String defaultValue, Properties properties)
	{
		String value;
		if (key!=null && !key.equals(""))
			value = properties.getProperty(key);
		else if (defaultValue!=null && !defaultValue.equals(""))
			value = defaultValue;
		else
			value = "";
		return value;
	}
	
	public static String getKeyValue(String key, String defaultValue)
	{
		String value;
		if (key!=null && !key.equals(""))
			value = getProperties().getProperty(key);
		else if (defaultValue!=null && !defaultValue.equals(""))
			value = defaultValue;
		else
			value = "";
		return value;
	}
	
	public static String getKeyValue(String key)
	{
		String value;
		if (key!=null && !key.equals(""))
			value = getProperties().getProperty(key);
		else
			value = "";
		return value;
	}

	
	public static String readFile(String filePath) 
	{
		StringBuffer contents =null;
		String fileSeparator = System.getProperty("file.separator");
		String frameWorkPath =System.getProperty("user.dir");
		try
		{
			File file = new File(filePath);
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;
			contents = new StringBuffer();
			BufferedReader reader = null;			
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			while ((text = reader.readLine()) != null) 
			{
				contents.append(text).append(System.getProperty("line.separator"));
			}
			
			fis = new FileInputStream(file);
			dis = new DataInputStream(fis);
			fis.close();
			dis.close();
		}
		catch (Exception e)
		{
			log.printErrorLog("failed while reading html report file\n"+getStackTrace(e));
			return "";
		}
		return contents.toString();
	}
		
   public static String getShortStackTrace(String e)
   {
		int a = 1024;
		int b = e.length();
		String str = e;
		str = str.substring(0, (a > b) ? b : a);
		return str;
   }

   static public String customFormat(String pattern, double value ) 
   {
	      DecimalFormat myFormatter = new DecimalFormat(pattern);
	      String output = myFormatter.format(value);
	      return output;
   }

	/**
	 * @return the properties
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public static void setProperties(Properties properties) throws IOException
	{
		Util.properties = properties;
	}
	
	
	public static boolean isImage(String path)
	{
		String extension="";
		for(int i=(path.length()-1);i>=0;i--)
		{
			String ch=String.valueOf(path.charAt(i));
			if(ch.equals("."))
			{
				break;
			}
			extension+=ch;
		}
		if(extension.equalsIgnoreCase("PMB"))
		{
			return true;
		}
		else if(extension.equalsIgnoreCase("gpj"))
		{
			return true;
		}

		else if(extension.equalsIgnoreCase("fig"))
			return true;
		else
		return false;
	}
}
