package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.TaskEntity;

public interface TaskEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskEntity record);

    int insertSelective(TaskEntity record);

    TaskEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskEntity record);

    int updateByPrimaryKey(TaskEntity record);
}