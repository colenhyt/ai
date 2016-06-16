package box.site.model;

public class User {
	private long sessionid;
	private String favos;		//文章收藏;
	private String sitekeys;	//关心的网站

	public String getFavos() {
		return favos;
	}

	public void setFavos(String favos) {
		this.favos = favos;
	}

	public String getSitekeys() {
		return sitekeys;
	}

	public void setSitekeys(String sitekeys) {
		this.sitekeys = sitekeys;
	}

	public long getSessionid() {
		return sessionid;
	}

	public void setSessionid(long sessionid) {
		this.sessionid = sessionid;
	}
	
}
