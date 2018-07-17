package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.ApplicationEntity;

public interface ApplicationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationEntity record);

    int insertSelective(ApplicationEntity record);

    ApplicationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplicationEntity record);

    int updateByPrimaryKeyWithBLOBs(ApplicationEntity record);

    int updateByPrimaryKey(ApplicationEntity record);
}