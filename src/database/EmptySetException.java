package database;

/**
 * Eccezione personalizzata lanciata se la tabella da cui si leggono i dati da database è vuota.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class EmptySetException extends Exception
{
	public EmptySetException(String msg)
	{
		super(msg);
	}
}
