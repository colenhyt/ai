package easyshop.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import es.util.url.URLStrFormattor;
import es.webref.model.JSPageRef;


public class UrlsDAOImpl extends EasyDAOImpl{

	protected Set<JSPageRef> getPageRefs(String sql) {
	    Set<JSPageRef> urls = new HashSet<JSPageRef>();
	    PreparedStatement pstmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    ResultSet rs = null;
	    try {
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            JSPageRef ref = new JSPageRef(URLStrFormattor.decode(rs.getString(1)));
	            ref.setUrlKey(rs.getString(2));
	            urls.add(ref);
	        }
	        return urls;
	    } catch (SQLException sqlex) {
	        log.error(sqlex);
	    } finally {
	        try {
	            rs.close();
	            rs = null;
	            pstmt.close();
	            pstmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	        	log.error(e);
	            System.out.print(e.getMessage());
	        }
	    }
	    return urls;
	}

}
