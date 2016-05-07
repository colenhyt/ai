package box.site.model;

import box.site.model.Website;
import box.site.model.WebsiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WebsiteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int countByExample(WebsiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int deleteByExample(WebsiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int deleteByPrimaryKey(Integer siteid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int insert(Website record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int insertSelective(Website record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    List<Website> selectByExample(WebsiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    Website selectByPrimaryKey(Integer siteid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int updateByExampleSelective(@Param("record") Website record, @Param("example") WebsiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int updateByExample(@Param("record") Website record, @Param("example") WebsiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int updateByPrimaryKeySelective(Website record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 07 09:59:17 CST 2016
     */
    int updateByPrimaryKey(Website record);
}