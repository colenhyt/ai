package box.db;

import java.util.Date;

public class Wxsite {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wxsite.id
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wxsite.siteid
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    private String siteid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wxsite.searchurl
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    private String searchurl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wxsite.status
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wxsite.crdate
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    private Date crdate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wxsite.id
     *
     * @return the value of wxsite.id
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wxsite.id
     *
     * @param id the value for wxsite.id
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wxsite.siteid
     *
     * @return the value of wxsite.siteid
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public String getSiteid() {
        return siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wxsite.siteid
     *
     * @param siteid the value for wxsite.siteid
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setSiteid(String siteid) {
        this.siteid = siteid == null ? null : siteid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wxsite.searchurl
     *
     * @return the value of wxsite.searchurl
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public String getSearchurl() {
        return searchurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wxsite.searchurl
     *
     * @param searchurl the value for wxsite.searchurl
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setSearchurl(String searchurl) {
        this.searchurl = searchurl == null ? null : searchurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wxsite.status
     *
     * @return the value of wxsite.status
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wxsite.status
     *
     * @param status the value for wxsite.status
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wxsite.crdate
     *
     * @return the value of wxsite.crdate
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public Date getCrdate() {
        return crdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wxsite.crdate
     *
     * @param crdate the value for wxsite.crdate
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setCrdate(Date crdate) {
        this.crdate = crdate;
    }
}