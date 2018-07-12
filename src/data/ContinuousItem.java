package data;

/**
 * Estende {@link Item} per modellare un Attributo di tipo continuo.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class ContinuousItem extends Item
{
	/**
	 * Invoca il costruttore della super classe per inizializzare attribute e value.
	 * 
	 * @param attribute Attributo coinvolto.
	 * @param value Valore dell'attributo coinvolto.
	 */
	public ContinuousItem(ContinuousAttribute attribute, Double value)
	{
		super(attribute, value);
	}
	
	/**
	 * Determina la distanza in valore assoluto tra il valore scalato memorizzato nell'item corrente e il valore scalato contenuto in a.
	 * Per ottenere valori scalati si utilizza {@link ContinuousAttribute#getScaledValue(double)}
	 */
	double distance(Object a)
	{
		return Math.abs(
				((ContinuousAttribute)getAttribute()).getScaledValue((double)getValue())- 
				((ContinuousAttribute)getAttribute()).getScaledValue((double) (((ContinuousItem)a).getValue())));
	}
}
