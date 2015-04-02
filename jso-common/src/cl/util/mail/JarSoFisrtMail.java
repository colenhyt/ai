package cl.util.mail;
import org.apache.log4j.Logger;

import es.util.date.JavaDateHelper;



/*
 ** JSO1.0, by Allen Huang,2007-6-28
 */
public class JarSoFisrtMail {
	static Logger log = Logger.getLogger("JarSoRegisterMail.java");
	private String title;
	private String content;
	
	public JarSoFisrtMail(){
		title="找求购、请简索--欢迎加入简索资讯网";
		
		constructMail();
	}
	
	private void constructMail(){
		StringBuffer context=new StringBuffer();
		
		context.append("<a href=\"http://www.jarso.cn\"><img src=\"http://www.jarso.cn/images/logo-dan.gif\""+
				"border=\"0\" width=136 height=45 alt=\"jarso.cn\"></a><br>");
		
		context.append("<br><br>尊敬的用户:<br><br><br>");
		
		context.append("&nbsp;&nbsp;&nbsp;&nbsp;您好，很高兴的通知您<br><br><br>");
		context.append("<a href=\"http://www.jarso.cn/join\"><span style=\"color:red\">30秒免费注册</span></a>成为简索资讯网会员，就可以享受以下资讯：<br><br>");
		
		context.append("1> 求购搜索：在每天数千条求购信息库中，查找您所需要的求购信息，可以免费获取一部分商业机会；"+"<br>");
		context.append("2> 供应搜索：在每天数万条供应信息库中，查找您所需要的供应信息，可以免费获取全部信息；"+"<br>");
		context.append("3> 企业搜索：在数百万企业名录库中，查找您所需要的企业信息，可以免费获取全部信息，最重要的是可以看到企业的诚信标准;"+"<br>");
		context.append("4> 资讯搜索：在简索资讯网海量资讯库中，查收您所需要的资讯信息，把握市场价格和动态，提高企业的自身竞争力;"+"<br><br><br>");
		context.append("<hr size=1>");
		context.append("<br><br>简索资讯网特色功能介绍："+"<br><br>");
		context.append("1> 诚信指数：针对企业的诚信度，提供专业的指数参考。"+"<br>");
		context.append("2> 信息海量：无论供求、企业信息、资讯行情，只要独此浏览，省去了大面积浏览其他网站的时间，真正的变时间为金钱。"+"<br>");
		context.append("3> 行业分类：产品分类详细，更便于用户查找。"+"<br><br>");
		context.append("简索资讯网付费特色："+"<br><br>");
		context.append("1>	简索资讯打破了原来传统的B2B行业的收取高额会员费的模式，提供按需收费，减少了用户的成本，提高了竞争力。"+"<br>");
		context.append("2>	实行关键词排名策略，让宣传发挥到极致。"+"<br><br>");
		context.append("<hr size=1>");
		context.append("<br><br>感谢您对<a href=\"http://www.jarso.cn\">简索资讯网</a>的全力支持<br><br>");
		context.append("如果您在使用本站服务的过程中有任何问题/建议，请惠赐E-MAIL至：<a href=\"mailto:jarso@jarso.cn\">jarso@jarso.cn</a>. "+
				"详细信息请访问<a href=\"http://www.jarso.cn\">简索资讯网</a>，或直接联系本站：0755-21092186");
		context.append("  客服MSN :<span style=\"color:blue\">jarso@jarso.cn</span>   客服QQ:<span style=\"color:blue\">825067676</span>");
		context.append(""+"<br><br><br><br>");
		context.append("<hr size=1 width=100 align=left>");
		context.append("简索资讯网<br>");
		context.append(JavaDateHelper.getCurrentDate());
		content=context.toString();
	}
	
	public String getContent() {
		return content;
	}
	public String getTitle() {
		return title;
	}

	public String getFromName() {
		return "简索资讯";
	}	
}

