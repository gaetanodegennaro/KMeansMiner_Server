package data;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class DiscreteAttribute extends Attribute implements Iterable<String>
{
    private TreeSet<String> values;
    
    DiscreteAttribute(String name, int index, TreeSet<String> values)
    {
        super(name,index);
        this.values = values;
    }
    
    int getNumberOfDistinctValues()
    {
        return values.size();
    }
    
    int frequency(Data data, Set<Integer> idList, String v)
    {
    	int occurrences = 0;
    	for(int i=0;i<data.getNumberOfExamples();i++)
    	{
    		if(idList.contains(i) && data.getAttributeValue(i, this.getIndex()).equals(v)) occurrences++;
    		//if(idList.get(i) && data.getAttributeValue(i, this.getIndex()).equals(v)) occurrences++;
    	}
    	return occurrences;
    }
    
    public Iterator<String> iterator()
    {
    	return values.iterator();
    }
}
