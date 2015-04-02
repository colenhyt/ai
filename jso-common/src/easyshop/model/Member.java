/*
 * 创建日期 2007-2-28
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */

package easyshop.model;

import java.util.HashSet;
import java.util.Set;

import easyshop.common.dao.EasyDAOImpl;

/**
 * @author Philip Huang
 * 
 * 2007-2-28
 */

/** 
 * 会员对象类
 * */

public class Member {
	
	/** 会员名字ID */
	private long memberId=-1;
	
	/** 会员名字也是登陆名 */
	private String memberName;
	
	/** 会员名字登陆密码 */
	private String passwords;
	
	/** 会员名字EMAIL */
	private String email,imgName,blog;
	
	/** 会员名字介绍 */	
	private String recommend;
	
	private String contactor,tel,postCode,address,im;
	
    private Set<String> votesBids=new HashSet<String>();
	/**
	 * @return memberId
	 */
	public long getMemberId() {
		return memberId;
	}
	
	public Set<String> getVotesBids() {
		return votesBids;
	}
	
	EasyDAOImpl dao=new EasyDAOImpl();
	
	public boolean existBookVote(long bid){
		if (votesBids.contains(String.valueOf(bid)))
			return true;
		else{
			String sql="select wish from bookvotesrecord where bid="+bid+" and uid="+memberId;
			boolean b=dao.exist(sql);
			if (b) votesBids.add(String.valueOf(bid));
			return b;
		}
	}

	/**
	 * @param memberId 要设置的 memberId
	 */
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	

}
