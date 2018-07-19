package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.TaskScoreEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskScoreEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskScoreEntity record);

    int insertSelective(TaskScoreEntity record);

    TaskScoreEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskScoreEntity record);

    int updateByPrimaryKey(TaskScoreEntity record);
    @Select("SELECT * FROM task_score WHERE sid = #{sid} AND group = #{intention}")
    TaskScoreEntity selectBySidAndIntention(@Param("sid") int sid ,@Param("intention") String intention);

    @Select("SELECT * FROM task_score WHERE sid = #{sid} ")
    List<TaskScoreEntity> selectBySid(@Param("sid") int sid );
}