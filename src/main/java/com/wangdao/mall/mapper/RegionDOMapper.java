package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.RegionDO;
import com.wangdao.mall.bean.RegionDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionDOMapper {
    long countByExample(RegionDOExample example);

    int deleteByExample(RegionDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionDO record);

    int insertSelective(RegionDO record);

    List<RegionDO> selectByExample(RegionDOExample example);

    RegionDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionDO record, @Param("example") RegionDOExample example);

    int updateByExample(@Param("record") RegionDO record, @Param("example") RegionDOExample example);

    int updateByPrimaryKeySelective(RegionDO record);

    int updateByPrimaryKey(RegionDO record);
}