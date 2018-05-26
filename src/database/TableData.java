package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;


public class TableData {

	DbAccess db;

	
	
	public TableData(DbAccess db)
	{
		
		this.db=db;
	}

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

	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException
	{
		Statement s = db.getConnection().createStatement();
		ResultSet rs = s.executeQuery("SELECT "+aggregate+"("+column.getColumnName()+") FROM "+table);
		Object value = null;
		if(rs.next()) value =  rs.getObject(1);
		
		if(value==null) throw new NoValueException("No results for: "+aggregate+" in "+column.getColumnName()+" of "+table);
		else return value;
	}
	
}
