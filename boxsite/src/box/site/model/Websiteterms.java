package box.site.model;

public class Websiteterms {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column websiteterms.id
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column websiteterms.siteid
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    private Integer siteid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column websiteterms.term
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    private String term;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column websiteterms.termcount
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    private Integer termcount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column websiteterms.id
     *
     * @return the value of websiteterms.id
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column websiteterms.id
     *
     * @param id the value for websiteterms.id
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column websiteterms.siteid
     *
     * @return the value of websiteterms.siteid
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public Integer getSiteid() {
        return siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column websiteterms.siteid
     *
     * @param siteid the value for websiteterms.siteid
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column websiteterms.term
     *
     * @return the value of websiteterms.term
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public String getTerm() {
        return term;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column websiteterms.term
     *
     * @param term the value for websiteterms.term
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column websiteterms.termcount
     *
     * @return the value of websiteterms.termcount
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public Integer getTermcount() {
        return termcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column websiteterms.termcount
     *
     * @param termcount the value for websiteterms.termcount
     *
     * @mbggenerated Wed Jun 01 11:55:25 CST 2016
     */
    public void setTermcount(Integer termcount) {
        this.termcount = termcount;
    }
}