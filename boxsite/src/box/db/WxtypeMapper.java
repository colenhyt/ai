package box.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WxtypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int countByExample(WxtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int deleteByExample(WxtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int insert(Wxtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int insertSelective(Wxtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    List<Wxtype> selectByExample(WxtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    Wxtype selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int updateByExampleSelective(@Param("record") Wxtype record, @Param("example") WxtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int updateByExample(@Param("record") Wxtype record, @Param("example") WxtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int updateByPrimaryKeySelective(Wxtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtype
     *
     * @mbggenerated Sat Mar 28 14:57:52 CST 2015
     */
    int updateByPrimaryKey(Wxtype record);
}