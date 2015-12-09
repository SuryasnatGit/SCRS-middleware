package edu.umn.csci5801;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
		for(ArrayList<Object> Objects : ObjectLists)
        {
			ArrayList<String> strList = new ArrayList<String>();
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
        			System.out.println("DATE : " + date.toString());
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
	
	
	/** Helper function that returns the age (difference in years between birth and current year)
	 * @param dateOfBirth
	 * @return
	 */
	public static int getAge(long dateOfBirth){
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		Date date = new Date((long) dateOfBirth);
		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		return currentYear - dob.get(Calendar.YEAR);
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
	
	
	/** Helper function that executes queries and changes Date of Birth -> Age
	 * @param sqlCmd
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<ArrayList<String>> runQueryWithAge(String sqlCmd) throws SQLException, ClassNotFoundException {
		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> result = db.queryData(sqlCmd);
		
		for(ArrayList<Object> record : result)
		{
			long dateOfBirth = (long) record.get(3);
			record.set(3, getAge(dateOfBirth)); // age is at index 3.
		}

		return toStringArray(result);
	}
	
	/** Helper function that allows a date to be checked if it is within range. 
	 * @param term e.g Fall2015 or Spring2014 
	 * @param currentDate of type java.Util.Date object. Any date would work. 
	 * @return Boolean true if date is within the term range, false otherwise.
	 * @throws ParseException
	 */
	public static boolean isDateWithinRange(String term, Date currentDate) throws ParseException
	{
		String beginRegistrationDate;
		String endRegistrationDate;
		
		String termYear = term.substring(4, 8);
		
		if (term.toLowerCase().trim().startsWith("fall")){
			beginRegistrationDate = "07/01/";
			endRegistrationDate = "09/01/";
		}
		else if(term.toLowerCase().trim().startsWith("spring")){
			beginRegistrationDate = "12/01/";
			endRegistrationDate = "02/01/";
		}
		else
		{
			throw new ParseException("Invalid term: " + term, 4);
		}
		
		beginRegistrationDate += termYear;
		endRegistrationDate += termYear;
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		Date dateStart = format.parse(beginRegistrationDate); 
		Date dateEnd = format.parse(endRegistrationDate);
		
		return currentDate.after(dateStart) && currentDate.before(dateEnd);
	}

}
