package easyshop.model;

import java.io.Serializable;
import java.util.Date;

public class ReviewItem implements Serializable{
	private long brid;
	private long bid;
	private String crDateStr;
	private Date crDate;
	private String content;
	private int diggs;
	private Member owner;
	private String title,author,oriUrlStr;
	private int stars;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getBid() {
		return bid;
	}
	public void setBid(long bid) {
		this.bid = bid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String context) {
		this.content = context;
	}
	public String getCrDateStr() {
		return crDateStr;
	}
	public void setCrDateStr(String crDateStr) {
		this.crDateStr = crDateStr;
	}
	public int getDiggs() {
		return diggs;
	}
	public void setDiggs(int diggs) {
		this.diggs = diggs;
	}
	public Member getOwner() {
		return owner;
	}
	public void setOwner(Member owner) {
		this.owner = owner;
	}
	public long getBrid() {
		return brid;
	}
	public void setBrid(long brid) {
		this.brid = brid;
	}
	public Date getCrDate() {
		return crDate;
	}
	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int starts) {
		this.stars = starts;
	}
	
	public boolean equals(Object obj){
		return this.hashCode()==obj.hashCode();
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		return "author:"+author+";date:"+crDate;
	}
	public String getOriUrlStr() {
		return oriUrlStr;
	}
	public void setOriUrlStr(String oriUrlStr) {
		this.oriUrlStr = oriUrlStr;
	}
}
