package data;

/**
 * Lanciata se il numero di cluster k è superiore al numero di tuple distinte presenti nel database, o se minore di 1.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class OutOfRangeSampleSize extends Exception
{
	public OutOfRangeSampleSize(String msg)
	{
		super(msg);
	}
	
}
