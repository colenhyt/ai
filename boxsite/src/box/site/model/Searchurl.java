package box.site.model;

public class Searchurl {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column searchurl.ID
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column searchurl.url
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column searchurl.status
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column searchurl.ID
     *
     * @return the value of searchurl.ID
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column searchurl.ID
     *
     * @param id the value for searchurl.ID
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column searchurl.url
     *
     * @return the value of searchurl.url
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column searchurl.url
     *
     * @param url the value for searchurl.url
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column searchurl.status
     *
     * @return the value of searchurl.status
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column searchurl.status
     *
     * @param status the value for searchurl.status
     *
     * @mbggenerated Wed Dec 30 17:57:15 CST 2015
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}