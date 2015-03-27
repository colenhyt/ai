package cn.hd.util;

public class SqlUtil {

	/**
	 * 防止sql注入：替换危险字符
	 * @param sql
	 * @return
	 */
	public static String replace(String sql){
		if (sql!=null) {
		     sql=sql.replaceAll(";","");
		     sql=sql.replaceAll("&","");
		     sql=sql.replaceAll("<","");
		     sql=sql.replaceAll(">","");
		     sql=sql.replaceAll("'","");
		     sql=sql.replaceAll("--","");
		     sql=sql.replaceAll("/","");
		     sql=sql.replaceAll("%","");
		}
	    return sql;
	}
}
