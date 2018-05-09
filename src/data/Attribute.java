package data;
abstract class Attribute
{
    private String name;
    private int index;
    
    Attribute(String name, int index)
    {
        this.name = name;
        this.index = index;
    }
    
    String getName(){return this.name;}
    int getIndex(){return this.index;}
    
    
    @Override
    public String toString()
    {
        return name;
    }
}
