package edu.umn.csci5801;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that interfaces with the SQLite Database, ensures queries are not prone to SQL Injection 
 * and that access to the database is controlled only through this class.
 */
public class DBCoordinator {
	/**
	 *  Connection object used to perform queries on the database. 
	 */
	private Connection con;
	/**
	 *  Statement object that will be executed upon getting a connection to the database. 
	 */
	private Statement stmt;

	/**
	 * @param sqlCmd the command to be executed.
	 * @return A List of ArrayLists (each list containing a record of the returned data)
	 * 		each ArrayList then contains objects that will later be downcasted.
	 * 		An empty list might be returned if there are no records returned from the query.   
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ArrayList<Object>> queryData(String sqlCmd) throws ClassNotFoundException, SQLException {
		List<ArrayList<Object>> res = new ArrayList<ArrayList<Object>>();
		String originStr = sqlCmd;
		sqlCmd = sqlCmd.toUpperCase();

		if ((sqlCmd.matches(".*\\sINSERT\\s.*") || sqlCmd.matches("INSERT\\s.*") || sqlCmd.matches(".*\\sUPDATE\\s.*")
				|| sqlCmd.matches("UPDATE\\s.*") || sqlCmd.matches(".*\\sDELETE\\s.*")
				|| sqlCmd.matches("DELETE\\s.*")) == true)
			throw new IllegalArgumentException("SQL contains non select command, such as Insert, Update, Delete.");

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SCRSDataBase.db");
		con.setAutoCommit(false);
		//System.out.println("Opened database successfully");

		sqlCmd = originStr;
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlCmd);
		while (rs.next()) {
			ArrayList<Object> tmpRes = new ArrayList<Object>();
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				tmpRes.add(rs.getObject(i + 1));
			}
			res.add(tmpRes);
		}
		rs.close();
		stmt.close();

		con.commit();
		con.close();

		//System.out.println("Operation done successfully");

		return res;
	}

	/**
	 * This method deletes records from a database safely.
	 * @param sqlCmd the delete query with ??? symbols for values.
	 * @param dataList The data to be inserted into the SQLQuery (avoids SQL Injection)
	 * @param typeList The types of data in the SQL Query
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void deleteData(String sqlCmd, ArrayList<String> dataList, ArrayList<Constants.PrimitiveDataType> typeList)
			throws ClassNotFoundException, SQLException, ParseException {
		sqlCmd = sqlCmd.toUpperCase();

		if ((sqlCmd.matches(".*\\sINSERT\\s.*") || sqlCmd.matches("INSERT\\s.*") || sqlCmd.matches(".*\\sUPDATE\\s.*")
				|| sqlCmd.matches("UPDATE\\s.*") || sqlCmd.matches(".*\\sSELECT\\s.*")
				|| sqlCmd.matches("SELECT\\s.*")) == true)
			throw new IllegalArgumentException("SQL contains non select command, such as Insert, Update, Select.");

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SCRSDataBase.db");
		con.setAutoCommit(false);
		System.out.println("Opened database successfully");

		PreparedStatement prepStmt = con.prepareStatement(sqlCmd);
		for (int i = 0; i < dataList.size(); i++) {
			switch (typeList.get(i)) {
			case STRING:
				prepStmt.setString(i + 1, dataList.get(i));
				break;
			case INT:
				prepStmt.setInt(i + 1, Integer.parseInt(dataList.get(i)));
				break;
			case DATE:
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				prepStmt.setDate(i + 1, new java.sql.Date(format.parse(dataList.get(i)).getTime()));
				break;
			}
		}
		prepStmt.execute();
		con.commit();

		prepStmt.close();
		con.close();

		System.out.println("Operation done successfully");
	}

	/**
	 * @param sqlCmd The SQL Query that contains the insert command. 
	 * @param dataList The list of values to be added into the database.
	 * @param typeList A list of values containing the types to be added into the database.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void insertData(String sqlCmd, ArrayList<String> dataList, ArrayList<Constants.PrimitiveDataType> typeList)
			throws ClassNotFoundException, SQLException, ParseException {
		sqlCmd = sqlCmd.toUpperCase();
		if ((sqlCmd.matches(".*\\sSELECT\\s.*") || sqlCmd.matches("SELECT\\s.*") || sqlCmd.matches(".*\\sUPDATE\\s.*")
				|| sqlCmd.matches("UPDATE\\s.*") || sqlCmd.matches(".*\\sDELETE\\s.*")
				|| sqlCmd.matches("DELETE\\s.*")) == true)
			throw new IllegalArgumentException("SQL contains non select command, such as Select, Update, Delete.");

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SCRSDataBase.db");
		con.setAutoCommit(false);
		System.out.println("Opened database successfully");

		PreparedStatement prepStmt = con.prepareStatement(sqlCmd);
		for (int i = 0; i < dataList.size(); i++) {
			switch (typeList.get(i)) {
			case STRING:
				prepStmt.setString(i + 1, dataList.get(i));
				break;
			case INT:
				prepStmt.setInt(i + 1, Integer.parseInt(dataList.get(i)));
				break;
			case DATE:
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				prepStmt.setDate(i + 1, new java.sql.Date(format.parse(dataList.get(i)).getTime()));
				break;
			}
		}
		prepStmt.execute();
		con.commit();

		prepStmt.close();
		con.close();

		System.out.println("Records created successfully");
	}

	/**
	 * Function executes an update operation, and prevents the a query from being executed if the command is 
	 * Not an update command. 
	 * @param sqlCmd The query that will carry out the update operation
	 * @param dataList The values that will be injected into the sqlCmd statement
	 * @param typeList The list of types that are contained in the values.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void updateData(String sqlCmd, ArrayList<String> dataList, ArrayList<Constants.PrimitiveDataType> typeList)
			throws ClassNotFoundException, SQLException, ParseException {
		sqlCmd = sqlCmd.toUpperCase();

		if ((sqlCmd.matches(".*\\sINSERT\\s.*") || sqlCmd.matches("INSERT\\s.*") || sqlCmd.matches(".*\\sSELECT\\s.*")
				|| sqlCmd.matches("SELECT\\s.*") || sqlCmd.matches(".*\\sDELETE\\s.*")
				|| sqlCmd.matches("DELETE\\s.*")) == true)
			throw new IllegalArgumentException("SQL contains non select command, such as Insert, Select, Delete.");

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SCRSDataBase.db");
		con.setAutoCommit(false);
		System.out.println("Opened database successfully");

		PreparedStatement prepStmt = con.prepareStatement(sqlCmd);
		for (int i = 0; i < dataList.size(); i++) {
			switch (typeList.get(i)) {
			case STRING:
				prepStmt.setString(i + 1, dataList.get(i));
				break;
			case INT:
				prepStmt.setInt(i + 1, Integer.parseInt(dataList.get(i)));
				break;
			case DATE:
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				prepStmt.setDate(i + 1, new java.sql.Date(format.parse(dataList.get(i)).getTime()));
				break;
			}
		}
		prepStmt.execute();
		con.commit();

		prepStmt.close();
		con.close();

		System.out.println("Operation done successfully");
	}
}
