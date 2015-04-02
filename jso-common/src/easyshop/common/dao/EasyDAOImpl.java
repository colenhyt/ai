/*
 * Created on 2005-9-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.common.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author Allenhuang
 *
 * created on 2005-9-14
 */
public class EasyDAOImpl {
	static Logger log=Logger.getLogger("EasyDAOImpl");
	protected Set getOriginals(int status) {
        return null;
    }
    
	protected boolean existColumn(String store,String column){
		return exist("select a.status from syscolumns a,sysobjects b where a.id=b.id and b.name='"+store+"' and a.name='"+column+"'");
	}
	public boolean exist(String sql){
	    boolean exist=false;
	    Statement stmt = null;
	    ResultSet rs = null;
	    Connection con = DbConnectionManager.getConnection();
	    try {
	        stmt = con.createStatement();
	        //在这里更新新ref的ref_u_id:
	        rs = stmt.executeQuery(sql);
	        if (rs.next())
	            exist=true;
	    } catch (SQLException sqlex) {
	        log.error(sqlex);
	    } finally {
	        try {
	            stmt.close();
	            stmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }        
	    return exist;
	}
	public boolean existTable(String store){
	    if (store==null){
	        log.error("table '"+store+"' not existed!");
	        return false;
	    }
	    
	    String sql="select * from sysobjects where name='"+store+"'";
	    return exist(sql);
	}
	
	protected boolean createOriginalRef(int docID, int ref_u_id, int refWord, String urlStr) {
        return false;
    }

    protected boolean createOriginalRefs(Set pageRefs) {
        return false;
    }

    protected Set getStringSet(String sql,int count){
		Set set=new HashSet();
	       Statement stmt = null;
	        Connection con = DbConnectionManager.getConnection();
	        ResultSet rs=null;
	        String sql2=null;
	        try {
	        	if (count>0)
	        		sql2="select top "+count;
	        	else
	        		sql2="select";
	            sql2=sql2+sql;
	            stmt=con.createStatement();
	            rs=stmt.executeQuery(sql2);
	            while (rs.next()){
		            set.add(rs.getString(1));
	            }
	        } catch (SQLException sqlex) {
	        	log.error(sqlex);
	
	        } finally {
	            try {
	            	stmt.close();
	            	stmt = null;
	                con.close();
	                con = null;
	            } catch (Exception e) {
	            	log.error(e);
	                System.out.print(e.getMessage());
	            }
	        }
	        return set;   	
	}

    public Set<String> getStringSet(String sql){
		Set<String> set=new HashSet<String>();
	       Statement stmt = null;
	        Connection con = DbConnectionManager.getConnection();
	        ResultSet rs=null;
	        try {
	            stmt=con.createStatement();
	            rs=stmt.executeQuery(sql);
	            while (rs.next()){
		            set.add(rs.getString(1));
	            }
	        } catch (SQLException sqlex) {
	        	log.error(sqlex+sql);
	
	        } finally {
	            try {
	            	stmt.close();
	            	stmt = null;
	                con.close();
	                con = null;
	            } catch (Exception e) {
	            	log.error(e);
	                System.out.print(e.getMessage());
	            }
	        }
	        return set;   	
	}

	protected String[] getStringArray(String sql){
		Set set=getStringSet(sql);
		return (String[])set.toArray(new String[set.size()]);
	}

	protected Set getLongSet(String sql){
		Set set=new HashSet();
	       Statement stmt = null;
	        Connection con = DbConnectionManager.getConnection();
	        ResultSet rs=null;
	        try {
	            stmt=con.createStatement();
	            rs=stmt.executeQuery(sql);
	            while (rs.next()){
		            set.add(rs.getLong(1));
	            }
	        } catch (SQLException sqlex) {
	        	log.error(sqlex+sql);
	
	        } finally {
	            try {
	            	stmt.close();
	            	stmt = null;
	                con.close();
	                con = null;
	            } catch (Exception e) {
	            	log.error(e);
	                System.out.print(e.getMessage());
	            }
	        }
	        return set;   	
	}

	protected List getLongList(String sql){
		List list=new ArrayList();
	       Statement stmt = null;
	        Connection con = DbConnectionManager.getConnection();
	        ResultSet rs=null;
	        try {
	            stmt=con.createStatement();
	            rs=stmt.executeQuery(sql);
	            while (rs.next()){
	            	list.add(rs.getLong(1));
	            }
	        } catch (SQLException sqlex) {
	        	log.error(sqlex+sql);

	        } finally {
	            try {
	            	stmt.close();
	            	stmt = null;
	                con.close();
	                con = null;
	            } catch (Exception e) {
	            	log.error(e);
	                System.out.print(e.getMessage());
	            }
	        }
	        return list;   	
    }
    
	public int callStatementWithIntReturn(String sql){
	    Statement stmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    ResultSet rs = null;
	    int count=0;
	    try {
	        stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        if (rs.next())
	            count = rs.getInt(1);
	    } catch (SQLException sqlex) {
	    	log.error(sqlex);
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            stmt.close();
	            stmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	        	log.error(e);
	            e.printStackTrace();
	        }
	    }
	    return count;        
	}

	public long callStatementWithLongReturn(String sql){
	    Statement stmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    ResultSet rs = null;
	    long count=0;
	    try {
	        stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        if (rs.next())
	            count = rs.getLong(1);
	    } catch (SQLException sqlex) {
	    	log.error(sqlex);
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            stmt.close();
	            stmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	        	log.error(e);
	            e.printStackTrace();
	        }
	    }
	    return count;        
	}

	public String callStatementWithStringReturn(String sql){
		if (sql==null) return null;
		
	    Statement stmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    ResultSet rs = null;
	    String str=null;
	    try {
	        stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        if (rs.next())
	        	str = rs.getString(1);
	    } catch (SQLException sqlex) {
	    	log.error(sqlex);
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            stmt.close();
	            stmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	        	log.error(e);
	            e.printStackTrace();
	        }
	    }
	    return str;        
	}

	public int executeUpdateWithResultCount(String sp){
	    Statement stmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    try {
	        stmt = con.createStatement();
	        //在这里更新新ref的ref_u_id:
	        String updateRefUId = sp;
	        int updateId = stmt.executeUpdate(updateRefUId);
	        return updateId;
	    } catch (SQLException sqlex) {
	    	log.error(sqlex);
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            stmt.close();
	            stmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	        	log.error(e);
	            e.printStackTrace();
	        }
	    }
	    return 0;        
	}

	public boolean executeUpdateWithNullReturn(String sp){
	    Statement stmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    try {
	        stmt = con.createStatement();
	        //在这里更新新ref的ref_u_id:
	        String updateRefUId = sp;
	        int updateId = stmt.executeUpdate(updateRefUId);
	        return true;
	    } catch (SQLException sqlex) {
	    	log.error(sqlex);
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            stmt.close();
	            stmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	        	log.error(e);
	            e.printStackTrace();
	        }
	    }
	    return false;        
	}

}
