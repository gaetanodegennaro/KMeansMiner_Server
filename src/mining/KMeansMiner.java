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
	 * Insieme di cluster. Frutto del risultato dell'algoritmo applicato sui dati letti da database o del caricamento da file.
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
	
	/**
	 * Crea un {@link java.io.ObjectInputStream} per effettuare la lettura da file.
	 * Avvalora C con l'oggetto letto da file.
	 * Chiude lo stream.
	 * 
	 * @param fileName nome del file che si intende leggere
	 * 
	 * @throws FileNotFoundException sollevata quando il file identificato da fileName non esiste sul disco. 
	 * @throws IOException sollevata quando si verificano errori durante la lettura di C da file
	 * @throws ClassNotFoundException sollevata quando si effettua il cast ad un tipo non risolvibile
	 * 
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html">ObjectInputStream</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/FileNotFoundException.html">FileNotFoundException</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">IOException</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/ClassNotFoundException.html">ClassNotFoundException</a>
	 * 
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		C = (ClusterSet) in.readObject();
		in.close();
	}
	
	/**
	 * Crea un {@link java.io.ObjectOutputStream} per effettuare la scrittura su file e serializza l'oggetto {@link #C}.
	 * Chiude lo stream di output.
	 * 
	 * @param fileName nome del file sul quale si intende serializzare {@link #C}
	 * 
	 * @throws FileNotFoundException sollevata quando non è possibile accedere al file identificato da fileName.
	 * @throws IOException sollevata quando si verificano errori durante la scrittura di {@link #C} su file.
	 * 
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html">ObjectOutputStream</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/FileNotFoundException.html">FileNotFoundException</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html">IOException</a>
	 * 
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(C);
		out.close();
	}
	
	/**
	 * Restituisce il ClusterSet {@link #C}.
	 * @return ClusterSet {@link #C}
	 */
	public ClusterSet getC()
	{
		return C;
	}
	
	/**
	 * 
	 * Il metodo esegue l'algoritmo k-means esegueno i seguenti passi:<br>
	 * 1. Scelta casuale di centroidi per k clusters assegnandoli al ClusterSet.<br>
	 * 2. Assegnazione di ciascuna riga in data al cluster avente centroide più vicino all'esempio (utilizzando {@link ClusterSet#updateCentroids(Data)}<br>
	 * 3. Calcolo dei nuovi centroidi per ciascun Cluster (utilizzando il metodo {@link ClusterSet#updateCentroids(Data)}<br>
	 * 4. Ripete i passi 2 e 3 finchè due iterazioni consecutive non restituiscono centroidi uguali.
	 * 
	 * @param data dati sui quali applicare l'algoritmo KMeans
	 * @return numero di iterazioni effettuate fino al completamento dell'algoritmo
	 * @throws OutOfRangeSampleSize sollevata nel caso in cui il numero di k Cluster da scoprire risulta essere maggiore delle
	 * tuple presenti in {@link data.Data} oppure se tale numero è 0.
	 * 
	 */
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
