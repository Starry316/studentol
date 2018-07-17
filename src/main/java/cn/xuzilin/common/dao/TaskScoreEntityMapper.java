package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.TaskScoreEntity;

public interface TaskScoreEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskScoreEntity record);

    int insertSelective(TaskScoreEntity record);

    TaskScoreEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskScoreEntity record);

    int updateByPrimaryKey(TaskScoreEntity record);
}