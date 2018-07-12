package data;

/**
 * Estende {@link Attribute} per modellare un attributo continuo.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * @see Attribute
 *
 */
class ContinuousAttribute extends Attribute
{
	/**
	 * Valore utilizzato per la normalizzazione.
	 */
	private double min;
	
	/**
	 * Valore utilizzato per la normalizzazione.
	 */
    private double max;     
    
    /**
     * Richiama il costruttore della super classe per inizializzare name e index.
     * Inizializza inoltre {@link #max} e {@link #min}.
     * 
     * @param name Nome dell'attributo
     * @param index Identificativo dell'attributo.
     * @param max Valore max utilizzato per la normalizzazione.
     * @param min Valore min utilizzato per la normalizzazione
     * @see Attribute
     */
    ContinuousAttribute(String name, int index, double max, double min)
    {
        super(name,index);
        this.max = max;
        this.min = min;
    }
    
    /**
     * Calcola e restituisce il valore normalizzato del parametro passato in input.
     * La normalizzazione del parametro v è quindi calcolata come segue: v'=(v-min)/(max-min).
     * @param v Prametro che si intende normalizzare.
     * @return Risultato della normalizzazione di v.
     */
    double getScaledValue(double v)
    {
        return (v-min)/(max-min);
    }
}
