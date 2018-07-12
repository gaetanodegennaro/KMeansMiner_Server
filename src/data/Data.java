package data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import database.TableSchema.Column;

/**
 * Modella una lsta di Example, dando vita ad una collezione di dati.
 * La lista viene popolata con le tuple lette dal database
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class Data
{
	/**
	 * Lista di Example.
	 */
    private List<Example> data;
    
    /**
     * Numero di Example presenti in {@link #data}
     */
    private int numberOfExamples;
    
    /**
     * Lista di Attributi
     */
    private List<Attribute> explanatorySet;
    
    /**
     * Inizializza tutti gli attributi di istanza con le informazioni ottenute dalla lettura del contenuto di table all'interno del database.
     * Inizializza quindi {@link #data} con le tuple lette<br>
     * {@link #numberOfExamples} con la lunghezza di {@link #data}
     * Ed {@link #explanatorySet} con le informazioni ottenute da {@link TableSchema}
     * 
     * @param table Nome della tabella dalla quale leggere il contenuto.
     * @throws EmptySetException Sollevata nel caso in cui la tabella dalla quale si intende leggere le tuple è vuota
     * @throws DatabaseConnectionException Sollevata nel caso in cui la connessione con il database fallisce
     * @throws SQLException Sollevata nel caso in cui si verificano problemi con la connessione al database
     * @throws NoValueException Sollevata se la funzione di aggregazione {@link TableData#getAggregateColumnValue(String, Column, QUERY_TYPE)} 
     * non produce alcun risultato a causa di mancanza di tuple nel database.
     * 
     * @see EmptySetException
     * @see DatabaseConnectionException
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html">SQLException</a>
     * @see NoValueException
     */
    public Data(String table) throws EmptySetException, DatabaseConnectionException, SQLException, NoValueException
    {
    	DbAccess db = new DbAccess();
		db.initConnection();
		
		TableData td = new TableData(db);
		
		data = td.getDistinctTransazioni(table);
		numberOfExamples = data.size();
		
		TableSchema schema = new TableSchema(db, table);
		
		/*Si decide di utilizzare un ArrayList per garantire un tempo di accesso più rapido rispetto a LinkedList*/
		explanatorySet = new ArrayList<Attribute>();
		for(int i=0; i<schema.getNumberOfAttributes(); i++)
		{
			System.out.println(i);
			Column col = schema.getColumn(i);
			if(col.isNumber())
			{
				final double max = (Float) td.getAggregateColumnValue(table, col, QUERY_TYPE.MAX);
				final double min = (Float) td.getAggregateColumnValue(table, col, QUERY_TYPE.MIN);
				explanatorySet.add(new ContinuousAttribute(schema.getColumn(i).getColumnName(), i, max, min));
			}
			else
			{
				Set<Object> values = td.getDistinctColumnValues("playtennis", col);
				TreeSet<String> setOfValues = new TreeSet<String>();
				for(Object o : values) setOfValues.add(o.toString());
				explanatorySet.add(new DiscreteAttribute(schema.getColumn(i).getColumnName(), i, setOfValues));
			}
		}
		db.getConnection().close();
    }
   
    /**
     * Restituisce il numero di Example in {@link #data}
     * @return Restituisce la lunghezza di {@link #data}
     */
    public int getNumberOfExamples()
    {
        return numberOfExamples;
    }
    
    /**
     * Restituisce il numero di Attribute in {@link #explanatorySet}
     * @return Restituisce la lunghezza di {@link #explanatorySet}
     */
    public int getNumberOfExplanatoryAttributes()
    {
        return explanatorySet.size();
    }
    
    /**
     * Restituisce {@link #explanatorySet}
     * @return Restituisce {@link #explanatorySet}
     */
    List<Attribute> getAttributeSchema()
    {
        return explanatorySet;
    }
    
    /**
     * Restituisce il valore dell'attributo identidificato da attributeIndex di Example in {@link #data} in posizione exampleIndex.
     * @param exampleIndex Indice dell'Example in {@link #data}
     * @param attributeIndex Indice dell'attributo in Example.
     * 
     * @return L'oggetto in {@link #data} in posizione exampleIndex con attributo con indice attributeIndex
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex)
    {
        return data.get(exampleIndex).get(attributeIndex);
    }
    
    
    /**
     * Restituisce il riferimento ad un'istanza di {@link Tuple} composto da tutti gli attributi di un Example in posizione index in {@link #data}
     * 
     * @param index Indice di Example in {@link #data}
     * 
     * @return Restituisce una Tuple composta da tutti gli Item presenti nell'Example con indice index in {@link #data}
     */
    public Tuple getItemSet(int index)
    {
    	Tuple tuple = new Tuple(explanatorySet.size());
    	Example e = data.get(index);
    	
    	for(Attribute a : explanatorySet)
		{
    		if(a instanceof DiscreteAttribute) tuple.add(new DiscreteItem((DiscreteAttribute) a, (String) e.get(a.getIndex())), a.getIndex());
    		else tuple.add(new ContinuousItem((ContinuousAttribute) a, (Double) e.get(a.getIndex())), a.getIndex()); 
		}
    	//for(int i=0;i<explanatorySet.size();i++) tuple.add(new DiscreteItem(explanatorySet.get(i), (String)data.get(index).get(i)),i);
    	return tuple;
    }
    
    /**
     * Inizializza i centroidi con delle tuple di {@link #data} in maniera casuale.
     * 
     * @param k Numero di Cluster
     * @return Array di interi contenente gli indici dei centroidi appena trovati
     * @throws OutOfRangeSampleSize Sollevata se il numero k di cluster passato per parametro è minore di 1 o maggiore di {@link #numberOfExamples}
     * @see OutOfRangeSampleSize
     */
    public int[] sampling(int k) throws OutOfRangeSampleSize
	{
    	if(k<1) throw new OutOfRangeSampleSize("k number is <1");
    	else if(k>data.size()) throw new OutOfRangeSampleSize("k number is >"+data.size()+" of distinct tuples");
    	
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
	
    /**
     * Confronta due Example in {@link #data}, identificati dagli indici i e j passati come parametro.
     * Il confronto è effettuato sfruttando il metodo {@link Example#compareTo(Example)}
     * 
     * @param i Indice del primo Example in {@link #data}
     * @param j Indice del secondo Example in {@link #data}
     * 
     * @return true se i due Example sono uguali, false altrimenti.
     */
	private boolean compare(int i, int j)
	{
		if(i<getNumberOfExamples() && j<getNumberOfExamples())
		{
			if(data.get(i).compareTo(data.get(j))==0) return true;
			else return false;
		}
		else return false;
	}
	
	/**
	 * Invoca {@link Data#computePrototype(Set, ContinuousAttribute)} o {@link #computePrototype(Set, DiscreteAttribute)}
	 * a seconda se attribute sia di tipo {@link ContinuousAttribute} o {@link DiscreteAttribute}.
	 * 
	 * @param idList Insieme di interi contenenti degli indici di riga degli Example di {@link #data}
	 * @param attribute Attributo sul quale effettuare il controllo
	 * @return A seconda dell'Attribute passato al metodo, r
	 * estituisce il risultato di {@link Data#computePrototype(Set, ContinuousAttribute)} o {@link #computePrototype(Set, DiscreteAttribute)}
	 */
	Object computePrototype(Set<Integer> idList, Attribute attribute)
	{
		if(attribute instanceof DiscreteAttribute) return computePrototype(idList, (DiscreteAttribute) attribute);
		else return computePrototype(idList, (ContinuousAttribute) attribute);
	}
	
	/**
	 * Determina il valore prototipo come media dei valori osservati per attribute nelle transazioni di data aventi indice di riga in idList.
	 * 
	 * @param idList Insieme di interi contenenti degli indici di riga degli Example di {@link #data}
	 * @param attribute Attribute sul quale calcolare la media
	 * 
	 * @return Media tra i valori degli Example i cui indici sono contenuti in idList dell'attributo attribute presenti in {@link #data}
	 */
	private double computePrototype(Set<Integer> idList, ContinuousAttribute attribute)
	{
		double media=0;
		int count=0;
		int i=0;
		for(Object o : data)
		{
			if(idList.contains(i))
			{
				media+=(double) ((Example) o).get(attribute.getIndex());
				count++;
			}
			i++;
		}
		return media/count;
	}
	
	/**
	 * Determina il valore dell'attributo attribute presente con maggior frequenza.
	 * 
	 * @param idList Insieme di interi contenenti degli indici di riga degli Example di {@link #data}
	 * @param attribute Attribute sul quale effettuare l'ispezione
	 * 
	 * @return Restituisce il valore presente con maggior frequenza dell'attributo Attribute.
	 */
	private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute)
	{
		int maxFrequency = -1;
		Object maxFrequencyAttribute = null;
		
		Iterator<String> iterator = attribute.iterator();
		while(iterator.hasNext())
		{
			Object a = iterator.next();
			int currentFrequency = attribute.frequency(this, idList, a.toString());
			if(currentFrequency>maxFrequency)
			{
				maxFrequency = currentFrequency;
				maxFrequencyAttribute = a;
			}
		}
		return maxFrequencyAttribute.toString();
	}
	
	public String toString()
    {
        String dataString = new String();
        for(Attribute a : explanatorySet) dataString+=a.toString()+" ";
        dataString+="\n";
        int i=0;
        for(Example ex: data)
        {
            dataString+=(i+1)+": ";
            dataString+=ex.toString();
            dataString+="\n";
            i++;
        }
        return dataString;
    }
}
