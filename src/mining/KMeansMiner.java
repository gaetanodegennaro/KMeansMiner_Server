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

public class KMeansMiner implements Serializable
{
	private ClusterSet C;
	
	public KMeansMiner(int k)
	{
		C = new ClusterSet(k);
	}
	
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
