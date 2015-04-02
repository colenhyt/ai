package easyshop.model;

public class ReviewVote {
	public final static int VOTE_DIGG=1;
	public final static int VOTE_BURY=0;
	private long brid;
	private long mid;
	private boolean digg;
	public long getBrid() {
		return brid;
	}
	public void setBrid(long brid) {
		this.brid = brid;
	}
	public boolean isDigg() {
		return digg;
	}
	public void setDigg(boolean digg) {
		this.digg = digg;
	}
	public long getMid() {
		return mid;
	}
	public void setMid(long mid) {
		this.mid = mid;
	}
}
