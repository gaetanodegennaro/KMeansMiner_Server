package data;

public class DiscreteItem extends Item
{
	public DiscreteItem(DiscreteAttribute attribute, Object value)
	{
		super(attribute,value);
	}
	
	double distance(Object a)
	{
		if(this.getValue().toString().equals(a.toString())) return 0;
		else return 1;
	}
}
