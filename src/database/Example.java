package database;

import java.util.ArrayList;
import java.util.List;


/**
 * Modella una tupla prelevata da un database.
 * Implementa l'interfaccia Comparable in modo da poter eseguire l'override del metodo compareTo ed effettuare un confronto
 * tra due istanze di questa classe.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class Example implements Comparable<Example>
{
	/**
	 * Lista dei campi di una tupla.
	 */
	private List<Object> example=new ArrayList<Object>();

	/**
	 * Aggiunge un campo ad {@link #example}
	 * 
	 * @param o Oggetto da inserire in {@link #example}
	 */
	public void add(Object o)
	{
		example.add(o);
	}
	
	/**
	 * Restituisce un valore della tupla specificato dal valore intero passato per parametro.
	 * 
	 * @param i indice della tupla all'interno di {@link #example}
	 * @return Un valore della tupla identificato dal valore intero passato per parametro
	 */
	public Object get(int i)
	{
		return example.get(i);
	}
	
	/**
	 * Confronta due Example utilizzando il metodo compareTo della classe generic ArrayList
	 * 
	 * @param ex Parametro Example da confrontare 
	 * @return Un valore intero uguale a 0 se l'oggetto example corrente è uguale all'exemple passato per parametro, positivo se è maggiore, negativo altrimenti  
	 * @see ArrayList
	 * 
	 */
	public int compareTo(Example ex)
	{
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	
	public String toString()
	{
		String str="";
		for(Object o:example)
			str+=o.toString()+ "\t";
		return str;
	}
	
}