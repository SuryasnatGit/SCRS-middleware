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
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		System.out.println("running");
		Search something = new Search();
		something.searchClasses(-1, "", "", "", "CS", "", "");
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
			types.add("INSTRUCTOR.LASTNAME");
			values.add("'" + instructor + "'");
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
		// String sqlCmd2 = "Select * from course";
		// stmt.executeUpdate("INSERT INTO COURSE
		// (ID,NAME,LOCATION,TERM,DEPARTMENT,TYPE,CREDITS,CAPACITY,FIRSTDAY,LASTDAY,CLASSBEGINTIME,CLASSENDTIME,ROUTINES,DESCRIPTION)
		// VALUES ( 600, 'test2','East Bank KHKH3-3012','term2','CS','Seminar',
		// 3, 10, 10/10/2016, 11/11/2016,'11:22','11:23','Tu Fri', 'do stuff3');
		// " );

		DBCoordinator db = new DBCoordinator();
		List<ArrayList<Object>> res = new ArrayList<ArrayList<Object>>();
		res = db.queryData(sqlCmd);
		System.out.println(res);

		resultList = DBProcessor.toStringArray(res);

		// System.out.println(resultList);

		return resultList;

	}

}
