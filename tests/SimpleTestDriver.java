import java.sql.SQLException;
import java.text.ParseException;

import edu.umn.csci5801.*;

public class SimpleTestDriver {
	/* this will be removed it is just used for testing my code */

	public static void main(String args[]) throws ParseException,SQLException, ClassNotFoundException {

	System.out.println("running");

	Student something = new Student();
	
//	System.out.println(something.queryRegistrationHistory(0));
	
//	System.out.println(something.queryStudentPersonalData(5));
	
	System.out.println(something.studentAddClass(0, 2, "S/N","Fall2015"));
	
	//System.out.println(something.studentEditClass(0, 0, "AUD", "Fall2015"));
	
	System.out.println(something.studentDropClass(0,2));
	}

}