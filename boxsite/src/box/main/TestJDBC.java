package box.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;  

public class TestJDBC {  
    String dbUrl = "jdbc:oracle:thin:@localhost:1521/orcl";  
    String theUser = "c##tas";  
    String thePw = "muchinfo";  
    Connection c = null;  
    Statement conn;  
    ResultSet rs = null;  
   
    public TestJDBC() {  
       try {  
           Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();  
           c = DriverManager.getConnection(dbUrl, theUser, thePw);  
           conn = c.createStatement();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
    }  
   
    public boolean executeUpdate(String sql) {  
       try {  
    	   c.setAutoCommit(false);
    	   long ss = System.currentTimeMillis();
		   String sql2 = "insert into HOLDERDETAIL (id) values(?)";
		  PreparedStatement st = c.prepareStatement(sql2);
  	   
    	   for (int i=0;i<5000;i++)
    	   {
    		   int k = 50000+i;
    		   st.setInt(1, k);
    		  st.execute();
              // conn.executeUpdate("insert into HOLDERDETAIL (id) values("+k+")");     		   
    	   }
    	   c.commit();
    	   System.out.println("cost time :"+(System.currentTimeMillis()-ss));
           return true;  
       } catch (SQLException e) {  
           e.printStackTrace();  
           return false;  
       }  
    }  
   
    public ResultSet executeQuery(String sql) {  
       rs = null;  
       try {  
           rs = conn.executeQuery(sql);  
       } catch (SQLException e) {  
           e.printStackTrace();  
       }  
       return rs;  
    }  
   
    public void close() {  
       try {  
           conn.close();  
           c.close();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
    }  
   
    public static void main(String[] args) {  
       ResultSet rs;  
       TestJDBC conn = new TestJDBC(); 
       conn.executeUpdate("dd");
//       rs = conn.executeQuery("select * from holderdetail");  
//       try {  
//           while (rs.next()) {  
//              System.out.println(rs.getString("username")+"--"+rs.getString("user_id"));  
//           }  
//       } catch (Exception e) {  
//           e.printStackTrace();  
//       }  
    }  
}  

