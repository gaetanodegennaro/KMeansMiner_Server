package data;

class ContinuousItem extends Item
{
	ContinuousItem(ContinuousAttribute attribute, Double value)
	{
		super(attribute, value);
	}
	
	double distance(Object a)
	{
		return Math.abs(
				((ContinuousAttribute)getAttribute()).getScaledValue((double)getValue())- 
				((ContinuousAttribute)getAttribute()).getScaledValue((double) (((ContinuousItem)a).getValue())));
	}
}
