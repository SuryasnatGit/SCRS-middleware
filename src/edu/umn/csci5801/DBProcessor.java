package edu.umn.csci5801;
import java.util.List;
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
	public static ArrayList<ArrayList<String>> toStringArray(List<ArrayList<Object>> ObjectLists)
	{
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
        		else 
        		{
        			throw new ClassCastException("Could not cast: " + fields );
        		}
        	}
        		
        	listOfStrList.add(strList);
        }
        return listOfStrList;
	}

}
