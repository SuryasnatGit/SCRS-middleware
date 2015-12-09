package edu.umn.csci5801;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {

	/**
	 * @param courseID
	 * @param courseName
	 * @param location
	 * @param term
	 * @param department
	 * @param classType
	 * @param instructor
	 * @return Return a List of Results, which are a list of strings containing the data. An empty string otherwise.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
		sqlCmd = sb.toString();
        
		String sqlAll = "Select * from course join instructorandcourse, instructor where course.id = instructorandcourse.courseid and instructor.id = instructorandcourse.id";
		if(courseID == -1)
			resultList = DBProcessor.runQuery(sqlAll);
		else
			resultList = DBProcessor.runQuery(sqlCmd);
		return resultList;
	}
}
