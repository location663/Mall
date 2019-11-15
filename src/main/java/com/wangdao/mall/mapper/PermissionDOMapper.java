package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.PermissionDO;
import com.wangdao.mall.bean.PermissionDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionDOMapper {
    long countByExample(PermissionDOExample example);

    int deleteByExample(PermissionDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PermissionDO record);

    int insertSelective(PermissionDO record);

    List<PermissionDO> selectByExample(PermissionDOExample example);

    PermissionDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByExample(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByPrimaryKeySelective(PermissionDO record);

    int updateByPrimaryKey(PermissionDO record);
}