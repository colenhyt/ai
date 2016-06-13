package box.site.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaiduurlsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int countByExample(BaiduurlsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int deleteByExample(BaiduurlsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int deleteByPrimaryKey(Integer autoid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int insert(Baiduurls record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int insertSelective(Baiduurls record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    List<Baiduurls> selectByExample(BaiduurlsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    Baiduurls selectByPrimaryKey(Integer autoid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int updateByExampleSelective(@Param("record") Baiduurls record, @Param("example") BaiduurlsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int updateByExample(@Param("record") Baiduurls record, @Param("example") BaiduurlsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int updateByPrimaryKeySelective(Baiduurls record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table baiduurls
     *
     * @mbggenerated Fri Apr 01 17:34:16 CST 2016
     */
    int updateByPrimaryKey(Baiduurls record);
}