package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Serve a modellare le informazioni di una tabella in un database.
 * Si serve di un oggetto DBAccess per eseguire una connessione al database.
 * Esegue una interrogazione per estrarre le tuple distinte da tale tabella.
 * Per ogni tupla del resultset, si crea un oggetto, istanza della classe Example,
 * il cui riferimento verr‡† incluso in una lista. 
 * In particolare, per la tupla corrente nel ResultSet,
 * si estraggono i valori dei singoli campi,
 * e vengono aggiunti all'oggetto istanza della classe Example costruito.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 * @see DbAccess
 * @see ResultSet
 *
 */
public class TableSchema
{
	/**
	 * Oggetto che modella una connessione al database.
	 * @see DbAccess
	 */
	DbAccess db;
	
	/**
	 * Contiene informazioni su una colonna di una tabella prelevata dal database
	 * 
	 * @author de Gennaro Gaetano, Farinola Francesco
	 *
	 */
	public class Column
	{
		/**
		 * Nome della colonna.
		 */
		private String name;
		
		/**
		 * Tipo di dato che rappresenta la colonna.
		 */
		private String type;
		
		/**
		 * Inizializza {@link #name} e {@link #type}.
		 * 
		 * @param name Nome della colonna.
		 * @param type Tipo di dato che rappresenta la colonna.
		 */
		Column(String name,String type)
		{
			this.name=name;
			this.type=type;
		}
		
		/**
		 * Restituisce il nome della colonna.
		 * 
		 * @return Restituisce una stringa contenente il nome della colonna.
		 */
		public String getColumnName()
		{
			return name;
		}
		
		/**
		 * Restituisce true se {@link #type} Ë "number", false altrimenti.
		 * 
		 * @return true se {@link #type} Ë "number", false altrimenti
		 */
		public boolean isNumber()
		{
			return type.equals("number");
		}
		
		public String toString()
		{
			return name+":"+type;
		}
	}
	
	/**
	 * Lista di Column.
	 */
	List<Column> tableSchema=new ArrayList<Column>();
	
	/**
	 * Istanzia un oggetto di tipo TableSchema e popola una lista di colonne che contengono 
	 * le informazioni relative alle colonne della tabella.
	 * Necessita di un DBAccess per la connessione al database e di una string che specifica
	 * il nome della tabella presa in considerazione. Pu√≤ sollevare l'eccezione SQLException se 
	 * ci sono problemi nella connessione al database. Oppure pu√≤ sollevare l'eccezione 
	 * EmplySetException nel caso in cui la tabella considerata √® vuota.
	 * 
	 * @param db istanza di classe {@link DbAccess}
	 * @param tableName Nome della tabella dalla quale si intende ricavare informazioni
	 * @throws SQLException Sollevata se ci sono problemi nella connessione al database
	 * @see DbAccess
	 * @see SQLException
	 * 
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException
	{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		
	
		Connection con=db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
	    ResultSet res = meta.getColumns(null, null, tableName, null);
	    		   
	    while (res.next())
	    {
	    	if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
	    }
	    res.close();
    }
	  
	/**
	 * Restituisce il numero degli attributi, ovvero la lunghezza di {@link #tableSchema}
	 * @return Restituisce la lunghezza di {@link #tableSchema}
	 */
	public int getNumberOfAttributes()
	{
		return tableSchema.size();
	}
	
	/**
	 * Restituisce la Column in {@link #tableSchema} con indice index.
	 * 
	 * @param index Indice della colonna da restituire
	 * @return Restituisce la Column in {@link #tableSchema} con indice index.
	 */
	public Column getColumn(int index)
	{
		return tableSchema.get(index);
	}

		
}

		     


