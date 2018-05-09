package data;
import utility.ArraySet;

public class DiscreteAttribute extends Attribute
{
    private String values[];
    
    DiscreteAttribute(String name, int index, String values[])
    {
        super(name,index);
        this.values = values;
    }
    
    int getNumberOfDistinctValues()
    {
        return values.length;
    }
    
    String getValue(int i)
    {
        return values[i];
    }
    
    int frequency(Data data, ArraySet idList, String v)
    {
    	int occurrences = 0;
    	for(int i=0;i<data.getNumberOfExamples();i++)
    	{
    		if(idList.get(i) && data.getAttributeValue(i, this.getIndex()).equals(v)) occurrences++;
    	}
    	
    	return occurrences;
    }
}
