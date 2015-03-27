package cn.hd.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>Title: MysqlToMssql.java</p>
 * 
 * <p>Description: 把mysql的备份文件中的insert table语句转成mssql可以用的</p>
 * 
 * <p>Date: Dec 7, 2012</p>
 * 
 * <p>Time: 8:12:42 PM</p>
 * 
 * <p>Copyright: 2012</p>
 * 
 * <p>Company: hd</p>
 * 
 * @author hd
 * @version 1.0
 * 
 * <p>============================================</p>
 * <p>Modification History
 * <p>Mender: </p>
 * <p>Date: </p>
 * <p>Reason: </p>
 * <p>============================================</p>
 */
public class MysqlToMssql {

	public static void tran(String filepath){
		File file=new File(filepath);
		if (file.exists()) {
			String content=FileUtil.readFile(file);
			content=content.replaceAll("`", "'");
			String tables[]={
					"'freecms_channel'('id','name','templet','contentTemplet','img','description','parId','site','url','state','orderNum','clickNum','navigation','pagemark')",
					"'freecms_func'('id','name','isOk','orderNum','url','parId')",
					"'freecms_info'('id','site','channel','title','shortTitle','titleColor','titleBlod','source','author','description','content','tags','img','url','attchs','addtime','templet','isTop','topEndTime','clickNum','addUser','state')",
					"'freecms_operbutton'('id','func','name','code','isOk','orderNum')",
					"'freecms_role_func'('id','roleId','funcId')",
					"'freecms_role_site'('id','roleid','siteid','siteadmin')",
					"'freecms_role_user'('id','roles','users')",
					"'freecms_roles'('id','name','isOk','adduser')",
					"'freecms_site'('id','name','siteDomain','sourcePath','copyright','recordCode','parId','state','url','indexTemplet','logo','orderNum','clickNum')",
					"'freecms_templet'('id','name','state','orderNum','useSites','useSiteNames','adduser')",
					"'freecms_unit'('id','name','parid','isOk','ordernum')",
					"'freecms_unit_user'('id','unit','users')",
					"'freecms_users'('id','loginName','roleNames','name','pwd','sex','birthday','tel','mobilephone','email','isOk','lastLoginTime','lastestLoginTime','loginFailNum','loginNum','addTime','unitNames','unitIds')"};
			for (int i = 0; i < tables.length; i++) {
				content=content.replace(tables[i], tables[i].replace("'", ""));
			}
			content=content.replaceAll("\\\\'", "''");
			FileUtil.writeFile(file, content);
		}
	}
	public static void main(String[] args) {
		tran("C:\\Documents and Settings\\Administrator\\桌面\\11freecms.sql");
	}
}
