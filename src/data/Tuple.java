package data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Modella una sequenza di {@link Item}.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class Tuple implements Serializable
{
	/**
	 * Sequenza di {@link Item}.
	 */
	private Item[] tuple;
	
	/**
	 * Istanzia un array di item di dimensione size.
	 * 
	 * @param size dimensione di {@link #tuple}
	 */
	public Tuple(int size)
	{
		tuple = new Item[size];
	}
	
	/**
	 * Restituisce il numero di tuple nell'array.
	 * 
	 * @return Restituisce la lunghezza di {@link #tuple}
	 */
	public int getLength()
	{
		return tuple.length;
	}
	
	/**
	 * Restituisce l'Item in posizione i dell'array {@link #tuple}.
	 * 
	 * @param i Indice dell'Item che si vuole restituire
	 * 
	 * @return Restituisce l'Item in posizione i dell'array {@link #tuple}.
	 */
	public Item get(int i)
	{
		return tuple[i];
	}
	
	/**
	 * Inserisce in posizione i in {@link #tuple} l'item c.
	 * 
	 * @param c Item da assegnare in posizione i in {@link #tuple}
	 * @param i Posizione di {@link #tuple} in cui assegnare il nuovo item c.
	 */
	public void add(Item c, int i)
	{
		tuple[i] = c;
	}
	
	/**
	 * Determina la distanza tra la tupla corrente e la tupla obj passata come parametro.
	 * Si avvale del metodo {@link Item#distance(Object)} per ottenere la distanza di ogni Item.
	 * Somma poi tutte le distanze ottenute da ogni Item.
	 * 
	 * @param obj Tupla con la quale calcolare la distanza.
	 * 
	 * @return Restituisce la distanza tra la tupla corrente e la tupla obj passata come parametro.
	 * 
	 * @see Item
	 */
	public double getDistance(Tuple obj)
	{
		double distance = 0;
		int length;
		
		if(obj.getLength() < this.getLength()) length = obj.getLength();
		else length = this.getLength();
		for(int i=0;i<length;i++)
		{
			distance+=tuple[i].distance(obj.get(i));
		}
		BigDecimal bd = new BigDecimal(Double.toString(distance));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
	}
	
	/**
	 * 
	 * Restituisce la media delle distanze tra la tupla corrente e quelle ottenibili dalle righe della tabella riferite da data
	 * aventi indice in clusteredData.<br>
	 * Per il calcolo della distanza tra una tupla e l'altra si usa {@link Tuple#getDistance(Tuple)}.<br>
	 * Per l'ottenimento delle tuple nelle corrispondenti righe indicizzare da clusteredData, si utilizza il metodo {@link Data#getItemSet(int)}
	 * 
	 * @param data Istanza di cui è necessario considerare alcune tuple per il calcolo della distanza.
	 * @param clusteredData Array contentnti gli indici delle tuple di cui calcolare la distanza con la tupla riferita da this.
	 * 
	 * @return Media delle distanze tra la tupla riferita da this e quelle riferite dagli indici presenti in clusteredData.
	 */
	public double avgDistance(Data data, int clusteredData[])
	{
		double p=0.0,sumD=0.0;
		for(int i=0;i<clusteredData.length;i++)
		{
			double d = getDistance(data.getItemSet(clusteredData[i]));
			sumD+=d;
		}
		p=sumD/clusteredData.length;
		BigDecimal bd = new BigDecimal(Double.toString(p));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
	}
	
	public String toString()
	{
		String str="[ ";
		for(int i=0;i<tuple.length;i++) str+=tuple[i].toString()+" ";
		str+="]";
		
		return str;
	}
}


