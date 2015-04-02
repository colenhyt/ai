package es.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookRank implements Serializable{
	private String rankName,rankDesc,shortName,title;
	private List<String> isbns=new ArrayList<String>();
	private List<String> bids=new ArrayList<String>();
	private int pri=200;
	private int rid,bkrank,ddrank,joyorank,cprank;
	public String getRankDesc() {
		return rankDesc;
	}
	public void setRankDesc(String rankDesc) {
		this.rankDesc = rankDesc;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getCprank() {
		return cprank;
	}
	public void setCprank(int cprank) {
		this.cprank = cprank;
	}
	public int getDdrank() {
		return ddrank;
	}
	public void setDdrank(int ddrank) {
		this.ddrank = ddrank;
	}
	public int getJoyorank() {
		return joyorank;
	}
	public void setJoyorank(int joyorank) {
		this.joyorank = joyorank;
	}
	public int getBkrank() {
		return bkrank;
	}
	public void setBkrank(int bkrank) {
		this.bkrank = bkrank;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public List<String> getIsbns() {
		return isbns;
	}
	public List<String> getBids() {
		return bids;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPri() {
		return pri;
	}
	public void setPri(int pri) {
		this.pri = pri;
	}
}
