package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.ApplicationEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ApplicationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationEntity record);

    int insertSelective(ApplicationEntity record);

    ApplicationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationEntity record);

    int updateByPrimaryKeyWithBLOBs(ApplicationEntity record);

    int updateByPrimaryKey(ApplicationEntity record);

    @Select("Select student_id from application where student_id = #{student_id}")
    ApplicationEntity SelectId(@Param("student_id") String student_id);

    @Update("Update application set intention=#{intention}, intention2 =#{intention2},introduction=#{introduction} where student_id = #{student_id}")
    void UpdateId(@Param("student_id") String student_id,@Param("intention") String intention,@Param("intention2") String intention2,
                               @Param("introduction") String introduction);
    @Select("Select intention,intention2,stage from application where student_id = #{student_id}")
    ApplicationEntity SelectById(@Param("student_id") String student_id);

}