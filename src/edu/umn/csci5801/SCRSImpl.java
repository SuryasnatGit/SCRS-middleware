/**
 * 
 */
package edu.umn.csci5801;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

import edu.umn.csci5801.ShibbolethAuth.Token;
import edu.umn.csci5801.ShibbolethAuth.Token.RoleType;

/**
 * This Class implements all the functions defined in the SCRS interface. 
 * Please refer to the JavaDoc there for other comments.
 * All implemented functions do not throw anymore exceptions but rather catch and display an error in 
 * STDERR and return an empty list or boolean status for failure. 
 * 
 * @author wclee
 *
 */
public class SCRSImpl implements SCRS{

	private final List<ArrayList<String>> _empty = new ArrayList<ArrayList<String>>();
	private Student student;
	private Administrator admin;
	private Search search;
	
	
	/**
	 *  Constructor that creates and instance of the classes that perform the operations.
	 */
	public SCRSImpl() {
		student = new Student();
		admin = new Administrator();	
	}

	
	/**
	 * This method is used to perform class queries or provide information to the GUI.
	 * @param courseID can be used by administrator for fast class query
	 * @param courseName   Mandatory Field
	 * @param location     Mandatory Field
	 * @param term		   Mandatory Field
	 * @param department
	 * @param classType
	 * @param instructorName
	 * @return A list of ArrayList, in the ArrayList, each entry stores one query result in the table property order.
	 * Actual Database operations are delegated to the search class.
	 * Empty list will be returned if the query is failed.
	 */
	@Override
	public List<ArrayList<String>> queryClass(int courseID, String courseName, String location, String term,
			String department, String classType, String instructorName) {

		if(location.isEmpty() || courseName.isEmpty() || term.isEmpty())
		{
			System.err.println("Location, Course Name and Term fields must not be empty.");
			return _empty;
		}
		search = new Search();
		try {
			return search.searchClasses(courseID, courseName, location, term, department, classType, instructorName);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Unable to perform search, please try again.");
			return _empty;
		}	
	}

	/**
	 * This method is used for querying student personal data.
	 * @param token
	 * @param studentID
	 * @return Student basic personal data, such as name, id, age, gender, degree, advisor(if applicable), total credits, etc.
	 * Empty list will be returned if the query is failed.
	 * Actual Database operations are delegated to the admin class.
	 * Students will not be able to make a query for anyone other than themselves 
	 * (validated by comparing token data and request)
	 */
	@Override
	public List<ArrayList<String>> queryStudentPersonalData(Token token, int studentID) {
		if(token.type != RoleType.UNDEFINED){
			//validate if a student is making this call and ensure they only query for their own history
			// a student should not request anyone else's history 
			if(token.type == RoleType.STUDENT){
				if(token.id != studentID)
					return _empty;
			}
			try {
				return student.queryStudentPersonalData(studentID);
			} catch (ClassNotFoundException | SQLException e) {
				System.err.println("Unable to query student personal data, please try again.");
			}
		}
		//authentication failed or not a student
		return _empty;
	}

	/**
	 * This method is used for querying student registration history
	 * @param token
	 * @param studentID
	 * @return ClassID, ClassName, Registration Time(term), Credits
	 * Empty list will be returned if the query has failed.
	 * Actual Database operations are delegated to the student class.
	 * This method only allows students to query for their own history (validated by comparing token data and requests)
	 */
	@Override
	public List<ArrayList<String>> queryStudentRegistrationHistory(Token token, int studentID) {
		if(token.type != RoleType.UNDEFINED){
			//validate if a student is making this call and ensure they only query for their own history
			// a student should not request anyone else's history 
			if(token.type == RoleType.STUDENT){
				if(token.id != studentID)
					return _empty;
			}
			return student.queryRegistrationHistory(studentID);
		}
		//authentication failed or not a student
		return _empty;
	}
	
