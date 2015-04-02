/*
 * Created on 2005-10-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.common.dao;

/**
 * @author Allenhuang
 *
 * created on 2005-10-31
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

public class DbConnectionManager {
   
    /**
     * 数据库连接池
     * @see http://jakarta.apache.org/commons/dbcp/index.html
     */
    private static PoolingDriver driver = null;
   
  
    /**
     * 设置一个数据库连接池
     *
     * @param name 连接池的名称
     * @param url 数据源
     * @throws SQLException
     */
    private static void setUpDriverPool(String name, String url) throws SQLException{
        if ((driver == null) || driver.getPoolNames().length < 2) {
            try {
                /**
                 * 首先创建一个对象池来保存数据库连接
                 *
                 * 使用 commons.pool 的 GenericObjectPool对象
                 */
                GenericObjectPool connectionPool = new GenericObjectPool();
//                connectionPool.setMinIdle(0);
                connectionPool.setMaxWait(5000);
                connectionPool.setMaxIdle(20);
                connectionPool.setMaxActive(50);
               
                /**
                 * 创建一个 DriverManagerConnectionFactory对象
                 * 连接池将用它来获取一个连接
                 */
                ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, null);
               
                /**
                 * 创建一个PoolableConnectionFactory 对象。
                 */
                PoolableConnectionFactory poolableConnectionFactory =
                    new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);
               
                /**
                 * 注册PoolingDriver。
                 */
                Class.forName("org.apache.commons.dbcp.PoolingDriver");
               
                driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
               
                driver.registerPool(name,  connectionPool);
               
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
   
    public static void main(String[] args){
       try {
           Connection conn=DbConnectionManager.getConnection();
           			System.out.println(conn.getAutoCommit());
		} catch (SQLException e) {
			// log error here
			e.printStackTrace();
		}
    }
    
    /**
     * 关闭所有数据库连接池
     *
     */
    public static void shutDownDriver() {
       
        try {         
            PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            for (int i = 0; i < 3; i++) {
                driver.closePool(poolName);
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    static String poolName = "easyshop";
    static boolean loaded;
    public static final String JSQLCONNET_DRIVER="com.jnetdirect.jsql.JSQLDriver";
    public static final String JSQLCONNET_URL="jdbc:JSQLConnect://127.0.0.1/database=bom/user=allen/password=allen";
    
    public static final String MSSQL_DRIVER="com.microsoft.jdbc.sqlserver.SQLServerDriver";
    public static final String MSSQL_URL="jdbc:microsoft:sqlserver://localhost;DatabaseName=bom;SelectMethod=Cursor;user=sa;password=allenhyt";
    
 //   public static final String JTDS_DRIVER="net.sourceforge.jtds.jdbc.Driver";
//    public static final String JTDS_URL="jdbc:jtds:sqlserver://192.168.1.6:1433/fbom;user=sa;password=jarsodb";
//  public static final String JTDS_URL="jdbc:jtds:sqlserver://192.168.1.6:1433/fbom;user=sa;password=jarsodb0418";
//  public static final String JTDS_URL="jdbc:jtds:sqlserver://192.168.1.16:1433/shopping;user=sa;password=huangyingtian0623";
//    public static final String JTDS_URL="jdbc:jtds:sqlserver://localhost:1433/hbom;user=sa;password=allenhyt";
    
    public static final String mysql_driver ="com.mysql.jdbc.Driver";
    public static final String mysql_url = "jdbc:mysql://localhost:3306/ec1?user=root&password=123a123@";
    
  private static String server;
  
  	public static void setServer(String dbURL){
  		server=dbURL;
  	}
    private static void load(){
        String driver=null;
        String url=null;
        try {
        if (server!=null){
        	url=server;
        }else
            url = mysql_url;
        
       	driver = mysql_driver;
        Class.forName(driver);
        setUpDriverPool(poolName, url); 
        loaded=true;
    }
    catch (ClassNotFoundException cnfe) {
        throw new RuntimeException("无法装入数据库引擎，没有发现驱动程序: "+driver) ;
    }
    catch (SQLException sqle) {
        throw new RuntimeException("无法打开数据库连接:"+url);
    }
    }
    public static Connection getDirectConn(){
        String driver=null;
        String url=null;
        try {
        driver = MSSQL_DRIVER;
        url = MSSQL_URL;
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }
    catch (ClassNotFoundException cnfe) {
        throw new RuntimeException("无法装入数据库引擎: "+driver) ;
    }
    catch (SQLException sqle) {
        throw new RuntimeException("无法打开数据库连接:"+url);
    }    	
    }
    /**
     * 取得一个数据库连接对象。
     *
     * 因为可能使用两个不同的数据库，
     * 所以依据report的值来确定使用那个数据库。
     *
     * @param report
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
       
            //装载jdbc驱动
        if (!loaded)
            load();

            try {
                con = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + poolName);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return con;
    }
   
    /**
     * 执行清理过程
     *
     * <li>关闭数据库连接</li>
     * <li>关闭语句对象</li>
     * <li>关闭结果集</li>
     *
     * @param con
     * @param s
     * @param rs
     */
    public static void closeAll(Connection con, Statement s, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
           
            if (s != null) {
                s.close();
                s = null;
            }
           
            if (con != null) {
                con.close();
                con = null;
            }
        }
        catch (SQLException sqle) {
            //nothing to do, forget it;
        }
    }
   
}
