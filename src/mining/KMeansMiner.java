package mining;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;


/**
 * Si occupa di effettuare la scoperta dei cluster per mezzo dell'algoritmo KMeans.
 * Implementa l'interfaccia {@link java.io.Serializable} in modo da consentire la scrittura/lettura su/da file del risultato dell'algoritmo eseguito.<br>
 * Il nome dei file salvati corrisponde alla concatenazione tra il nome della tabella del database dalla quale viene
 * effettuata la lettura e il numero dei K Cluster.
 *  
 * @author de Gennaro Gaetano, Farinola Francesco
 * 
 * @see Cluster
 * @see ClusterSet
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html">Serializable</a> 
 *
 */
public class KMeansMiner implements Serializable
{
	/**
	 * Insieme di cluster. Frutto del risultato dell'algoritmo applicato sui dati letti da database o frutto del caricamento da file.
	 * 
	 * @see Cluster
	 */
	private ClusterSet C;
	
	/**
	 * Inizializza {@link #C} in base al numero k di cluster che si intende scoprire a partire dalle informazioni lette da database.
	 * 
	 * @param k numero di cluster da scoprire.
	 */
	public KMeansMiner(int k)
	{
		C = new ClusterSet(k);
	}
	
	/*verificare*/
	/**
	 * Crea un {@link java.io.ObjectInputStream} per effettuare la lettura da file.
	 * Avvalora C con l'oggetto letto da file.
	 * Chiude lo stream.
	 * 
	 * @param fileName nome del file che si intende leggere
	 * DA COMPLETARE
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		C = (ClusterSet) in.readObject();
		in.close();
	}
	
	public void salva(String fileName) throws FileNotFoundException, IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(C);
		out.close();
	}
	
	public ClusterSet getC()
	{
		return C;
	}
	
	public int kmeans(Data data) throws OutOfRangeSampleSize
	{
		int numberOfIterations=0;
		//STEP 1
		C.initializeCentroids(data);
		boolean changedCluster=false;
		do
		{
			
			numberOfIterations++;
			//STEP 2
			changedCluster=false;
			for(int i=0;i<data.getNumberOfExamples();i++)
			{
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
				Cluster oldCluster=C.currentCluster(i);
				boolean currentChange=nearestCluster.addData(i);
				if(currentChange) changedCluster=true;
				//rimuovo la tupla dal vecchio cluster
				if(currentChange && oldCluster!=null)
				{
					//il nodo va rimosso dal suo vecchio cluster
					oldCluster.removeTuple(i);
				}
				
			}
			//STEP 3
			C.updateCentroids(data);
		}
		while(changedCluster);
		
		return numberOfIterations;
	}
	
	public String toString()
	{
		return C.toString();
	}
}
