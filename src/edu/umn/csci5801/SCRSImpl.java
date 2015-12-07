/**
 * 
 */
package edu.umn.csci5801;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.umn.csci5801.ShibbolethAuth.Token;
import edu.umn.csci5801.ShibbolethAuth.Token.RoleType;

/**
 * @author wclee
 *
 */
public class SCRSImpl implements SCRS{

	public ShibbolethAuth authenticate;
	private final List<ArrayList<String>> _empty = new ArrayList<ArrayList<String>>();
	
	public SCRSImpl() {
		authenticate = new ShibbolethAuth();
	}

	@Override
	public List<ArrayList<String>> queryClass(int courseID, String courseName, String location, String term,
			String department, String classType, String instructorName) {
		Search search = new Search();
		try {
			return search.searchClasses(courseID, courseName, location, term, department, classType, instructorName);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return _empty;
		}	
	}

	@Override
	public List<ArrayList<String>> queryStudentPersonalData(Token token, int studentID) {
		Student student = new Student();
		if(authenticate.TokenAuth(token)){
			//validate if a student is making this call and ensure they only query for their own history
			// a student should not request anyone else's history 
			if(token.type == RoleType.STUDENT){
				if(token.id != studentID)
					return _empty;
			}
			return student.queryStudentPersonalData(studentID);
		}
		//authentication failed or not a student
		return _empty;
	}

	@Override
	public List<ArrayList<String>> queryStudentRegistrationHistory(Token token, int studentID) {
		Student student = new Student();
		if(authenticate.TokenAuth(token)){
			//validate if a student is making this call and ensure they only query for their own history
			// a student should not request anyone else's history 
			if(token.type == RoleType.STUDENT){
				if(token.id != studentID)
					return _empty;
			}
			return student.queryStudentRegistrationHistory(studentID);
		}
		//authentication failed or not a student
		return _empty;
	}

	@Override
	public List<ArrayList<String>> queryAdminPersonalData(Token token) {
		Administrator admin = new Administrator();
		if(authenticate.tokenAuth(token) && token.type != RoleType.STUDENT)
		{
			return admin.queryAdminPersonalData(token);
		}
		return _empty;
	}

	@Override
	public List<ArrayList<String>> queryInstructor(Token token, int instructorID) {
		Administrator admin = new Administrator();
		if(authenticate.tokenAuth(token) && token.type != RoleType.STUDENT)
		{
			return admin.queryInstructor(instructorID);
		}
		return _empty;
	}

	@Override
	public boolean studentAddClass(Token token, int courseID, String grading, String courseTerm) {
		Student student = new Student();
		if(authenticate.TokenAuth(token)){
			return student.studentAddClass(courseID, grading, courseTerm);
		}
		//authentication failed
		return false;
	}

	@Override
	public boolean studentDropClass(Token token, int courseID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean studentEditClass(Token token, int courseID, String grading, String courseTerm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adminAddClass(Token token, int courseID, String courseName, int courseCredits, String instructor,
			String firstDay, String lastDay, String classBeginTime, String classEndTime, String weekDays,
			String location, String type, String prerequisite, String description, String department) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adminDeleteClass(Token token, int courseID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adminEditClass(Token token, int courseID, String courseName, int courseCredits, String instructor,
			String firstDay, String lastDay, String classBeginTime, String classEndTime, String weekDays,
			String location, String type, String prerequisite, String description, String department) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adminAddStudentToClass(Token token, int studentID, int courseID, String grading, String courseTerm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adminEditStudentRegisteredClass(Token token, int studentID, int courseID, String grading,
			String courseTerm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adminDropStudentRegisteredClass(Token token, int studentID, int courseID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
