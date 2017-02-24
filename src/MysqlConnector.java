import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnector 
{
	static Connection mysqlConn;
	static Statement mysqlStmt;
	static Log log = Log.getJavaWriteLogInstance();
    static String mysql_driver = "org.gjt.mm.mysql.Driver";
    static String mysql_url = "jdbc:mysql://localhost:3306/cbirs";
    static String mysql_username = "cbirs";
    static String mysql_password = "cbirs";
    
	public static void openConnection() throws SQLException
	{
		try
		{
		    if (mysqlConn==null)
		    {
				log.printInfoLog("Opening new DB connection");
		    	Class.forName(mysql_driver);
		    	mysqlConn = DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
		    	mysqlStmt = mysqlConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		    }
		    if(getMysqlStmt()==null)
		    	mysqlStmt = mysqlConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);		   
		}
	    catch (SQLException sql)
	    {
	    	log.printErrorLog("Exception while opening mysql connection\n"+sql.getLocalizedMessage());

	    }	
	    catch (Exception e)
	    {
	    	log.printErrorLog("Exception while opening mysql connection\n"+e.getLocalizedMessage());
	    }
    	log.printInfoLog("DB connection opened");

	}
	
	
	static Statement newStatement() throws SQLException
	{
		Statement stmt = null;
		try
		{
		    if (mysqlConn==null)
		    {
				log.printInfoLog("Opening new DB connection");
		    	Class.forName(mysql_driver);
		    	mysqlConn = DriverManager.getConnection(mysql_url, mysql_username, mysql_password);		    	
		    }	
		    stmt = mysqlConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
	    catch (SQLException sql)
	    {
	    	log.printErrorLog("Exception while opening mysql connection\n"+sql.getLocalizedMessage());
	    }	
	    catch (Exception e)
	    {
	    	log.printErrorLog("Exception while opening mysql connection\n"+e.getLocalizedMessage());
	    }
		log.printInfoLog("New DB statement created");
    	return stmt;
	}
	
	public static boolean updateRecord(String sql)
	{
		if(log==null) log = Log.getJavaWriteLogInstance();

		try
		{
			if(getMysqlStmt()==null) openConnection();
			getMysqlStmt().executeUpdate(sql);
			return true;
		}	
		catch (Exception e)
		{
			log.printErrorLog("Exception while updating record\n"+Util.getStackTrace(e));
		}
		return false;
	}
	
	public static ResultSet getRecords(String sql)
	{
		ResultSet rs = null;
		if(log==null) log = Log.getJavaWriteLogInstance();

		try
		{
			if(getMysqlStmt()==null) openConnection();
			rs = getMysqlStmt().executeQuery(sql);			
		}	
		catch (Exception e)
		{
			log.printErrorLog("Exception while updating record\n"+Util.getStackTrace(e));
		}
		return rs;
	}
	
	static void closeConnection()
	{
		if(log==null)
			log = Log.getJavaWriteLogInstance();

		try
		{
			if (getMysqlStmt()!=null)
				getMysqlStmt().close();
			if(mysqlConn!=null)
				mysqlConn.close();		
		}
	    catch (Exception e)
	    {
	    	log.printErrorLog("Exception while closing mysql connection\n"+e.getLocalizedMessage());
	    }
	}
	/**
	 * @return the mysqlConn
	 */
	public static Connection getMysqlConn() {
		return mysqlConn;
	}

	/**
	 * @return the mysqlStmt
	 */
	public static Statement getMysqlStmt() {
		return mysqlStmt;
	}
	
}
