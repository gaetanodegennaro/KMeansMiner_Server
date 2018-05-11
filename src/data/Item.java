package data;
import java.util.Set;

public abstract class Item
{
	private Attribute attribute;
	private Object value;
	
	Item(Attribute attribute, Object value)
	{
		this.attribute = attribute;
		this.value = value;
	}
	
	Attribute getAttribute()
	{
		return attribute;
	}
	
	Object getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return value.toString();
	}
	
	abstract double distance(Object a);
	
	public void update(Data data, Set<Integer> clusteredData)
	{
		Object newValue = data.computePrototype(clusteredData, this.attribute);
		if(newValue!=null) value = newValue;
	}
}
