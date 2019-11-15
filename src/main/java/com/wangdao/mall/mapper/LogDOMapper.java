package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.LogDO;
import com.wangdao.mall.bean.LogDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogDOMapper {
    long countByExample(LogDOExample example);

    int deleteByExample(LogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogDO record);

    int insertSelective(LogDO record);

    List<LogDO> selectByExample(LogDOExample example);

    LogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogDO record, @Param("example") LogDOExample example);

    int updateByExample(@Param("record") LogDO record, @Param("example") LogDOExample example);

    int updateByPrimaryKeySelective(LogDO record);

    int updateByPrimaryKey(LogDO record);
}