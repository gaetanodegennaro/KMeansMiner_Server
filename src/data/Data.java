package data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import database.TableSchema.Column;

public class Data
{
    private List<Example> data;
    private int numberOfExamples;
    private List<Attribute> explanatorySet;
    
    public Data(String table) throws EmptySetException, DatabaseConnectionException, SQLException, NoValueException
    {
    	DbAccess db = new DbAccess();
		db.initConnection();
		
		TableData td = new TableData(db);
		
		data = td.getDistinctTransazioni(table);
		numberOfExamples = data.size();
		
		TableSchema schema = new TableSchema(db, table);
		
		/*Si decide di utilizzare un ArrayList per garantire un tempo di accesso più rapido rispetto a LinkedList*/
		explanatorySet = new ArrayList<Attribute>();
		for(int i=0; i<schema.getNumberOfAttributes(); i++)
		{
			System.out.println(i);
			Column col = schema.getColumn(i);
			if(col.isNumber())
			{
				final double max = (Float) td.getAggregateColumnValue(table, col, QUERY_TYPE.MAX);
				final double min = (Float) td.getAggregateColumnValue(table, col, QUERY_TYPE.MIN);
				explanatorySet.add(new ContinuousAttribute(schema.getColumn(i).getColumnName(), i, max, min));
			}
			else
			{
				Set<Object> values = td.getDistinctColumnValues("playtennis", col);
				TreeSet<String> setOfValues = new TreeSet<String>();
				for(Object o : values) setOfValues.add(o.toString());
				explanatorySet.add(new DiscreteAttribute(schema.getColumn(i).getColumnName(), i, setOfValues));
			}
		}
		db.getConnection().close();
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
        for(Attribute a : explanatorySet) dataString+=a.toString()+" ";
        dataString+="\n";
        int i=0;
        for(Example ex: data)
        {
            dataString+=(i+1)+": ";
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
