package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.TaskEntity;
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

    @Select("SELECT * FROM task_score WHERE task_id = #{tid} AND local_sid = #{sid}")
    TaskScoreEntity selectByTidAndLocalSid(@Param("tid") int tid,@Param("sid")int localSid);
//    @Select("SELECT * From task_score WHERE intention = #{intention} AND local_sid = #{sid}")
//    TaskEntity selectByIntentionAndSid(@Param("intention") String intention, @Param("sid") int localSid);
}