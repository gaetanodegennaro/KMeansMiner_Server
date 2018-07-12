package data;

/**
 * Estende Item per la modellazione di un Item di tipo Discreto.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class DiscreteItem extends Item
{
	/**
	 * Invoca il costruttore della super classe per inizializzare attribute e value.
	 * 
	 * @param attribute Attributo coinvolto.
	 * @param value Valore dell'attrivuto coinvolto.
	 */
	public DiscreteItem(DiscreteAttribute attribute, Object value)
	{
		super(attribute,value);
	}
	
	/**
	 * Determina la distanza tra il valore dell'item corrente e il valore di a, effettuando un controllo di uguaglianza tra stringhe.
	 * Restituisce 0 se il valore dell'item corrente è uguale al valore di a, 1 altrimenti.
	 */
	double distance(Object a)
	{
		if(this.getValue().toString().equals(a.toString())) return 0;
		else return 1;
	}
}
