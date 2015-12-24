package box.main;

import java.sql.*;  

public class TestMyJDBC {  
    String dbUrl = "jdbc:mysql://localhost:3306/box";  
    String theUser = "root";  
    String thePw = "123a123@";  
    Connection c = null;  
    Statement conn;  
    ResultSet rs = null;  
   
    public TestMyJDBC() {  
       try {  
           Class.forName("com.mysql.jdbc.Driver").newInstance();  
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
    	   for (int i=0;i<3000;i++)
    	   {
    		   int k = 6000+i;
               conn.executeUpdate("insert into HOLDERDETAIL (id) values("+k+")");     		   
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
       TestMyJDBC conn = new TestMyJDBC(); 
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

