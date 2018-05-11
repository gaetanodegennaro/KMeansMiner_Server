package data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Data
{
	class Example implements Comparable<Example>
	{
		private List<Object> example = new ArrayList<Object>();
		
		private void add(Object o)
		{
			example.add(o);
		}
		
		private Object get(int i)
		{
			return example.get(i);
		}
		
		/*TreeSet tempData utilizza questo metodo per controllare se inserire l'elemento o meno*/
		public int compareTo(Example ex)
		{
			int i=0;
			for(Object o : example)
			{
				if(!o.equals(ex.get(i))) return o.toString().compareTo(ex.get(i).toString());
				i++;
			}
			return 0;
		}
		
		public String toString()
		{
			String str = "(";
			for(Object o: example)
			{
				str+=o.toString()+" ";
			}
			str+=")";
			return str;
		}
		
	}
	
    private List<Example> data;
    private int numberOfExamples;
    private List<Attribute> explanatorySet;
    
    public Data()
    {
    	TreeSet<Example> tempData = new TreeSet<Example>();
    	Example ex0=new Example();
    	Example ex1=new Example();
    	Example ex2=new Example();
    	Example ex3=new Example();
    	Example ex4=new Example();
    	Example ex5=new Example();
    	Example ex6=new Example();
    	Example ex7=new Example();
    	Example ex8=new Example();
    	Example ex9=new Example();
    	Example ex10=new Example();
    	Example ex11=new Example();
    	Example ex12=new Example();
    	Example ex13=new Example();
    	
    	ex0.add(new String ("Sunny"));
    	ex1.add(new String ("Sunny"));
    	ex2.add(new String("Overcast"));
    	ex3.add(new String("Rain"));
    	ex4.add(new String("Rain"));
    	ex5.add(new String("Rain"));
    	ex6.add(new String("Overcast"));
    	ex7.add(new String("Sunny"));
    	ex8.add(new String("Sunny"));
    	ex9.add(new String("Rain"));
    	ex10.add(new String("Sunny"));
    	ex11.add(new String("Overcast"));
    	ex12.add(new String("Overcast"));
    	ex13.add(new String("Rain"));
    	
    	ex0.add(new Double(37.5));
    	ex1.add(new Double(38.7));
    	ex2.add(new Double(37.5));
    	ex3.add(new Double(20.05));
    	ex4.add(new Double(20.07));
    	ex5.add(new Double(21.2));
    	ex6.add(new Double(21.2));
    	ex7.add(new Double(20.05));
    	ex8.add(new Double(21.2));
    	ex9.add(new Double(19.8));
    	ex10.add(new Double(3.5));
    	ex11.add(new Double(3.6));
    	ex12.add(new Double(3.5));
    	ex13.add(new Double(3.2));
    	
    	ex0.add(new String ("High"));
    	ex1.add(new String ("High"));
    	ex2.add(new String("High"));
    	ex3.add(new String("High"));
    	ex4.add(new String("Normal"));
    	ex5.add(new String("Normal"));
    	ex6.add(new String("Normal"));
    	ex7.add(new String("High"));
    	ex8.add(new String("Normal"));
    	ex9.add(new String("Normal"));
    	ex10.add(new String("Normal"));
    	ex11.add(new String("High"));
    	ex12.add(new String("Normal"));
    	ex13.add(new String("High"));
    	
    	ex0.add(new String ("Weak"));
    	ex1.add(new String ("Strong"));
    	ex2.add(new String("Weak"));
    	ex3.add(new String("Weak"));
    	ex4.add(new String("Weak"));
    	ex5.add(new String("Strong"));
    	ex6.add(new String("Strong"));
    	ex7.add(new String("Weak"));
    	ex8.add(new String("Weak"));
    	ex9.add(new String("Weak"));
    	ex10.add(new String("Strong"));
    	ex11.add(new String("Strong"));
    	ex12.add(new String("Weak"));
    	ex13.add(new String("Strong"));
    	
    	ex0.add(new String ("No"));
    	ex1.add(new String ("No"));
    	ex2.add(new String("Yes"));
    	ex3.add(new String("Yes"));
    	ex4.add(new String("Yes"));
    	ex5.add(new String("No"));
    	ex6.add(new String("Yes"));
    	ex7.add(new String("No"));
    	ex8.add(new String("Yes"));
    	ex9.add(new String("Yes"));
    	ex10.add(new String("Yes"));
    	ex11.add(new String("Yes"));
    	ex12.add(new String("Yes"));
    	ex13.add(new String("No"));
    	
    	tempData.add(ex0);
    	tempData.add(ex1);
    	tempData.add(ex2);
    	tempData.add(ex3);
    	tempData.add(ex4);
    	tempData.add(ex5);
    	tempData.add(ex6);
    	tempData.add(ex7);
    	tempData.add(ex8);
    	tempData.add(ex9);
    	tempData.add(ex10);
    	tempData.add(ex11);
    	tempData.add(ex12);
    	tempData.add(ex13);
    	
    	data = new ArrayList<Example>(tempData);
    	// COMPLETARE
    	 this.numberOfExamples = data.size();
    	// COMPLETARE
    	
        explanatorySet = new LinkedList<Attribute>();
        
        TreeSet<String> outLookValues = new TreeSet<String>();
        outLookValues.add("Overcast");
        outLookValues.add("Rain");
        outLookValues.add("Sunny");
        explanatorySet.add(new DiscreteAttribute("Outlook",0, outLookValues));
        
        TreeSet<String> temperatureValues = new TreeSet<String>();
        temperatureValues.add("Cool");
        temperatureValues.add("Hot");
        temperatureValues.add("Mild");
        explanatorySet.add(new ContinuousAttribute("Temperature",1, 3.2, 38.7));
        
        TreeSet<String> humidityValues = new TreeSet<String>();
        humidityValues.add("High");
        humidityValues.add("Normal");
        explanatorySet.add(new DiscreteAttribute("Humidity",2, humidityValues));
        
        TreeSet<String> windValues = new TreeSet<String>();
        windValues.add("Strong");
        windValues.add("Weak");
        explanatorySet.add(new DiscreteAttribute("Wind",3, windValues));
        
        TreeSet<String> playTennisValues = new TreeSet<String>();
        playTennisValues.add("No");
        playTennisValues.add("Yes");
        explanatorySet.add(new DiscreteAttribute("Play Tennis",4, playTennisValues));
    }
    
    public int getNumberOfExamples()
    {
        return numberOfExamples;
    }
    
    public int getNumberOfExplanatoryAttributes()
    {
        return explanatorySet.size();
    }
    
    List<Attribute> getAttributeSchema()
    {
        return explanatorySet;
    }
    
    public Object getAttributeValue(int exampleIndex, int attributeIndex)
    {
        return data.get(exampleIndex).get(attributeIndex);
    }
    
    
    public String toString()
    {
        String dataString = new String();
        for(Attribute a : explanatorySet) dataString+=a.toString();
        dataString+="\n";
        int i=0;
        for(Example ex: data)
        {
            dataString+=(i+1)+":";
            dataString+=ex.toString();
            dataString+="\n";
            i++;
        }
        return dataString;
    }
    
    public Tuple getItemSet(int index)
    {
    	Tuple tuple = new Tuple(explanatorySet.size());
    	Example e = data.get(index);
    	
    	for(Attribute a : explanatorySet)
		{
    		if(a instanceof DiscreteAttribute) tuple.add(new DiscreteItem((DiscreteAttribute) a, (String) e.get(a.getIndex())), a.getIndex());
    		else tuple.add(new ContinuousItem((ContinuousAttribute) a, (Double) e.get(a.getIndex())), a.getIndex());
		}
    	//for(int i=0;i<explanatorySet.size();i++) tuple.add(new DiscreteItem(explanatorySet.get(i), (String)data.get(index).get(i)),i);
    	return tuple;
    }
    
    public int[] sampling(int k) throws OutOfRangeSampleSize
	{
    	if(k<1) throw new OutOfRangeSampleSize("k number is <1");
    	else if(k>data.size()) throw new OutOfRangeSampleSize("k number is >"+data.size()+" of distinct tuples");
    	
		int centroidIndexes[]=new int[k];
		//choose k random different centroids in data.
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i=0;i<k;i++)
		{
			boolean found=false;
			int c;
			do
			{
				found=false;
				c=rand.nextInt(getNumberOfExamples());
				// verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
				for(int j=0;j<i;j++)
					if(compare(centroidIndexes[j],c))
					{
						found=true;
						break;
					}
			}
			while(found);
			centroidIndexes[i]=c;
		}
		return centroidIndexes;
	}
	
	private boolean compare(int i, int j)
	{
		if(i<getNumberOfExamples() && j<getNumberOfExamples())
		{
			if(data.get(i).compareTo(data.get(j))==0) return true;
			else return false;
		}
		else return false;
	}
	
	Object computePrototype(Set<Integer> idList, Attribute attribute)
	{
		if(attribute instanceof DiscreteAttribute) return computePrototype(idList, (DiscreteAttribute) attribute);
		else return computePrototype(idList, (ContinuousAttribute) attribute);
	}
	
	/*  Determina il valore prototipo come media dei valori osservati per attribute nelle transazioni di data
	 *  aventi indice di riga in idList
	 */
	private double computePrototype(Set<Integer> idList, ContinuousAttribute attribute)
	{
		double media=0;
		int count=0;
		int i=0;
		for(Object o : data)
		{
			if(idList.contains(i))
			{
				media+=(double) ((Example) o).get(attribute.getIndex());
				count++;
			}
			i++;
		}
		return media/count;
	}
	
	private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute)
	{
		int maxFrequency = -1;
		Object maxFrequencyAttribute = null;
		
		Iterator<String> iterator = attribute.iterator();
		while(iterator.hasNext())
		{
			Object a = iterator.next();
			int currentFrequency = attribute.frequency(this, idList, a.toString());
			if(currentFrequency>maxFrequency)
			{
				maxFrequency = currentFrequency;
				maxFrequencyAttribute = a;
			}
		}
		return maxFrequencyAttribute.toString();
	}
}
