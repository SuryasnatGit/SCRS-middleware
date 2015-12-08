package edu.umn.csci5801;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author wclee
 *
 */
public final class DBProcessor {

	/**
	 * 
	 */
	private DBProcessor() {
		// static class does not require a constructor, make it private
	}
	
	/*
	 * Function that down casts return types into strings
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
	
	public static int getIntegerFromQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		if(result.size() != 1)
			throw new SQLException("Query returned more than 1 Integer.");
		return (int) result.get(0).get(0);
	}
	
	public static String getStringFromQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		if(result.size() != 1)
			throw new SQLException("Query returned more than 1 String.");
		return (String) result.get(0).get(0).toString();
	}
	
	public static String getDateFromQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		if(result.size() != 1)
			throw new SQLException("Query returned more than 1 Date.");
		Date date = new Date((long) result.get(0).get(0));
		return  new SimpleDateFormat("MM/dd/yyyy").format(date);
		
	}
	
	public static ArrayList<ArrayList<String>> runQuery(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		return toStringArray(result);
	}

}
