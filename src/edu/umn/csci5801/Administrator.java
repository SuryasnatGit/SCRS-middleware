package edu.umn.csci5801;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	

	public List<ArrayList<String>> queryAdminPersonalData(ShibbolethAuth.Token token) throws ClassNotFoundException, SQLException{
		List<ArrayList<String>> resultList;
		
		int instructorId = token.id;
		if (instructorId == -1){
		String	sqlCmdAll = "Select * from instructor;";
		
		resultList = DBProcessor.runQueryWithAge(sqlCmdAll);
		}
		else {
			String sqlCmdOne = "Select * from instructor Where id = "+ instructorId +";";
			resultList = DBProcessor.runQueryWithAge(sqlCmdOne);
		}
		return resultList;
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
	 * @param prequisite 
	 * @return Return true if the operation is successfully, false otherwise
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	boolean adminAddClass(ShibbolethAuth.Token token, int courseID, String courseName, int courseCredits, int courseCapacity, String term, int instructorID, String firstDay,
			String lastDay, String classBeginTime, String classEndTime, String weekDays, String location, String type,
			String prerequisite, String description, String department)  throws ClassNotFoundException, SQLException, ParseException{
		boolean isValid = true;
		ArrayList<String>coursePropertyValue = new ArrayList<String>();
		ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
		
		String sqlCmdEdit = " insert into course ";
		
        if(courseID != -1) {
				coursePropertyValue.add(Integer.toString(courseID));
				coursePropertyType.add(Constants.PrimitiveDataType.INT);
				sqlCmdEdit = sqlCmdEdit.concat("(id, ");
        }else 
        	isValid = false;
    		
    		if (courseName != null && courseName != "") {
				coursePropertyValue.add(courseName);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("name, ");
    		}else
    			isValid = false;
    		
    		if (courseCredits > 0) {
				coursePropertyValue.add(Integer.toString(courseCredits));
				coursePropertyType.add(Constants.PrimitiveDataType.INT);
				sqlCmdEdit =sqlCmdEdit.concat("credits, ");
    		}else
    			isValid = false;
    		
    		if (courseCapacity > 0) {
				coursePropertyValue.add(Integer.toString(courseCapacity));
				coursePropertyType.add(Constants.PrimitiveDataType.INT);
				sqlCmdEdit =sqlCmdEdit.concat("capacity, ");
    		}else
    			isValid = false;
    		
    		if (term != null && term != "") {
				coursePropertyValue.add(term);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("term, ");
    		}else
    			isValid = false;
    		
    		if (firstDay != null && firstDay != "") {
    			coursePropertyValue.add(firstDay);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("firstDay, ");
    		}else
    			isValid = false;
    		
    		if (lastDay != null && lastDay != "") {
    			coursePropertyValue.add(lastDay);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("lastDay, ");
    		} else
    			isValid = false;
       		if (classBeginTime != null && classBeginTime != "") {
    			coursePropertyValue.add(classBeginTime);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("classBeginTime,");
    		} else
    			isValid = false;
       		
       		if (classEndTime != null && classEndTime != "") {
    			coursePropertyValue.add(classEndTime);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("classEndTime, ");
    		} else
    			isValid = false;
       		
       		if (weekDays != null && weekDays != "") {
    			coursePropertyValue.add(weekDays);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("routines, ");
    		} else
    			isValid=false;
       		
    		if (location != null && location != "") {
    			coursePropertyValue.add(location);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("location,");
    		} else
    			isValid = false;
       		
       		if (type != null && type != "") {
    			coursePropertyValue.add(type);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("type, ");
    		} else
    			isValid = false;
       		   		
       		if (description != null && description != "") {
    			coursePropertyValue.add(description);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat(" description, ");
    		} else
    			isValid = false;
       		
       		if (department != null && department != "") {
    			coursePropertyValue.add(department);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("department, ");
    		} else
    			isValid = false;
       		
       			if (prerequisite == null)
       				prerequisite = "";
        		coursePropertyValue.add(prerequisite);
        		coursePropertyType.add(Constants.PrimitiveDataType.STRING);
        		sqlCmdEdit =sqlCmdEdit.concat("prerequisite) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        		
     
        if(isValid){
        	DBCoordinator db = new DBCoordinator();
			db.insertData(sqlCmdEdit, coursePropertyValue, coursePropertyType);
			coursePropertyValue.clear();
			coursePropertyType.clear();
			String sqlCmdInsert = "insert into instructorandcourse (courseID, instructorID) Values (?,?);";
	
			coursePropertyValue.add(Integer.toString(courseID));
			coursePropertyType.add(Constants.PrimitiveDataType.INT);
			sqlCmdEdit = sqlCmdEdit.concat("courseid,");
			if (instructorID > -1) {
				coursePropertyValue.add(Integer.toString(instructorID));
				coursePropertyType.add(Constants.PrimitiveDataType.INT);
				sqlCmdEdit =sqlCmdEdit.concat("instructorId,");
		    		}else
		    			isValid = false;
			if(isValid){
				db.insertData(sqlCmdInsert, coursePropertyValue, coursePropertyType);	
			}
        }
		return isValid;
	}

	/**
	 * This method allows the admin to delete a class from the database if and only if there is not one register it.
	 * @param token
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	boolean adminDeleteClass(ShibbolethAuth.Token token, int courseID) throws ClassNotFoundException, SQLException, ParseException{
		boolean isValid = true;
		String sqlNumRegistered = "select count (studentid)from studentandcourse " +
				"where studentandcourse.courseid ="  +courseID + "";
		int numRegForClass = DBProcessor.getIntegerFromQuery(sqlNumRegistered);
		
		
		if (numRegForClass == 0){
			ArrayList<String>coursePropertyValue = new ArrayList<String>();
			ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
			String sqlCmd = "delete from course Where Id= (?);";
			String sqlDelInstCour = "delete from instructorandcourse where courseId = (?);";
			coursePropertyValue.add(Integer.toString(courseID));
			coursePropertyType.add(Constants.PrimitiveDataType.INT);
			DBCoordinator db = new DBCoordinator();
			db.deleteData(sqlCmd, coursePropertyValue, coursePropertyType);	
			db.deleteData(sqlDelInstCour, coursePropertyValue, coursePropertyType);
			
		} else
		{
			isValid = false;
		}
		return isValid;
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
	public boolean adminEditClass(ShibbolethAuth.Token token, int courseID, String courseName, int courseCredits, String instructor, String firstDay,
		String lastDay, String classBeginTime, String classEndTime, String weekDays, String location, String type,
		String prerequisite, String description, String department)throws SQLException,ClassNotFoundException,ParseException{
		
		boolean isValid = true;
		ArrayList<String>coursePropertyValue = new ArrayList<String>();
		ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
		
		String sqlNumRegistered = "select count (studentid)from studentandcourse " +
				"where studentandcourse.courseid ="  +courseID + "";
		int numRegForClass = DBProcessor.getIntegerFromQuery(sqlNumRegistered);
		
		String sqlCmdEdit = "update course set ";
		
        if(numRegForClass==0 && courseID != -1) {
				coursePropertyValue.add(Integer.toString(courseID));
				coursePropertyType.add(Constants.PrimitiveDataType.INT);
				sqlCmdEdit = sqlCmdEdit.concat("id = (?) and ");
    		
    		if (courseName != null && courseName != "") {
				coursePropertyValue.add(courseName);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("name = (?) and ");
    		}
    		
    		if (courseCredits > 0) {
				coursePropertyValue.add(Integer.toString(courseCredits));
				coursePropertyType.add(Constants.PrimitiveDataType.INT);
				sqlCmdEdit =sqlCmdEdit.concat("credits = (?) and ");
    		}
    		if (instructor != null && instructor != "") {
    			
    			String nameString[] = instructor.split("\\s+");
				coursePropertyValue.add(nameString[0]);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("firstname = (?) and ");
				coursePropertyValue.add(nameString[1]);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("lastname = (?) and ");
    		}
    		if (firstDay != null && firstDay != "") {
    			coursePropertyValue.add(firstDay);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("firstDay = (?) and ");
    		}
    		if (lastDay != null && lastDay != "") {
    			coursePropertyValue.add(lastDay);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("lastDay = (?) and ");
    		}
       		if (classBeginTime != null && classBeginTime != "") {
    			coursePropertyValue.add(classBeginTime);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("classBeginTime = (?) and ");
    		}
       		if (classEndTime != null && classEndTime != "") {
    			coursePropertyValue.add(classEndTime);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("classEndTime = (?) and ");
    		}
       		if (weekDays != null && weekDays != "") {
    			coursePropertyValue.add(weekDays);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("routines = (?) and ");
    		}
    		if (location != null && location != "") {
    			coursePropertyValue.add(location);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("location = (?) and ");
    		}
       		if (classBeginTime != null && classBeginTime != "") {
    			coursePropertyValue.add(classBeginTime);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("classBeginTime = (?) and ");
    		}
       		if (type != null && type != "") {
    			coursePropertyValue.add(type);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("type = (?) and ");
    		}
       		if (prerequisite != null && prerequisite != "") {
    			coursePropertyValue.add(prerequisite);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("preRequisite = (?) and ");
    		}
       		if (description != null && description != "") {
    			coursePropertyValue.add(description);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("description = (?) and ");
    		}
       		if (department != null && department != "") {
    			coursePropertyValue.add(classBeginTime);
				coursePropertyType.add(Constants.PrimitiveDataType.STRING);
				sqlCmdEdit =sqlCmdEdit.concat("department = (?) and ");
    		}
			coursePropertyValue.add(Integer.toString(courseID));
			coursePropertyType.add(Constants.PrimitiveDataType.INT);
			sqlCmdEdit =sqlCmdEdit.substring(0,(sqlCmdEdit.length()-4));
			sqlCmdEdit =sqlCmdEdit.concat("where id = (?)");
       		
        } else if((numRegForClass >= 1) && (description != null || description != "" )){
        	if (courseID != -1){
        		coursePropertyValue.add(description);
        		coursePropertyType.add(Constants.PrimitiveDataType.STRING);
        		coursePropertyValue.add(Integer.toString(courseID));
        		coursePropertyType.add(Constants.PrimitiveDataType.INT);
        		sqlCmdEdit =sqlCmdEdit.concat("description = (?) where id = (?)");
        		}
        	} else 
        		isValid = false;
        	
		DBCoordinator db = new DBCoordinator();
		db.updateData(sqlCmdEdit, coursePropertyValue, coursePropertyType);	
		return isValid;
	}
		
		
		

	/**
	 * This method will allow the admin to add one student to one specific class if exist
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	boolean adminAddStudentToClass(ShibbolethAuth.Token token, int studentID, int courseID, String grading, String courseTerm) throws ClassNotFoundException, SQLException, ParseException{

		boolean isValid = false;
		String sqlCmd;

		
		String sqlStudCredits = "select count (course.credits)from studentandcourse join course " +
				"where studentandcourse.courseid = course.id and studentandcourse.studentid = " + studentID;
		int studCredits = DBProcessor.getIntegerFromQuery(sqlStudCredits);
		
		String sqlCourseCredit = "select course.credits from course where course.Id = " + courseID;
		int courseCredit = DBProcessor.getIntegerFromQuery(sqlCourseCredit);
		
		String sqlCapacity = "select course.capacity from course where course.Id = " + courseID;
		int capacity = DBProcessor.getIntegerFromQuery(sqlCapacity);
		
		String sqlTotalCredits = "select student.credits from student where student.Id = " + studentID;
		int totalCredit = DBProcessor.getIntegerFromQuery(sqlTotalCredits);
		int incrementCredits = totalCredit + courseCredit;

				if ((courseCredit + studCredits)<=30)
					if (capacity != 30){
						ArrayList<String>coursePropertyValue = new ArrayList<String>();
						ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
						coursePropertyValue.add(Integer.toString(studentID));
						coursePropertyType.add(Constants.PrimitiveDataType.INT);
						coursePropertyValue.add(Integer.toString(courseID));
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
						coursePropertyValue.add(Integer.toString(studentID));
						coursePropertyType.add(Constants.PrimitiveDataType.INT);
					    sqlCmd = "update student set credits = (?) Where id = (?);";				
						db.updateData(sqlCmd, coursePropertyValue, coursePropertyType);
					    sqlTotalCredits = "select student.credits from student where student.Id = " + studentID;
						totalCredit = DBProcessor.getIntegerFromQuery(sqlTotalCredits);
						isValid = true;
				}
		
		return isValid;
	}

	
	/**
	 * This method will allow the admin to edit one student to one specific class if exist
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @param grading
	 * @param courseTerm
	 * @return Return true if the operation is successfully, false otherwise
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public boolean adminEditStudentRegisteredClass(ShibbolethAuth.Token token, int studentID, int courseID, String grading, String courseTerm) throws ClassNotFoundException, SQLException, ParseException{

				boolean isValid = true;
				ArrayList<String>coursePropertyValue = new ArrayList<String>();
				ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
				
				if (courseID != -1){
					String sqlGetId = "select studentandcourse.id from studentAndCourse " +
							"where courseid = "  +courseID + " and studentid = " + studentID + "";
					if (courseTerm != null && courseTerm != "")
						sqlGetId = sqlGetId.concat(" and courseterm = " +"'"+ courseTerm +"'" + ";");
					else 
						sqlGetId = sqlGetId.concat(";");
					int id = DBProcessor.getIntegerFromQuery(sqlGetId);
					
					if ( id<0 )
						isValid = false;
					else {
						String sqlUpdate = "update studentandcourse set grading = (?) where id = (?);";
		        		coursePropertyValue.add(grading);
		        		coursePropertyType.add(Constants.PrimitiveDataType.STRING);
		        		coursePropertyValue.add(Integer.toString(id));
		        		coursePropertyType.add(Constants.PrimitiveDataType.INT);
		        		DBCoordinator db = new DBCoordinator();
		        		db.updateData(sqlUpdate, coursePropertyValue, coursePropertyType);	
					}	
				}		
				return isValid;
	}
	
	/**
	 * This method should allow the admin to remove one registered class from a student's registered class's list.
	 * @param token
	 * @param studentID
	 * @param courseID
	 * @return Return true if the operation is successfully, false otherwise
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	boolean adminDropStudentRegisteredClass(ShibbolethAuth.Token token, int studentID, int courseID) throws ClassNotFoundException, SQLException, ParseException{
		boolean isValid = false;
		
		String sqlCmd;
		
		String sqlCourseCredit = "select course.credits from course where course.Id = " + courseID;
		int courseCredit = DBProcessor.getIntegerFromQuery(sqlCourseCredit);
		
		String sqlTotalCredits = "select student.credits from student where student.Id = " + studentID;
		int totalCredit = DBProcessor.getIntegerFromQuery(sqlTotalCredits);
		int decrementCredits = totalCredit - courseCredit;
				if(courseID>=0){
					ArrayList<String>coursePropertyValue = new ArrayList<String>();
					ArrayList<Constants.PrimitiveDataType>coursePropertyType = new ArrayList<Constants.PrimitiveDataType>();
					sqlCmd = "delete from StudentAndCourse Where courseId= (?) and studentId = (?);";
					coursePropertyValue.add(Integer.toString(courseID));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					coursePropertyValue.add(Integer.toString(studentID));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					DBCoordinator db = new DBCoordinator();
					db.deleteData(sqlCmd, coursePropertyValue, coursePropertyType);
					coursePropertyValue.clear();
					coursePropertyType.clear();
					coursePropertyType.add(Constants.PrimitiveDataType.STRING);
					coursePropertyValue.add(Integer.toString(decrementCredits));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
					coursePropertyValue.add(Integer.toString(studentID));
					coursePropertyType.add(Constants.PrimitiveDataType.INT);
				    sqlCmd = "update student set credits = (?) Where Id = (?);";				
					db.updateData(sqlCmd, coursePropertyValue, coursePropertyType);
				}
				else 
					isValid = false;
		
		return isValid;
	}
		
		

}
