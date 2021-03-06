package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.TaskScoreEntity;
import org.apache.ibatis.annotations.Delete;
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
    @Select("select * from task_score where sid = #{sid}")
    List<TaskScoreEntity> getBySid(@Param("sid") int sid);

    /**
     * 获取某个报了个某个事业群的人的作业信息
     * @param sg
     * @param bg
     * @return
     */
    @Select("select * from task_score where sid = #{sid} and `group` >= #{sg} and `group` < #{bg}")
    List<TaskScoreEntity> getBySidAndGroup(@Param("sid")int sid,@Param("sg") int sg,@Param("bg") int bg);

    @Select("select * from task_score where `group` >= #{sg} and `group` < #{bg}")
    List<TaskScoreEntity> getByGroup(@Param("sg") int sg,@Param("bg") int bg);

    @Select("select * from task_score where sid = #{sid} and `group` = #{department}")
    TaskScoreEntity getBySidAndDepartment(@Param("sid")int sid,@Param("department") int department);

    @Delete("delete from  task_score where id = #{id}")
    void deleteById(@Param("id")int id);
}