package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ApplicationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationEntity record);

    int insertSelective(ApplicationEntity record);

    ApplicationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationEntity record);

    int updateByPrimaryKeyWithBLOBs(ApplicationEntity record);

    int updateByPrimaryKey(ApplicationEntity record);



    @Select("Select * from application where student_id = #{student_id}")
    ApplicationEntity selectBySid(@Param("student_id") String student_id);
    @Select("Select student_id from application where student_id = #{student_id}")
    ApplicationEntity SelectId(@Param("student_id") String student_id);

    @Update("Update application set intention=#{intention}, intention2 =#{intention2},introduction=#{introduction} where student_id = #{student_id}")
    void UpdateId(@Param("student_id") String student_id,@Param("intention") String intention,@Param("intention2") String intention2,
                  @Param("introduction") String introduction);
    @Select("Select intention,intention2,stage,stage2 from application where student_id = #{student_id}")
    ApplicationEntity SelectById(@Param("student_id") String student_id);

    @Select("Select student_id from student where campus = #{campus}")
    List<String> SelectByCampus(@Param("campus") String campus);

    @Select("Select student_id from application where student_id = #{student_id} and " +
            "((intention=#{intention} and stage=#{stage}) or (intention2=#{intention} and stage2=#{stage}))")
    String Select(@Param("student_id") String student_id,@Param("intention") String intention,@Param("stage") String stage);

    @Select("Select * from student where student_id = #{student_id}")
    StudentEntity StudentInfo(@Param("student_id") String student_id);

    @Select("Select * from student where id = #{id}")
    StudentEntity StudentInfoById(@Param("id") String id);

    @Select("Select * from application where student_id = #{student_id}")
    ApplicationEntity ApplicationInfo(@Param("student_id") String student_id);

    @Update("Update application set stage = #{stage} where student_id = #{student_id} and intention = #{intention}")
    void ChangeStage(@Param("student_id") String id,@Param("stage") String stage,@Param("intention") String intention);

    @Update("Update application set stage2=#{stage2} where student_id = #{student_id} and intention2 = #{intention2}")
    void ChangeStage2(@Param("student_id") String id,@Param("stage2") String stage2,@Param("intention2") String intention2);

}