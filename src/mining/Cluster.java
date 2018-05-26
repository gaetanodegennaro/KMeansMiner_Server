package mining;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Tuple;

public class Cluster  implements Serializable
{
	private Tuple centroid;
	private Set<Integer> clusteredData; 
	
	/*Cluster(){
		
	}*/

	Cluster(Tuple centroid)
	{
		this.centroid=centroid;
		clusteredData=new HashSet<Integer>();
	}
		
	Tuple getCentroid()
	{
		return centroid;
	}
	
	void computeCentroid(Data data)
	{
		for(int i=0;i<centroid.getLength();i++)
		{
			centroid.get(i).update(data,clusteredData);
		}
		
	}
	//return true if the tuple is changing cluster
	boolean addData(int id)
	{
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id)
	{
		//return clusteredData.get(id);
		return clusteredData.contains(id);
	}
	

	//remove the tuple that has changed the cluster
	void removeTuple(int id)
	{
		clusteredData.remove(new Integer(id));
	}
	
	public String toString()
	{
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++) str+=centroid.get(i).toString();
		str+=")";
		return str;
	}
	

	
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
