package edu.umn.csci5801;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Student extends People {
	private String department;
	private String type; // Undergraduate, graduate, phd, etc.
	private int currentCredits;
	private String plan;
	private String status; // Full time or part time
	private String advisor;
	private List<ArrayList<String>> resultList;
	private String sqlCmd;
	
	/* this will be removed it is just used for testing my code */
	public static void main(String args[]) throws ParseException,SQLException, ClassNotFoundException {
		System.out.println("running");
		Student something = new Student();
	//	something.queryRegistrationHistory(0);
	//	something.queryStudentPersonalData(0);
		something.studentAddClass(0, 2, "A-F","Fall2015");
		}
	
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
		System.out.println(sqlCmd);
		resultList = DBProcessor.runQuery(sqlCmd);
		 System.out.println(resultList);	
		return resultList;
	}
	
	public List<ArrayList<String>> queryStudentPersonalData (int studentId) throws ClassNotFoundException, SQLException{
		//*****what about age***** currently returns just a dob
		sqlCmd = "Select * from student where Id = " + studentId + ";";
		resultList=DBProcessor.runQuery(sqlCmd);
		return resultList;	
	}
	
	public boolean studentAddClass (int studentId, int courseId, String grading, String courseTerm)throws ParseException, ClassNotFoundException, SQLException{
		boolean isValid = false;
		Date date = new Date();
		Date dateEnd = new Date();
		Date dateStart = new Date();
		String beginRegisterDate = null;
		String endRegisterDate = null;
		String temp = null;
		
		String sqlGetTerm = "select course.term from course where course.id = " + courseId;
		String term = DBProcessor.getStringFromQuery(sqlGetTerm);
		
		int yearInt = Calendar.getInstance().get(Calendar.YEAR);
		
		String sqlStudCredits = "select count (course.credits)from studentandcourse join course " +
				"where studentandcourse.courseid = course.id and studentandcourse.studentid = " + studentId;
		int studCredits = DBProcessor.getIntegerFromQuery(sqlStudCredits);
		
		String sqlCourseCredit = "select course.credits from course where course.Id = " + courseId;
		int courseCredit = DBProcessor.getIntegerFromQuery(sqlCourseCredit);
		
		String sqlCapacity = "select course.capacity from course where course.Id = " + courseId;
		int capacity = DBProcessor.getIntegerFromQuery(sqlCapacity);
		
		if (term.toLowerCase().startsWith("fall")){
		    temp = term.substring(4,8);
		    beginRegisterDate = "07/01/" + temp;
		    endRegisterDate = "09/01/" + temp;
		}
			else if(term.toLowerCase().startsWith("spring")) {
				temp = term.substring(6,12);
				beginRegisterDate = "12/01/" + temp;
				endRegisterDate = "02/01/" + temp;
			}

		if (temp!=null && !Integer.toString(yearInt).equals(temp))
			isValid = false;

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = new SimpleDateFormat("MM/dd/yyyy").format(date);
		date = format.parse(dateString);
		dateStart = format.parse(beginRegisterDate); 
		dateEnd = format.parse(endRegisterDate);
		
		if (temp!=null && Integer.toString(yearInt).equals(temp))
			if (date.after(dateStart) && date.before(dateEnd))
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

				        isValid = true;
				}
		return isValid;
	}

	
	public boolean studentEditClass(int courseId, String grading, String courseTerm){
		
		
		
		
		return true;
		
	}
	
	
	
	public boolean studentDropClass (int studentId, int courseId){
		//verify student in class
		//increment capacity in courses
		//remove from student and course
		// decrement credits from student
		return true;
	}
	// studentAddClass(Token token, int courseID, string grading, String courseterm): return Boolean 

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
