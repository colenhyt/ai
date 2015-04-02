package easyshop.common.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import es.util.io.FileContentHelper;

public class MemoryAndDBConnCompareTest extends TestCase {
	public void testMemory(){
		Map set=new HashMap();
		for (int i=0;i<100000;i++){
			set.put(new Integer(i).toString(), "allenhuang");
		}
		
		long start=System.currentTimeMillis();
		for (int i=0;i<10000000;i++){//一千万:1500
			String a=(String)set.get("12345");
		}
		System.out.println("cost time:"+(System.currentTimeMillis()-start));
	}
	public void testDBConn(){
//		1k:use the same conn:2000;usin connection pool:4500;open a conn per time:12000
		long start=System.currentTimeMillis();
		for (int i=0;i<1000;i++){
		Connection conn=DbConnectionManager.getDirectConn();
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement("select mvname from moviesRs where mvid=?");
			pst.setInt(1,12345);
			rs=pst.executeQuery();
			if (rs.next()){
				String a=rs.getString(1);
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		}
		System.out.println("cost time:"+(System.currentTimeMillis()-start));
	}
	
	public void testReadFile(){
		//100k:10000;1k:170
		long start=System.currentTimeMillis();
		for (int i=0;i<1000;i++){
	        File file=new File("D:/movie.txt");
	        String content=FileContentHelper.getStringContent(file);
		}
		System.out.println("cost time:"+(System.currentTimeMillis()-start));
		
	}
}
