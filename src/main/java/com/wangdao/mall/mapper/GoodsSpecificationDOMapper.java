package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GoodsSpecificationDO;
import com.wangdao.mall.bean.GoodsSpecificationDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSpecificationDOMapper {
    long countByExample(GoodsSpecificationDOExample example);

    int deleteByExample(GoodsSpecificationDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSpecificationDO record);

    int insertSelective(GoodsSpecificationDO record);

    List<GoodsSpecificationDO> selectByExample(GoodsSpecificationDOExample example);

    GoodsSpecificationDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsSpecificationDO record, @Param("example") GoodsSpecificationDOExample example);

    int updateByExample(@Param("record") GoodsSpecificationDO record, @Param("example") GoodsSpecificationDOExample example);

    int updateByPrimaryKeySelective(GoodsSpecificationDO record);

    int updateByPrimaryKey(GoodsSpecificationDO record);
}