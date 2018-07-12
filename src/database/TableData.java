package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

/**
 * Preleva da un database le tuple modellate mediante l'oggetto Example.
 * Utilizza un oggetto DBAccess per stabilire la connessione al database e interrogare
 * la tabella di nome table.
 * 
 * @author de Gennaro Gaetano, Farinola Francesco
 *
 */
public class TableData
{
	/**
	 * Oggetto che modella una connessione al database.
	 * @see DbAccess
	 */
	DbAccess db;

	
	/**
	 * Inizializza {@link #db} con l'oggetto passato per paramtro.
	 * @param db istanza di classe {@link DbAccess}
	 */
	public TableData(DbAccess db)
	{
		this.db=db;
	}

	/**
	 * Legge da database tutte le tuple distinte presenti in table.
	 * 
	 * @param table Nome della tabella dalla quale prelevare le tuple
	 * @return Lista di Example rappresentanti le tuple di una tabella
	 * @throws SQLException sollevata nel caso in cui la connessione al database fallisce
	 * @throws EmptySetException sollevata nel caso in cui la tabella dalla quale si intende leggere le tuple è vuota
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException
	{
		TableSchema schema = new TableSchema(db, table);
		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT DISTINCT * FROM "+table);
		/*Si utilizza una collection perchè i valori saranno sempre necessariamente tutti distinti grazie a DISTINCT*/
		List<Example> examples = new ArrayList<Example>();
		
		while(rs.next())
		{
			Example example = new Example();
			for(int i=0; i<schema.getNumberOfAttributes(); i++)
			{
				Column col = schema.getColumn(i);
				if(col.isNumber()) example.add(rs.getDouble(col.getColumnName()));
				else example.add(rs.getString(col.getColumnName()));
			}
			examples.add(example);
		}
		s.close();
		if(examples.isEmpty()) throw new EmptySetException("No tuples found in: "+table);
		
		return examples;
	}

	/**
	 * Restituisce l'insieme dei valori distinti assunti dalla colonna specificata
	 * 
	 * @param table Nome della tabella dalla quale prelevare i valori
	 * @param column Colonna il cui insieme associato di valori deve essere restituito
	 * @return Insieme dei valori distinti assunti dalla colonna specificata
	 * @throws SQLException sollevata nel caso in cui la connessione al database fallisce
	 */
	public Set<Object> getDistinctColumnValues(String table,Column column) throws SQLException
	{
		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT DISTINCT "+column.getColumnName()+" FROM "+table+" ORDER BY "+column.getColumnName()+" ASC");
		/*in hashset l'ordine non è garantito, quindi si utilizza un treeset*/
		Set<Object> columnValues = new TreeSet<Object>();
		while(rs.next())
		{
			columnValues.add(rs.getObject(column.getColumnName()));
		}
		s.close();
		//System.out.println(columnValues);
		return columnValues;
	}

	/**
	 * Restituisce il risultato di una funzione di aggregazione eseguita dal DBMS.
	 * 
	 * @param table Nome della tabella sulla quale impartire la funzione di aggregazione.
	 * @param column Colonna sulla quale impartire la funzione di aggregazione.
	 * @param aggregate Tipo della funzione di aggregazione.
	 * @return Object Contenente il risultato della funzione di aggregazione.
	 * @throws SQLException Se la connessione al database fallisce.
	 * @throws NoValueException Sollevata se la funzione di aggregazione non produce alcun risultato a causa di mancanza di tuple nel database.
	 */
	public Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException
	{
		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT "+aggregate+"("+column.getColumnName()+") FROM "+table);
		Object value = null;
		if(rs.next()) value =  rs.getObject(1);
		
		if(value==null) throw new NoValueException("No results for: "+aggregate+" in "+column.getColumnName()+" of "+table);
		else return value;
	}
	
}
