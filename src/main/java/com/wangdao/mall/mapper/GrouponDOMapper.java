package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GrouponDO;
import com.wangdao.mall.bean.GrouponDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GrouponDOMapper {
    long countByExample(GrouponDOExample example);

    int deleteByExample(GrouponDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GrouponDO record);

    int insertSelective(GrouponDO record);

    List<GrouponDO> selectByExample(GrouponDOExample example);

    GrouponDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GrouponDO record, @Param("example") GrouponDOExample example);

    int updateByExample(@Param("record") GrouponDO record, @Param("example") GrouponDOExample example);

    int updateByPrimaryKeySelective(GrouponDO record);

    int updateByPrimaryKey(GrouponDO record);
}