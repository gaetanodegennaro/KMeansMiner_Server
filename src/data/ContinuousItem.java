package data;

public class ContinuousItem extends Item
{
	public ContinuousItem(ContinuousAttribute attribute, Double value)
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
