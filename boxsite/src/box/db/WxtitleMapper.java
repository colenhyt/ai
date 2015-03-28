package box.db;

import box.db.Wxtitle;
import box.db.WxtitleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxtitleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int countByExample(WxtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int deleteByExample(WxtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int insert(Wxtitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int insertSelective(Wxtitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    List<Wxtitle> selectByExample(WxtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    Wxtitle selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") Wxtitle record, @Param("example") WxtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int updateByExample(@Param("record") Wxtitle record, @Param("example") WxtitleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int updateByPrimaryKeySelective(Wxtitle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Sat Mar 28 15:12:17 CST 2015
     */
    int updateByPrimaryKey(Wxtitle record);
}