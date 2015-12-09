import java.sql.SQLException;
import java.text.ParseException;

import edu.umn.csci5801.*;

public class SimpleTestDriver {
	/* this will be removed it is just used for testing my code */

	public static void main(String args[]) throws ParseException,SQLException, ClassNotFoundException {

	System.out.println("running");

	Student something = new Student();
	
	Administrator administrator = new Administrator();
	
	ShibbolethAuth auth = new ShibbolethAuth();
	ShibbolethAuth.Token token = auth.tokenGenerator("Abc1005", "5000");

//	System.out.println(something.queryRegistrationHistory(0));
	
//	System.out.println(something.queryStudentPersonalData(5));
	
	//System.out.println(something.studentAddClass(0, 2, "S/N","Fall2015"));
	
	//System.out.println(something.studentEditClass(0, 0, "AUD", "Fall2015"));
	
//	System.out.println(something.studentDropClass(0,2));
	
 //   System.out.println(administrator.adminEditClass(token, 2, "KurtClass", 3, "", "","", "", "" ,"", "", "","", "do stuff", ""));	
	
//	System.out.println(administrator.adminEditStudentRegisteredClass(token, 2, 2, "A-F", "Fall2015"));
	
	//	System.out.println(administrator.adminAddStudentToClass(token, 0, 2, "AUD", "Fall2015"));
		
	//	System.out.println(administrator.adminDropStudentRegisteredClass( token, 0, 2));
		//just need to change courseid to do more adds change 2nd value 
		
//	    System.out.println(administrator.adminAddClass( token, 34, "compStuff", 4, 25, "Fall2015", 11, "1441947600000",
	//			 "1452492000000", "10:01 am", "11:02 am", "Tu, Th", "East Campus", "Lecture",
	//				"algorith", "none", "CS"));
		
	//	System.out.println(administrator.adminDeleteClass(token, 34));
		
		System.out.println(administrator.queryAdminPersonalData(token));
	}
}