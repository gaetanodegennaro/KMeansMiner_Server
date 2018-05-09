package data;
import java.util.Arrays;
import java.util.Random;

import utility.ArraySet;

public class Data
{
    // Le visibilità di classi , attributi e metodi devono essere decise dagli studenti	
    private Object data[][];
    private int numberOfExamples;
    private int distinctTuples;
    private Attribute explanatorySet[];
    
    public Data()
    {
        data = new Object[14][5];
        explanatorySet = new Attribute[5];
        
        String outLookValues[]=new String[3];
        outLookValues[0]="Overcast";
        outLookValues[1]="Rain";
        outLookValues[2]="Sunny";
        explanatorySet[0] = new DiscreteAttribute("Outlook",0, outLookValues);
        
        String temperatureValues[]=new String[3];
        temperatureValues[0]="Cool";
        temperatureValues[1]="Hot";
        temperatureValues[2]="Mild";
        explanatorySet[1] = new DiscreteAttribute("Temperature",1, temperatureValues);
        
        String humidityValues[]=new String[2];
        humidityValues[0]="High";
        humidityValues[1]="Normal";
        explanatorySet[2] = new DiscreteAttribute("Humidity",2, humidityValues);
        
        String windValues[]=new String[2];
        windValues[0]="Strong";
        windValues[1]="Weak";
        explanatorySet[3] = new DiscreteAttribute("Wind",3, windValues);
        
        String playTennisValues[]=new String[2];
        playTennisValues[0]="No";
        playTennisValues[1]="Yes";
        explanatorySet[4] = new DiscreteAttribute("Play Tennis",4, playTennisValues);
        
        data[0][0] = "Sunny";
        data[0][1] = "Hot";
        data[0][2] = "High";
        data[0][3] = "Weak";
        data[0][4] = "No";
        
        data[1][0] = "Sunny";
        data[1][1] = "Hot";
        data[1][2] = "High";
        data[1][3] = "Strong";
        data[1][4] = "No";
        
        data[2][0] = "Overcast";
        data[2][1] = "Hot";
        data[2][2] = "High";
        data[2][3] = "Weak";
        data[2][4] = "Yes";
        
        data[3][0] = "Rain";
        data[3][1] = "Mild";
        data[3][2] = "High";
        data[3][3] = "Weak";
        data[3][4] = "Yes";
        
        data[4][0] = "Rain";
        data[4][1] = "Cool";
        data[4][2] = "Normal";
        data[4][3] = "Weak";
        data[4][4] = "Yes";
        
        data[5][0] = "Rain";
        data[5][1] = "Cool";
        data[5][2] = "Normal";
        data[5][3] = "Strong";
        data[5][4] = "No";
        
        data[6][0] = "Overcast";
        data[6][1] = "Cool";
        data[6][2] = "Normal";
        data[6][3] = "Strong";
        data[6][4] = "Yes";
       
        data[7][0] = "Sunny";
        data[7][1] = "Mild";
        data[7][2] = "High";
        data[7][3] = "Weak";
        data[7][4] = "No";
        
        data[8][0] = "Sunny";
        data[8][1] = "Cool";
        data[8][2] = "Normal";
        data[8][3] = "Weak";
        data[8][4] = "Yes";
        
        data[9][0] = "Rain";
        data[9][1] = "Mild";
        data[9][2] = "Normal";
        data[9][3] = "Weak";
        data[9][4] = "Yes";
        
        data[10][0] = "Sunny";
        data[10][1] = "Mild";
        data[10][2] = "Normal";
        data[10][3] = "Strong";
        data[10][4] = "Yes";
        
        data[11][0] = "Overcast";
        data[11][1] = "Mild";
        data[11][2] = "High";
        data[11][3] = "Strong";
        data[11][4] = "Yes";
        
        data[12][0] = "Overcast";
        data[12][1] = "Hot";
        data[12][2] = "Normal";
        data[12][3] = "Weak";
        data[12][4] = "Yes";
        
        data[13][0] = "Rain";
        data[13][1] = "Mild";
        data[13][2] = "High";
        data[13][3] = "Strong";
        data[13][4] = "No";
        
        this.numberOfExamples = 14;
        
        distinctTuples = countDistinctTuples();
    }
    
    public int getNumberOfExamples()
    {
        return numberOfExamples;
    }
    
    public int getNumberOfExplanatoryAttributes()
    {
        return explanatorySet.length;
    }
    
    Attribute[] getAttributeSchema()
    {
        return explanatorySet;
    }
    
    public Object getAttributeValue(int exampleIndex, int attributeIndex)
    {
        return data[exampleIndex][attributeIndex];
    }
    
    @Override
    public String toString()
    {
        String dataString = new String();
        for(int i=0;i<explanatorySet.length;i++) dataString+=explanatorySet[i].getName()+",";
        dataString+="\n";
        for(int i=0;i<numberOfExamples;i++)
        {
            dataString+=(i+1)+":";
            for(int j=0;j<5;j++) dataString+=data[i][j]+",";
            dataString+="\n";
        }
        return dataString;
    }
    
    public Tuple getItemSet(int index)
    {
    	Tuple tuple = new Tuple(explanatorySet.length);
    	for(int i=0;i<explanatorySet.length;i++) tuple.add(new DiscreteItem(explanatorySet[i], (String)data[index][i]),i);
    	return tuple;
    }
    
    public int[] sampling(int k) throws OutOfRangeSampleSize
	{
    	if(k<1 || k>distinctTuples) throw new OutOfRangeSampleSize("k number is >"+distinctTuples+" of distinct tuples");
    	
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
			if(Arrays.equals(data[i], data[j])) return true;
			else return false;
		}
		else return false;
	}
	
	Object computePrototype(ArraySet idList, Attribute attribute)
	{
		return computePrototype(idList, (DiscreteAttribute) attribute);
	}
	
	String computePrototype(ArraySet idList, DiscreteAttribute attribute)
	{
		int maxFrequency = -1;
		int maxFrequencyIndex = -1;
		
		for(int i=0; i<attribute.getNumberOfDistinctValues();i++)
		{
			int currentFrequency = attribute.frequency(this, idList, attribute.getValue(i));
			if(currentFrequency>maxFrequency)
			{
				maxFrequency = currentFrequency;
				maxFrequencyIndex = i;
			}
		}
		
		if(maxFrequencyIndex==-1) return null;
		else return attribute.getValue(maxFrequencyIndex);
	}
	
	private int countDistinctTuples()
	{
		int distinctTuples = 0;
		for(int i=0; i<this.getNumberOfExamples(); i++)
		{
			boolean distinctFound = false;
			for(int j=0; j<i; j++)
			{
				if(i!=j && this.compare(i, j))
				{
					distinctFound = true;
					break;
				}
			}
			if(!distinctFound) distinctTuples++;
		}
		
		return distinctTuples;
	}

}
