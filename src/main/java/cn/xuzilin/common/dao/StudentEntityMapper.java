package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.StudentEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentEntity record);

    int insertSelective(StudentEntity record);

    StudentEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentEntity record);

    int updateByPrimaryKey(StudentEntity record);

    @Select("SELECT * FROM student WHERE  student_id = #{sno}")
    StudentEntity selectBySno(@Param("sno") String sno);

    @Select("SELECT * FROM student WHERE  id = #{sid} AND campus = #{campus}")
    StudentEntity selectBySidCampus(@Param("sid") int sid ,@Param("campus") String campus);

    @Select("SELECT * FROM student WHERE  student_name like \"\"#{sno}\"%\"")
    StudentEntity likeBySno(@Param("sno") String sno);
}