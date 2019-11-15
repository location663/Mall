package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.CollectDO;
import com.wangdao.mall.bean.CollectDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectDOMapper {
    long countByExample(CollectDOExample example);

    int deleteByExample(CollectDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CollectDO record);

    int insertSelective(CollectDO record);

    List<CollectDO> selectByExample(CollectDOExample example);

    CollectDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CollectDO record, @Param("example") CollectDOExample example);

    int updateByExample(@Param("record") CollectDO record, @Param("example") CollectDOExample example);

    int updateByPrimaryKeySelective(CollectDO record);

    int updateByPrimaryKey(CollectDO record);
}