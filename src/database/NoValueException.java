package database;

/**
 * Sollevata quando dopo un'interrogazione di aggregazione ad un database, non ci sono risultati 
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class NoValueException extends Exception
{
	NoValueException(String msg)
	{
		super(msg);
	}
	
}

