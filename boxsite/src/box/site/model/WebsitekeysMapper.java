package box.site.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WebsitekeysMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int countByExample(WebsitekeysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int deleteByExample(WebsitekeysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int insert(Websitekeys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int insertSelective(Websitekeys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    List<Websitekeys> selectByExample(WebsitekeysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    Websitekeys selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int updateByExampleSelective(@Param("record") Websitekeys record, @Param("example") WebsitekeysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int updateByExample(@Param("record") Websitekeys record, @Param("example") WebsitekeysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int updateByPrimaryKeySelective(Websitekeys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table websitekeys
     *
     * @mbggenerated Fri Apr 01 15:26:04 CST 2016
     */
    int updateByPrimaryKey(Websitekeys record);
}