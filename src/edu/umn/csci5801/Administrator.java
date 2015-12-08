package edu.umn.csci5801;

public class Administrator extends People{
	private String department;
	private String type;
	
	public String getDepartmentInfo() {
		return department;
	}
	
	public String getTypeInfo() {
		return type;
	}
	
	public void updateDepartment(String newDepartment) {
		department = newDepartment;
	}
	
	public void updateType(String newType) {
		type = newType;
	}
	
	/*
	 * Admin Method Implementations
	 */
	/**
	 * This method allows the admin to add class into the database
	 * and does the actual database calls and work delegated from SCRSImpl.
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
	boolean adminAddClass(ShibbolethAuth.Token token, int courseID, String courseName, int courseCredits, String instructor, String firstDay,
			String lastDay, String classBeginTime, String classEndTime, String weekDays, String location, String type,
			String prerequisite, String description, String department){
		return false;
	}

	/**
	 * This method allows the admin to delete a class from the database if and only if there is not one register it.
	 * @param token
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 */
	boolean adminDeleteClass(ShibbolethAuth.Token token, int courseID){
		return false;
	}

	/**
	 * This method will allow the admin to modify the existed class in the database.
	 * The admin can only edit the class's description if at least one student registers this class
	 * The admin can edit everything of the class if no one registers it.
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
	boolean adminEditClass(ShibbolethAuth.Token token, int courseID, String courseName, int courseCredits, String instructor, String firstDay,
			String lastDay, String classBeginTime, String classEndTime, String weekDays, String location, String type,
			String prerequisite, String description, String department){
		return false;
	}

	/**
	 * This method will allow the admin to add one student to one specific class if exist
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 */
	boolean adminAddStudentToClass(ShibbolethAuth.Token token, int studentID, int courseID, String grading, String courseTerm){
		return false;
	}
	
	/**
	 * This method will allow the admin to edit one student to one specific class if exist
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 */
	boolean adminEditStudentRegisteredClass(ShibbolethAuth.Token token, int studentID, int courseID, String grading, String courseTerm){
		return false;
	}
	
	/**
	 * This method should allow the admin to remove one registered class from a student's registered class's list.
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 */
	boolean adminDropStudentRegisteredClass(ShibbolethAuth.Token token, int studentID, int courseID){
		return false;
	}
}
