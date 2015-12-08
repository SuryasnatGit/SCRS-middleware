package edu.umn.csci5801;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author wclee
 * A static and final class that contains helper functions for interacting with the 
 * database to obtain the right types using casting. This avoids duplicate code used to cast in 
 * each function.
 */
public final class DBProcessor {

	/**
	 * Static class should not have a constructor, hence this is made private to avoid 
	 * instantiation.
	 */
	private DBProcessor() {
	}
	
	/**Function that down casts return types into strings
	 * @param ObjectLists from the Database results
	 * @return A list of of strings of list of strings. Date formats are also converted.
	 */
	public static ArrayList<ArrayList<String>> toStringArray(List<ArrayList<Object>> ObjectLists) {
		ArrayList<ArrayList<String>> listOfStrList = new ArrayList<ArrayList<String>>();
		ArrayList<String> strList = new ArrayList<String>();
		for(ArrayList<Object> Objects : ObjectLists)
        {
        	strList.clear();
        	for( Object fields : Objects )
        	{
        		if (fields == null)
        			strList.add("");
        		else if(fields instanceof String)
        			strList.add( (String) fields  );
        		else if(fields instanceof Integer)
        			strList.add( (String) fields.toString());
        		else if(fields instanceof Long)
        		{
        			Date date = new Date((long) fields);
        			strList.add( new SimpleDateFormat("MM/dd/yyyy").format(date));
        		}
          		else 
        		{
        			throw new ClassCastException("Could not cast: " + fields );
        		}
        	}
        		
        	listOfStrList.add(strList);
        }
        return listOfStrList;
	}
	
	/** Helper function that returns one and only one integer from a query result.
	 * Unwraps the values from a list of lists and type casted into the proper primitive.
	 * @param sqlCmd
	 * @return An integer from the database result.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static int getIntegerFromQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		if(result.size() != 1)
			throw new SQLException("Query returned more than 1 Integer.");
		return (int) result.get(0).get(0);
	}
	
	/** Helper function that returns one and only one String from the query result.
	 * Unwraps the values from a the list of lists and type casts into a String type. 
	 * @param sqlCmd
	 * @return A string from the database result.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String getStringFromQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		if(result.size() != 1)
			throw new SQLException("Query returned more than 1 String.");
		return (String) result.get(0).get(0).toString();
	}
	
	/** Helper function that returns a Stringified version of the date from the query.
	 *  Only returns one and only one date. 
	 * @param sqlCmd
	 * @return A string version of the date in the required format.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String getDateFromQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		if(result.size() != 1)
			throw new SQLException("Query returned more than 1 Date.");
		Date date = new Date((long) result.get(0).get(0));
		return  new SimpleDateFormat("MM/dd/yyyy").format(date);
		
	}
	
	/** Helper function that executes queries using a new DbCoordinator instance. 
	 * @param sqlCmd
	 * @return An array list of records, which are converted into a string array using toStringArray()
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<ArrayList<String>> runQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		return toStringArray(result);
	}

}
