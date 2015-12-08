package edu.umn.csci5801;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;


public class Student extends People {
	private String department;
	private String type; // Undergraduate, graduate, phd, etc.
	private int currentCredits;
	private String plan;
	private String status; // Full time or part time
	private String advisor;
	private List<ArrayList<String>> resultList;
	private String sqlCmd;
	
	public Student() { 
		department = "";
		type = "";
		currentCredits = 0;
		plan = "";
		status = "";
		advisor = "";
	}
	
	public List<ArrayList<String>> queryRegistrationHistory (int studentId)throws ClassNotFoundException,SQLException{
		sqlCmd = "Select studentandcourse.id, course.name, studentandcourse.courseterm ,studentandcourse.grading " + 
		"from course join StudentAndCourse where course.id = studentandcourse.courseid " +
		"and studentID = " + studentId + ";";
		resultList = DBProcessor.runQuery(sqlCmd);
		return resultList;
	}
	
	public List<ArrayList<String>> queryStudentPersonalData (int studentId) throws ClassNotFoundException, SQLException{
		sqlCmd = "Select * from student where Id = " + studentId + ";";
		resultList=DBProcessor.runQueryWithAge(sqlCmd);
		return resultList;	
	}
	
	public boolean studentAddClass (int studentId, int courseId, String grading, String courseTerm)throws ParseException, ClassNotFoundException, SQLException{
		boolean isValid = false;
		Date date = new Date();

		String sqlGetTerm = "select course.term from course where course.id = " + courseId;
		String term = DBProcessor.getStringFromQuery(sqlGetTerm);
		
		String sqlStudCredits = "select count (course.credits)from studentandcourse join course " +
				"where studentandcourse.courseid = course.id and studentandcourse.studentid = " + studentId;
		int studCredits = DBProcessor.getIntegerFromQuery(sqlStudCredits);
		
		String sqlCourseCredit = "select course.credits from course where course.Id = " + courseId;
		int courseCredit = DBProcessor.getIntegerFromQuery(sqlCourseCredit);
		
		String sqlCapacity = "select course.capacity from course where course.Id = " + courseId;
		int capacity = DBProcessor.getIntegerFromQuery(sqlCapacity);
		
		String sqlTotalCredits = "select student.credits from student where student.Id = " + studentId;
		int totalCredit = DBProcessor.getIntegerFromQuery(sqlTotalCredits);
		int incrementCredits = totalCredit + courseCredit;
		
		if (DBProcessor.isDateWithinRange(term, date)){
				if ((courseCredit + studCredits)<=30)
					if (capacity != 30){
						ArrayList<String>coursePropertyValue = new ArrayList<String>();
						ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
						coursePropertyValue.add(Integer.toString(studentId));
						coursePropertyType.add(Constants.PrimitiveDataType.INT);
						coursePropertyValue.add(Integer.toString(courseId));
						coursePropertyType.add(Constants.PrimitiveDataType.INT);
						coursePropertyValue.add(grading);
						coursePropertyType.add(Constants.PrimitiveDataType.STRING);
						coursePropertyValue.add(courseTerm);
						coursePropertyType.add(Constants.PrimitiveDataType.STRING);
						System.out.println(coursePropertyValue);
						System.out.println(coursePropertyType);
						DBCoordinator db = new DBCoordinator();
						sqlCmd = "Insert into StudentAndCourse ( studentId, courseId, grading, courseterm ) values (?,?,?,?); ";					
						db.insertData(sqlCmd, coursePropertyValue, coursePropertyType);
						
						coursePropertyType.clear();
						coursePropertyValue.clear();
						coursePropertyType.add(Constants.PrimitiveDataType.STRING);
						coursePropertyValue.add(Integer.toString(incrementCredits));
						coursePropertyType.add(Constants.PrimitiveDataType.INT);
						coursePropertyValue.add(Integer.toString(studentId));
						coursePropertyType.add(Constants.PrimitiveDataType.INT);
					    String sqlCmd = "update student set credits = (?) Where id = (?);";				
						db.updateData(sqlCmd, coursePropertyValue, coursePropertyType);
						
					    sqlTotalCredits = "select student.credits from student where student.Id = " + studentId;
						totalCredit = DBProcessor.getIntegerFromQuery(sqlTotalCredits);
						isValid = true;
				}
		}
		return isValid;
	}

