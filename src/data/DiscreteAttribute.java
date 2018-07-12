package data;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Estende {@link Item} per modellare un Attributo di tipo discreto.
 * Implementa l'interfaccia Iterable per consentire l'uso di Iterator su {@link #values}.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * @see Iterable
 * @see Iterator
 *
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>
{
	/**
	 * Insieme ordinato di valori di tipo String.
	 * L'ordinamento è effettuato secondo l'ordine alfabetico delle stringhe contenute.
	 */
    private TreeSet<String> values;
    
    /**
     * Invoca il costruttore della super classe per inizializzare name e index.
     * Inizializza inoltre #values.
     * 
     * @param name Nome dell'attributo coinvolto.
     * @param index Identificativo dell'attributo coinvolto.
     * @param values Insieme ordinato di valori di tipo String.
     */
    public DiscreteAttribute(String name, int index, TreeSet<String> values)
    {
        super(name,index);
        this.values = values;
    }
    
    /**
     * Restituisce il numero di valori distinti contenuti in {@link #values}, ovvero la sua lunghezza.
     * @return Restituisce la lunghezza di {@link #values}
     */
    int getNumberOfDistinctValues()
    {
        return values.size();
    }
    
    /**
     * Determina il numero di volte con cui il valore v compare in corrispondenza dell'attributo corrente
     * negli {@link database.Example} memorizzati in {@link Data} e indicizzati per riga da idList.
     * 
     * @param data Dati sul quale calcolare la frequenza.
     * @param idList Indici di riga degli Example in Data sui quali effettuare il calcolo.
     * @param v Valore di cui calcolare la frequenza in corrispondenza degli indici di riga in idList e in corrispondenza della colonna
     * dell'attributo corrente.
     * @return Restituisce il numero di volte con cui il valore v compare in corrispondenza dell'attributo corrente
     * negli {@link database.Example} memorizzati in {@link Data} e indicizzati per riga da idList.
     */
    int frequency(Data data, Set<Integer> idList, String v)
    {
    	int occurrences = 0;
    	for(int i=0;i<data.getNumberOfExamples();i++)
    	{
    		if(idList.contains(i) && data.getAttributeValue(i, this.getIndex()).equals(v)) occurrences++;
    	}
    	return occurrences;
    }
    
    /**
     * Ritorna l'iteratore del TreeSet {@link #values}
     */
    public Iterator<String> iterator()
    {
    	return values.iterator();
    }
}
