import static org.junit.Assert.*;

import java.util.*;

import edu.umn.csci5801.*;
import edu.umn.csci5801.ShibbolethAuth.Token;

import org.junit.Before;
import org.junit.Test;

public class SCRSTest {

	SCRS scrs;
	ShibbolethAuth shibboleth;

	@Before
	public void setUp() throws Exception {
		scrs = new SCRSImpl();
		shibboleth=new ShibbolethAuth();
	}

	@Test
	public void testQueryClass() {
		//Token studentToken = shibboleth.tokenGenerator("Abc1000","0");
		
		List<ArrayList<String>> resultExpected= new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> resultActual= new ArrayList<ArrayList<String>>();
		
		ArrayList<String> aClass = new ArrayList<String>();
		aClass.add("1");
		aClass.add("Advanced Algorithm");
		aClass.add("2");
		
		resultExpected.add(aClass);
		//NULL: Instructor name
		System.out.println("1-ok");
		resultActual=scrs.queryClass(1, "Advanced Algorithm ","East Campus ","Fall2014 ","CS ","Lecture ","NULL"); 
		
		if(resultActual.isEmpty())
		{
			fail("Empty List");
		}
		else if (resultExpected.containsAll(resultActual) && resultActual.containsAll(resultExpected))
		{
			System.out.println("testQueryClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}

	}

	@Test
	public void testQueryStudentPersonalData() throws Exception{
		Token studentToken = shibboleth.tokenGenerator("Abc1000","0");
		
		List<ArrayList<String>> resultExpected= new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> resultActual= new ArrayList<ArrayList<String>>();
		//age is calculated by the queryPersonalData
		ArrayList<String> aClass = new ArrayList<String>();
		aClass.add("Jack ");
		aClass.add("Stark ");
		aClass.add("0 ");
		aClass.add("25 ");
		aClass.add("Female ");
		aClass.add("NULL ");
		aClass.add("N/A ");
		aClass.add("5 ");
		
		resultExpected.add(aClass);
		System.out.println("2-ok");
		
		resultActual=scrs.queryStudentPersonalData(studentToken, 0); 
		
		if(resultActual.isEmpty())
		{
			fail("Empty List");
		}
		else if (resultExpected.containsAll(resultActual) && resultActual.containsAll(resultExpected))
		{
			System.out.println("testQueryStudentPersonalData passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		

		//fail("Not yet implemented");
	}

	@Test
	public void testQueryStudentRegistrationHistory()throws Exception {
		Token studentToken = shibboleth.tokenGenerator("Abc1003","3000");
		
		List<ArrayList<String>> resultExpected= new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> resultActual= new ArrayList<ArrayList<String>>();
		
		ArrayList<String> aClass = new ArrayList<String>();
		aClass.add("0");
		aClass.add("Computer Graphics ");
		aClass.add("Fall2014 ");
		//NULL:class status, whether finished or dropped or...
		aClass.add("NULL ");
		aClass.add("32");
		
		resultExpected.add(aClass);
		System.out.println("3-ok");
		
		resultActual=scrs.queryStudentRegistrationHistory(studentToken, 3);

		if(resultActual.isEmpty())
		{
			fail("Empty List");
		}
		else if (resultExpected.containsAll(resultActual) && resultActual.containsAll(resultExpected))
		{
			System.out.println("testQueryStudentRegisterationHistory passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testQueryAdminPersonalData() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1006","6000");
		
		List<ArrayList<String>> resultExpected= new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> resultActual= new ArrayList<ArrayList<String>>();
		
		ArrayList<String> aClass = new ArrayList<String>();
		aClass.add("0 ");
		aClass.add("Daniel ");
		aClass.add("Jack ");
		aClass.add("CS ");
		
		resultExpected.add(aClass);
		System.out.println("4-ok");
		
		resultActual=scrs.queryAdminPersonalData(adminToken);

		if(resultActual.isEmpty())
		{
			fail("Empty List");
		}
		else if (resultExpected.containsAll(resultActual) && resultActual.containsAll(resultExpected))
		{
			System.out.println("testQueryAdminPersonalData passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		
		//fail("Not yet implemented");
	}

	@Test
	public void testQueryInstructor() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1006","6000");
		
		List<ArrayList<String>> resultExpected= new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> resultActual= new ArrayList<ArrayList<String>>();
		
		ArrayList<String> aClass = new ArrayList<String>();
		aClass.add("KFC ");
		aClass.add("Jack ");
		//age is calculated by the queryPersonalData
		aClass.add("25 ");
		aClass.add("Male ");
		aClass.add("Professor");
		aClass.add("85932 ");
		aClass.add("CS");
		
		resultExpected.add(aClass);
		System.out.println("5-ok");
		
		resultActual=scrs.queryStudentRegistrationHistory(adminToken, 2);
		
		if(resultActual.isEmpty())
		{
			fail("Empty List");
		}
		else if (resultExpected.containsAll(resultActual) && resultActual.containsAll(resultExpected))
		{
			System.out.println("testQueryInstructor passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		//fail("Not yet implemented");
	}

	//==========student interface=====================
	@Test
	public void testStudentAddClass() throws Exception{
		Token studentToken = shibboleth.tokenGenerator("Abc1003","3000");
		
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("6-ok");
		
		resultActual=scrs.studentAddClass(studentToken, 0, "S/N", "Fall2014");
		
		if (resultExpected && resultActual)
		{
			System.out.println("testStudentAddClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testStudentDropClass() throws Exception{
		Token studentToken = shibboleth.tokenGenerator("Abc1003","3000");
		
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("7-ok");
		
		resultActual=scrs.studentDropClass(studentToken, 0);
		
		if (resultExpected && resultActual)
		{
			System.out.println("testStudentDropClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		
		//fail("Not yet implemented");
	}

	@Test
	public void testStudentEditClass() throws Exception{
		Token studentToken = shibboleth.tokenGenerator("Abc1004","4000");
		
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("8-ok");
		
		resultActual=scrs.studentEditClass(studentToken, 1, "A-F", "Fall2014");
		
		if (resultExpected && resultActual)
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		
		//fail("Not yet implemented");
	}

	
	
	//===========Admin interface
	@Test
	public void testAdminAddClass() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1005","5000");
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("9-ok");
		//NULL:don't have instructor name
		//firstday and lastday is calculated by the queryPersonalData
		resultActual=scrs.adminAddClass(adminToken, 2, "Parallel Computing ", 3, 30, "Fall2015", 0 ,"9/2/2015 ","12/21/2015 ",
				"09:00 Am ","11:00 Am ","Tu, Fri ","East Campus ","Lecture ","NULL ","Good Good Good Class ","CS");

		if (resultExpected && resultActual)
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		//fail("Not yet implemented");
	}

	
	@Test
	public void testAdminDeleteClass() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1005","5000");
		boolean resultExpected= false;
		boolean resultActual;
		System.out.println("10-ok");
		
		resultActual=scrs.adminDeleteClass(adminToken, 0);

		if (!(resultExpected && resultActual))
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
	}

	@Test
	public void testAdminEditClass() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1005","5000");
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("11-ok");
		
		//Since modify, i am giving info randomly.
		resultActual=scrs.adminEditClass(adminToken, 1, "Advanced Algorithm ", 5, 2, "9/1/2014 ","12/12/2014 ",
				"8:45 AM ", "9:30 AM ","Tu, Fri ", "East Bank ", "Lecture ", "Algorithm and Data Structure ",
				"Good Good Good Class ", "CS "); 

		if (resultExpected && resultActual)
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testAdminAddStudentToClass() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1005","5000");
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("12-ok");
		
		//Since modify, i am giving info randomly.
		resultActual=scrs.adminAddStudentToClass(adminToken, 1, 2, "A-F", "Fall2015"); 
		
		if ((resultExpected && resultActual))
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
	}

	@Test
	public void testAdminEditStudentRegisteredClass() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1005","5000");
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("13-ok");
		
		//Since modify, i am giving info randomly.
		resultActual=scrs.adminEditStudentRegisteredClass(adminToken, 1, 2, "S/N", "Fall2016"); 
		
		if (resultExpected && resultActual)
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		
		
		//fail("Not yet implemented");
	}

	@Test
	public void testAdminDropStudentRegisteredClass() throws Exception{
		Token adminToken = shibboleth.tokenGenerator("Abc1005","5000");
		boolean resultExpected= true;
		boolean resultActual;
		System.out.println("14-ok");
		
		//Since modify, i am giving info randomly.
		resultActual=scrs.adminDropStudentRegisteredClass(adminToken, 1, 1); 
	
		if (resultExpected && resultActual)
		{
			System.out.println("testStudentEditClass passed! Nice!");
		}
		else
		{
			fail("Actual result is not the expected result!!!");
		}
		
		
		//fail("Not yet implemented");
	}

}
