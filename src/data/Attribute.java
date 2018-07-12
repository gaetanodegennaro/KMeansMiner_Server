package data;

import java.io.Serializable;

/**
 * Modella un attributo.
 * Implementa l'interfaccia Serializable per consentirne la serializzazione.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html">Serializable</a> 
 *
 */
abstract class Attribute implements Serializable
{
	/**
	 * Nome dell'attributo.
	 */
    private String name;
    
    /**
     * Identificativo numerico dell'attributo.
     */
    private int index;
    
    /**
     * Inizializza {@link #name} e {@link #index} con i valori degli oggetti passati per paramteri.
     * @param name Nome dell'attributo.
     * @param index Identificativo numerico dell'attributo.
     */
    Attribute(String name, int index)
    {
        this.name = name;
        this.index = index;
    }
    
    /**
     * Restituisce {@link #name}
     * @return Restituisce il nome {@link #name} dell'attributo.
     */
    String getName(){return this.name;}
    
    /**
     * Restituisce {@link #index}
     * @return Restituisce l'identificativo {@link #index} dell'attributo.
     */
    int getIndex(){return this.index;}
    
    @Override
    public String toString()
    {
        return name;
    }
}
