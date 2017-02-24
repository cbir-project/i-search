

import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Log

{
	private PrintStream ps; // declare a print stream object

	// need this java log instance in many places, so creating Singleton for
	private static Log log; 
	// constant label, using in so many places & doing validations 
	private final String infoLabel = "INFO"; 
	private final String errorLabel = "ERROR"; 
	private final String failureLabel = "FAILURE"; 
	private final String warningLabel = "WARNING";

	
	public Log(String logFileName) 
	{
		//String filePath;
		String fileSeparator = System.getProperty("file.separator");
		//filePath = "log" + fileSeparator;
		//String logFile = logFileName;

		try 
		{	/*		
			if (!isValidPath(filePath)) 
			{
				boolean logsPath = (new File(filePath)).mkdir();
				System.out.println("Logs directory path created, " + logsPath);
			}// if
			*/
			// Create a new file output stream	
			File dir = new File("log");
			dir.mkdirs();
			File outputFile = new File(dir,logFileName);			
			FileOutputStream outStream = new FileOutputStream(outputFile, true);
			ps = new PrintStream(outStream);
			ps = System.out;
			System.setOut(ps);			

		} 
		catch (Exception ex) 
		{
			System.out.println("Exception in WriteLog(String logFile ) : "+ ex.getMessage());
		}
	}

	
	/**
	 * It will return WriteLog singleton instance for writing java logs only.
	 * 
	 * @return : WriteLog instance
	 */
	public static Log getJavaWriteLogInstance() 
	{
		if (log == null) 
		{
			synchronized (Log.class) 
			{
				log = new Log("cbirs.log"); //WriteLog.getCurrTimeStamp()
			}
		}
		return log;
	}

	public static Log getJavaWriteLogInstance(String aClassName) 
	{
		if (log == null) 
		{
			synchronized (Log.class) 
			{
				log = new Log(Log.getCurrTimeStamp()+".log");
			}
		}
		
		return log;
	}


	/**
	 * This method will write the message into the log file
	 * 
	 * @param message
	 *            is the string type
	 * @return <None>
	 */
	public void printInfoLog(String message) 
	{
		ps.println("["+getCurrTimeStamp()+"] ["+infoLabel+"] ["+message+"]"); //["+getCallingClassDetails()+"

	}// EOM
	
	/**
	 * This method will write the message into the log file
	 * 
	 * @param message
	 *            is the string type
	 * @return <None>
	 */
	public void printWarningLog(String message) 
	{
		ps.println("["+getCurrTimeStamp()+"] ["+warningLabel+"] ["+message+"]"); //["+getCallingClassDetails()+"]
		
	}
	
	/**
	 * This method will write the message into the log file with **ERROR:
	 * keyword
	 * 
	 * @param message
	 *            is the string type
	 * @return <None>
	 */
	public void printErrorLog(String message) 
	{
		ps.println("["+getCurrTimeStamp()+"] ["+errorLabel+"] ["+message+"]"); //["+getCallingClassDetails()+"]

	}

	/**
	 * This method will write the message into the log file with **Failure	 * 
	 * @param message
	 *            is the string type
	 * @return <None>
	 */
	public void printFailureLog(String message) 
	{
		ps.println("["+getCurrTimeStamp()+"] ["+failureLabel+"] ["+message+"]");

	}

	/**
	 * This method will write the new line into the log file
	 * 
	 * @param <None>
	 * @return <None>
	 */
	public void printBlankLog() 
	{
		ps.println("");

	}

	/**
	 * This method will close the print stream instance
	 * 
	 * @param <None>
	 * @return <None>
	 */
	public void flush() 
	{
		ps.close();
	}

	/**
	 * This method will return the current date and time in the format of
	 * yyyy-MM-dd HH:mm:ss:SSS
	 * 
	 * @param <None>
	 * @return String
	 */
	public static String getCurrTimeStamp() 
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		return sdf.format(cal.getTime());

	}

}
