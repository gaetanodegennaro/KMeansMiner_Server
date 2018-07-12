package mining;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Tuple;


/**
 * 
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class Cluster  implements Serializable
{
	/**
	 * Tupla rappresentante il centroide del Cluster.
	 */
	private Tuple centroid;
	
	/**
	 * Insieme degli indici di riga di una istanza di {@link data.Data} appartenenti al Cluster. 
	 */
	private Set<Integer> clusteredData; 

	/**
	 *
	 * Assegna centroid a {@link #centroid} e istanzia {@link #clusteredData} .
	 * 
	 * @param centroid tupla corrispondente al centroide che viene assegnato a {@link #centroid}
	 */
	Cluster(Tuple centroid)
	{
		this.centroid=centroid;
		clusteredData=new HashSet<Integer>();
	}
		
	/**
	 * Restituisce la tupla corrispondente al centroide del Cluster.
	 * @return {@link #centroid}
	 */
	Tuple getCentroid()
	{
		return centroid;
	}
	
	/**
	 * Calcola il centroide per questo cluster utilizzanto il metodo {@link data.Item#update(Data, Set)}
	 * 
	 * @param data dati sui quali calcolare il nuovo centroide per questo Cluster
	 * @see data.Data
	 */
	void computeCentroid(Data data)
	{
		for(int i=0;i<centroid.getLength();i++)
		{
			centroid.get(i).update(data,clusteredData);
		}
		
	}
	
	/**
	 * 
	 * Aggiunge un elemento all'insieme {@link #clusteredData}.
	 * L'aggiunta avviene solo nel caso in cui tale elemento identificato da id, non è già presente nell'insieme.
	 * 
	 * @param id indice di riga di {@link data.Data} che si vuole aggiungere al cluster.
	 * @return true se id è stato aggiunto, falso altrimenti.
	 */
	boolean addData(int id)
	{
		return clusteredData.add(id);
	}
	
	/**
	 * Controlla se la riga identificata da id appartiene a questo cluster, ovvero se è contenuto in {@link #clusteredData}.
	 * 
	 * @param id indice di riga della tupla
	 * @return true se la tupla identificata da id è contenuta in questo cluster, false altrimenti
	 */
	boolean contain(int id)
	{
		return clusteredData.contains(id);
	}
	
	/**
	 * Rimuove la tupla identificata da id da {@link #clusteredData}.
	 * 
	 * @param id indice di riga della tupla
	 */
	void removeTuple(int id)
	{
		clusteredData.remove(new Integer(id));
	}
	
	/**
	 * Restituisce una stringa che descrive lo stato dell'oggetto, costruita concatenando ogni valore del centroide.
	 * 
	 * @return Stringa che descrive lo stato dell'oggetto
	 */
	public String toString()
	{
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++) str+=centroid.get(i).toString();
		str+=")";
		return str;
	}
	

	/**
	 * Restituisce una stringa che descrive lo stato dell'oggetto, costruita concatenando ogni valore del centroide, e le tuple di
	 * data che appartengono a questo cluster, associando inoltre una distanza
	 * 
	 * @param data contiene i dati attraverso i quali è possibile realizzare la stampa del contenuto del cluster
	 * @return Stringa che descrive lo stato dell'oggetto
	 */
	public String toString(Data data)
	{
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++) str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		/*VERIFICARE*/
		int array[] = Arrays.stream(clusteredData.toArray(new Integer[0])).mapToInt(Integer::intValue).toArray();
		for(int i=0;i<array.length;i++)
		{
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++) str+=data.getAttributeValue(array[i], j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(array[i]))+"\n";
		}
		str+="AvgDistance="+getCentroid().avgDistance(data, array)+"\n\n";
		return str;
	}

}
