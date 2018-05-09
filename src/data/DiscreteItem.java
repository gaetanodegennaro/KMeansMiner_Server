package data;

class DiscreteItem extends Item
{
	DiscreteItem(Attribute attribute, Object value)
	{
		super(attribute,value);
	}
	
	double distance(Object a)
	{
		if(this.getValue().toString().equals(a.toString())) return 0;
		else return 1;
	}
}
