import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Search { 
  /* this will be removed just used for testing my code */  
    public static void main (String args[]) throws ClassNotFoundException, SQLException {
        System.out.println("running");
        Search something = new Search();
        something.searchClasses(0, "", "", "", "CS", "", "");
    }
    
    
    public List<ArrayList<String>> searchClasses (int courseID, String courseName, String location, String term, String department, String classType, String instructor) throws ClassNotFoundException, SQLException{

        List<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
        
        ArrayList<String> types = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        
        String sqlCmd = "SELECT * FROM COURSE WHERE ";
        
        if (courseID > 0){
            String strCourseID = Integer.toString(courseID);
            types.add("ID");
            values.add( strCourseID );
        }
        if (courseName != null && courseName != ""){
            types.add("NAME");
            values.add("'" + courseName + "'");
        }
        if (location != null && location != ""){
            types.add("LOCATION");
            values.add("'" + location + "'");
        }
        if (term != null && term != ""){
            types.add("TERM");
            values.add("'" + term + "'");
        }
        if (department != null && department !=""){
            types.add("DEPARTMENT");
            values.add("'" + department + "'");
        }
        if (classType != null && classType !=""){
            types.add("TYPE");
            values.add("'" + classType + "'");
        }
  //      if (instructor != null && instructor !=""){
   //        types.add("INSTRUCTOR");
   //         values.add("'" + instructor + "'");
     //   }
            
        StringBuilder sb = new StringBuilder();
        sb.append(sqlCmd);
        
        for (int i = 0; i < types.size(); i++){
            if (i > 0)
                sb.append(" AND ");
            sb.append(types.get(i));
            sb.append(" = ");
            sb.append(values.get(i)); 
        }
        
        sqlCmd = sb.toString();
        System.out.println(sqlCmd);
    /* this will be removed too just used for updating and testing code */   
        Connection c = null;
        Statement stmt = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:SCRSDataBase.db");
        System.out.println("Opened database successfully");
        stmt = c.createStatement();
        
      //  stmt.executeUpdate("INSERT INTO COURSE (ID,NAME,LOCATION,TERM,DEPARTMENT,TYPE,CREDITS,CAPACITY,FIRSTDAY,LASTDAY,CLASSBEGINTIME,CLASSENDTIME,ROUTINES,DESCRIPTION) VALUES ( 600, 'test2','East Bank KHKH3-3012','term2','CS','Seminar', 3, 10, 10/10/2016, 11/11/2016,'11:22','11:23','Tu Fri', 'do stuff3'); " );
        
        DBCoordinator db = new DBCoordinator ();
        List<ArrayList<Object>> res = new ArrayList<ArrayList<Object>>();
        res = db.queryData(sqlCmd);
        System.out.println(res);
        
        ArrayList<String> temp = new ArrayList<String>();
        for(int j = 0;j < res.size();j++){
            System.out.println(res.get(j));
            
            
            for(int i=0;i < res.get(j).size(); i++){
                System.out.println(res.get(j).get(i));
                List<String> strings = new ArrayList<String>();
               if (res.get(j).get(i) == null){
                   temp.add("");
               }
               else
               temp.add( res.get(j).get(i).toString());
            
                
        }
            resultList.add(temp);
            System.out.println(resultList);
        }
        return resultList;
        

    }
    
}
  
