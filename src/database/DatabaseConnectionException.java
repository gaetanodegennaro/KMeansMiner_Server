package database;

/**
 * La classe estende Exception per modellare il fallimento della connessione al database.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * @see Exception
 *
 */

public class DatabaseConnectionException extends Exception
{
	/**
	 * Richiama il costruttore della superclasse specificando come parametro msg.
	 * 
	 * @param msg contiene il messaggio che descrive l'eccezione
	 */
	DatabaseConnectionException(String msg)
	{
		super(msg);
	}
}
