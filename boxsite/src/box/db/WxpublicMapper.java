package box.db;

import box.db.Wxpublic;
import box.db.WxpublicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxpublicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int countByExample(WxpublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int deleteByExample(WxpublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int insert(Wxpublic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int insertSelective(Wxpublic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    List<Wxpublic> selectByExample(WxpublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    Wxpublic selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int updateByExampleSelective(@Param("record") Wxpublic record, @Param("example") WxpublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int updateByExample(@Param("record") Wxpublic record, @Param("example") WxpublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int updateByPrimaryKeySelective(Wxpublic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpublic
     *
     * @mbggenerated Sat Mar 28 12:07:11 CST 2015
     */
    int updateByPrimaryKey(Wxpublic record);
}