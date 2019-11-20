package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.SystemPermissionDO;
import com.wangdao.mall.bean.SystemPermissionDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemPermissionDOMapper {
    long countByExample(SystemPermissionDOExample example);

    int deleteByExample(SystemPermissionDOExample example);

    int deleteByPrimaryKey(Integer sId);

    int insert(SystemPermissionDO record);

    int insertSelective(SystemPermissionDO record);

    List<SystemPermissionDO> selectByExample(SystemPermissionDOExample example);

    SystemPermissionDO selectByPrimaryKey(Integer sId);

    int updateByExampleSelective(@Param("record") SystemPermissionDO record, @Param("example") SystemPermissionDOExample example);

    int updateByExample(@Param("record") SystemPermissionDO record, @Param("example") SystemPermissionDOExample example);

    int updateByPrimaryKeySelective(SystemPermissionDO record);

    int updateByPrimaryKey(SystemPermissionDO record);

}