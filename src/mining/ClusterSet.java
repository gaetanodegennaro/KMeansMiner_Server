package mining;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

public class ClusterSet implements Serializable
{
	private Cluster C[];
	private int i=0;
	
	ClusterSet(int k)
	{
		C = new Cluster[k];
	}
	
	private void add(Cluster c)
	{
		C[i] = c;
		i++;
	}
	
	private Cluster get(int i)
	{
		return C[i];
	}
	
	void initializeCentroids(Data data) throws OutOfRangeSampleSize
	{
		int centroidIndexes[]=data.sampling(C.length);
		for(int i=0;i<centroidIndexes.length;i++)
		{
			Tuple centroidI=data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}
	
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
		
		
		/*Da rivedere*/
		if(nearestIndex!=-1) return C[nearestIndex];
		else return null;
	}
	
	Cluster currentCluster(int id)
	{
		Cluster currentCluster = null;
		
		for(int i=0;i<C.length;i++)
		{
			if(C[i].contain(id)) currentCluster = C[i];
		}
		
		return currentCluster;
	}
	
	void updateCentroids(Data data)
	{
		for(int i=0;i<C.length;i++)
		{
			C[i].computeCentroid(data);
		}
	}
	
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
