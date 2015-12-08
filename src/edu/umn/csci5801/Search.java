package edu.umn.csci5801;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.TypeConstraintException;

public class Search {
	
	
	/* this will be removed it is just used for testing my code */
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
		System.out.println("running");
		Search something = new Search();
		something.searchClasses(-1, "", "", "", "CS", "", "Daniel Mack");
	}

	public List<ArrayList<String>> searchClasses(int courseID, String courseName, String location, String term,
			String department, String classType, String instructor) throws ClassNotFoundException, SQLException {

		List<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();

		ArrayList<String> types = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		String sqlCmd = "Select course.*, instructor.firstname, instructor.lastname from instructorAndcourse join course, instructor where course.id = instructorandcourse.courseid and instructor.id = instructorandcourse.id and ";
		if (courseID != -1) {
			String strCourseID = Integer.toString(courseID);
			types.add("COURSE.ID");
			values.add(strCourseID);
		}
		if (courseName != null && courseName != "") {
			types.add("COURSE.NAME");
			values.add("'" + courseName + "'");
		}
		if (location != null && location != "") {
			types.add("COURSE.LOCATION");
			values.add("'" + location + "'");
		}
		if (term != null && term != "") {
			types.add("COURSE.TERM");
			values.add("'" + term + "'");
		}
		if (department != null && department != "") {
			types.add("COURSE.DEPARTMENT");
			values.add("'" + department + "'");
		}
		if (classType != null && classType != "") {
			types.add("COURSE.TYPE");
			values.add("'" + classType + "'");
		}
		if (instructor != null && instructor != "") {
			
			String nameString[] = instructor.split("\\s+");
			types.add("INSTRUCTOR.FIRSTNAME");
			values.add("'"+ nameString[0] + "'");
				
			types.add("INSTRUCTOR.LASTNAME");
			values.add("'" + nameString[1] + "'");
		}

		StringBuilder sb = new StringBuilder();
		sb.append(sqlCmd);

		for (int i = 0; i < types.size(); i++) {
			if (i > 0)
				sb.append(" AND ");
			sb.append(types.get(i));
			sb.append(" = ");
			sb.append(values.get(i));
		}
		// sb.append(";");
		sqlCmd = sb.toString();
		System.out.println(sqlCmd);

		resultList = DBProcessor.runQuery(sqlCmd);
        System.out.println(resultList);
		return resultList;
	}
}
