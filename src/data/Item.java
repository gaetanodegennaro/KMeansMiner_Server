package data;
import java.io.Serializable;
import java.util.Set;

/**
 * Classe astratta che modella un Item, ovvero una coppia (Attributo, Valore).
 * Implementa l'interfaccia Serializable per consentirne la serializzazione.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public abstract class Item implements Serializable
{
	/**
	 * Attributo coinvolto.
	 * @see Attribute
	 */
	private Attribute attribute;
	
	/**
	 * Valore dell'attributo.
	 */
	private Object value;
	
	/**
	 * Inizializza {@link #attribute} e {@link #value} con i valori passati per parametro.
	 * 
	 * @param attribute Attributo coinvolto.
	 * @param value Valore dell'attributo.
	 */
	Item(Attribute attribute, Object value)
	{
		this.attribute = attribute;
		this.value = value;
	}
	
	/**
	 * Restituisce {@link #attribute}.
	 * @return Restituisce l'attributo {@link #attribute}.
	 */
	Attribute getAttribute()
	{
		return attribute;
	}
	
	/**
	 * Restituisce {@link #value}
	 * @return Restituisce il valore dell'attributo {@link #value}.
	 */
	Object getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return value.toString();
	}
	
	/**
	 * Metodo astratto per il calcolo della distanza tra due valori di attributo.
	 * Il metodo è astratto per consentire una differente implementazione per attributi di tipo continuo o discreto.
	 * 
	 * @param a Valore del quale si vuole calcolare la distanza
	 * @return Restituisce la distanza tra {@link #value} e a.
	 */
	abstract double distance(Object a);
	
	/**
	 * Aggiorna {@link #value} con il risultato del metodo {@link Data#computePrototype(Set, Attribute)}
	 * 
	 * @param data Riferimento ad un oggetto della classe {@link Data}
	 * @param clusteredData Insieme di interi che contengono gli indici delle righe appartenenti al cluster.
	 */
	public void update(Data data, Set<Integer> clusteredData)
	{
		Object newValue = data.computePrototype(clusteredData, this.attribute);
		if(newValue!=null) value = newValue;
	}
}
