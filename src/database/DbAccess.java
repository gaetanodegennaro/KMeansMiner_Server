package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess
{
	private final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";
	private final String DATABASE ="MapDB";
	private final String PORT = "3306";
	private final String USER_ID = "MapUser";
	private final String PASSWORD = "map";
	private Connection conn;
	
	public void initConnection() throws DatabaseConnectionException
	{
		try
		{
			Class.forName(DRIVER_CLASS_NAME);
			try
			{
				conn = DriverManager.getConnection(DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE, USER_ID, PASSWORD);
			}
			catch(SQLException e) {throw new DatabaseConnectionException("Error while opening database connection.");}
		}
		catch(ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public Connection getConnection() {return conn;}
	
	void closeConnection() throws DatabaseConnectionException
	{
		try
		{
			conn.close();
		}
		catch(SQLException e) {throw new DatabaseConnectionException("Error while closing database connection.");}
	}
}
