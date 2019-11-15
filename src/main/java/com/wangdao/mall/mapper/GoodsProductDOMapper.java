package com.wangdao.mall.mapper;

import com.wangdao.mall.bean.GoodsProductDO;
import com.wangdao.mall.bean.GoodsProductDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsProductDOMapper {
    long countByExample(GoodsProductDOExample example);

    int deleteByExample(GoodsProductDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsProductDO record);

    int insertSelective(GoodsProductDO record);

    List<GoodsProductDO> selectByExample(GoodsProductDOExample example);

    GoodsProductDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsProductDO record, @Param("example") GoodsProductDOExample example);

    int updateByExample(@Param("record") GoodsProductDO record, @Param("example") GoodsProductDOExample example);

    int updateByPrimaryKeySelective(GoodsProductDO record);

    int updateByPrimaryKey(GoodsProductDO record);
}
