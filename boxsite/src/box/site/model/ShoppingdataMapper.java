package box.site.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ShoppingdataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int countByExample(ShoppingdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int deleteByExample(ShoppingdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int insert(Shoppingdata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int insertSelective(Shoppingdata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    List<Shoppingdata> selectByExample(ShoppingdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    Shoppingdata selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int updateByExampleSelective(@Param("record") Shoppingdata record, @Param("example") ShoppingdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int updateByExample(@Param("record") Shoppingdata record, @Param("example") ShoppingdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int updateByPrimaryKeySelective(Shoppingdata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shoppingdata
     *
     * @mbggenerated Fri May 20 09:15:27 CST 2016
     */
    int updateByPrimaryKey(Shoppingdata record);
}