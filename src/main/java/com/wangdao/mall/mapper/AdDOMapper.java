package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.AdDO;
import com.wangdao.mall.bean.AdDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdDOMapper {
    long countByExample(AdDOExample example);

    int deleteByExample(AdDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdDO record);

    int insertSelective(AdDO record);

    List<AdDO> selectByExample(AdDOExample example);

    AdDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdDO record, @Param("example") AdDOExample example);

    int updateByExample(@Param("record") AdDO record, @Param("example") AdDOExample example);

    int updateByPrimaryKeySelective(AdDO record);

    int updateByPrimaryKey(AdDO record);
}