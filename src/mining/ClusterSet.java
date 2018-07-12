package mining;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

/**
 * Astrae un insieme di Cluster, rappresentato da un array. 
 * Ciascuna cella dell'array conterrà quindi un Cluster.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * 
 * @see Cluster
 * @see data.Data
 *
 */
public class ClusterSet implements Serializable
{
	/**
	 * Array di Cluster utilizzato per l'implementazione dell'algoritmo KMeans.
	 * 
	 * @see Cluster
	 */
	private Cluster C[];
	
	/**
	 * Indice dell'array {@link #C}
	 */
	private int i=0;
	
	/**
	 * Istanzia l'array di Cluster di dimensione k.
	 * 
	 * @param k numero di Cluster
	 * @see Cluster
	 */
	ClusterSet(int k)
	{
		C = new Cluster[k];
	}
	
	
	/**
	 * Assegna il cluster c alla posizione {@link #i} dell'array {@link #C}.
	 * Incrementa poi l'indice {@link #i}.
	 * 
	 * @param c cluster da assegnare
	 * @see Cluster
	 */
	private void add(Cluster c)
	{
		C[i] = c;
		i++;
	}
	
	
	/**
	 * Restituisce l'i-esimo elemento di C.
	 * 
	 * @param i indice dell'elemento di {@link #C} che si vuole restituire
	 * @return Cluster in posizione {@link #i} dell'array {@link #C}
	 * @see Cluster
	 */
	private Cluster get(int i)
	{
		return C[i];
	}
	
	/**
	 * Sceglie k Centroidi in maniera casuale secondo i criteri dell'algoritmo KMeans.
	 * Crea quindi un Cluster per ogni centroide e lo memorizza in {@link #C}.
	 * 
	 * @param data dati sui quali applicare l'algoritmo KMeans
	 * @throws OutOfRangeSampleSize lanciata se il numero di tuple distinte in data è minore di k.
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize
	{
		int centroidIndexes[]=data.sampling(C.length);
		for(int i=0;i<centroidIndexes.length;i++)
		{
			Tuple centroidI=data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}
	
	/**
	 * Restituisce il cluster più vicino tra tuple e il centroide di ciascun cluster in {@link #C}.
	 * 
	 * @param tuple tupla dalla quale si vuole ottenere il Cluster più vicino.
	 * @return Cluster più vicino a tuple.
	 */
	Cluster nearestCluster(Tuple tuple)
	{
		int nearestIndex = -1;
		double minDistance = Double.POSITIVE_INFINITY;
		
		for(int i=0;i<C.length;i++)
		{
			double distance = tuple.getDistance(C[i].getCentroid());
			if(distance<minDistance)
			{
				minDistance = distance;
				nearestIndex = i;
			}
		}
		
		if(nearestIndex!=-1) return C[nearestIndex];
		else return null;
	}
	
	/**
	 * Restituisce il cluster che contiene la tupla avente come indice: id.
	 * Se la tupla non appartiene a nessun cluster viene restituito null.
	 * 
	 * @param id indice di riga di {@link data.Data}
	 * @return Cluster a cui appartiene la tupla in posizione id.
	 */
	Cluster currentCluster(int id)
	{
		Cluster currentCluster = null;
		
		for(int i=0;i<C.length;i++)
		{
			if(C[i].contain(id)) currentCluster = C[i];
		}
		
		return currentCluster;
	}
	
	/**
	 * Calcola il nuovo centroide per ciascun cluster dell'array {@link #C}.
	 * 
	 * @param data dati sui quali calcolare i nuovi centroidi.
	 */
	void updateCentroids(Data data)
	{
		for(int i=0;i<C.length;i++)
		{
			C[i].computeCentroid(data);
		}
	}
	
	/**
	 * Restituisce una stringa che descrive lo stato dell'oggetto, richiamando il metodo {@link Cluster#toString()} su ciascun elemento di 
	 * {@link #C}.
	 * 
	 * @return Stringa che descrive lo stato dell'oggetto
	 */
	public String toString()
	{
		String str="AllCentroids(\n";
		for(int i=0;i<C.length;i++)
		{
			if(C[i]!=null) str+=C[i].getCentroid().toString()+"\n";
		}
		str+=");";
		return str;
	}
	
	/**
	 * Restituisce una stringa che descrive lo stato dell'oggetto, richiamando il metodo {@link Cluster#toString(Data)} su ciascun elemento di 
	 * {@link #C}.
	 * 
	 * @param data dati sui quali utilizzare il metodo {@link Cluster#toString(Data)}
	 * @return Stringa che descrive lo stato dell'oggetto
	 */
	public String toString(Data data )
	{
		String str="";
		for(int i=0;i<C.length;i++)
		{
			if (C[i]!=null) str+=i+":"+C[i].toString(data)+"\n";
			else str+="null";
		}
		return str;
	}
	
	
}
