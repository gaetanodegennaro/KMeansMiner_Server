package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Modella l'accesso alla base di dati.
 * La classe si occupa di effettuare una connessione alla base di dati sfruttando i driver di connessione mysql.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class DbAccess
{
	/**
	 * Nome del driver utilizzato per realizzare la connessione al database.<br>
	 * Di default: "org.gjt.mm.mysql.Driver".
	 * 
	 */
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	
	/**
	 * Tipo del DBMS utilizzato.<br>
	 * Di default: "jdbc:mysql".
	 */
	private static final String DBMS = "jdbc:mysql";
	
	/**
	 * Indirizzo sul quale richiedere una connessione. Coincide con l'indirizzo sul quale risiede il DBMS.<br>
	 * Di default: "localhost".
	 */
	private String SERVER = "localhost";
	
	/**
	 * Nome del database.<br>
	 * Di default: "MapDB"
	 */
	private String DATABASE ="MapDB";
	
	/**
	 * Porta sulla quale richiedere una connessione.<br>
	 * Di default: 3306
	 */
	private String PORT = "3306";
	
	/**
	 * User id per l'autenticazione
	 */
	transient private String USER_ID = "MapUser";
	
	/**
	 * Password per l'autenticazione
	 */
	transient private String PASSWORD = "map";
	
	/**
	 * Oggetto per gestire la connessione al database
	 */
	private Connection conn;
	
	public DbAccess() {}
	
	/**
	 * Alloca un nuovo oggetto di tipo DBAccess inizializzando lo stato dell'oggetto con i valori passati per parametro.
	 * 
	 * @param server indirizzo del server
	 * @param database nome del database
	 * @param port porta sulla quale richiedere la connessione
	 * @param user_id user id per l'autenticazione
	 * @param password password per l'autenticazione
	 */
	public DbAccess(String server, String database, String port, String user_id, String password)
	{
		this.SERVER = server;
		this.DATABASE = database;
		this.PORT = port;
		this.USER_ID = user_id;
		this.PASSWORD = password;
	}
	
	/**
	 * Richiede al class loader che venga caricato il driver {@link #DRIVER_CLASS_NAME}.
	 * Inizializza inoltre la connessione.
	 * 
	 * @throws DatabaseConnectionException sollevata se si verificano problemi durante l'apertura della connessione
	 */
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
	
	/**
	 * Restituisce il riferimento all'oggetto {@link #conn}.
	 * Se la connessione non è mai stata stabilita (il metodo {@link #initConnection()} non è mai stato chiamato), restituisce null.
	 * 
	 * @return {@link #conn}
	 * @see initConnection()
	 */
	public Connection getConnection() {return conn;}
	
	/**
	 * Chiude la connessione con il database.
	 * 
	 * @throws DatabaseConnectionException sollevata se si verificano problemi durante la chiusura della connessione, ad esempio se
	 * la funzione {@link #initConnection()} non è mai stata chiamata su questo oggetto.
	 * 
	 * @see initConnection()
	 */
	void closeConnection() throws DatabaseConnectionException
	{
		try
		{
			conn.close();
		}
		catch(SQLException e) {throw new DatabaseConnectionException("Error while closing database connection.");}
	}
}
