package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.TaskEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskEntity record);

    int insertSelective(TaskEntity record);

    TaskEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskEntity record);

    int updateByPrimaryKey(TaskEntity record);

    @Select("SELECT * From task WHERE intention = #{intention}")
    List<TaskEntity> selectByIntention(@Param("intention") String intention);
}