	/**
	 * This method is used for querying admin basic information. 
	 * Actual Database operations are delegated to the admin class.
	 * @param token Only Admin can invoke this function
	 * @return A list with one ArrayList of strings that has the admin data.
	 * Empty list will be returned if the query is failed.
	 * Only admins can use this function.
	 */
	@Override
	public List<ArrayList<String>> queryAdminPersonalData(Token token) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT)
		{
			return admin.queryAdminPersonalData(token);
		}
		return _empty;
	}

	/**
	 * This method is used for querying instructors basic personal information
	 * Actual Database operations are delegated to the admin class.
	 * @param token
	 * @param instructorID -1 if all instructors information need to be returned
	 * @return Store a designated instructor's basic information in database table property order.
	 * Empty list will be returned if the query is failed.
	 * Only admins can use this function.
	 */
	@Override
	public List<ArrayList<String>> queryInstructor(Token token, int instructorID) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT)
		{
			return admin.queryInstructor(instructorID);
		}
		return _empty;
	}
	
	/**
	 * This method should allow the students add one class to the database.
	 * @param token
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successful, false otherwise
	 * Actual database operations are delegated to the student class.
	 * Only students can use this function.
	 */
	@Override
	public boolean studentAddClass(Token token, int courseID, String grading, String courseTerm) {
		Student student = new Student();
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.ADMIN){
			return student.studentAddClass(token, courseID, grading, courseTerm);
		}
		//authentication failed
		return false;
	}

	/**
	 * This method should allow the students drop one class from the database.
	 * @param token
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 * Actual database operations are delegated to the student class.
	 * Only studetns can use this class.
	 */
	@Override
	public boolean studentDropClass(Token token, int courseID) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.ADMIN){
			return student.studentDropClass(token.id, courseID);
		}
		return false;
	}

	/**
	 * This method should allow the students edit one registered class in the database.
	 * @param token
	 * @param courseID
	 * @param grading This parameter should just have one of "A/F", "Audit", "S/N" value
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean studentEditClass(Token token, int courseID, String grading, String courseTerm) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.ADMIN){
			return student.studentEditClass(token.id, courseID, grading, courseTerm);
		}
		return false;
	}

	/*
	 * Admin Methods
	 */
	/**
	 * This interface should allow the admin to add class into the database
	 * @param token
	 * @param courseID
	 * @param courseName
	 * @param courseCredits
	 * @param instructor
	 * @param firstDay The first day of the class in the new semester
	 * @param lastDay The last day of the class in the new semester
	 * @param classBeginTime E.g. 9:00
	 * @param classEndTime	E.g. 16:00
	 * @param weekDays E.g. Tu, Fri
	 * @param location
	 * @param type
	 * @param prerequisite
	 * @param description
	 * @param department
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean adminAddClass(Token token, int courseID, String courseName, int courseCredits, String instructor,
			String firstDay, String lastDay, String classBeginTime, String classEndTime, String weekDays,
			String location, String type, String prerequisite, String description, String department) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT){
			return admin.adminAddClass(token, courseID, courseName, courseCredits,
					instructor, firstDay, lastDay, classBeginTime, classEndTime, 
					weekDays, location, type, prerequisite, description, department);
		}
		return false;
	}

	/**
	 * This method should allow the admin to delete a class from the database if and only if there is not one register it.
	 * @param token
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean adminDeleteClass(Token token, int courseID) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT){
			return admin.adminDeleteClass(token, courseID);
		}
		return false;
	}

	/**
	 * This method should allow the admin to modify the existed class in the database.
	 * The admin can only edit the class's description if at least one student registers this class
	 * The admin can edit everything of the class if no one registers it
	 * @param token
	 * @param courseID
	 * @param courseName
	 * @param courseCredits
	 * @param instructor
	 * @param firstDay The first day of the class in the new semester
	 * @param lastDay The last day of the class in the new semester
	 * @param classBeginTime E.g. 9:00
	 * @param classEndTime	E.g. 16:00
	 * @param weekDays E.g. Tu, Fri
	 * @param location
	 * @param type
	 * @param prerequisite
	 * @param description
	 * @param department
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean adminEditClass(Token token, int courseID, String courseName, int courseCredits, int instructorID,
			String firstDay, String lastDay, String classBeginTime, String classEndTime, String weekDays,
			String location, String type, String prerequisite, String description, String department) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT){
			return admin.adminEditClass(token, courseID, courseName, courseCredits, 
										instructor, firstDay, lastDay, classBeginTime, 
										classEndTime, weekDays, location, type, prerequisite, description, department);
		}
		return false;
	}

	/**
	 * This method should allow the admin to add one student to one specific class if exist
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean adminAddStudentToClass(Token token, int studentID, int courseID, String grading, String courseTerm) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT){
			return admin.adminAddStudentToClass(token, studentID, courseID, grading, courseTerm);
		}
		return false;
	}

	/**
	 * This method should allow the admin to edit one student to one specific class if exist
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean adminEditStudentRegisteredClass(Token token, int studentID, int courseID, String grading,
			String courseTerm) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT){
			return admin.adminEditStudentRegisteredClass(token, studentID, courseID, grading, courseTerm);
		}
		return false;
	}

	/**
	 * This method should allow the admin to remove one registered class from a student's registered class's list.
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 */
	@Override
	public boolean adminDropStudentRegisteredClass(Token token, int studentID, int courseID) {
		if(token.type != RoleType.UNDEFINED && token.type != RoleType.STUDENT){
			return admin.adminDropStudentRegisteredClass(token, studentID, courseID);
		}
		return false;
	}


	@Override
	public boolean adminAddClass(Token token, int courseID, String courseName, int courseCredits, int courseCapacity,
			String term, int instructorID, String firstDay, String lastDay, String classBeginTime, String classEndTime,
			String weekDays, String location, String type, String prerequisite, String description, String department) {
		// TODO Auto-generated method stub
		return false;
	}

}
