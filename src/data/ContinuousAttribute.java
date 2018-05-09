package data;
class ContinuousAttribute extends Attribute
{
    private double max,min; 
    
    ContinuousAttribute(String name, int index, double max, double min)
    {
        super(name,index);
        this.max = max;
        this.min = min;
    }
    
    private double getScaledValue(double v)
    {
        return (v-min)/(max-min);
    }
}
