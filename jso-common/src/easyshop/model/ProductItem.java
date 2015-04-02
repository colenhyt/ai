/*
 * Created on 2005-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import es.webref.model.PageRef;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductItem extends ShoppingItem  {
    
    private String oriName,oriPubName,translateAuthor,seriesName,kaiben,version,paperDesc;
    private int pageCount=-1,wordCount;
    private Date pubDate;
    private String isbn13;
	/**
     * @return 返回 commonPsImg。
     */
    public String getCommonPsImg() {
        return commonPsImg;
    }
    /**
     * @param commonPsImg 要设置的 commonPsImg。
     */
    public void setCommonPsImg(String commonPsImg) {
        this.commonPsImg = commonPsImg;
    }

    /**
     * @return 返回 priceImg。
     */
    public String getPriceImg() {
        return priceImg;
    }
    /**
     * @param priceImg 要设置的 priceImg。
     */
    public void setPriceImg(String priceImg) {
        this.priceImg = priceImg;
    }
    private String priceImg,commonPsImg;
    
	private Properties _props;
	private String smallImgUrl;
	private String psImg,vipPsImg,cPsImg;
	

    public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}

	public Properties getProperties() {
		return _props;
	}
	public String getKaiben() {
		return kaiben;
	}
	public void setKaiben(String kaiben) {
		this.kaiben = kaiben;
	}
	public String getOriName() {
		return oriName;
	}
	public void setOriName(String oriName) {
		this.oriName = oriName;
	}
	public String getOriPubName() {
		return oriPubName;
	}
	public void setOriPubName(String oriPubName) {
		this.oriPubName = oriPubName;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getTranslateAuthor() {
		return translateAuthor;
	}
	public void setTranslateAuthor(String translateAuthor) {
		this.translateAuthor = translateAuthor;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getPaperDesc() {
		return paperDesc;
	}
	public void setPaperDesc(String paperDesc) {
		this.paperDesc = paperDesc;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	   public String getCPsImg() {
	        return cPsImg;
	    }
	    public void setCPsImg(String psImg) {
	        cPsImg = psImg;
	    }
	    public String getPsImg() {
	        return psImg;
	    }
	    public void setPsImg(String psImg) {
	        this.psImg = psImg;
	    }
	    public String getVipPsImg() {
	        return vipPsImg;
	    }
	    public void setVipPsImg(String vipPsImg) {
	        this.vipPsImg = vipPsImg;
	    }
		public String getIsbn13() {
			return isbn13;
		}
		public void setIsbn13(String isbn13) {
			this.isbn13 = isbn13;
		}


}
