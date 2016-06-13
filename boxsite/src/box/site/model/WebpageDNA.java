package box.site.model;

import java.util.Vector;

public class WebpageDNA {
	private Vector<String> domainUrls;
	private Vector<String> texts;
	private int textLength;
	
	public int getTextLength() {
		return textLength;
	}

	public void setTextLength(int textLength) {
		this.textLength = textLength;
	}

	public WebpageDNA(){
		domainUrls = new Vector<String>();
		texts = new Vector<String>();
	}
	
	public void addDomainUrl(String url){
		domainUrls.add(url);
	}
	
	public void addText(String text){
		texts.add(text);
	}	
}