	//Make sure we passing student id from SCRS
	public boolean studentEditClass(int studentId, int courseId, String grading, String courseTerm) throws ParseException, SQLException, ClassNotFoundException{
		boolean isValid = false;
		Date date = new Date();
	
		String sqlGetTerm = "select course.term from course where course.id = " + courseId;
		String term = DBProcessor.getStringFromQuery(sqlGetTerm);
			
		if (DBProcessor.isDateWithinRange(term, date)){
					ArrayList<String>coursePropertyValue = new ArrayList<String>();
					ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
					coursePropertyValue.add((grading));
					coursePropertyType.add(Constants.PrimitiveDataType.STRING);
					coursePropertyValue.add(Integer.toString(courseId));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					coursePropertyValue.add(Integer.toString(studentId));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
				    String sqlCmd = "update StudentAndCourse set grading = (?) Where courseId= (?) and studentId = (?);";				
					DBCoordinator db = new DBCoordinator();
					db.updateData(sqlCmd, coursePropertyValue, coursePropertyType);
					isValid = true;	
		}
		return isValid;
	}
	
	
	
	public boolean studentDropClass (int studentId, int courseId)throws ParseException, SQLException, ClassNotFoundException{
		boolean isValid = false;
		Date date = new Date(); //get current date
		
		String sqlGetTerm = "select course.term from course where course.id = " + courseId;
		String term = DBProcessor.getStringFromQuery(sqlGetTerm);
				
		String sqlCourseCredit = "select course.credits from course where course.Id = " + courseId;
		int courseCredit = DBProcessor.getIntegerFromQuery(sqlCourseCredit);
		
		String sqlTotalCredits = "select student.credits from student where student.Id = " + studentId;
		int totalCredit = DBProcessor.getIntegerFromQuery(sqlTotalCredits);
		int decrementCredits = totalCredit - courseCredit;
		
		if (DBProcessor.isDateWithinRange(term, date)){
					ArrayList<String>coursePropertyValue = new ArrayList<String>();
					ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
					sqlCmd = "delete from StudentAndCourse Where courseId= (?) and studentId = (?);";
					coursePropertyValue.add(Integer.toString(courseId));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					coursePropertyValue.add(Integer.toString(studentId));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					DBCoordinator db = new DBCoordinator();
					db.deleteData(sqlCmd, coursePropertyValue, coursePropertyType);
					coursePropertyValue.clear();
					coursePropertyType.clear();
					coursePropertyType.add(Constants.PrimitiveDataType.STRING);
					coursePropertyValue.add(Integer.toString(decrementCredits));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					coursePropertyValue.add(Integer.toString(studentId));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
				    String sqlCmd = "update student set credits = (?) Where Id = (?);";				
					db.updateData(sqlCmd, coursePropertyValue, coursePropertyType);
					isValid = true;	
		}
		return isValid;
	}
	
	public String getDepartmentInfo() {
		return department;
	}

	public String getStudentTypeInfo() {
		return type;
	}

	public int getCurrentCreditsInfo() {
		return currentCredits;
	}

	public String getPlanInfo() {
		return plan;
	}

	public String getStatusInfo() {
		return status;
	}

	public String getAdvisorInfo() {
		return advisor;
	}

	public void updateDepartment(String newDepartment) {
		department = newDepartment;
	}
	
	public void updateType(String newType) {
		type = newType;
	}
	
	public void updateCurrentCredits(int newCredits) {
		currentCredits = newCredits;
	}
	
	public void updatePlan(String newPlan) {
		plan = newPlan;
	}
	
	public void updateStatus(String newStatus) {
		status = newStatus;
	}
	
	public void updateAdvisor(String newAdvisor) {
		advisor = newAdvisor;
	}
}
