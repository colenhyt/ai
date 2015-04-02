package es.webref.model;

public class PictureRef extends JSPageRef {
	private String imgUrl,sImgUrl,bImgUrl;

    public PictureRef(String urlStr){
    	super(urlStr);
    }
    
	public String getBImgUrl() {
		return bImgUrl;
	}

	public void setBImgUrl(String imgUrl) {
		bImgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSImgUrl() {
		return sImgUrl;
	}

	public void setSImgUrl(String imgUrl) {
		sImgUrl = imgUrl;
	}
	
	public int getTypeId() {
		return WebLink.TYPE_IMG;
	}
}
